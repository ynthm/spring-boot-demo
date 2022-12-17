package com.ynthm.common.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ynthm.common.mybatis.plus.config.EntityMetaObjectHandler;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.springframework.context.annotation.Bean;

/**
 * @author Ethan Wang
 */
// @Configuration
public class MybatisPlusConfig {

  /**
   * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
   * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
    return interceptor;
  }

  @Bean
  public ConfigurationCustomizer configurationCustomizer() {
    return configuration -> configuration.setLogImpl(Slf4jImpl.class);
  }

  @Bean
  public EntityMetaObjectHandler entityMetaObjectHandler() {
    return new EntityMetaObjectHandler();
  }
}
