package com.ynthm.spring.jpa.demo;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

/**
 * 单一容器模式
 *
 * <p>不使用@Testcontainers，因为此注解会确保容器在每次测试后启动和停止。手动启动数据库。 测试完手动停止数据库。
 *
 * <p>每个需要数据库的测试类都扩展此抽象类。 必须配置的唯一一件事就是应如何初始化应用程序上下文。
 *
 * <p>这意味着，当需要整个应用程序上下文时，请使用@SpringBootTest 。
 * 如果只需要持久层，则将@DataJpaTest(默认启动嵌入式内存数据库)与@AutoConfigureTestDatabase(replace =
 * AutoConfigureTestDatabase.Replace.NONE) 可禁用启动内存数据库 。
 */
public abstract class AbstractContainerBaseTest {
  protected static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0");

  static {
    MY_SQL_CONTAINER.start();
  }

  @DynamicPropertySource
  static void databaseProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
  }
}
