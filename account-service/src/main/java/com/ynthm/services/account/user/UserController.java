package com.ynthm.services.account.user;

import com.ynthm.services.account.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** @author ethan */
@RestController
public class UserController {
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  @RequiresPermissions("users:view")
  public User findUserInfoByUsername(@RequestParam String username) {
    return userService.findByUsername(username);
  }
}
