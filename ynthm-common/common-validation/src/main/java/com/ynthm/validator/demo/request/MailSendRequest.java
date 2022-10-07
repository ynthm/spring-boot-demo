package com.ynthm.validator.demo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送邮件验证码请求
 *
 * @author ethan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MailSendRequest extends Mail {
  /**
   * 语言
   *
   * @mock zh-CN
   */
  private String lang;
}
