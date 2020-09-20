package com.ynthm.services.account.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/** @author ethan */
@Entity
@Data
@Table(name = "users")
public class User {
  @Id @GeneratedValue private Long id;

  @Column(unique = true)
  private String username;

  private String name;
  private String password;
  private String salt;

  /** 立即从数据库中进行加载数据 */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles;
}
