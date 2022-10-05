package com.ynthm.aop.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.ynthm.aop.demo.mapper")
@SpringBootApplication
public class AopDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(AopDemoApplication.class, args);
  }
}
