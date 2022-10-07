package com.ynthm.demo.mybatis.plus.enums;

import com.ynthm.common.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public enum BusinessCode implements ResultCode {
  OLD_NEW_PASSWORD_SAME(9900, "FAILED"),
  WRONG_PASSWORD(9901, "SUCCESS"),
  ;

  private final int code;
  private final String message;

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
