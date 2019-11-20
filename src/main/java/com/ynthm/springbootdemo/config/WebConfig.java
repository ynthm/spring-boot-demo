package com.ynthm.springbootdemo.config;

import com.ynthm.springbootdemo.handler.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author : Ynthm
 * WebMvcConfigurationSupport 冲突
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截路径和排除拦截路径
        registry.addInterceptor(userInterceptor).addPathPatterns("/**");
    }

}
