package com.ynthm.demo.mybatis.plus.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ynthm.demo.mybatis.plus.user.entity.UserRole;
import com.ynthm.demo.mybatis.plus.user.mapper.UserRoleMapper;
import com.ynthm.demo.mybatis.plus.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author ynthm
 * @since 2020-05-09
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {}
