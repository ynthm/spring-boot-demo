package com.ynthm.demo.spring.jdbc.entity;

import com.ynthm.demo.spring.jdbc.enums.Gender;
import com.ynthm.demo.spring.jdbc.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class User {
  private Integer id;
  private Integer age;
  private String name;
  private BigDecimal balance;
  private Gender sex;
  private Long phone;
  private String province;
  private String city;
  private String country;
  private Status status;

  private OffsetDateTime createTime;
  private OffsetDateTime updateTime;
}
