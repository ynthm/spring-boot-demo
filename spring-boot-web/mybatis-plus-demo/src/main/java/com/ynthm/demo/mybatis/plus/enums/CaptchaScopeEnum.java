package com.ynthm.demo.mybatis.plus.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ethan
 */
@Getter
@AllArgsConstructor
public enum CaptchaScopeEnum {
  /** 注册 */
  REGISTER(0, "u:reg:"),
  FORGET_PASSWORD(1, "forget:pwd:"),
  WITHDRAWAL_COIN(2, "withdrawal:coin:"),
  INVITATION_CUSTOMER(3, "invitation:customer:"),
  INVITATION_AGENT(4, "invitation:agent:");

  @JsonValue private final int scope;
  private final String cacheCode;
}
