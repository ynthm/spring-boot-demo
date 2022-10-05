package com.ynthm.demo.jpa.service;

import com.ynthm.common.domain.PageReq;
import com.ynthm.common.domain.PageResp;
import com.ynthm.common.domain.Result;
import com.ynthm.common.jpa.util.PageUtil;
import com.ynthm.demo.jpa.domain.TenantLink;
import com.ynthm.demo.jpa.repository.TenantLinkRepository;
import com.ynthm.demo.jpa.vo.SwitchTenantListReq;
import com.ynthm.demo.jpa.vo.SwitchTenantReq;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ethan Wang
 */
@Service
public class TenantLinkService {
  private final TenantLinkRepository repository;

  public TenantLinkService(TenantLinkRepository repository) {
    this.repository = repository;
  }

  public Result<SwitchTenantReq> switchTenantOld(@RequestBody SwitchTenantReq req) {
    TenantLink tenantLink = repository.findByTenantId(req.getTenantId());
    return Result.ok(
        Optional.ofNullable(tenantLink)
            .map(
                item ->
                    new SwitchTenantReq()
                        .setTenantId(tenantLink.getTenantId())
                        .setCompanyId(tenantLink.getCompanyId()))
            .orElse(new SwitchTenantReq()));
  }

  public Result<SwitchTenantListReq> switchTenantNew(@RequestBody SwitchTenantListReq req) {

    Set<String> companyIds =
        req.getList().stream()
            .map(SwitchTenantReq::getCompanyId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    List<TenantLink> tenantLinkList = repository.findAllByCompanyIdIn(companyIds);

    return Result.ok(
        new SwitchTenantListReq(
            tenantLinkList.stream()
                .map(
                    item ->
                        new SwitchTenantReq()
                            .setTenantId(item.getTenantId())
                            .setCompanyId(item.getCompanyId()))
                .collect(Collectors.toList())));
  }

  public TenantLink save(TenantLink req) {
    return repository.save(req);
  }

  public void update(TenantLink req) {
    if (repository.existsById(req.getId())) {
      save(req);
    }
  }

  public PageResp<TenantLink> findPage(PageReq<?> req) {
    return PageUtil.page(repository::findAll, req);
  }
}
