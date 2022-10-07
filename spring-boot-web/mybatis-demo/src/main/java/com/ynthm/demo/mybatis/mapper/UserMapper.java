package com.ynthm.demo.mybatis.mapper;

import com.ynthm.demo.mybatis.entity.User;

import java.util.List;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public interface UserMapper {
  void batchInsert(List<User> list);

  void insert(User user);

  User selectByPrimaryKey(Integer id);
}
