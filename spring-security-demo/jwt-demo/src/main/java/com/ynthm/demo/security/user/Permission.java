package com.ynthm.demo.security.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/**
 * @author ethan
 */
@Data
@Table(name = "permission")
public class Permission {

  @Id private Long id;
  /** 权限名称,如 user:select */
  private String name;
  /** 权限描述,用于UI显示 */
  private String description;
  /** 权限地址 */
  private String url;

  @MappedCollection private List<Role> roles;
}
