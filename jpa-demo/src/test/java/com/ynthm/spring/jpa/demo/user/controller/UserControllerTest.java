package com.ynthm.spring.jpa.demo.user.controller;

import com.ynthm.spring.jpa.demo.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午1:53
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void getAllUsers() {}

  @Test
  void getUserById() {
    ResponseEntity<User> forEntity =
        this.restTemplate.getForEntity("/api/v1/users/" + 3, User.class);
    User body = forEntity.getBody();
  }

  @Test
  void createUser() {}

  @Test
  void updateUser() {}

  @Test
  void deleteUser() {}
}
