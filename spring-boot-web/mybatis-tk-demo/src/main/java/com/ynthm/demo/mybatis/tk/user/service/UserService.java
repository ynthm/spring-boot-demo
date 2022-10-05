package com.ynthm.demo.mybatis.tk.user.service;

import com.github.pagehelper.PageInfo;
import com.ynthm.demo.mybatis.tk.user.entity.User;

import java.util.List;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public interface UserService {

  void saveBatchExecutorType(List<User> userList);

  PageInfo<User> queryPage(Integer pageNo, Integer pageSize);
}
