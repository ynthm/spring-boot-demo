package com.ynthm.demo.security.auth.handler;

import com.ynthm.common.web.util.ServletUtil;
import com.ynthm.common.web.util.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ethan
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
  /**
   * 用户登出返回结果 这里应该让前端清除掉Token
   *
   * @param request
   * @param response
   * @param authentication
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    UserUtil.clearAllUserInfo();
    ServletUtil.renderString(response, "退出成功");
  }
}
