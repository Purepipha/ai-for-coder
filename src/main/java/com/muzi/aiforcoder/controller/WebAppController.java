package com.muzi.aiforcoder.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.muzi.aiforcoder.common.Result;
import com.muzi.aiforcoder.common.anotation.AuthCheck;
import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.exception.ThrowUtils;
import com.muzi.aiforcoder.model.dto.*;
import com.muzi.aiforcoder.model.entity.User;
import com.muzi.aiforcoder.model.entity.WebApp;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;
import com.muzi.aiforcoder.model.enums.UserRoleEnum;
import com.muzi.aiforcoder.model.vo.WebAppVo;
import com.muzi.aiforcoder.service.UserService;
import com.muzi.aiforcoder.service.WebAppService;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.muzi.aiforcoder.constant.UserConstant.ADMIN_ROLE;

/**
 * 控制层。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
@RestController
@RequestMapping("/webApp")
public class WebAppController {

    @Resource
    private WebAppService webAppService;

    @Resource
    private UserService userService;

    /**
     * 创建应用
     */
    @PostMapping("/add")
    public Result<Long> addWebApp(@RequestBody WebAppAddRequest webAppAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(webAppAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 获取当前登录用户
        User loginUser = userService.getLogUser(request);
        WebApp webApp = new WebApp();
        BeanUtil.copyProperties(webAppAddRequest, webApp);
        webApp.setUserId(loginUser.getId());
        webApp.setAppName(webAppAddRequest.getInitPrompt().substring(0,
                Math.min(webAppAddRequest.getInitPrompt().length(), 20)));
        webApp.setCodeGenType(CodeGenTypeEnum.MULTI_FILE.getValue());
        // 校验参数
        webAppService.validWebApp(webApp, true);
        boolean result = webAppService.save(webApp);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return Result.ofSuccess(webApp.getId());
    }

    /**
     * 用户修改自己的应用
     */
    @PostMapping("/update")
    public Result<Boolean> updateWebApp(@RequestBody WebAppUpdateRequest webAppUpdateRequest, 
                                       HttpServletRequest request) {
        if (webAppUpdateRequest == null || webAppUpdateRequest.getId() <= 0) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        // 获取当前登录用户
        User loginUser = userService.getLogUser(request);
        // 判断是否存在
        WebApp oldWebApp = webAppService.getById(webAppUpdateRequest.getId());
        ThrowUtils.throwIf(oldWebApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人可修改
        if (!oldWebApp.getUserId().equals(loginUser.getId())) {
            throw new ServiceException(ErrorCode.NO_AUTH_ERROR);
        }
        WebApp webApp = new WebApp();
        BeanUtil.copyProperties(webAppUpdateRequest, webApp);
        webApp.setEditTime(LocalDateTime.now());
        // 校验参数
        webAppService.validWebApp(webApp, false);
        boolean result = webAppService.updateById(webApp);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return Result.ofSuccess(true);
    }

    /**
     * 用户删除自己的应用
     */
    @PostMapping("/delete")
    public Result<Boolean> deleteWebApp(@RequestBody DeleteRequest deleteRequest, 
                                       HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        // 获取当前登录用户
        User loginUser = userService.getLogUser(request);
        // 判断是否存在
        WebApp oldWebApp = webAppService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(oldWebApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldWebApp.getUserId().equals(loginUser.getId()) && 
            !UserRoleEnum.ADMIN.getValue().equals(loginUser.getUserRole())) {
            throw new ServiceException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = webAppService.removeById(deleteRequest.getId());
        return Result.ofSuccess(result);
    }

    /**
     * 根据 id 获取应用详情
     */
    @GetMapping("/get/vo")
    public Result<WebAppVo> getWebAppVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        WebApp webApp = webAppService.getById(id);
        ThrowUtils.throwIf(webApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return Result.ofSuccess(webAppService.getWebAppVo(webApp));
    }

    /**
     * 分页获取当前用户应用列表
     */
    @PostMapping("/my/list/page/vo")
    public Result<Page<WebAppVo>> listMyWebAppVOByPage(@RequestBody WebAppQueryRequest webAppQueryRequest,
                                                       HttpServletRequest request) {
        ThrowUtils.throwIf(webAppQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 获取当前登录用户
        User loginUser = userService.getLogUser(request);
        Page<WebAppVo> webAppVOPage = webAppService.listWebAppVoByPageForUser(webAppQueryRequest, loginUser.getId());
        return Result.ofSuccess(webAppVOPage);
    }

    /**
     * 分页获取精选应用列表
     */
    @PostMapping("/list/page/vo/featured")
    public Result<Page<WebAppVo>> listWebAppVOByPageForFeatured(@RequestBody WebAppQueryRequest webAppQueryRequest) {
        ThrowUtils.throwIf(webAppQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<WebAppVo> webAppVOPage = webAppService.listWebAppVoByPageForFeatured(webAppQueryRequest);
        return Result.ofSuccess(webAppVOPage);
    }

    // region 管理员接口

    /**
     * 管理员删除应用
     */
    @PostMapping("/admin/delete")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<Boolean> deleteWebAppByAdmin(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = webAppService.removeById(deleteRequest.getId());
        return Result.ofSuccess(result);
    }

    /**
     * 管理员更新应用
     */
    @PostMapping("/admin/update")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<Boolean> updateWebAppByAdmin(@RequestBody WebAppAdminUpdateRequest webAppAdminUpdateRequest) {
        if (webAppAdminUpdateRequest == null || webAppAdminUpdateRequest.getId() <= 0) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        WebApp webApp = new WebApp();
        BeanUtil.copyProperties(webAppAdminUpdateRequest, webApp);
        // 校验参数
        webAppService.validWebApp(webApp, false);
        boolean result = webAppService.updateById(webApp);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return Result.ofSuccess(true);
    }

    /**
     * 管理员分页获取应用列表
     */
    @PostMapping("/admin/list/page/vo")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<Page<WebAppVo>> listWebAppVOByPageForAdmin(@RequestBody WebAppQueryRequest webAppQueryRequest) {
        ThrowUtils.throwIf(webAppQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long pageNum = webAppQueryRequest.getPageNum();
        long pageSize = webAppQueryRequest.getPageSize();
        Page<WebApp> webAppPage = webAppService.page(Page.of(pageNum, pageSize),
                webAppService.getQueryWrapper(webAppQueryRequest));
        // 获取封装类
        Page<WebAppVo> webAppVOPage = new Page<>(pageNum, pageSize, webAppPage.getTotalRow());
        webAppVOPage.setRecords(webAppService.getWebAppVoList(webAppPage.getRecords()));
        return Result.ofSuccess(webAppVOPage);
    }

    /**
     * 管理员根据 id 获取应用详情
     */
    @GetMapping("/admin/get")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<WebApp> getWebAppById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        WebApp webApp = webAppService.getById(id);
        ThrowUtils.throwIf(webApp == null, ErrorCode.NOT_FOUND_ERROR);
        return Result.ofSuccess(webApp);
    }
}
