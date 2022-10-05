package com.ynthm.demo.jpa.vo;

import lombok.Data;

/**
 * @author Ethan Wang
 */
@Data
public class OldTokenVo {
  private String token;
  private Long expireTimestamp;
}
