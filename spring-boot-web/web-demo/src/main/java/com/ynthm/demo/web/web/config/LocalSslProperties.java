package com.ynthm.demo.web.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("local.ssl")
public class LocalSslProperties {
  private String truststore;
  private String trustStorePassword;
}
