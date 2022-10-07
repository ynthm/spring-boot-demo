package com.ynthm.demo.security.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 生物性别
 *
 * @author Ynthm
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum SexEnum {
  /** 未设置 初始值 */
  UNKNOWN(0, "unknown", "未设置"),
  MALE(1, "male", "男性"),
  FEMALE(2, "female", "女性");

  /** 值 */
  private final int value;
  /** 另一个维度的编码值 */
  private final String code;
  /** 描述 */
  private final String desc;
}
