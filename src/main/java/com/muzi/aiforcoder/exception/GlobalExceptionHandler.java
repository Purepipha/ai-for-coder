package com.muzi.aiforcoder.exception;

import com.muzi.aiforcoder.common.Result;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全球异常处理程序
 *
 * @author muzi
 * @date 2025/8/27 - 23:16
 */
@Hidden
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 服务异常处理程序
     *
     * @param serviceException 服务例外
     * @return {@link Result }<{@link ? }>
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceExceptionHandler(ServiceException serviceException) {
        log.error("service exception", serviceException);
        return Result.ofSuccess(serviceException.getCode(), serviceException.getMessage());
    }
}
