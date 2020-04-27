package com.ynthm.springbootdemo.repository;

import com.ynthm.springbootdemo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** Author : Ynthm */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  List<User> findByIdIn(List<Long> userIds);

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String email);
}
