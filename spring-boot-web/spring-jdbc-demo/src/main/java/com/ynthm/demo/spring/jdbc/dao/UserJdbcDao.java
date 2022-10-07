package com.ynthm.demo.spring.jdbc.dao;

import com.ynthm.demo.spring.jdbc.entity.User;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Repository
public class UserJdbcDao {

  private SimpleJdbcInsert insertUser;
  private SimpleJdbcInsert insertUserAndReturnKey;

  @Resource
  public void setDataSource(DataSource dataSource) {
    this.insertUser = new SimpleJdbcInsert(dataSource).withTableName("user");

    this.insertUserAndReturnKey =
        new SimpleJdbcInsert(dataSource).withTableName("user").usingGeneratedKeyColumns("id");
  }

  public void addAndReturnKey(User user) {
    Map<String, Object> parameters = new HashMap<>(2);
    parameters.put("name", user.getName());
    parameters.put("age", user.getAge());
    parameters.put("sex", user.getSex());
    Number newId = insertUserAndReturnKey.executeAndReturnKey(parameters);
    user.setId(newId.intValue());
  }

  public void addBySqlParameterSource(User user) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
    Number newId = insertUserAndReturnKey.executeAndReturnKey(parameters);
    user.setId(newId.intValue());
  }

  public void addByMapSqlParameterSource(User user) {
    SqlParameterSource parameters =
        new MapSqlParameterSource().addValue("name", user.getName()).addValue("age", user.getAge());
    Number newId = insertUserAndReturnKey.executeAndReturnKey(parameters);
    user.setId(newId.intValue());
  }

  public void add(User user) {
    Map<String, Object> parameters = new HashMap<>(3);
    parameters.put("id", user.getId());
    parameters.put("name", user.getName());
    parameters.put("age", user.getAge());
    parameters.put("sex", user.getSex());
    insertUser.execute(parameters);
  }
}
