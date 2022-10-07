package com.ynthm.springbootdemo.controller;

import com.ynthm.common.domain.Result;
import com.ynthm.springbootdemo.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ynthm Wang
 */
@RestController
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Result<String> login(HttpSession session, @Validated @RequestBody User user) {
    logger.info("login user:" + user.getUserName());
    return Result.ok(user.getUserName());
  }

  @GetMapping(
      params = {"page", "size"},
      value = "/list")
  public List<User> findPaginated(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) {

    List<User> userList = new ArrayList<>();
    Page<User> resultPage = new PageImpl<>(userList);

    return resultPage.getContent();
  }
}
