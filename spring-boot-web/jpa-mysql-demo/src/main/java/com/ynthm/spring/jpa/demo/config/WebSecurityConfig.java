package com.ynthm.spring.jpa.demo.config;

import com.ynthm.spring.jpa.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 开启方法级别上的保护
 * https://www.funtl.com/zh/spring-security-oauth2/%E5%88%9B%E5%BB%BA%E8%B5%84%E6%BA%90%E6%9C%8D%E5%8A%A1%E5%99%A8%E6%A8%A1%E5%9D%97.html#%E8%AE%BF%E9%97%AE%E8%B5%84%E6%BA%90
 *
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午4:50
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest()
        .permitAll()
        // .authenticated() // 所有请求都需要安全验证
        .and()
        .csrf()
        .disable();

    http.userDetailsService(userService);

    // http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    return http.build();
  }
}
