package com.ynthm.common.mybatis.plus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public enum Gender {
  /** 未知 */
  UNKNOWN(-1),
  FEMALE(0),
  MALE(1),
  ;

  @EnumValue private final int value;

  public static Gender getByValue(int value) {
    return Arrays.stream(Gender.values())
        .filter(i -> i.getValue() == value)
        .findFirst()
        .orElse(null);
  }
}
