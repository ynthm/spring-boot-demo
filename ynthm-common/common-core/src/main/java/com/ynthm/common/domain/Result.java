package com.ynthm.common.domain;

import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.enums.ResultCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Function;

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
    return of(data, BaseResultCode.OK.getCode(), BaseResultCode.OK.getMessage());
  }

  public static <T> Result<T> ok() {
    return of(null, BaseResultCode.OK.getCode(), BaseResultCode.OK.getMessage());
  }

  public static <T> Result<T> warn(String message) {
    return of(null, BaseResultCode.WARN.getCode(), message);
  }

  public static <T> Result<T> error(ResultCode errorCode) {
    return of(null, errorCode.getCode(), errorCode.getMessage());
  }

  public static <T> Result<T> error(ResultCode errorCode, String message) {
    return of(null, errorCode.getCode(), message);
  }

  public static <T> Result<T> error(ResultCode errorCode, String message, T data) {
    return of(data, errorCode.getCode(), message);
  }

  public static <T> Result<T> of(T data, int code, String msg) {
    return of(data, code, null, msg);
  }

  public static <T> Result<T> of(T data, int code, Integer externalCode, String externalMsg) {
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

  public static <T, S> Result<S> externalResult(Result<T> first, Result<S> second) {
    if (first.success()) {
      return second;
    } else {
      return Result.of(null, first.getCode(), first.getMsg());
    }
  }

  public static <T, R> Result<T> thenApply(Result<R> result, Function<R, T> function) {
    if (!result.success()) {
      return Result.of(null, result.getCode(), result.getMsg());
    }

    return Result.ok(function.apply(result.getData()));
  }
}
