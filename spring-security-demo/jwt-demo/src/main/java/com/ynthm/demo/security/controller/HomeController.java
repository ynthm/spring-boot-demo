package com.ynthm.demo.security.controller;

import com.ynthm.common.domain.Result;
import com.ynthm.common.web.validate.UpdateGroup;
import com.ynthm.demo.security.user.User;
import com.ynthm.validator.demo.common.validator.groups.CreateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 *
 * @author ethan
 */
@RestController
@RequestMapping("/users")
public class HomeController {

  /**
   * 测试
   *
   * @return 结果
   */
  @GetMapping("/test")
  public Result<User> test() {
    User user = new User();
    user.setUsername("Ethan");
    return Result.ok(user);
  }

  /**
   * 新建用户
   *
   * @param user 参数
   * @return 结果
   */
  @PostMapping("")
  public Result<User> create(@Validated(CreateGroup.class) @RequestBody User user) {
    return Result.ok(user);
  }

  /**
   * 更新用户
   *
   * @param user 参数
   * @return 结果
   */
  @PutMapping("")
  public Result<User> update(@Validated(UpdateGroup.class) @RequestBody User user) {
    return Result.ok(user);
  }
}
