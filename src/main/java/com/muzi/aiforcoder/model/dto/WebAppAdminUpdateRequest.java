package com.muzi.aiforcoder.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用管理员更新请求
 *
 * @author muzi
 * @date 2025/09/27
 */
@Data
public class WebAppAdminUpdateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 封面
     */
    private String cover;

    /**
     * 优先级
     */
    private Integer priority;
}
