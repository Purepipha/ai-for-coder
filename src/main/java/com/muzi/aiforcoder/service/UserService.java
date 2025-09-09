package com.muzi.aiforcoder.service;

import com.muzi.aiforcoder.model.dto.UserQueryRequest;
import com.muzi.aiforcoder.model.entity.User;
import com.muzi.aiforcoder.model.vo.LoginUserVo;
import com.muzi.aiforcoder.model.vo.UserVo;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 *  服务层。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 获取加密密码
     *
     * @param userPassword 用户密码
     * @return {@link String }
     */
    String getEncryptPassword(String userPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户帐户
     * @param userPassword 用户密码
     * @param request 请求
     * @return {@link User }
     */
    LoginUserVo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request 要求
     * @return {@link Boolean }
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取日志用户
     *
     * @param request 要求
     * @return {@link LoginUserVo }
     */
    User getLogUser(HttpServletRequest request);

    /**
     * 转移到用户vo
     *
     * @param user 用户
     * @return {@link UserVo }
     */
    UserVo transferToUserVo(User user);

    /**
     * 转移用户VO列表
     *
     * @param userList 用户列表
     * @return {@link List }<{@link UserVo }>
     */
    List<UserVo> transferUserVoList(List<User> userList);

    /**
     * 获取查询包装器
     *
     * @param userQueryRequest 用户查询请求
     * @return {@link QueryWrapper }
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}
