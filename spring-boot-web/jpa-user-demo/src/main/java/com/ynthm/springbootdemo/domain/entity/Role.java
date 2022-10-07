package com.ynthm.springbootdemo.domain.entity;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ynthm Wang
 */
@Data
@Entity
@Table(name = "role")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 60)
  private RoleName name;

  /** 角色 -- 权限关系：多对多 */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "role_permission",
      joinColumns = {@JoinColumn(name = "role_id")},
      inverseJoinColumns = {@JoinColumn(name = "permission_id")})
  private List<Permission> permissions;

  /** 用户 -- 角色关系：多对多 */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_role",
      joinColumns = {@JoinColumn(name = "role_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private List<User> users;
}
