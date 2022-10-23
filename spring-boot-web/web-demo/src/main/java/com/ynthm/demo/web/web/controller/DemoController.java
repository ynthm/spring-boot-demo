package com.ynthm.demo.web.web.controller;

import com.ynthm.demo.web.web.domain.TimeTestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@RestController
public class DemoController {
  @GetMapping("/time")
  public TimeTestResponse get() {
    Instant now = Instant.now();
    LocalDateTime localDateTime = LocalDateTime.ofInstant(now, TimeZone.getDefault().toZoneId());
    TimeTestResponse timeTestResponse =
        new TimeTestResponse()
            .setInstant(now)
            .setLocalDateTime(localDateTime)
            .setLocalDate(localDateTime.toLocalDate())
            .setLocalTime(localDateTime.toLocalTime())
            .setDate(new Date(now.toEpochMilli()))
            .setOffsetDateTime(OffsetDateTime.ofInstant(now, TimeZone.getDefault().toZoneId()))
            .setOffsetTime(OffsetTime.ofInstant(now, TimeZone.getDefault().toZoneId()))
            .setZonedDateTime(ZonedDateTime.ofInstant(now, TimeZone.getDefault().toZoneId()))
            .setTimeZone(TimeZone.getDefault());
    System.out.println(timeTestResponse);

    return timeTestResponse;
  }

  @PostMapping("/time")
  public void post(@RequestBody TimeTestResponse timeTestResponse) {
    System.out.println(timeTestResponse);
  }

  private FeignClientDemo feignClientDemo;

  @Autowired
  public void setFeignClientDemo(FeignClientDemo feignClientDemo) {
    this.feignClientDemo = feignClientDemo;
  }

  private FeignClientHttps feignClientHttps;

  @Autowired
  public void setFeignClientHttps(FeignClientHttps feignClientHttps) {
    this.feignClientHttps = feignClientHttps;
  }

  @GetMapping("/feignclient/test")
  public void feign() {
    feignClientDemo.test();
  }

  @GetMapping("/feignclient/https")
  public void feign2() {
    feignClientHttps.test();
  }
}
