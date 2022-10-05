package com.ynthm.spring.jpa.demo.exceptions;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午1:03
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
