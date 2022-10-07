package com.ynthm.services.account.entity;

import com.ynthm.common.web.util.phone.validator.PhoneNumber;
import lombok.Data;

import javax.validation.constraints.Min;

/** @author ethan */
@Data
@PhoneNumber
public class Account {
  @Min(value = 0)
  private String areaCode;

  @Min(value = 0)
  private String phoneNumber;
}
