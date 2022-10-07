package com.ynthm.services.account.entity;

import com.ynthm.common.web.validator.groups.Update;
import com.ynthm.common.web.validator.IdentityCardNumber;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** @author ethan */
public class UserInfo {
  /** id(只有在有Update分组中校验非空) */
  @NotNull(message = "id 不能为空", groups = Update.class)
  private Long id;

  @NotNull(message = "id 不能为空")
  private Long userId;

  @NotBlank(message = "身份证号不能为空")
  @IdentityCardNumber(message = "身份证信息有误,请核对后提交")
  private String clientCardNo;

  @URL private String personalUrl;
}
