package com.ynthm.spring.jpa.demo.common;

/**
 * 用枚举类表示查询连接条件
 *
 * @author ethan
 */
public enum MatchEnum {
  /** filed = value */
  EQUAL,
  /** 下面四个用于Number类型的比较 filed > value */
  GT,
  // field >= value
  GE,
  // field < value
  LT,
  // field <= value
  LE,
  // field != value
  NOT_EQUAL,
  // field like value
  LIKE,
  // field not like value
  NOT_LIKE,
  // 下面四个用于可比较类型(Comparable)的比较
  // field > value
  GREATER_THAN,
  // field >= value
  GREATER_THAN_OR_EQUAL_TO,
  // field < value
  LESS_THAN,
  // field <= value
  LESS_THAN_OR_EQUAL_TO,
  ;
}
