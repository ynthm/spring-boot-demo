package com.ynthm.aop.demo.aspect;

import com.ynthm.aop.demo.handler.CdcServiceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
// @Aspect
@Component
public class MapperDeleteAspect {

  @Autowired CdcServiceHolder cdcServiceHolder;

  @Pointcut("execution(public * com.ynthm.aop.demo.mapper.*.delete*(..))")
  public void mapperAspect() {}

  @Before("mapperAspect()")
  public void doBefore(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    log.info(methodName);
    Object[] args = joinPoint.getArgs();
    Arrays.stream(args)
        .findFirst()
        .ifPresent(
            item -> {
              // cdcServiceHolder.getByClass(item).delete(item.toString());
            });
  }
}
