package com.ynthm.demo.spring.jdbc.dao;

import com.ynthm.demo.spring.jdbc.constant.Constant;
import com.ynthm.demo.spring.jdbc.entity.User;
import com.ynthm.demo.spring.jdbc.enums.Gender;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Repository
public class NamedParameterUserDao implements UserRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public NamedParameterUserDao(DataSource dataSource) {
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public int count() {

    String sql = "select count(*) from user where sex = :sex";

    Map<String, Integer> namedParameters =
        Collections.singletonMap("sex", Gender.FEMALE.getValue());

    return Optional.ofNullable(
            this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class))
        .orElse(0);
  }

  @Override
  public int update(String sql, User user) {
    // "update user set balance = :balance where id = :id"
    return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
  }

  @Override
  public int deleteById(Long id) {
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
    return namedParameterJdbcTemplate.update("delete from user where id = :id", namedParameters);
  }

  @Override
  public List<User> findAll() {
    return namedParameterJdbcTemplate.query("select * from user", USER_ROW_MAPPER);
  }

  @Override
  public List<User> findByName(String name) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("name", "%" + name + "%");

    return namedParameterJdbcTemplate.query(
        "select * from user where name like :name", mapSqlParameterSource, USER_ROW_MAPPER);
  }

  @Override
  public Optional<User> findById(Integer id) {
    return namedParameterJdbcTemplate.queryForObject(
        "select * from user where id = :id",
        new MapSqlParameterSource("id", id),
        (rs, rowNum) -> Optional.of(USER_ROW_MAPPER.mapRow(rs, rowNum)));
  }

  @Override
  public String getNameById(Integer id) {
    return namedParameterJdbcTemplate.queryForObject(
        "select name from user where id = :id", new MapSqlParameterSource("id", id), String.class);
  }

  @Override
  public int[] batchUpdate(String sql, List<User> users) {
    // SqlParameterSourceUtils.createBatch(users)
    return this.namedParameterJdbcTemplate.batchUpdate(
        sql,
        users.stream()
            .map(
                i ->
                    new BeanPropertySqlParameterSource(i) {
                      @Override
                      public Object getValue(@NotNull String paramName)
                          throws IllegalArgumentException {
                        Object value = super.getValue(paramName);
                        if (value instanceof Gender) {
                          return ((Gender) value).getValue();
                        }
                        return value;
                      }
                    })
            .toArray(SqlParameterSource[]::new));
  }

  @Override
  public int[][] sectionBatchUpdate(String sql, Collection<User> users) {
    final AtomicInteger sublist = new AtomicInteger();
    List<int[]> result = new ArrayList<>();
    CompletableFuture<?>[] futures =
        users.stream()
            .collect(Collectors.groupingBy(t -> sublist.getAndIncrement() / Constant.BATCH_SIZE))
            .values()
            .stream()
            .map(
                sectionList ->
                    CompletableFuture.supplyAsync(() -> batchUpdate(sql, sectionList))
                        .whenComplete((r, e) -> result.add(r)))
            .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).join();

    return result.toArray(new int[0][]);
  }
}
