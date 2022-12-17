package com.ynthm.demo.web.web.event;

import lombok.Getter;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Getter
public class GenericSpringEvent<T> {
  private T what;
  protected boolean success;

  public GenericSpringEvent(T what, boolean success) {
    this.what = what;
    this.success = success;
  }
}
