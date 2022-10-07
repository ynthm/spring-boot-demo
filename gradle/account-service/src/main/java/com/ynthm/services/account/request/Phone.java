package com.ynthm.services.account.request;

import com.ynthm.common.web.util.phone.validator.PhoneNumber;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** @author ethan */
@Data
@PhoneNumber(groups = Phone.SendVerificationCode.class)
public class Phone {

  /**
   * 国际电话区号
   *
   * @mock 86
   */
  @Size(min = 1, max = 4)
  @NotBlank
  private String areaCode;
  /**
   * 电话号码
   *
   * @mock 18866667777
   */
  @NotBlank private String phoneNumber;

  public interface SendVerificationCode {}
}
