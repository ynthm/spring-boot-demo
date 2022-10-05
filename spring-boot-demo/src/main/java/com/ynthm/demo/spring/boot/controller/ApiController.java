package com.ynthm.demo.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@RestController
public class ApiController {

  @RequestMapping("/")
  public String home() {
    return "Hello Docker World";
  }
}
