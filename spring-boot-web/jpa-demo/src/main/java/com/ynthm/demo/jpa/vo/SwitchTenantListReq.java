package com.ynthm.demo.jpa.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ethan Wang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwitchTenantListReq {

  @Valid private List<SwitchTenantReq> list;
}
