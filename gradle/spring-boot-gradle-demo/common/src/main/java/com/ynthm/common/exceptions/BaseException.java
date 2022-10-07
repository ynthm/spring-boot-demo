package com.ynthm.common.exceptions;

import com.ynthm.common.ResultCode;
import lombok.Getter;

/** @author ethan */
@Getter
public class BaseException extends RuntimeException {
  private static final long serialVersionUID = 1112067081287601988L;
  private final ResultCode resultCode;
  private Object[] args;

  public BaseException(ResultCode resultCode) {
    this.resultCode = resultCode;
  }

  public BaseException(ResultCode resultCode, Throwable cause, Object... args) {
    super(resultCode.getMessage(), cause);
    this.args = args;
    this.resultCode = resultCode;
  }
}
