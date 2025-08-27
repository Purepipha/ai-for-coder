package com.muzi.aiforcoder.common;

import com.muzi.aiforcoder.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 结果
 *
 * @author muzi
 * @version 1.0
 * @date 2023/7/27 - 22:47
 */

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 8132353405735583420L;

    private int code;

    private T data;

    private String message;

    private String description;

    /**
     * 结果
     *
     * @param code    代码
     * @param data    数据
     * @param message 消息
     */
    public Result(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 结果
     *
     * @param code        代码
     * @param data        数据
     * @param message     消息
     * @param description 描述
     */
    public Result(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(String message) {
        this.message = message;
    }

    public Result(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.data = null;
        this.message = errorCode.getMessage();
    }

    public Result(ErrorCode errorCode, String description) {
        this.code = errorCode.getCode();
        this.data = null;
        this.message = errorCode.getMessage();
        this.description = description;
    }


    /**
     * 成功
     *
     * @param data    数据
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofSuccess(T data, String message) {
        return new Result<T>(200, data, message);
    }

    /**
     * 成功
     *
     * @param message 消息
     * @param data    数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofSuccess(String message, T data) {
        return new Result<T>(200, data, message);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofSuccess(T data) {
        return new Result<T>(200, data);
    }

    /**
     * 失败
     *
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofFail(String message) {
        return new Result<T>(400, null, message);
    }

    /**
     * 失败
     *
     * @param data    数据
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofFail(T data, String message) {
        return new Result<T>(400, data, message);
    }

    /**
     * 失败
     *
     * @param code    代码
     * @param message 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofFail(int code, String message) {
        return new Result<T>(code, null, message);
    }

    /**
     * 失败
     *
     * @param code        代码
     * @param message     消息
     * @param description 描述
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofFail(int code, String message, String description) {
        return new Result<T>(code, null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode 错误代码
     * @return {@link Result}
     */
    public static <T> Result<T> ofFail(ErrorCode errorCode) {
        return new Result<>(errorCode);
    }

    /**
     * 失败
     *
     * @param errorCode   错误代码
     * @param description 描述
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> ofFail(ErrorCode errorCode, String description) {
        return new Result<>(errorCode, description);
    }
}
