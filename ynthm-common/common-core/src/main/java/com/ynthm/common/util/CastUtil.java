package com.ynthm.common.util;

/**
 * @author Ethan Wang
 * @version 1.0
 */
public class CastUtil {

  private CastUtil() {}

  @SuppressWarnings("unchecked")
  public static <T> T cast(Object obj) {
    return (T) obj;
  }
}
