package com.muzi.aiforcoder.service;

import com.muzi.aiforcoder.model.dto.WebAppQueryRequest;
import com.muzi.aiforcoder.model.entity.User;
import com.muzi.aiforcoder.model.entity.WebApp;
import com.muzi.aiforcoder.model.vo.WebAppVo;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 服务层。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
public interface WebAppService extends IService<WebApp> {

    /**
     * 校验应用参数
     *
     * @param webApp 应用
     * @param add    是否为添加操作
     */
    void validWebApp(WebApp webApp, boolean add);

    /**
     * 转换为WebAppVo
     *
     * @param webApp 应用
     * @return WebAppVo
     */
    WebAppVo getWebAppVo(WebApp webApp);

    /**
     * 转换为WebAppVo列表
     *
     * @param webAppList 应用列表
     * @return WebAppVo列表
     */
    List<WebAppVo> getWebAppVoList(List<WebApp> webAppList);

    /**
     * 获取查询条件
     *
     * @param webAppQueryRequest 查询请求
     * @return 查询条件
     */
    QueryWrapper getQueryWrapper(WebAppQueryRequest webAppQueryRequest);

    /**
     * 分页获取精选应用列表
     *
     * @param webAppQueryRequest 查询请求
     * @return 分页结果
     */
    Page<WebAppVo> listWebAppVoByPageForFeatured(WebAppQueryRequest webAppQueryRequest);

    /**
     * 分页获取当前用户应用列表
     *
     * @param webAppQueryRequest 查询请求
     * @param userId            用户ID
     * @return 分页结果
     */
    Page<WebAppVo> listWebAppVoByPageForUser(WebAppQueryRequest webAppQueryRequest, Long userId);

    /**
     * 聊天代码
     *
     * @param appId     应用ID
     * @param message   信息
     * @param loginUser 登录用户
     * @return {@link Flux }<{@link String }>
     */
    Flux<ServerSentEvent<String>> chatToGenCode(Long appId, String message, User loginUser);
}
