package com.ynthm.common.redis.config;

import com.ynthm.common.redis.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 实现 WebMvcConfigurer 需要添加 @EnableWebMvc 可以存在多个WebMvcConfigurer
 *
 * <p>不需要返回逻辑视图 WebMvcConfigurationSupport
 *
 * @author Ethan Wang
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private AccessLimitInterceptor accessLimitInterceptor;

  @Autowired
  public void setAccessLimitInterceptor(AccessLimitInterceptor accessLimitInterceptor) {
    this.accessLimitInterceptor = accessLimitInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(accessLimitInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/static/**", "/login");
  }
}
