package com.ynthm.demo.security.repository;

import com.ynthm.demo.security.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public interface UserRepository extends CrudRepository<User, Integer> {}
