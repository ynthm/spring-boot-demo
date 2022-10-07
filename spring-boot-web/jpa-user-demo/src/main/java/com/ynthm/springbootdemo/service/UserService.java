package com.ynthm.springbootdemo.service;

import com.ynthm.common.exception.BaseException;
import com.ynthm.springbootdemo.domain.entity.Role;
import com.ynthm.springbootdemo.domain.entity.RoleName;
import com.ynthm.springbootdemo.domain.entity.User;
import com.ynthm.springbootdemo.repository.RoleRepository;
import com.ynthm.springbootdemo.repository.UserRepository;
import com.ynthm.springbootdemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;

/** Author : Ynthm */
@Service
public class UserService {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired JwtTokenProvider tokenProvider;

  public User register(User userToAdd) {
    final String username = userToAdd.getUsername();
    if (userRepository.findByUsername(username) != null) {
      return null;
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    final String rawPassword = userToAdd.getPassword();
    userToAdd.setPassword(encoder.encode(rawPassword));
    userToAdd.setUpdatedAt(Instant.now());

    Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(BaseException::new);

    userToAdd.setRoles(Collections.singleton(userRole));

    return userRepository.save(userToAdd);
  }

  public String login(User user) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return tokenProvider.generateToken(authentication);
  }

  public String refresh(String oldToken) {
    //        final String token = oldToken.substring(tokenHead.length());
    //        String username = jwtTokenUtil.getUsernameFromToken(token);
    //        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
    //        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
    //            return jwtTokenUtil.refreshToken(token);
    //        }
    return null;
  }
}
