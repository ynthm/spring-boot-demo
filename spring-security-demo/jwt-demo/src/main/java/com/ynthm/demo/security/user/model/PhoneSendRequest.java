package com.ynthm.demo.security.user.model;

import com.ynthm.validator.demo.common.validator.PhoneNumber;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送手机验证码请求
 *
 * @author ethan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PhoneSendRequest extends PhoneNumber {

  /**
   * 语言
   *
   * @mock zh-CN
   */
  private String lang;
}
