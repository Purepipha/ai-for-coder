package com.muzi.aiforcoder.constant;


import com.muzi.aiforcoder.model.enums.UserRoleEnum;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/8 - 22:03
 */

public interface UserConstant {

    /**
     * 用户登录状态
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = UserRoleEnum.USER.getValue();

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = UserRoleEnum.ADMIN.getValue();
}
