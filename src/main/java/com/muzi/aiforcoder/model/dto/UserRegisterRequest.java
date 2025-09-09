package com.muzi.aiforcoder.model.dto;


import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/7 - 10:53
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -2931740176977401036L;

    private String username;

    private String password;

    private String checkPassword;
}
