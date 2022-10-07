package com.ynthm.demo.spring.boot.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
  MALE(0, "Male"),
  FEMALE(1, "Female"),
  ;

  @JsonValue private final Integer value;
  private final String label;
}
