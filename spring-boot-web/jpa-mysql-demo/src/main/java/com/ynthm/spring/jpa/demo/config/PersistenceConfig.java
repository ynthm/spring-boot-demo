package com.ynthm.spring.jpa.demo.config;

import com.ynthm.spring.jpa.demo.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午2:44
 */
@Configuration
@EnableTransactionManagement
// @EnableJpaRepositories(basePackages = "com.ynthm.spring.jpa.demo.user.entity")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfig {

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }
}
