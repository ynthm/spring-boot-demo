package com.ynthm.demo.resource.isolate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author ethan */
@Slf4j
@RestController
public class ApiController {

  @GetMapping("/hello")
  public String test() {
    return "Hello";
  }
}
