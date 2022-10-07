package com.ynthm.demo.spring.jdbc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum Status {
  /** 禁用 */
  DISABLE,
  ENABLE,
  ;

  public static Gender getByValue(int value) {
    return Arrays.stream(Gender.values())
        .filter(i -> i.ordinal() == value)
        .findFirst()
        .orElse(null);
  }
}
