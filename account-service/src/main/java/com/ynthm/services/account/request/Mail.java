package com.ynthm.services.account.request;

import lombok.Data;

import javax.validation.constraints.Email;

/** @author ethan */
@Data
public class Mail {

  /** 电子邮箱地址 */
  @Email private String email;
}
