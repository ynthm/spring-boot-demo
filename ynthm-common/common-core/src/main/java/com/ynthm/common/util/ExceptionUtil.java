package com.ynthm.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

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

  public static Throwable getRootCause(Throwable throwable) {
    if (throwable == null) {
      return null;
    } else {
      Throwable rootCause = throwable;
      Set<Throwable> seenThrowables = new HashSet();
      seenThrowables.add(throwable);

      while (rootCause.getCause() != null && !seenThrowables.contains(rootCause.getCause())) {
        seenThrowables.add(rootCause.getCause());
        rootCause = rootCause.getCause();
      }

      return rootCause;
    }
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
