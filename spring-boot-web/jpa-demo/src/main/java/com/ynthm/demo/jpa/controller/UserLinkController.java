package com.ynthm.demo.jpa.controller;

import com.ynthm.common.domain.PageReq;
import com.ynthm.common.domain.PageResp;
import com.ynthm.common.domain.Result;
import com.ynthm.common.web.validate.InsertGroup;
import com.ynthm.common.web.validate.UpdateGroup;
import com.ynthm.demo.jpa.domain.UserLink;
import com.ynthm.demo.jpa.service.UserLinkService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 新旧平台用户关联
 *
 * @author Ethan Wang
 */
@RestController
@RequestMapping("/userLink")
public class UserLinkController {
  private final UserLinkService userService;

  public UserLinkController(UserLinkService userService) {
    this.userService = userService;
  }

  @PostMapping("/page")
  public Result<PageResp<UserLink>> getPage(@RequestBody PageReq<?> req) {
    return Result.ok(userService.findPage(req));
  }

  @PostMapping("")
  public Result<Void> add(@Validated(InsertGroup.class) @RequestBody UserLink req) {
    userService.save(req);
    return Result.ok();
  }

  @PutMapping("")
  public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody UserLink req) {
    userService.update(req);
    return Result.ok();
  }

  @PatchMapping("")
  public Result<Void> updatePartialOrInsert(@Validated @RequestBody UserLink req) {
    userService.save(req);
    return Result.ok();
  }
}
