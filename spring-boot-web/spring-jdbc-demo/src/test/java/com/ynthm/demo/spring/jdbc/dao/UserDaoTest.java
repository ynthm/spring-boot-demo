package com.ynthm.demo.spring.jdbc.dao;

import com.ynthm.demo.spring.jdbc.entity.User;
import com.ynthm.demo.spring.jdbc.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ComponentScan("com.ynthm.demo.spring.jdbc.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDaoTest {

  @Autowired private UserDao userDao;

  @Test
  void getUserById() {
    User userById = userDao.getUserById(1);
    System.out.println(userById);
  }

  @Test
  void count() {
    System.out.println(userDao.count());
  }

  @Test
  void save() {
    String sql = "insert into user (name, age, balance, sex, create_time) values(?,?,?,?,?)";
    userDao.update(
        sql,
        List.of("name", "age", "balance", "sex", "create_time"),
        new User()
            .setName("ethan")
            .setAge(36)
            .setSex(Gender.MALE)
            .setBalance(new BigDecimal("999.99"))
            .setCreateTime(OffsetDateTime.now()));
  }

  @Test
  void update() {
    String sql = "update user set balance = ? where id = ?";
    System.out.println(
        userDao.update(
            sql, List.of("balance", "id"), new User().setBalance(new BigDecimal("100")).setId(1)));
  }

  @Test
  void deleteById() {}

  @Test
  void findAll() {
    System.out.println(userDao.findAll());
  }

  @Test
  void findByName() {
    System.out.println(userDao.findByName("ethan"));
  }

  @Test
  void findById() {
    System.out.println(userDao.findById(1));
  }

  @Test
  void getNameById() {
    System.out.println(userDao.getNameById(1));
  }

  @Test
  void batchUpdate() {
    String sql = "insert into user (name, age, balance, sex, create_time) values(?,?,?,?,?)";

    int[] result =
        userDao.batchUpdate(
            sql,
            List.of(
                new User()
                    .setName("zhangsan")
                    .setAge(30)
                    .setBalance(new BigDecimal("1000"))
                    .setSex(Gender.FEMALE)
                    .setCreateTime(OffsetDateTime.now())));
    System.out.println(result);
  }

  @Test
  void sectionBatchUpdate() {
    String sql = "insert into user (name, age, balance, sex, create_time) values(?,?,?,?,?)";

    int[][] result =
        userDao.sectionBatchUpdate(
            sql,
            List.of(
                new User()
                    .setName("zhangsan")
                    .setAge(30)
                    .setBalance(new BigDecimal("8888"))
                    .setSex(Gender.FEMALE)
                    .setCreateTime(OffsetDateTime.now())));
    System.out.println(result);
  }

  @Test
  void testGetUserById() {}

  @Test
  void getUsers() {}

  @Test
  void updateUser() {}

  @Test
  void register() {}
}
