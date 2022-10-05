package com.ynthm.demo.webflux.entity;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("person")
@Data
public class Person {
  @Id private Long id;
  private String name;
  private int age;
}
