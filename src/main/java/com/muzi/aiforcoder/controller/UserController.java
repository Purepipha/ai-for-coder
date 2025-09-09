package com.muzi.aiforcoder.controller;

import com.muzi.aiforcoder.common.Result;
import com.muzi.aiforcoder.model.dto.UserLoginRequest;
import com.muzi.aiforcoder.model.dto.UserRegisterRequest;
import com.muzi.aiforcoder.model.vo.LoginUserVo;
import com.muzi.aiforcoder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return Result.ofSuccess(userService.getLogUser(request));
    }
}
