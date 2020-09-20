package com.ynthm.services.account.request;

import com.ynthm.common.web.validator.FieldMatch;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 手机注册请求
 *
 * @author ethan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldMatch(
    first = "password",
    second = "confirmPassword",
    message = "{constraints.field.password.match}")
public class PhoneRegisterRequest extends Phone {

  /** 验证码 */
  @NotEmpty(message = "{user.nb.verification.code}")
  @Length(min = 6, max = 6)
  private String verificationCode;

  /** 密码 */
  @NotEmpty(message = "{user.nb.password}")
  @Size(min = 8, max = 25)
  private String password;
  /** 密码确认 */
  @NotNull
  @Size(min = 8, max = 25)
  private String confirmPassword;
}
