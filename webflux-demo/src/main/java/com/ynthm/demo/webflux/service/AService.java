package com.ynthm.demo.webflux.service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
public class AService extends BaseService {
  @Override
  public void doSomething(List<String> list) {
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    list.forEach(e -> log.info("aaaa"));
  }
}
