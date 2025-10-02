package com.muzi.aiforcoder.model.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Web应用程序部署请求
 *
 * @author muzi
 * @date 2025/10/2 - 12:21
 */
@Data
public class WebAppDeployRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8295093763366689104L;
    /**
     * Web应用程序ID
     */
    private Long webAppId;
}
