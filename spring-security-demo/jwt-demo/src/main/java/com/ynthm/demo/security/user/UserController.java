package com.ynthm.demo.security.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author ethan
 */
@RestController
public class UserController {
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public User findUserInfoByUsername(@RequestParam String username) {
    return userService.findByUsername(username);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
