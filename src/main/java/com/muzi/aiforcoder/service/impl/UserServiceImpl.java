package com.muzi.aiforcoder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.mapper.UserMapper;
import com.muzi.aiforcoder.model.dto.UserQueryRequest;
import com.muzi.aiforcoder.model.entity.User;
import com.muzi.aiforcoder.model.enums.UserRoleEnum;
import com.muzi.aiforcoder.model.vo.LoginUserVo;
import com.muzi.aiforcoder.model.vo.UserVo;
import com.muzi.aiforcoder.service.UserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.muzi.aiforcoder.constant.UserConstant.USER_LOGIN_STATE;

/**
 *  服务层实现。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{

    /**
     * 用户注册
     *
     * @param userAccount   用户帐户
     * @param userPassword  用户密码
     * @param checkPassword 检查密码
     * @return {@link Long }
     */
    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 2. 检查是否重复
        QueryWrapper queryWrapper = this.query().eq(User::getUserAccount, userAccount);
        long count = this.mapper.selectCountByQuery(queryWrapper);
        if (count > 0) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 3. 加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 4. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new ServiceException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        return user.getId();
    }

    /**
     * 获取加密密码
     *
     * @param userPassword 用户密码
     * @return {@link String }
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "muzi";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户帐户
     * @param userPassword 用户密码
     * @param request      要求
     * @return {@link LoginUserVo }
     */
    @Override
    public LoginUserVo userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "用户账号错误");
        }
        if (userPassword.length() < 8) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "用户密码错误");
        }
        String encryptPassword = getEncryptPassword(userPassword);
        QueryWrapper queryWrapper = this.query().eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptPassword);
        User loginUser = this.getOne(queryWrapper);
        if (Objects.isNull(loginUser)) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "用户账号或密码错误");
        }
        LoginUserVo loginUserVO = getLoginUserVo(loginUser);
        request.getSession().setAttribute("loginUser", loginUserVO);
        return loginUserVO;
    }

    /**
     * 用户登出
     *
     * @param request 请求
     * @return {@link Boolean }
     */
    @Override
    public Boolean userLogout(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (Objects.isNull(userObj)) {
            throw new ServiceException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    /**
     * 获取日志用户
     *
     * @param request 要求
     * @return {@link LoginUserVo }
     */
    @Override
    public User getLogUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (Objects.isNull(userObj)) {
            throw new ServiceException(ErrorCode.NOT_LOGIN_ERROR);
        }
        LoginUserVo loginUserVo = (LoginUserVo) userObj;
        return this.getById(loginUserVo.getId());
    }

    /**
     * 转移到用户vo
     *
     * @param user 用户
     * @return {@link UserVo }
     */
    @Override
    public UserVo transferToUserVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user, userVo);
        return userVo;
    }

    /**
     * 转移用户VO列表
     *
     * @param userList 用户列表
     * @return {@link List }<{@link UserVo }>
     */
    @Override
    public List<UserVo> transferUserVoList(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::transferToUserVo).toList();
    }

    /**
     * 获取查询包装器
     *
     * @param userQueryRequest 用户查询请求
     * @return {@link QueryWrapper }
     */
    @Override
    public QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq(User::getId, id)
                .eq(User::getUserRole, userRole)
                .like(User::getUserAccount, userAccount)
                .like(User::getUserName, userName)
                .like(User::getUserProfile, userProfile)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }


    /**
     * 获取登录用户vo
     *
     * @param loginUser 登录用户
     * @return {@link LoginUserVo }
     */
    public static LoginUserVo getLoginUserVo(User loginUser) {
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(loginUser.getId());
        loginUserVo.setUserAccount(loginUser.getUserAccount());
        loginUserVo.setUserName(loginUser.getUserName());
        loginUserVo.setUserAvatar(loginUser.getUserAvatar());
        loginUserVo.setUserProfile(loginUser.getUserProfile());
        loginUserVo.setUserRole(loginUser.getUserRole());
        loginUserVo.setCreateTime(loginUser.getCreateTime());
        loginUserVo.setUpdateTime(loginUser.getUpdateTime());
        return loginUserVo;
    }
}
