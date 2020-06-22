package com.ynthm.spring.jpa.demo;

import org.testcontainers.containers.MySQLContainer;

/**
 * spring.datasource.url=${DB_URL} spring.datasource.username=${DB_USERNAME}
 * spring.datasource.password=${DB_PASSWORD}
 *
 * <p>@Container public static TestMySQLContainer container = TestMySQLContainer.getInstance();
 */
public class TestMySQLContainer extends MySQLContainer<TestMySQLContainer> {

  private static final String IMAGE_VERSION = "mysql:8.0";
  private static TestMySQLContainer container;

  private TestMySQLContainer() {
    super(IMAGE_VERSION);
  }

  public static TestMySQLContainer getInstance() {
    if (container == null) {
      container = new TestMySQLContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
  }

  @Override
  public void stop() {
    // do nothing, JVM handles shut down
  }
}
