package com.muzi.aiforcoder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.muzi.aiforcoder.core.AiCodeGeneratorFacade;
import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.exception.ThrowUtils;
import com.muzi.aiforcoder.mapper.WebAppMapper;
import com.muzi.aiforcoder.model.dto.WebAppQueryRequest;
import com.muzi.aiforcoder.model.entity.User;
import com.muzi.aiforcoder.model.entity.WebApp;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;
import com.muzi.aiforcoder.model.vo.UserVo;
import com.muzi.aiforcoder.model.vo.WebAppVo;
import com.muzi.aiforcoder.service.UserService;
import com.muzi.aiforcoder.service.WebAppService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
@Service
public class WebAppServiceImpl extends ServiceImpl<WebAppMapper, WebApp> implements WebAppService {

    @Autowired
    private UserService userService;
    @Autowired
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Override
    public void validWebApp(WebApp webApp, boolean add) {
        if (webApp == null) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        String appName = webApp.getAppName();
        String initPrompt = webApp.getInitPrompt();
        // 创建时，提示词必须非空
        if (add && StrUtil.isBlank(initPrompt)) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "应用名称和提示词不能为空");
        }
        // 有参数则校验
        if (StrUtil.isNotBlank(appName) && appName.length() > 256) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "应用名称过长");
        }
        if (StrUtil.isNotBlank(initPrompt) && initPrompt.length() > 8192) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "提示词过长");
        }
    }

    @Override
    public WebAppVo getWebAppVo(WebApp webApp) {
        if (webApp == null) {
            return null;
        }
        WebAppVo webAppVo = new WebAppVo();
        BeanUtil.copyProperties(webApp, webAppVo);
        // 关联用户信息
        Long userId = webApp.getUserId();
        if (userId != null && userId > 0) {
            User user = userService.getById(userId);
            UserVo userVo = userService.transferToUserVo(user);
            webAppVo.setUser(userVo);
        }
        return webAppVo;
    }

    @Override
    public List<WebAppVo> getWebAppVoList(List<WebApp> webAppList) {
        if (CollectionUtils.isEmpty(webAppList)) {
            return new ArrayList<>();
        }

        // 获取用户信息
        Set<Long> userIdSet = webAppList.stream()
                .map(WebApp::getUserId)
                .collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet)
                .stream()
                .collect(Collectors.groupingBy(User::getId));

        // 填充信息
        return webAppList.stream().map(webApp -> {
            WebAppVo webAppVo = new WebAppVo();
            BeanUtil.copyProperties(webApp, webAppVo);
            Long userId = webApp.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            webAppVo.setUser(userService.transferToUserVo(user));
            return webAppVo;
        }).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper getQueryWrapper(WebAppQueryRequest webAppQueryRequest) {
        if (webAppQueryRequest == null) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = webAppQueryRequest.getId();
        String appName = webAppQueryRequest.getAppName();
        String codeGenType = webAppQueryRequest.getCodeGenType();
        Long userId = webAppQueryRequest.getUserId();
        Integer priority = webAppQueryRequest.getPriority();
        String sortField = webAppQueryRequest.getSortField();
        String sortOrder = webAppQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq(WebApp::getId, id)
                .likeLeft(WebApp::getAppName, appName)
                .eq(WebApp::getCodeGenType, codeGenType)
                .eq(WebApp::getUserId, userId)
                .eq(WebApp::getPriority, priority)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }

    @Override
    public Page<WebAppVo> listWebAppVoByPageForFeatured(WebAppQueryRequest webAppQueryRequest) {
        // 精选应用：按优先级降序排列
        int pageNum = webAppQueryRequest.getPageNum();
        int pageSize = Math.min(webAppQueryRequest.getPageSize(), 20);
        QueryWrapper queryWrapper = getQueryWrapper(webAppQueryRequest);
        queryWrapper.orderBy(WebApp::getPriority, false);
        Page<WebApp> webAppPage = this.page(Page.of(pageNum, pageSize), queryWrapper);
        Page<WebAppVo> webAppVoPage = new Page<>(pageNum, pageSize, webAppPage.getTotalRow());
        List<WebAppVo> webAppVoList = getWebAppVoList(webAppPage.getRecords());
        webAppVoPage.setRecords(webAppVoList);
        return webAppVoPage;
    }

    @Override
    public Page<WebAppVo> listWebAppVoByPageForUser(WebAppQueryRequest webAppQueryRequest, Long userId) {
        // 用户应用：只查询当前用户的应用
        int pageNum = webAppQueryRequest.getPageNum();
        int pageSize = Math.min(webAppQueryRequest.getPageSize(), 20);
        webAppQueryRequest.setUserId(userId);
        QueryWrapper queryWrapper = getQueryWrapper(webAppQueryRequest);
        Page<WebApp> webAppPage = this.page(Page.of(pageNum, pageSize), queryWrapper);
        Page<WebAppVo> webAppVoPage = new Page<>(pageNum, pageSize, webAppPage.getTotalRow());
        List<WebAppVo> webAppVoList = getWebAppVoList(webAppPage.getRecords());
        webAppVoPage.setRecords(webAppVoList);
        return webAppVoPage;
    }

    /**
     * 聊天代码
     *
     * @param appId     应用ID
     * @param message   信息
     * @param loginUser 登录用户
     * @return {@link Flux }<{@link String }>
     */
    @Override
    public Flux<ServerSentEvent<String>> chatToGenCode(Long appId, String message, User loginUser) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用id不正确");
        ThrowUtils.throwIf(StringUtils.isBlank(message), ErrorCode.PARAMS_ERROR, "用户消息不能为空");
        WebApp webApp = this.getById(appId);
        ThrowUtils.throwIf(webApp == null, ErrorCode.PARAMS_ERROR, "应用不存在");
        if (!webApp.getUserId().equals(loginUser.getId())) {
            throw new ServiceException(ErrorCode.NO_AUTH_ERROR, "无权限访问该应用");
        }
        CodeGenTypeEnum codeGenTypeEnum = CodeGenTypeEnum.getEnumByValue(webApp.getCodeGenType());
        if (Objects.isNull(codeGenTypeEnum)) {
            throw new ServiceException(ErrorCode.SYSTEM_ERROR, "不支持的代码生成类型");
        }
        return aiCodeGeneratorFacade.generateSaveCodeStream(message, codeGenTypeEnum, appId)
                .map(chunk -> {
                    Map<String, String> keyValueMap = Map.of("d", chunk);
                    String jsonData = JSONUtil.toJsonStr(keyValueMap);
                    return ServerSentEvent.<String>builder()
                            .data(jsonData)
                            .build();
                })
                .concatWith(Mono.just(
                        ServerSentEvent.<String>builder()
                                .event("done")
                                .data("")
                                .build()
                ));
    }
}
