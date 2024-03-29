package com.ynthm.demo.security.user.model;

import com.ynthm.validator.demo.common.validator.PhoneNumber;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 忘记密码请求
 *
 * @author ethan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ForgetPasswordRequest extends PhoneNumber {

  @NotEmpty(message = "{user.nb.password}")
  @Length(min = 8, max = 128, message = "密码最短8位到128位")
  private String newPassword;

  @NotBlank(message = "{user.nb.verification.code}")
  private String verificationCode;
}
