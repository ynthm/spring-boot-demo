package com.ynthm.validator.demo.common.validator;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@VerifyPhoneNumber
public class PhoneNumber {

  @Min(value = 0)
  private Integer areaCode;

  @Min(value = 0)
  @NotNull
  private Long number;
}
