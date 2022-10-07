package com.ynthm.demo.security.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author Ethan Wang
 * @version 1.0
 */
@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {

    return builder
        .setConnectTimeout(Duration.ofMillis(3000))
        .setReadTimeout(Duration.ofMillis(3000))
        .build();
  }
}
