package com.boot.javactrl.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Zhang Yuyue
 * @Date: 2022/10/28 22:42
 * @Project com.javaCtrl
 * 登录结果
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Result {

    private String code; // code 登录请求
    private String msg;  // msg 请求原因
    private Object data; // 后台需要的数据


    // 有数据的成功 方法
    public static Result success(){
        return new Result(Constants.CODE_200, "请求成功",null);
    }

    // 没有数据的成功 方法
    public static Result success(Object data){
        return new Result(Constants.CODE_200, "请求成功",data);
    }

    // 请求失败
    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }
}
