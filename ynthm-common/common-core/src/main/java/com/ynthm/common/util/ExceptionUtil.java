package com.ynthm.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author Ethan Wang
 */
@Slf4j
public class ExceptionUtil {

  private ExceptionUtil() {}

  /** 打印异常信息 */
  public static String printStackTrace(Exception e) {
    String swStr = null;
    try (StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw)) {
      e.printStackTrace(pw);
      pw.flush();
      sw.flush();
      swStr = sw.toString();
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
    return swStr;
  }

  public static String getFullStackTrace(Exception e) {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos)) {
      e.printStackTrace(ps);
      return bos.toString();
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }

    return null;
  }
}
