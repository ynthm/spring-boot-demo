package com.ynthm.aop.demo.mapper;

import com.ynthm.aop.demo.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public interface UserMapper extends Mapper<User> {
  void batchInsert(List<User> list);
}
