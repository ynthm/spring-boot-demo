package com.ynthm.demo.resource.isolate.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** @author ynthm */
@Slf4j
public class ServletUtil {

  public static final String UNKNOWN = "unknown";

  private ServletUtil() {}

  public static void renderString(HttpServletResponse response, String json) throws IOException {
    response.setStatus(200);
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    try (PrintWriter pw = response.getWriter()) {
      pw.write(json);
    }
  }

  public static String getIpAddr(HttpServletRequest request) {
    String ip = null;
    try {
      // X-Forwarded-For: client1, proxy1, proxy2, proxy3
      ip = request.getHeader("x-forwarded-for");
      if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
      if (ip.equals("0:0:0:0:0:0:0:1")) {
        ip = "127.0.0.1";
      }
    } catch (Exception e) {
      log.error("WebUtil ERROR ", e);
    }

    // 使用代理，则获取第一个IP地址
    if (StringUtils.isNotEmpty(ip) && ip.length() > 15) {
      if (ip.indexOf(",") > 0) {
        ip = ip.substring(0, ip.indexOf(","));
      }
    }
    return ip;
  }

  /**
   * 获取User-Agent
   *
   * @return
   */
  public static String getUserAgent(HttpServletRequest request) {
    return request.getHeader("User-Agent");
  }

  public static String getBaseUrl(HttpServletRequest request) {
    // nginx location 处加上proxy_set_header X-Forwarded-Proto  $scheme;
    String scheme = request.getHeader("X-Forwarded-Proto");
    if (StringUtils.isBlank(scheme)) {
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
    String url = request.getHeader("Referer");
    if (StringUtils.isBlank(url)) {
      url = request.getRequestURL().toString();
    }
    return url;
  }
}
