package com.ynthm.demo.jpa.vo;

import lombok.Data;

/**
 * @author Ethan Wang
 */
@Data
public class LoginReq {
  private String oldUserId;
  private String username;
}
