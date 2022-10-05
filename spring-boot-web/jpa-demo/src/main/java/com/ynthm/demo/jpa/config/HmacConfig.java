package com.ynthm.demo.jpa.config;

import com.ynthm.common.enums.HmacAlgorithm;
import com.ynthm.common.util.HmacHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ethan Wang
 */
@Configuration
public class HmacConfig {

  @Bean
  public HmacHelper hmacHelper(LocalServiceProperties localServiceProperties) {
    return new HmacHelper(localServiceProperties.getSecret(), HmacAlgorithm.HMAC_SHA256);
  }
}
