package com.ynthm.demo.security.config;

import com.ynthm.demo.security.entity.BaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.time.OffsetDateTime;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Configuration
@EnableJdbcRepositories
public class DataJdbcConfig extends AbstractJdbcConfiguration {

  @Bean
  public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean
  public TransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public BeforeSaveCallback<BaseEntity> absEntityBeforeSet() {
    return (entity, aggregateChange) -> {
      entity.setLastUpdateTime(OffsetDateTime.now());
      return entity;
    };
  }
}
