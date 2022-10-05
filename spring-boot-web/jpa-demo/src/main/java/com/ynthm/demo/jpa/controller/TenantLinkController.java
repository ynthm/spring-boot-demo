package com.ynthm.demo.jpa.controller;

import com.ynthm.common.domain.PageReq;
import com.ynthm.common.domain.PageResp;
import com.ynthm.common.domain.Result;
import com.ynthm.demo.jpa.domain.TenantLink;
import com.ynthm.demo.jpa.service.TenantLinkService;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 新旧平台租户关联
 *
 * @author Ethan Wang
 */
@RestController
@RequestMapping("/tenantLink")
public class TenantLinkController {

  private final TenantLinkService service;

  public TenantLinkController(TenantLinkService service) {
    this.service = service;
  }

  @PostMapping("")
  public Result<Void> add(@Validated(Insert.class) @RequestBody TenantLink req) {
    service.save(req);
    return Result.ok();
  }

  @PutMapping("")
  public Result<Void> update(@Validated(Update.class) @RequestBody TenantLink req) {
    service.update(req);
    return Result.ok();
  }

  @PostMapping("/page")
  public Result<PageResp<TenantLink>> getPage(@RequestBody PageReq<?> req) {
    return Result.ok(service.findPage(req));
  }
}
