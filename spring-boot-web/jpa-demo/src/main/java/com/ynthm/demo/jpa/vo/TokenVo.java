package com.ynthm.demo.jpa.vo;

/**
 * @author Ethan Wang
 */
public class TokenVo {
  private String token;
  private Long expireTime;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }
}
