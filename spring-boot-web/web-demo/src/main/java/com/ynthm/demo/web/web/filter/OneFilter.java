package com.ynthm.demo.web.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
@Order(2)
public class OneFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    FilterConfig filterConfig = super.getFilterConfig();
    ServletContext servletContext = super.getServletContext();
    Environment environment = super.getEnvironment();

    String path = request.getRequestURI();

    String contentType = request.getContentType();
    log.info("filter one Request URL path : {}, Request content type: {}", path, contentType);

    filterChain.doFilter(request, response);

    log.info("filter one after filterChain.doFilter");
  }
}
