package com.ynthm.demo.security.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Getter
public abstract class BaseRepository {
  /** 高度封装的JDBC操作，可直接保存实体 */
  private JdbcAggregateOperations operations;

  @Autowired
  public void setOperations(JdbcAggregateOperations operations) {
    this.operations = operations;
  }

  /**
   * 普通命名的JDBC查询和操作，可使用 {@link org.springframework.jdbc.core.BeanPropertyRowMapper}
   * 完成高级自动装配，可自动完成驼峰和下划线的自动映射
   */
  private NamedParameterJdbcTemplate template;

  @Autowired
  public void setTemplate(NamedParameterJdbcTemplate template) {
    this.template = template;
  }
}
