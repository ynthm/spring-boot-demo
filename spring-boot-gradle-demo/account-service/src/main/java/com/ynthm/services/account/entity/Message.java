package com.ynthm.services.account.entity;

import com.ynthm.common.web.validator.ScopeMatch;
import lombok.Data;

@Data
public class Message {

  /** 只能为text 和image */
  @ScopeMatch(scopes = {"1", "2", "3"})
  private String messageType;
}
