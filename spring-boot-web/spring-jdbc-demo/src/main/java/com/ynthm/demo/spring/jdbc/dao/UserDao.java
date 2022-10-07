package com.ynthm.demo.spring.jdbc.dao;

import com.ynthm.demo.spring.jdbc.constant.Constant;
import com.ynthm.demo.spring.jdbc.entity.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Repository
public class UserDao implements IUserDao {

  private final JdbcTemplate jdbcTemplate;

  public UserDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public int count() {
    return this.jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
  }

  @Override
  public int update(String sql, List<String> placeholders, User user) {
    // insert into user (name, age, balance, sex, create_time) values(?,?,?,?,?)"
    //  "update user set balance = ? where id = ?"
    Map<String, Function<User, Object>> placeholder2Func = new LinkedHashMap<>();
    placeholder2Func.put("id", User::getId);
    placeholder2Func.put("name", User::getName);
    placeholder2Func.put("age", User::getAge);
    placeholder2Func.put("balance", User::getBalance);
    placeholder2Func.put("sex", i -> i.getSex().getValue());
    placeholder2Func.put("create_time", User::getCreateTime);

    return jdbcTemplate.update(
        sql,
        ps -> {
          int index = 1;
          for (String placeholder : placeholders) {
            if (placeholder2Func.containsKey(placeholder)) {
              ps.setObject(index, placeholder2Func.get(placeholder).apply(user));
            } else {
              ps.setObject(index, null);
            }

            index++;
          }
        });
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update("delete user where id = ?", id);
  }

  @Override
  public List<User> findAll() {
    return jdbcTemplate.query("select * from user", USER_ROW_MAPPER);
  }

  @Override
  public List<User> findByName(String name) {
    return jdbcTemplate.query(
        "select * from user where name like ?", USER_ROW_MAPPER, "%" + name + "%");
  }

  @Override
  public Optional<User> findById(Integer id) {
    return jdbcTemplate.queryForObject(
        "select * from user where id = ?",
        (rs, rowNum) -> Optional.of(USER_ROW_MAPPER.mapRow(rs, rowNum)),
        id);
  }

  @Override
  public String getNameById(Integer id) {
    return jdbcTemplate.queryForObject("select name from user where id = ?", String.class, id);
  }

  @Override
  public int[] batchUpdate(List<User> users) {
    String sql = "update user set name = ? where id = ?";
    return batchUpdate(sql, users);
  }

  @Override
  public int[] batchUpdate(String sql, List<User> users) {
    // String sql = "insert into user (name, age, balance, sex, create_time) values(?,?,?,?,?)";
    Map<String, Function<User, Object>> map = new LinkedHashMap<>();
    map.put("name", User::getName);
    map.put("age", User::getAge);
    map.put("balance", User::getBalance);
    map.put("sex", i -> i.getSex().getValue());
    map.put("create_time", User::getCreateTime);

    return this.jdbcTemplate.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            int index = 1;
            User user = users.get(i);
            for (Map.Entry<String, Function<User, Object>> entry : map.entrySet()) {
              ps.setObject(index, entry.getValue().apply(user));
              index++;
            }
          }

          @Override
          public int getBatchSize() {
            return users.size();
          }
        });
  }

  @Override
  public int[][] sectionBatchUpdate(String sql, Collection<User> users) {

    Map<String, Function<User, Object>> map = new LinkedHashMap<>();
    map.put("name", User::getName);
    map.put("age", User::getAge);
    map.put("balance", User::getBalance);
    map.put("sex", i -> i.getSex().getValue());
    map.put("create_time", User::getCreateTime);
    // "update user set name = ?, age = ? where id = ?"
    return jdbcTemplate.batchUpdate(
        sql,
        users,
        Constant.BATCH_SIZE,
        (PreparedStatement ps, User user) -> {
          int index = 1;
          for (Map.Entry<String, Function<User, Object>> entry : map.entrySet()) {
            ps.setObject(index, entry.getValue().apply(user));
            index++;
          }
        });
  }

  public User getUserById(int id) {
    // 注意传入的是ConnectionCallback:
    return jdbcTemplate.execute(
        (Connection conn) -> {
          // 可以直接使用conn实例，不要释放它，回调结束后JdbcTemplate自动释放:
          // 在内部手动创建的PreparedStatement、ResultSet必须用try(...)释放:
          try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
              if (rs.next()) {
                return USER_ROW_MAPPER.mapRow(rs, rs.getRow());
              }
              throw new RuntimeException("user not found by id.");
            }
          }
        });
  }

  public List<User> getUsers(int pageIndex) {
    int limit = 100;
    int offset = limit * (pageIndex - 1);
    return jdbcTemplate.query(
        "SELECT * FROM user LIMIT ? OFFSET ?",
        new BeanPropertyRowMapper<>(User.class),
        limit,
        offset);
  }

  public void updateUser(User user) {
    // 传入SQL，SQL参数，返回更新的行数:
    if (1
        != jdbcTemplate.update(
            "UPDATE user SET name = ? WHERE id=?", user.getName(), user.getId())) {
      throw new RuntimeException("User not found by id");
    }
  }

  public User register(User user) {
    // 创建一个KeyHolder:
    KeyHolder holder = new GeneratedKeyHolder();
    if (1
        != jdbcTemplate.update(
            // 参数1:PreparedStatementCreator
            conn -> {
              // 创建PreparedStatement时，必须指定RETURN_GENERATED_KEYS:
              PreparedStatement ps =
                  conn.prepareStatement(
                      "INSERT INTO user(name, age, balance, sex) VALUES(?,?,?,?)",
                      Statement.RETURN_GENERATED_KEYS);
              ps.setString(1, user.getName());
              ps.setInt(2, user.getAge());
              ps.setBigDecimal(3, user.getBalance());
              ps.setObject(4, user.getSex());
              return ps;
            },
            // 参数2:KeyHolder
            holder)) {
      throw new RuntimeException("Insert failed.");
    }
    // 从KeyHolder中获取返回的自增值:
    return user.setId(Objects.requireNonNull(holder.getKey()).intValue());
  }
}
