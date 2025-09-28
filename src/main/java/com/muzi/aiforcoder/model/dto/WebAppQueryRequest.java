package com.muzi.aiforcoder.model.dto;

import com.muzi.aiforcoder.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用查询请求
 *
 * @author muzi
 * @date 2025/09/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WebAppQueryRequest extends PageRequest implements Serializable {

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
     * 代码生成类型
     */
    private String codeGenType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 优先级
     */
    private Integer priority;
}
