package com.ynthm.services.account.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发送手机验证码请求
 *
 * @author ethan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PhoneSendRequest extends Phone {

  /**
   * 语言
   *
   * @mock zh-CN
   */
  private String lang;
}
