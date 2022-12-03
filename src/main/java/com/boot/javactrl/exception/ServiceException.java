package com.boot.javactrl.exception;

import lombok.Getter;

/**
 * @Author: Zhang Yuyue
 * @Date: 2022/10/28 23:18
 * @Project com.javaCtrl
 * 自定义异常
 **/
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}
