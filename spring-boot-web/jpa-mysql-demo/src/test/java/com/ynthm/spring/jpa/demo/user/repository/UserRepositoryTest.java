package com.ynthm.spring.jpa.demo.user.repository;

import com.ynthm.spring.jpa.demo.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 默认情况下数据库操作会回滚
 *
 * <p>默认情况下 @DataJpaTest 用嵌入式数据库替换你的 DataSource 使用正式datasource 请设置
 * AutoConfigureTestDatabase.Replace.NONE
 *
 * @author Ynthm
 * @version 1.0
 * @date 2020/6/7 下午1:09
 */
@Testcontainers
@DataJpaTest
// @Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {UserRepositoryTest.Initializer.class})
public class UserRepositoryTest {

  /** 静态在最后一个测试删除容器 */
  @Container private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0");

  static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
              "spring.datasource.url=" + MY_SQL_CONTAINER.getJdbcUrl(),
              "spring.datasource.username=" + MY_SQL_CONTAINER.getUsername(),
              "spring.datasource.password=" + MY_SQL_CONTAINER.getPassword())
          .applyTo(configurableApplicationContext.getEnvironment());
    }
  }

  @Autowired private UserRepository userRepository;
  @Autowired private TestEntityManager entityManager;

  @Test
  void testSaveUser() {

    User user = new User();
    user.setFirstName("Ynthm");
    user.setUsername("iynthm");
    user.setLastName("Wang");
    user.setEmailAddress("iwys@qq.com");
    user.setPassword("123456");

    userRepository.save(user);
    //    Integer id = entityManager.persistAndGetId(user, Integer.class);
    //    assertNotNull(id);

    User userFind = userRepository.findByUsername("iynthm");
    assertNotNull(userFind);
    assertEquals(userFind.getFirstName(), user.getFirstName());
    assertEquals(userFind.getLastName(), user.getLastName());
  }

  @Test
  public void deleteByUserIdTest() {
    User user = new User().setFirstName("Ethan").setLastName("Wang").setEmailAddress("iwys@qq.com");
    User saved = userRepository.save(user);
    userRepository.deleteById(saved.getId());
  }
}
