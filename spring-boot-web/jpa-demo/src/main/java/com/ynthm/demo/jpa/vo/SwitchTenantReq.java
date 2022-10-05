package com.ynthm.demo.jpa.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ethan Wang
 */
@Data
@Accessors(chain = true)
public class SwitchTenantReq {
  private String companyId;
  private Long tenantId;
}
