package com.ynthm.common.lang;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Tuple2<T1 extends Serializable, T2 extends Serializable> implements Serializable {
  private final T1 t1;
  private final T2 t2;

  /**
   * 构建{@link Tuple2}对象
   *
   * @param <T1> 键类型
   * @param <T2> 值类型
   * @param t1 第一个值
   * @param t2 第二个值
   * @return {@link Tuple2}
   * @since 5.4.3
   */
  public static <T1 extends Serializable, T2 extends Serializable> Tuple2<T1, T2> of(T1 t1, T2 t2) {
    return new Tuple2<>(t1, t2);
  }
}
