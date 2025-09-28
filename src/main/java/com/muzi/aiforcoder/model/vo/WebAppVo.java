package com.muzi.aiforcoder.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用视图对象
 *
 * @author muzi
 * @date 2025/09/27
 */
@Data
public class WebAppVo implements Serializable {

    /**
     * id
     */
    private Long id;

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

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部署唯一键
     */
    private String deployKey;

    /**
     * 部署时间
     */
    private LocalDateTime deployTime;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户信息
     */
    private UserVo user;

    private static final long serialVersionUID = 1L;
}
