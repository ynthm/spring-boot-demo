
# Spring boot starter JDBC

## 数据初始化

测试针对真实数据库运行，则可以使用与@AutoConfigureTestDatabase注释

### @JdbcTest

- 在不使用 Spring Data JDBC 并且只需要 DataSource 的测试中使用
- @JdbcTest 创建一个 JdbcTemplate 用于测试内存嵌入式数据库设置
- 不扫描普通的 @ConfigurationProperties 和 @Component bean。
- 默认情况下，JDBC 为每个测试测试回滚
- 如果想使用实际的数据库，请使用 @AutoConfigureTestDatabase

```java
@JdbcTest
@Sql({"schema.sql", "test-data.sql"})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MyTransactionalTests {
  @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void whenInjectInMemoryDataSource_thenReturnCorrectEmployeeCount() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.setJdbcTemplate(jdbcTemplate);

        assertEquals(4, employeeDAO.getCountOfEmployees());
    }
}
```

http://localhost:8080/h2-console