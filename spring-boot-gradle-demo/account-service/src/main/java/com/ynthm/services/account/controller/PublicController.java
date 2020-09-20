package com.ynthm.services.account.controller;

import com.ynthm.common.Result;
import com.ynthm.services.account.entity.Message;
import com.ynthm.services.account.request.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 非登录公开访问API
 *
 * @author ethan
 */
@RestController
@RequestMapping("/auth")
public class PublicController {

  @PostMapping("/test")
  public void test1(@Validated @RequestBody Message message) {

    System.out.println("132");
  }

  /**
   * 手机发送验证码
   *
   * @param request 请求参数
   * @return 结果
   */
  @PostMapping("/code")
  public Result<Boolean> sendVerificationCode(
      @RequestBody @Validated(Phone.SendVerificationCode.class) Phone request) {
    return Result.ok();
  }

  /**
   * 邮件发送验证码
   *
   * @param request 请求参数
   * @return 结果
   */
  @PostMapping("/mail/code")
  public Result<Boolean> sendVerificationCode(@RequestBody @Validated Mail request) {
    return Result.ok();
  }

  /**
   * 注册验证手机验证码
   *
   * @param request 请求参数
   * @return 结果
   */
  @PostMapping("/register/verifyCode")
  public Result<String> registerVerify(
      @RequestBody @Validated VerifyVerificationCodeRequest request) {
    return Result.ok();
  }

  /**
   * 注册
   *
   * @param request 请求参数
   * @return 结果
   */
  @PostMapping("/register")
  public Result<String> register(@RequestBody @Validated PhoneRegisterRequest request) {
    return Result.ok();
  }

  /**
   * 忘记密码
   *
   * @param request 请求参数
   * @return 结果
   */
  @PostMapping("/forgetPassword")
  public Result<Boolean> changePasswordByVerificationCode(
      @Validated @RequestBody ForgetPasswordRequest request) {
    return Result.ok();
  }
}
