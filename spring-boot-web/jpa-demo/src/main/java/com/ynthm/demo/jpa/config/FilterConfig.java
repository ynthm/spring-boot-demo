package com.ynthm.demo.jpa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynthm.common.util.HmacHelper;
import com.ynthm.demo.jpa.common.filter.SignatureFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ethan Wang
 */
@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean someFilterRegistration(
      HmacHelper hmacHelper, ObjectMapper objectMapper) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new SignatureFilter(hmacHelper, objectMapper));
    registration.setName("signatureFilter");
    registration.addUrlPatterns("/local/*");
    return registration;
  }
}
