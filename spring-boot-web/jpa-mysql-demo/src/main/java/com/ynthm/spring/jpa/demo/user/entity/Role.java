package com.ynthm.spring.jpa.demo.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午3:40
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private List<User> users;

  @Override
  public String getAuthority() {
    return name;
  }
}
