package com.ynthm.validator.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发送验证码维度
 *
 * @author ethan
 */
@Getter
@AllArgsConstructor
public enum CaptchaScopeEnum {
  /** 注册发送验证码 */
  REGISTER(0, "user:register:"),
  /** 修改密码发送 */
  CHANGE_PASSWORD(1, "change:password:"),
  ;

  @JsonValue private final int scope;
  private final String cacheCode;
}
