package com.ynthm.demo.jpa.common.filter;

import com.google.common.base.Strings;
import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ethan Wang
 */
public class DemoFilter extends OncePerRequestFilter {

  private HandlerExceptionResolver handlerExceptionResolver;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  public void setHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String test = request.getHeader("test");
    if (!Strings.isNullOrEmpty(test)) {
      // Filter 无法被 RestControllerAdvice 拦截
      handlerExceptionResolver.resolveException(
          request, response, null, new BaseException(BaseResultCode.VALID_ERROR));
      return;
    }

    filterChain.doFilter(request, response);
  }
}
