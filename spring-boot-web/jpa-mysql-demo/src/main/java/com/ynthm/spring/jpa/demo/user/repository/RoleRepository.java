package com.ynthm.spring.jpa.demo.user.repository;

import com.ynthm.spring.jpa.demo.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** @author ethan */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}
