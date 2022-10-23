package com.ynthm.demo.security.config;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@FeignClient(configuration = FeignHttpsConfig.class)
public interface UserFeignClient {}
