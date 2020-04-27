package com.ynthm.springbootdemo.exception;

/** Author : Ynthm */
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private ErrorCode errorCode;
  private Object[] params;

  public ApiException(ErrorCode errorCode, Object... params) {
    this.errorCode = errorCode;
    this.params = params;
  }

  public ApiException(ErrorCode errorCode, Throwable e, Object... params) {
    super(e);
    this.errorCode = errorCode;
    this.params = params;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public Object[] getParams() {
    return params;
  }

  public void setParams(Object[] params) {
    this.params = params;
  }
}
