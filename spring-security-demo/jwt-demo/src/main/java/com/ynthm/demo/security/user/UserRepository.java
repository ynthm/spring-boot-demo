package com.ynthm.demo.security.user;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
  User findByUsername(String username);
}
