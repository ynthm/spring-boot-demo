package com.ynthm.common.web.exception;

import com.ynthm.common.domain.Result;

/**
 * @author Ethan Wang
 */
public class CarryDataException extends RuntimeException {
  private final Result<Object> result;

  public CarryDataException(Result<Object> result) {
    super(result.getMsg());
    this.result = result;
  }

  public CarryDataException(Result<Object> result, Throwable cause) {
    super(result.getMsg(), cause);
    this.result = result;
  }

  public Result<Object> getResult() {
    return result;
  }
}
