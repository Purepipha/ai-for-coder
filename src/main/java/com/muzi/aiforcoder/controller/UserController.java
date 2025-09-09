package com.muzi.aiforcoder.controller;

import cn.hutool.core.bean.BeanUtil;
import com.muzi.aiforcoder.common.Result;
import com.muzi.aiforcoder.common.anotation.AuthCheck;
import com.muzi.aiforcoder.constant.UserConstant;
import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.exception.ThrowUtils;
import com.muzi.aiforcoder.model.dto.*;
import com.muzi.aiforcoder.model.entity.User;
import com.muzi.aiforcoder.model.vo.LoginUserVo;
import com.muzi.aiforcoder.model.vo.UserVo;
import com.muzi.aiforcoder.service.UserService;
import com.muzi.aiforcoder.service.impl.UserServiceImpl;
import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muzi.aiforcoder.constant.UserConstant.ADMIN_ROLE;

/**
 *  控制层。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return {@link Result }<{@link Long }>
     */
    @PostMapping("/register")
    Result<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        return Result.ofSuccess(userService.userRegister(username, password, checkPassword));
    }

    /**
     * 用户注册
     *
     * @param userLoginRequest 用户登录请求
     * @return {@link Result }<{@link Long }>
     */
    @PostMapping("/login")
    Result<LoginUserVo> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        return Result.ofSuccess(userService.userLogin(userAccount, userPassword, request));
    }

    /**
     * 用户注册
     *
     * @param request 要求
     * @return {@link Result }<{@link Long }>
     */
    @PostMapping("/logout")
    Result<Boolean> userLogout(HttpServletRequest request) {
        return Result.ofSuccess(userService.userLogout(request));
    }

    /**
     * 用户注册
     *
     * @param request 要求
     * @return {@link Result }<{@link Long }>
     */
    @PostMapping("/get")
    Result<LoginUserVo> getLogUser(HttpServletRequest request) {
        return Result.ofSuccess(UserServiceImpl.getLoginUserVo(userService.getLogUser(request)));
    }

    /**
     * 创建用户
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        ThrowUtils.throwIf(userAddRequest == null, ErrorCode.PARAMS_ERROR);
        User user = new User();
        BeanUtil.copyProperties(userAddRequest, user);
        // 默认密码 12345678
        final String DEFAULT_PASSWORD = "12345678";
        String encryptPassword = userService.getEncryptPassword(DEFAULT_PASSWORD);
        user.setUserPassword(encryptPassword);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return Result.ofSuccess(user.getId());
    }

    /**
     * 根据 id 获取用户（仅管理员）
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<User> getUserById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return Result.ofSuccess(user);
    }

    /**
     * 根据 id 获取包装类
     */
    @GetMapping("/get/vo")
    public Result<UserVo> getUserVOById(long id) {
        Result<User> response = getUserById(id);
        User user = response.getData();
        return Result.ofSuccess(userService.transferToUserVo(user));
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return Result.ofSuccess(b);
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new ServiceException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return Result.ofSuccess(true);
    }

    /**
     * 分页获取用户封装列表（仅管理员）
     *
     * @param userQueryRequest 查询请求参数
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public Result<Page<UserVo>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long pageNum = userQueryRequest.getPageNum();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(Page.of(pageNum, pageSize),
                userService.getQueryWrapper(userQueryRequest));
        // 数据脱敏
        Page<UserVo> userVOPage = new Page<>(pageNum, pageSize, userPage.getTotalRow());
        List<UserVo> userVOList = userService.transferUserVoList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return Result.ofSuccess(userVOPage);
    }

}
