package com.ynthm.common.domain;

import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.enums.ResultCode;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 响应信息主体
 *
 * @author Ethan Wang
 */
@Data
@Accessors(chain = true)
public class Result<T> {

  public static final String FIELD_CODE = "code";
  public static final String FIELD_MESSAGE = "msg";
  public static final String FIELD_DATA = "data";

  private int code;

  private Integer externalCode;

  private String msg;

  /**
   * 返回错误
   *
   * <p>当有第三方错误时，返回错误码
   */
  private T data;

  public static <T> Result<T> ok(T data) {
    return restResult(data, BaseResultCode.OK.getCode(), BaseResultCode.OK.getMessage());
  }

  public static <T> Result<T> ok() {
    return restResult(null, BaseResultCode.OK.getCode(), BaseResultCode.OK.getMessage());
  }

  public static <T> Result<T> warn(String message) {
    return restResult(null, BaseResultCode.WARN.getCode(), message);
  }

  public static <T> Result<T> error(ResultCode errorCode) {
    return restResult(null, errorCode.getCode(), errorCode.getMessage());
  }

  public static <T> Result<T> error(ResultCode errorCode, String message) {
    return restResult(null, errorCode.getCode(), message);
  }

  public static <T> Result<T> error(ResultCode errorCode, String message, T data) {
    return restResult(data, errorCode.getCode(), message);
  }

  public static <T> Result<T> restResult(T data, int code, String msg) {
    return restResult(data, code, null, msg);
  }

  public static <T> Result<T> restResult(
      T data, int code, Integer externalCode, String externalMsg) {
    Result<T> apiResult = new Result<>();
    apiResult.setCode(code);
    apiResult.setExternalCode(externalCode);
    apiResult.setData(data);
    apiResult.setMsg(externalMsg);
    return apiResult;
  }

  public boolean success() {
    return this.code == BaseResultCode.OK.getCode();
  }

  public static <T> Result<T> externalResult(Result<T> result, ResultCode externalErrorCode) {
    if (result.success()) {
      return Result.ok(result.getData());
    } else {
      return Result.restResult(
          result.getData(), externalErrorCode.getCode(), result.getCode(), result.getMsg());
    }
  }
}
