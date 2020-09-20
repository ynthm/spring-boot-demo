package com.ynthm.services.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/** @author ethan */
@SpringBootApplication
@ComponentScan({"com.ynthm.common", "com.ynthm.services"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
