package com.ynthm.springbootdemo.config;

import com.ynthm.springbootdemo.security.CustomUserDetailsService;
import com.ynthm.springbootdemo.security.JwtAuthenticationEntryPoint;
import com.ynthm.springbootdemo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Ynthm Wang
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig {

  @Autowired private CustomUserDetailsService customUserDetailsService;

  @Autowired private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  //  @Bean
  //  public PasswordEncoder passwordEncoder() {
  //    return new BCryptPasswordEncoder();
  //  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        // 使用JWT 不需要 csrf
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        // 基于token，所以不需要session
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(
            "/",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js")
        .permitAll()
        .antMatchers("/api/auth/**")
        .permitAll()
        .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
        .permitAll()
        // 除上面的所有请求都需要鉴权认证
        .anyRequest()
        .authenticated();

    // Add our custom JWT security filter
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    http.userDetailsService(customUserDetailsService);

    // http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    return http.build();
  }
}
