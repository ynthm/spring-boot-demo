package com.ynthm.services.account.controller;

import com.ynthm.common.Result;
import com.ynthm.common.web.entity.User;
import com.ynthm.common.web.validator.groups.Create;
import com.ynthm.common.web.validator.groups.Update;
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
  public Result<User> create(@Validated(Create.class) @RequestBody User user) {
    return Result.ok(user);
  }

  /**
   * 更新用户
   *
   * @param user 参数
   * @return 结果
   */
  @PutMapping("")
  public Result<User> update(@Validated(Update.class) @RequestBody User user) {
    return Result.ok(user);
  }
}
