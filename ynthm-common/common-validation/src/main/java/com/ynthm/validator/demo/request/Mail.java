package com.ynthm.validator.demo.request;

import lombok.Data;

import javax.validation.constraints.Email;

/** @author ethan */
@Data
public class Mail {

  /** 电子邮箱地址 */
  @Email private String email;
}
