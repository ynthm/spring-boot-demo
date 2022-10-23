package com.ynthm.demo.security.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynthm.common.domain.Result;
import com.ynthm.common.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ynthm
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private ObjectMapper objectMapper;

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
      throws IOException {
    ServletUtil.renderString(
        response,
        objectMapper.writeValueAsString(
            Result.ok().setMsg(HttpStatus.FORBIDDEN.toString()).setData(e.getMessage())));
  }
}
