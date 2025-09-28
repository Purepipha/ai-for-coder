package com.muzi.aiforcoder.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用添加请求
 *
 * @author muzi
 * @date 2025/09/27
 */
@Data
public class WebAppAddRequest implements Serializable {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用提示词
     */
    private String initPrompt;

    /**
     * 封面
     */
    private String cover;

    /**
     * 代码生成类型
     */
    private String codeGenType;

    private static final long serialVersionUID = 1L;
}
