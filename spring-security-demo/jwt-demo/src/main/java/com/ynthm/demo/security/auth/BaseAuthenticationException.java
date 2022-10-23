package com.ynthm.demo.security.auth;

import com.ynthm.common.enums.ResultCode;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class BaseAuthenticationException extends AuthenticationException {

  private ResultCode resultCode;

  public BaseAuthenticationException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public ResultCode getResultCode() {
    return resultCode;
  }

  public void setResultCode(ResultCode resultCode) {
    this.resultCode = resultCode;
  }
}
