package com.ynthm.spring.jpa.demo.user.repository;

import com.ynthm.spring.jpa.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午1:01
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  @Query("FROM User p WHERE p.emailAddress = :emailId")
  User findByEmail(@Param("emailId") String email);

  Page<User> findAll(Specification<User> spec, Pageable pageable);
}
