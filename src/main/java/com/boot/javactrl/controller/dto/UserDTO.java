package com.boot.javactrl.controller.dto;

import lombok.Data;

/**
 * @Author: Zhang Yuyue
 * @Date: 2022/10/25 22:11
 * @Project com.javaCtrl
 * 接受前端登录请求参数
 **/
@Data
public class UserDTO{
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
