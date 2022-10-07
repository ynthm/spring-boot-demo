package com.ynthm.demo.spring.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

/** @author Ynthm Wang */
@Slf4j
@SpringBootApplication
public class SpringBootDemoApplication {

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    SpringApplication.run(SpringBootDemoApplication.class, args);
  }

  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {
      log.info("##########  Application Running  ##########");
      log.info(
          "\uD83D\uDE00 \uD83D\uDE03 \uD83D\uDE04 \uD83D\uDE01 \uD83D\uDE06 \uD83D\uDE05 \uD83D\uDE02 \uD83E\uDD23 "
              + "\uD83E\uDD23 \uD83D\uDE02 \uD83D\uDE05 \uD83D\uDE06 \uD83D\uDE01 \uD83D\uDE04 \uD83D\uDE03 \uD83D\uDE00");
    };
  }
}
