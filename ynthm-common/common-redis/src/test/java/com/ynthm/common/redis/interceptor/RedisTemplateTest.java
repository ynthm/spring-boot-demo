// package com.ynthm.common.redis.interceptor;
//
// import com.google.common.collect.Lists;
// import com.google.common.io.ByteSource;
// import com.ynthm.common.constant.Constant;
// import com.ynthm.common.web.util.ResourceUtil;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.data.redis.core.ValueOperations;
// import org.springframework.data.redis.core.script.DefaultRedisScript;
// import org.springframework.data.redis.core.script.RedisScript;
//
// import javax.annotation.Resource;
// import java.io.File;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.ArrayList;
//
// @SpringBootTest
// class RedisTemplateTest {
//
//  @Autowired private RedisTemplate<String, Object> redisTemplate;
//
//  @Autowired private StringRedisTemplate stringRedisTemplate;
//
//  /** Value 存入Long 获取 为 Integer 代码中用Long 类型接收报类型转化错误 */
//  @Resource(name = "stringRedisTemplate")
//  private ValueOperations<String, Object> valueOperations;
//
//  @Autowired private ValueOperations<String, Object> valueOps;
//
//  @Autowired private DefaultRedisScript<Long> fixedWindow;
//
//  @Test
//  void test1() {
//    String key = "redis:demo:abc";
//    stringRedisTemplate.opsForValue().get(key);
//  }
//
//  @Test
//  void test2() throws IOException {
//    InputStream inputStream =
//        ResourceUtil.getResourceAsStream(
//            null, File.separator + "lua" + File.separator + "fixed-window.lua");
//    ByteSource byteSource =
//        new ByteSource() {
//          @Override
//          public InputStream openStream() throws IOException {
//            return inputStream;
//          }
//        };
//    String s = byteSource.asCharSource(Constant.CHARSET_UTF_8).read();
//
//    // spring-boot-starter-data-redis 返回类型不支持 Integer
//    // org.springframework.data.redis.connection.ReturnType
//    RedisScript<Long> redisScript = new DefaultRedisScript<>(s, Long.class);
//    String keyLimit = "key:abc";
//    ArrayList<String> keys = Lists.newArrayList(keyLimit);
//    Long result = stringRedisTemplate.execute(redisScript, keys, "2", "60");
//    Long result1 = stringRedisTemplate.execute(redisScript, keys, "2", "60");
//    Long result2 = stringRedisTemplate.execute(redisScript, keys, "2", "60");
//    System.out.println(stringRedisTemplate.boundValueOps(keyLimit).get());
//  }
//
//  @Test
//  void test3() {
//
//    String keyLimit = "key:001";
//    ArrayList<String> keys = Lists.newArrayList(keyLimit);
//    Long result = stringRedisTemplate.execute(fixedWindow, keys, "2", "60");
//    Long result1 = stringRedisTemplate.execute(fixedWindow, keys, "2", "60");
//    Long result2 = stringRedisTemplate.execute(fixedWindow, keys, "2", "60");
//    System.out.println(stringRedisTemplate.boundValueOps(keyLimit).get());
//  }
//
//  @Test
//  void test4() {}
// }
