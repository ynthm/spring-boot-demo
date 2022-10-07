package com.ynthm.springbootdemo.controller;

import com.ynthm.common.domain.Result;
import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.exception.BaseException;
import com.ynthm.springbootdemo.domain.entity.Role;
import com.ynthm.springbootdemo.domain.entity.RoleName;
import com.ynthm.springbootdemo.domain.entity.User;
import com.ynthm.springbootdemo.payload.JwtAuthenticationResponse;
import com.ynthm.springbootdemo.payload.LoginRequest;
import com.ynthm.springbootdemo.payload.SignUpRequest;
import com.ynthm.springbootdemo.repository.RoleRepository;
import com.ynthm.springbootdemo.repository.UserRepository;
import com.ynthm.springbootdemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

/** Author : Ynthm */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired JwtTokenProvider tokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<Result<JwtAuthenticationResponse>> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(Result.ok(new JwtAuthenticationResponse(jwt)));
  }

  @PostMapping("/signup")
  public ResponseEntity<Result<Void>> registerUser(
      @Valid @RequestBody SignUpRequest signUpRequest) {

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<>(Result.error(BaseResultCode.DB_EXIST), HttpStatus.BAD_REQUEST);
    }

    // Creating user's account
    User user =
        new User(
            signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(BaseException::new);

    user.setRoles(Collections.singleton(userRole));

    User result = userRepository.save(user);

    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/users/{username}")
            .buildAndExpand(result.getUsername())
            .toUri();

    return ResponseEntity.created(location).body(Result.ok());
  }
}
