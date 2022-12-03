package com.boot.javactrl.config;

import com.boot.javactrl.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Zhang Yuyue
 * @Date: 2022/11/2 13:04
 * @Project com.javaCtrl
 **/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtlnterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/user/login", "/user/register", "/**/export", "/**/import","/file/**"); //排除文件上传 下载 登录 注册
    }

    @Bean
    public JwtInterceptor jwtlnterceptor() {
        return new JwtInterceptor();
    }
}
