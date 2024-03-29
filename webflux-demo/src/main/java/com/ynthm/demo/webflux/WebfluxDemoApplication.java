package com.ynthm.demo.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/** @author ynthm */
@EnableR2dbcRepositories
@SpringBootApplication
public class WebfluxDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebfluxDemoApplication.class, args);
  }
}
