package com.ynthm.demo.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Ethan Wang
 */
@Slf4j
@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class CrossLoginApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrossLoginApplication.class, args);
  }

  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {
      log.info("=====================(♥◠‿◠)ﾉﾞ  隐藏模块启动成功   ლ(´ڡ`ლ)ﾞ =============================");
    };
  }
}
