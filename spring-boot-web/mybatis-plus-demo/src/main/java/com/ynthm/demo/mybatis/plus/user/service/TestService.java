package com.ynthm.demo.mybatis.plus.user.service;

import com.ynthm.demo.mybatis.plus.user.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Service
public class TestService {
  @Autowired RoleService roleService;

  public Role getOne(Long id) {
    return roleService.getById(id);
  }
}
