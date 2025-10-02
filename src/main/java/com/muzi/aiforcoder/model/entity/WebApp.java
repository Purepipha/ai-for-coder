package com.muzi.aiforcoder.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

/**
 *  实体类。
 *
 * @author <a href="https://github.com/Purepipha">muzi</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("web_app")
public class WebApp implements Serializable {

    @Serial
    private static final long serialVersionUID = 5034989643251099887L;

    /**
     * id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
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
    private LocalDateTime editTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @Column(isLogicDelete = true)
    private Boolean isDelete;

}
