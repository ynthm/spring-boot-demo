package com.ynthm.demo.web.web.controller;

import com.ynthm.common.domain.Result;
import com.ynthm.demo.web.web.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@SpringBootTest
class FeignClientDemoTest {

  @Autowired private FeignClientDemo feignClientDemo;

  @Test
  void test1() {

    Result<User> test = feignClientDemo.test();
    System.out.println(test);
  }
}
