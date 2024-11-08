package com.ljh.handler;

import com.ljh.constant.MessageConstant;
import com.ljh.exception.BaseException;
import com.ljh.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result exceptionHandler(BaseException ex) {
        log.error("业务异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQL完整性约束异常
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        log.error("SQL异常信息：{}", message);

        // 检查是否是重复条目的错误
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String entry = split[2]; // 获取重复的值
            String msg = entry + MessageConstant.ID_ERROR;
            return Result.error(msg);
        } else {
            return Result.error("未知数据库错误，请联系管理员");
        }
    }

}
