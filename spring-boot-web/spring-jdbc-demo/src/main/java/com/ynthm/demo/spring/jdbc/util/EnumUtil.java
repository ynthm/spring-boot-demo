package com.ynthm.demo.spring.jdbc.util;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class EnumUtil {

  private EnumUtil() {}

  public static <E extends Enum<E>> E getBy(Class<E> enumClass, Predicate<? super E> predicate) {
    return Arrays.stream(enumClass.getEnumConstants()).filter(predicate).findFirst().orElse(null);
  }
}
