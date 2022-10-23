// package com.ynthm.demo.security.auth.handler;
//
// import com.ynthm.common.domain.Result;
// import com.ynthm.common.enums.BaseResultCode;
// import com.ynthm.common.web.util.ServletUtil;
// import com.ynthm.demo.security.auth.BaseAuthenticationException;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.security.authentication.*;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.stereotype.Component;
//
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
//
/// **
// * Authentication Failure AuthenticationException 无法全局捕获只能在这里处理
// *
// * @author ynthm
// */
// @Slf4j
// @Component
// public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
//  @Override
//  public void onAuthenticationFailure(
//      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
//      throws IOException, ServletException {
//    // 这些对于操作的处理类可以根据不同异常进行不同处理
//    Result<String> result = new Result<>();
//    result.setCode(BaseResultCode.ERROR.getCode());
//    result.setData(exception.getMessage());
//    if (exception instanceof LockedException) {
//      result.setMessage("账户被冻结,请联系管理员。");
//    } else if (exception instanceof CredentialsExpiredException) {
//      result.setMessage("密码过期,请联系管理员。");
//    } else if (exception instanceof AccountExpiredException) {
//      result.setMessage("账户过期,请联系管理员。");
//    } else if (exception instanceof DisabledException) {
//      result.setMessage("账户被禁用,请联系管理员。");
//    } else if (exception instanceof BadCredentialsException) {
//      result.setMessage("用户名或者密码错误,请重新输入。");
//    } else if (exception instanceof UsernameNotFoundException) {
//      result.setMessage("用户名或者密码错误,请重新输入。");
//    } else if (exception instanceof BaseAuthenticationException) {
//
//      result = Result.error(((BaseAuthenticationException) exception).getResultCode());
//    }
//
//    log.info("【登录失败】" + exception.getMessage());
//    ServletUtil.renderString(response, result.toJson());
//  }
// }
