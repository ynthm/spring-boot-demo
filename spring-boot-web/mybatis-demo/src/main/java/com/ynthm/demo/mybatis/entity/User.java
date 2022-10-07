package com.ynthm.demo.mybatis.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
public class User {

  private Integer id;

  private String name;

  private Integer age;
  private LocalDate birth;
  private LocalDateTime createTime;
  private BigDecimal amount;
}
