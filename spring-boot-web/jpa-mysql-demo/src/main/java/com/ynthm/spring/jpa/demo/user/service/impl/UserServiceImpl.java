package com.ynthm.spring.jpa.demo.user.service.impl;

import com.ynthm.spring.jpa.demo.user.entity.Role;
import com.ynthm.spring.jpa.demo.user.entity.User;
import com.ynthm.spring.jpa.demo.user.repository.UserRepository;
import com.ynthm.spring.jpa.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午3:51
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(s);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("用户不存在");
    }

    return user;
  }

  public User save(User registration) {
    User user = new User();
    user.setFirstName(registration.getFirstName());
    user.setLastName(registration.getLastName());
    user.setEmailAddress(registration.getEmailAddress());
    user.setPassword(passwordEncoder.encode(registration.getPassword()));
    user.setRoles(Arrays.asList(new Role().setName("ROLE_USER")));
    return userRepository.save(user);
  }
}
