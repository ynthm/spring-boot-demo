package com.ynthm.demo.security.user;

import org.springframework.stereotype.Service;

/**
 * @author ethan
 */
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
