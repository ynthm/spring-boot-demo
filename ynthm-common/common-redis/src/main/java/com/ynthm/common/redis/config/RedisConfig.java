package com.ynthm.common.redis.config;

import com.ynthm.common.redis.util.RedisUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.File;

/**
 * @author Ethan Wang
 */
@Configuration
@EnableCaching
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig {

  /**
   * RedisTemplate 需要手动配置 StringRedisTemplate 会自动配置
   *
   * @param connectionFactory 默认 LettuceConnectionFactory
   * @return RedisTemplate<String, Object> value 可以是 Serializable
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    template.setKeySerializer(RedisSerializer.string());
    template.setValueSerializer(RedisSerializer.json());

    template.setHashKeySerializer(RedisSerializer.string());
    template.setHashValueSerializer(RedisSerializer.json());
    // 使上面参数生效
    template.afterPropertiesSet();
    return template;
  }

  @Bean
  public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
    return new RedisUtil(redisTemplate);
  }

  @Bean
  public ValueOperations<String, Object> valueOps(RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForValue();
  }

  @Bean
  public HashOperations<String, String, Object> hashOps(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForHash();
  }

  /**
   * spring-boot-starter-data-redis 返回类型不支持 Integer
   * org.springframework.data.redis.connection.ReturnType
   *
   * @return DefaultRedisScript<Long>
   */
  @Bean
  public DefaultRedisScript<Long> fixedWindow() {
    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
    redisScript.setResultType(Long.class);
    redisScript.setScriptSource(
        new ResourceScriptSource(
            new ClassPathResource("lua" + File.separator + "fixed-window.lua")));
    return redisScript;
  }

  @Bean
  public DefaultRedisScript<Boolean> slidingWindow() {
    DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
    redisScript.setResultType(Boolean.class);
    redisScript.setScriptSource(
        new ResourceScriptSource(
            new ClassPathResource("lua" + File.separator + "sliding-window.lua")));
    return redisScript;
  }

  @Bean
  public RedisScript<Long> redisScript() {
    return RedisScript.of(
        new ClassPathResource("lua" + File.separator + "fixed-window.lua"), Long.class);
  }
}
