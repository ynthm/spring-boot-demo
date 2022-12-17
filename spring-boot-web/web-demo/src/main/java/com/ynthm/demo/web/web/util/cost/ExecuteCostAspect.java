package com.ynthm.demo.web.web.util.cost;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author Ethan Wang
 */
@Slf4j
@Aspect
@Component
public class ExecuteCostAspect {

  @Pointcut("@annotation(executeCost)")
  public void serviceExecutionTimeLog(ExecuteCost executeCost) {
    /* point cut */
  }

  @Around(
      value = "serviceExecutionTimeLog(executeCost)",
      argNames = "proceedingJoinPoint,executeCost")
  public Object doAfter(ProceedingJoinPoint proceedingJoinPoint, ExecuteCost executeCost)
      throws Throwable {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Object proceed = proceedingJoinPoint.proceed();
    stopWatch.stop();

    log.info(
        "execute-time-name : [{}], execution-time : [{}], class-method : [{}]",
        executeCost.name(),
        stopWatch.getTotalTimeMillis(),
        proceedingJoinPoint.getTarget().getClass().getName()
            + "."
            + proceedingJoinPoint.getSignature().getName());

    return proceed;
  }
}
