package com.muzi.aiforcoder.exception;

import lombok.Getter;

/**
 * 服务异常
 *
 * @author muzi
 * @date 2025/8/27 - 23:02
 */
@Getter
public class ServiceException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    /**
     * 服务异常
     *
     * @param code    代码
     * @param message 信息
     */
    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 服务异常
     *
     * @param errorCode 错误代码
     */
    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }


    /**
     * 服务异常
     *
     * @param errorCode 错误代码
     * @param message   信息
     */
    public ServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}
