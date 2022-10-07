package com.ynthm.demo.spring.jdbc.dao;

import com.ynthm.demo.spring.jdbc.entity.User;
import com.ynthm.demo.spring.jdbc.enums.Gender;
import com.ynthm.demo.spring.jdbc.enums.Status;
import com.ynthm.demo.spring.jdbc.util.EnumUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public interface IUserDao {
  int count();

  int update(String sql, List<String> placeholders, User user);

  int deleteById(Long id);

  List<User> findAll();

  List<User> findByName(String name);

  Optional<User> findById(Integer id);

  String getNameById(Integer id);

  int[] batchUpdate(final List<User> users);

  int[] batchUpdate(String sql, final List<User> users);

  int[][] sectionBatchUpdate(String sql, final Collection<User> users);

  RowMapper<User> USER_ROW_MAPPER =
      (rs, rowNum) ->
          new User()
              .setId(rs.getInt("id"))
              .setAge(rs.getInt("age"))
              .setName(rs.getString("name"))
              .setBalance(rs.getBigDecimal("balance"))
              .setSex(Gender.getByValue(rs.getInt("sex")))
              .setStatus(
                  EnumUtil.getBy(
                      Status.class,
                      i -> {
                        try {
                          return i.ordinal() == rs.getInt("status");
                        } catch (SQLException e) {
                          throw new RuntimeException(e);
                        }
                      }))
              .setCreateTime(rs.getObject("create_time", OffsetDateTime.class))
              .setUpdateTime(rs.getObject("update_time", OffsetDateTime.class));
  ;
}
