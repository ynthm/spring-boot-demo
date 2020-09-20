package com.ynthm.services.account.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/** @author ethan */
@Entity
@Data
@Table(name = "roles")
public class Role {
  @Id @GeneratedValue private Long id;
  private String name;

  /** 角色 -- 权限关系：多对多 */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "roles_permissions",
      joinColumns = {@JoinColumn(name = "role_id")},
      inverseJoinColumns = {@JoinColumn(name = "permission_id")})
  private List<Permission> permissions;

  /** 用户 -- 角色关系：多对多 */
  @ManyToMany
  @JoinTable(
      name = "users_roles",
      joinColumns = {@JoinColumn(name = "role_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private List<User> users;
}
