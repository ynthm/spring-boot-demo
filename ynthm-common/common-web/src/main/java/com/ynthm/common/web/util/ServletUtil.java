package com.ynthm.common.web.util;

import com.google.common.base.Strings;
import com.ynthm.common.constant.CharPool;
import com.ynthm.common.constant.Constant;
import com.ynthm.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author Ethan Wang
 */
@Slf4j
public class ServletUtil {
  public static final String UNKNOWN = "unknown";

  public static final String IPV6_DEFAULT = "0:0:0:0:0:0:0:1";

  public static final int IPV4_LENGTH = 15;

  private ServletUtil() {}

  public static String getHeader(String name) {
    return getHeader(getRequest(), name);
  }

  public static String getHeader(HttpServletRequest request, String name) {
    return request.getHeader(name);
  }

  public static HttpServletRequest getRequest() {
    return Optional.ofNullable(getRequestAttributes())
        .map(ServletRequestAttributes::getRequest)
        .orElseThrow(BaseException::new);
  }

  public static HttpServletResponse getResponse() {
    return Optional.ofNullable(getRequestAttributes())
        .map(ServletRequestAttributes::getResponse)
        .orElseThrow(BaseException::new);
  }

  /** 获取session */
  public static HttpSession getSession() {
    return getRequest().getSession();
  }

  public static ServletRequestAttributes getRequestAttributes() {
    try {
      RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
      return (ServletRequestAttributes) attributes;
    } catch (Exception e) {
      throw new BaseException(e);
    }
  }

  public static void renderString(HttpServletResponse response, String json) throws IOException {
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    try (PrintWriter pw = response.getWriter()) {
      pw.write(json);
    }
  }

  /**
   * 设置webflux模型响应
   *
   * @param response HttpServletResponse
   * @param jsonBody 响应内容
   * @return void
   */
  public static void webFluxResponseWriter(HttpServletResponse response, String jsonBody) {
    response.setStatus(HttpStatus.OK.value());
    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    try {
      response.getWriter().write(jsonBody);
    } catch (IOException e) {
      throw new BaseException(e);
    }
  }

  /**
   * @param response 响应
   * @param fileName 带扩展名的文件名
   */
  public static void responseForDownload(HttpServletResponse response, String fileName) {
    response.setContentType(
        MediaTypeFactory.getMediaType(fileName).orElse(MediaType.ALL).toString());
    response.setCharacterEncoding(Constant.UTF_8);
    try {
      // 这里URLEncoder.encode可以防止中文乱码
      fileName = URLEncoder.encode(fileName, Constant.UTF_8).replace("\\+", "%20");
    } catch (UnsupportedEncodingException e) {
      log.error("encoding filename error.", e);
    }

    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + fileName);
  }

  public static String getIpAddress(HttpServletRequest request) {
    String ip = null;
    try {
      // X-Forwarded-For: client1, proxy1, proxy2, proxy3
      ip = request.getHeader("x-forwarded-for");
      if (Strings.isNullOrEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (Strings.isNullOrEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (Strings.isNullOrEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (Strings.isNullOrEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (Strings.isNullOrEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
      if (IPV6_DEFAULT.equals(ip)) {
        ip = "127.0.0.1";
      }
    } catch (Exception e) {
      log.error("ERROR", e);
    }

    // 使用代理，则获取第一个IP地址
    if (!Strings.isNullOrEmpty(ip)
        && ip.length() > IPV4_LENGTH
        && ip.indexOf(CharPool.COMMA) > -1) {
      ip = ip.substring(0, ip.indexOf(','));
    }

    return ip;
  }

  /**
   * 获取User-Agent
   *
   * @return User-Agent
   */
  public static String getUserAgent(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.USER_AGENT);
  }

  /**
   * nginx 的 location 处加上 proxy_set_header X-Forwarded-Proto $scheme;
   *
   * @param request 请求
   * @return base url
   */
  public static String getBaseUrl(HttpServletRequest request) {

    String scheme = request.getHeader("X-Forwarded-Proto");
    if (Strings.isNullOrEmpty(scheme)) {
      scheme = request.getScheme();
    }
    return scheme
        + "://"
        + request.getServerName()
        + ":"
        + request.getServerPort()
        + request.getContextPath();
  }

  public static String getReferer(HttpServletRequest request) {
    String url = request.getHeader(HttpHeaders.REFERER);
    if (Strings.isNullOrEmpty(url)) {
      url = request.getRequestURL().toString();
    }
    return url;
  }
}
