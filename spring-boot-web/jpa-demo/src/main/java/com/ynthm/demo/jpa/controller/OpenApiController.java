package com.ynthm.demo.jpa.controller;

import com.ynthm.common.domain.Result;
import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.demo.jpa.service.TenantLinkService;
import com.ynthm.demo.jpa.vo.SwitchTenantListReq;
import com.ynthm.demo.jpa.vo.SwitchTenantReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户对应关系
 *
 * @author Ethan Wang
 */
@RestController
@RequestMapping("/open/tenantLink")
public class OpenApiController {
  private final TenantLinkService service;

  public OpenApiController(TenantLinkService service) {
    this.service = service;
  }

  @PostMapping("/switchTenantOld")
  public Result<SwitchTenantReq> switchTenantOld(@RequestBody SwitchTenantReq req) {
    if (req.getTenantId() == null) {
      return Result.error(BaseResultCode.VALID_ERROR);
    }

    return service.switchTenantOld(req);
  }

  @PostMapping("/switchTenantNew")
  public Result<SwitchTenantListReq> switchTenantNew(@RequestBody SwitchTenantListReq req) {
    return service.switchTenantNew(req);
  }
}
