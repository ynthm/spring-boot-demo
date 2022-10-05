package com.ynthm.demo.web.web;

import com.ynthm.common.web.util.CacheHttpServletRequestWrapper;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class SignatureInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (isJson(request)) {
      // 获取json字符串
      String jsonParam = new CacheHttpServletRequestWrapper(request).getBodyString();

      // 验签逻辑...略...
    }

    return true;
  }

  private boolean isJson(HttpServletRequest request) {
    if (request.getContentType() != null) {
      return request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE);
    }

    return false;
  }
}
