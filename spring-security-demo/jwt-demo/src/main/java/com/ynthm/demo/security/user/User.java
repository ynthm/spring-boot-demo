package com.ynthm.demo.security.user;

import com.ynthm.demo.security.user.model.SexEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/**
 * @author Ethan Wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "user")
public class User {
  @Id private Integer id;

  private String username;

  private String name;
  private SexEnum sex;

  private String password;
  private String salt;

  /** 立即从数据库中进行加载数据 */
  @MappedCollection private List<Role> roles;
}
