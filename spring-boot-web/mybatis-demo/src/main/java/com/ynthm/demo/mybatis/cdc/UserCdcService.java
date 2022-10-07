package com.ynthm.demo.mybatis.cdc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynthm.demo.mybatis.entity.User;
import com.ynthm.demo.mybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
@Service
public class UserCdcService implements CdcService<User> {

  private final UserMapper userMapper;

  public UserCdcService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public Class<User> getBeanClass() {
    return User.class;
  }

  @Override
  public void insert(User item) {
    log.info("insert {}", item);
  }

  @Override
  public User before(User update) {
    return userMapper.selectByPrimaryKey(update.getId());
  }

  @Override
  public void update(User update, User before) {

    try {
      User user = new User();
      setPropertyIntroSpector(User.class, before, update, user);
      user.setId(before.getId());
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      log.info("update {}", objectMapper.writeValueAsString(user));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public <T> void setPropertyIntroSpector(Class<T> clazz, T before, T after, T result)
      throws Exception {
    BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
    PropertyDescriptor[] proDescriptors = beanInfo.getPropertyDescriptors();
    if (proDescriptors != null) {
      for (PropertyDescriptor propDesc : proDescriptors) {

        Method readMethod = propDesc.getReadMethod();
        Method writeMethod = propDesc.getWriteMethod();
        Object beforeValue = readMethod.invoke(before);
        Object afterValue = readMethod.invoke(after);
        if (!Objects.equals(beforeValue, afterValue)) {
          writeMethod.invoke(result, beforeValue);
        }
      }
    }
  }

  @Autowired private SqlSessionTemplate sqlSessionTemplate;

  @Override
  public void batchInsert(List<User> list) {

    SqlSession sqlSession =
        sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

    for (int i = 0; i < 100000; i++) {
      User user = new User();
      user.setId(i);
      user.setName("name" + i);
      userMapper.insert(user);
    }
    sqlSession.commit();
  }

  @Override
  public void delete(String id) {
    log.info("delete {}", id);
  }
}
