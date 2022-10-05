package com.ynthm.springbootdemo.controller;

import com.ynthm.springbootdemo.domain.ApiResult;
import com.ynthm.springbootdemo.domain.User;
import com.ynthm.springbootdemo.exception.ApiException;
import com.ynthm.springbootdemo.exception.ErrorCode;
import com.ynthm.springbootdemo.util.UserContentUtil;
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

/** Author : Ynthm */
@RestController
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ApiResult<String> login(HttpSession session, @Validated @RequestBody User user) {
    logger.info("login user:" + user.getUserName());

    if (user.getUserName() == null) {
      throw new ApiException(ErrorCode.NO_LOGIN);
    }

    // TODO 只是模拟登陆
    session.setAttribute(UserContentUtil.KEY_USER, user.getUserName());

    return new ApiResult<>(user.getUserName());
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
    if (page > resultPage.getTotalPages()) {
      throw new ApiException(ErrorCode.VALIDATE_FAILED);
    }

    return resultPage.getContent();
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }
}
