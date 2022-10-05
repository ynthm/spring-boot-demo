package com.ynthm.demo.jpa.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 局域网外部服务配置
 *
 * @author Ethan Wang
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("local")
public class LocalServiceProperties {
  private String oldRootUrl;
  private String newRootUrl;
  private String secret;
}
