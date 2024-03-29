package com.ynthm.springbootdemo.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author ethan
 */
@Entity
@Data
@Table(name = "permission")
public class Permission {

  @Id @GeneratedValue private Long id;
  /** 权限名称,如 user:select */
  private String name;
  /** 权限描述,用于UI显示 */
  private String description;
  /** 权限地址 */
  private String url;

  @ManyToMany
  @JoinTable(
      name = "role_permission",
      joinColumns = {@JoinColumn(name = "permission_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private List<Role> roles;
}
