package com.ynthm.aop.demo.aspect;

import com.ynthm.aop.demo.handler.CdcService;
import com.ynthm.aop.demo.handler.CdcServiceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
// @Aspect
// @Component
public class MapperInsertOrUpdateAspect {

  @Autowired CdcServiceHolder cdcServiceHolder;

  @Pointcut(
      "execution(public * com.ynthm.aop.demo.mapper.*.insert*(..))||execution(public * com.ynthm.aop.demo.mapper.*.update*(..))")
  public void mapperAspect() {}

  @Before("mapperAspect()")
  public void doBefore(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    if (methodName.startsWith("update")) {
      Arrays.stream(args)
          .findFirst()
          .ifPresent(
              item -> {
                CdcService byClass = cdcServiceHolder.getByClass(item);
              });
    } else if (methodName.startsWith("insert")) {
      Arrays.stream(args)
          .findFirst()
          .ifPresent(
              item -> {
                CdcService byClass = cdcServiceHolder.getByClass(item);
                byClass.insert(item);
              });
    }
  }
}
