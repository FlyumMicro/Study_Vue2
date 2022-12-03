package com.boot.javactrl.exception;

import com.boot.javactrl.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Zhang Yuyue
 * @Date: 2022/10/28 23:16
 * @Project se 业务异常
 * @return Result
 **/

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }
}
