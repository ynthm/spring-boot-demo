package com.ynthm.demo.jpa.repository;

import com.ynthm.demo.jpa.domain.TenantLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Ethan Wang
 */
@Repository
public interface TenantLinkRepository extends JpaRepository<TenantLink, Integer> {

  TenantLink findByTenantId(Long tenantId);

  List<TenantLink> findAllByCompanyIdIn(Set<String> companyIds);
}
