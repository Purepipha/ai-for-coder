package com.muzi.aiforcoder.model.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/9 - 21:51
 */
@Data
public class DeleteRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1684697651552524872L;

    private Long id;
}
