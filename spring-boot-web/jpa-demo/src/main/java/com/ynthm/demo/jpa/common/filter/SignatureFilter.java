package com.ynthm.demo.jpa.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ynthm.common.domain.Result;
import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.util.HmacHelper;
import com.ynthm.common.web.util.CacheHttpServletRequestWrapper;
import com.ynthm.common.web.util.ServletUtil;
import com.ynthm.demo.jpa.common.Const;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ethan Wang
 */
public class SignatureFilter implements Filter {

  private final HmacHelper hmacHelper;
  private final ObjectMapper objectMapper;

  public SignatureFilter(HmacHelper hmacHelper, ObjectMapper objectMapper) {
    this.hmacHelper = hmacHelper;
    this.objectMapper = objectMapper;
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    if (servletRequest instanceof HttpServletRequest) {
      HttpServletRequest request = (HttpServletRequest) servletRequest;
      HttpServletResponse response = (HttpServletResponse) servletResponse;

      String signature = request.getHeader(Const.HTTP_HEADER_SIGNATURE);
      String timestamp = request.getHeader(Const.HTTP_HEADER_TIMESTAMP);

      if (timestamp == null || signature == null) {
        ServletUtil.renderString(
            response,
            objectMapper.writeValueAsString(
                Result.error(
                    BaseResultCode.VALID_ERROR,
                    "头部缺少",
                    Lists.newArrayList(Const.HTTP_HEADER_SIGNATURE, Const.HTTP_HEADER_TIMESTAMP))));
        return;
      }

      if (!isJson(request)) {
        return;
      }

      CacheHttpServletRequestWrapper requestWrapper = new CacheHttpServletRequestWrapper(request);

      String body = requestWrapper.getBodyString();
      if (body == null) {
        ServletUtil.renderString(
            response, objectMapper.writeValueAsString(Result.error(BaseResultCode.VALID_ERROR)));
        return;
      }

      if (!hmacHelper.base64Verify(signature, body + timestamp + hmacHelper.getKey())) {
        ServletUtil.renderString(
            response,
            objectMapper.writeValueAsString(Result.error(BaseResultCode.SIGN_VERIFY_FAILED)));
        return;
      }

      // 获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
      // 在chain.doFiler方法中传递新的request对象
      filterChain.doFilter(requestWrapper, servletResponse);
      return;
    }

    // 确认异常会被全局捕捉吗
    throw BaseResultCode.SIGN_VERIFY_FAILED.newException();

    //    ServletUtil.renderString(
    //        response,
    // objectMapper.writeValueAsString(Result.error(BaseResultCode.SIGN_VERIFY_FAILED)));
  }

  private boolean isJson(HttpServletRequest request) {
    return MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType());
  }
}
