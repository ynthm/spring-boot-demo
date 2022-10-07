package com.ynthm.demo.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.ynthm.demo.**.mapper"})
public class MyBatisConfig {}
