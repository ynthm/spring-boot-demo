package com.ynthm.demo.web.web.controller;

import com.ynthm.common.domain.Result;
import com.ynthm.demo.web.web.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@FeignClient(name = "infra", url = "https://localhost:8803")
public interface FeignClientDemo {

  @GetMapping("/users/test")
  Result<User> test();
}
