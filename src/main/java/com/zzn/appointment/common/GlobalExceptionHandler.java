package com.zzn.appointment.common;

import com.zzn.appointment.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("系统异常：", e);
        return ApiResponse.error(500, "系统繁忙，请稍后再试");
    }

    /**
     * 处理 IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("参数错误：{}", e.getMessage());
        return ApiResponse.error(400, e.getMessage());
    }

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        log.warn("业务异常：{} - {}", e.getCode(), e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理 JWT 相关异常
     */
    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    public ApiResponse<?> handleJwtException(io.jsonwebtoken.JwtException e) {
        log.warn("Token 无效：{}", e.getMessage());
        return ApiResponse.error(401, "登录已过期，请重新登录");
    }

    /**
     * 处理数据库约束冲突异常
     */
    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
    public ApiResponse<?> handleDuplicateKeyException(org.springframework.dao.DuplicateKeyException e) {
        log.warn("数据重复：{}", e.getMessage());
        return ApiResponse.error(400, "数据已存在");
    }
}
