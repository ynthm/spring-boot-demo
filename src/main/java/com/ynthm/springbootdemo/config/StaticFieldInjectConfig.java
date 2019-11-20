package com.ynthm.springbootdemo.config;

import com.ynthm.springbootdemo.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author : Ynthm
 */
@Component
public class StaticFieldInjectConfig {

    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    private void init() {
        CheckUtil.setResources(messageSource);
    }
}
