package com.ynthm.demo.mybatis.tk.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.ynthm.demo.mybatis.tk.*.mapper"})
public class MyBatisConfig {}
