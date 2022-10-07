package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.validator.PhoneNumber;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
public class RegisterRequest {
  @Valid private PhoneNumber phoneNumber;

  @NotEmpty(message = "{user.nb.password}")
  private String password;
}
