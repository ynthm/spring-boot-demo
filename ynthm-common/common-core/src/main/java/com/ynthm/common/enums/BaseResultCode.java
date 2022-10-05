package com.ynthm.common.enums;

import com.ynthm.common.exception.ResultExceptionAssert;

/**
 * 公共错误码
 *
 * <p>路径区分业务模块
 *
 * @author Ethan Wang
 */
public enum BaseResultCode implements ResultCode, ResultExceptionAssert {
  /** OK */
  OK(0, "OK"),
  ERROR(-1, "ERROR"),

  NULL(998, "系统错误 NULL"),
  UNKNOWN_ERROR(999, "Unknown Error!"),

  /** 权限部分 */
  AUTHENTICATION_EXCEPTION(1000, "权限异常"),
  SIGN_VERIFY_FAILED(1001, "签名验证失败"),
  UNAUTHORIZED(1002, "未登录，请先登录。"),
  BAD_CREDENTIALS(1003, "用户名或密码错误，请确认。"),
  WRONG_PASSWORD(1004, "密码错误，请确认。"),
  USERNAME_NOT_FOUND(1005, "用户名不存在。"),

  /** 用户输入 */
  VALID_ERROR(2000, "参数校验错误"),
  DB_NOT_EXIST(2001, "数据库不存在"),
  DB_EXIST(2002, "数据库已存在"),

  EXCEL_EXPORT_FAILED(10000, "Excel 导出异常"),

  /** 调用第三方服务发生错误 */
  THIRD_PART_SERVICE_ERROR(90000, "调用第三方服务发生错误"),

  S_AUTH_ERROR(100000, "AUTH服务错误"),
  S_INFRA_ERROR(110000, "INFRA服务错误"),
  S_SYSTEM_ERROR(120000, "USER服务错误"),
  S_ERP_ERROR(130000, "ERP错误"),
  S_CRM_ERROR(140000, "CRM错误"),
  S_WMS_ERROR(150000, "WMS错误"),
  S_SCM_ERROR(160000, "SCM错误"),
  ;

  private final int code;
  private final String message;

  BaseResultCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
