package com.ynthm.demo.web.web;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ethan Wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class User {
  private Integer id;

  private String username;

  private String name;
  private String sex;

  private String password;
  private String salt;
}
