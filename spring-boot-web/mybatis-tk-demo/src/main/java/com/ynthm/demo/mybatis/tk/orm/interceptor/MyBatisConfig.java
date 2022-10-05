package com.ynthm.demo.mybatis.tk.orm.interceptor;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.ynthm.excel.demo.*.mapper"})
public class MyBatisConfig {}
