package com.ynthm.services.account.user;

import com.ynthm.services.account.entity.User;

public interface UserService {
  User findByUsername(String username);
}
