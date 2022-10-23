package com.ynthm.demo.security.auth;

import java.util.function.Function;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class GetSetUtil {

  private GetSetUtil() {}

  public static <T, P> T get(P object, Function<P, T> getFunction) {
    return getFunction.apply(object);
  }
}
