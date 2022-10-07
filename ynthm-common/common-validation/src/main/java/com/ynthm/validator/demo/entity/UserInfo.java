package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.validator.groups.UpdateGroup;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * @author ethan
 */
public class UserInfo {
  /** id(只有在有Update分组中校验非空) */
  @NotNull(message = "id 不能为空", groups = UpdateGroup.class)
  private Long id;

  @NotNull(message = "id 不能为空")
  private Long userId;

  @URL private String personalUrl;
}
