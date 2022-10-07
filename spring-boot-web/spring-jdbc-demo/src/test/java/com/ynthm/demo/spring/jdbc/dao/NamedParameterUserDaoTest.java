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
class NamedParameterUserDaoTest {

  @Autowired private NamedParameterUserDao namedParameterUserDao;

  @Test
  void count() {}

  @Test
  void update() {}

  @Test
  void deleteById() {}

  @Test
  void findAll() {}

  @Test
  void findByName() {}

  @Test
  void findById() {}

  @Test
  void getNameById() {}

  @Test
  void batchUpdate() {}

  @Test
  void testBatchUpdate() {}

  @Test
  void sectionBatchUpdate() {
    String sql =
        "insert into user (name, age, balance, sex, create_time)"
            + " values (:name, :age, :balance, :sex, :createTime)";
    int[][] result =
        namedParameterUserDao.sectionBatchUpdate(
            sql,
            List.of(
                new User()
                    .setName("ethan")
                    .setAge(36)
                    .setSex(Gender.MALE)
                    .setBalance(new BigDecimal("999.99"))
                    .setCreateTime(OffsetDateTime.now())));

    System.out.println(result);
  }
}
