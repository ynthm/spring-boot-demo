package com.ynthm.demo.security.controller;

import com.ynthm.common.domain.Result;
import com.ynthm.demo.security.user.model.ForgetPasswordRequest;
import com.ynthm.demo.security.user.model.Mail;
import com.ynthm.demo.security.user.model.PhoneRegisterRequest;
import com.ynthm.demo.security.user.model.VerifyVerificationCodeRequest;
import com.ynthm.validator.demo.common.validator.PhoneNumber;
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

  /**
   * 手机发送验证码
   *
   * @param request 请求参数
   * @return 结果
   */
  @PostMapping("/code")
  public Result<Boolean> sendVerificationCode(@RequestBody @Validated PhoneNumber request) {
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
