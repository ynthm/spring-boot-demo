package com.ynthm.common.mybatis.plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Ethan Wang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MybatisPlusConfig.class)
@MapperScan("com.ynthm.demo.**.mapper")
public @interface EnableMybatisPlus {}
