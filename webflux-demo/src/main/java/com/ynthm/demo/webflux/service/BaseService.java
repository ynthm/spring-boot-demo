package com.ynthm.demo.webflux.service;

import java.util.List;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public abstract class BaseService {
  private List<String> list;

  public abstract void doSomething(List<String> list);
}
