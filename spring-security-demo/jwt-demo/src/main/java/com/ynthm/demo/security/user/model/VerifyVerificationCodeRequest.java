package com.ynthm.demo.security.user.model;

import com.ynthm.validator.demo.common.validator.PhoneNumber;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 校验验证码请求
 *
 * @author ethan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VerifyVerificationCodeRequest extends PhoneNumber {
  /** 验证码 */
  @NotEmpty(message = "{user.nb.verification.code}")
  @Length(min = 6, max = 6)
  private String verificationCode;
}
