package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.validator.PhoneNumber;
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
