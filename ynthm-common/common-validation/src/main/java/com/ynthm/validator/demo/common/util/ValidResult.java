package com.ynthm.validator.demo.common.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
public class ValidResult {

  /** 是否有错误 */
  private boolean hasErrors;

  /** 错误信息 */
  private List<ErrorMessage> errors;

  public ValidResult() {
    this.errors = new ArrayList<>();
  }

  public boolean hasErrors() {
    return hasErrors;
  }

  public void setHasErrors(boolean hasErrors) {
    this.hasErrors = hasErrors;
  }

  /**
   * 获取所有验证信息
   *
   * @return 集合形式
   */
  public List<ErrorMessage> getAllErrors() {
    return errors;
  }
  /**
   * 获取所有验证信息
   *
   * @return 字符串形式
   */
  public String getErrors() {
    return errors.stream()
        .map(i -> String.join(":", i.getPropertyPath(), i.getMessage()))
        .collect(Collectors.joining(";"));
  }

  public void addError(String propertyName, String message) {
    this.errors.add(new ErrorMessage(propertyName, message));
  }

  @Data
  public static class ErrorMessage {

    private String propertyPath;

    private String message;

    public ErrorMessage() {}

    public ErrorMessage(String propertyPath, String message) {
      this.propertyPath = propertyPath;
      this.message = message;
    }
  }
}
