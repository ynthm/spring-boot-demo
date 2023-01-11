package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.validator.groups.InsertGroup;
import com.ynthm.validator.demo.common.validator.groups.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * @author ethan
 */
@Data
public class User {
  /** ID 更新的时候非空 */
  @Null(message = "ID 为空", groups = InsertGroup.class)
  @NotNull(message = "ID 不能为空", groups = UpdateGroup.class)
  private Long id;
  /** 性别(0,1) */
  private String gender;

  /**
   * 用户名（校验：不能为空，不能超过20个字符串）
   *
   * @required
   */
  @NotBlank(message = "{nb.name}")
  @Length(max = 20, message = "用户名不能超过20个字符")
  private String username;

  /** 手机号（校验：不能为空且按照正则校验格式） */
  @NotBlank(message = "手机号不能为空")
  private String mobile;

  /** 邮箱（校验：不能唯恐且校验邮箱格式） */
  @NotBlank(message = "联系邮箱不能为空")
  @Email(message = "邮箱格式不对")
  private String email;

  /**
   * 创建时间
   *
   * @ignore
   */
  private LocalDateTime createTime;
}
