package com.ynthm.demo.security.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/**
 * @author ethan
 */
@Data
@Table(name = "role")
public class Role {
  @Id private Long id;

  @Column("name")
  private String name;

  /** 角色 -- 权限关系：多对多 */
  @MappedCollection private List<Permission> permissions;

  /** 用户 -- 角色关系：多对多 */
  @MappedCollection private List<User> users;
}
