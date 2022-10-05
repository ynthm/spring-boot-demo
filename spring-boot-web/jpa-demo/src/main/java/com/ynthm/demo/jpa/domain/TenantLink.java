package com.ynthm.demo.jpa.domain;

import com.ynthm.common.web.validate.InsertGroup;
import com.ynthm.common.web.validate.UpdateGroup;
import com.ynthm.demo.jpa.common.Const;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author Ethan Wang
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = Const.T_TENANT_LINK)
public class TenantLink extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = {UpdateGroup.class})
  @Null(groups = {InsertGroup.class})
  private Integer id;

  @Column(name = "company_id", nullable = false, length = 64)
  @NotBlank
  private String companyId;

  @Column(name = "tenant_id", nullable = false)
  @NotNull
  private Long tenantId;
}
