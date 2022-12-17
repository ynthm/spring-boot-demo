package com.ynthm.demo.web.web.config;

import com.ynthm.demo.web.web.filter.OneFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean<OneFilter> oneFilter() {
    FilterRegistrationBean<OneFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new OneFilter());
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
