package com.muzi.aiforcoder.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author muzi
 * @date 2025/09/09
 */
@Data
public class UserUpdateRequest extends UserAddRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -776261249518552310L;
    /**
     * id
     */
    private Long id;
}
