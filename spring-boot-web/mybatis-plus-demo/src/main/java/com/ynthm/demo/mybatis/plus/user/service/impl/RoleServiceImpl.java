package com.ynthm.demo.mybatis.plus.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ynthm.demo.mybatis.plus.user.entity.Role;
import com.ynthm.demo.mybatis.plus.user.mapper.RoleMapper;
import com.ynthm.demo.mybatis.plus.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author ynthm
 * @since 2020-05-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {}
