package com.ynthm.springbootdemo.repository;

import com.ynthm.springbootdemo.domain.entity.Role;
import com.ynthm.springbootdemo.domain.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author : Ynthm
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(RoleName roleName);
}
