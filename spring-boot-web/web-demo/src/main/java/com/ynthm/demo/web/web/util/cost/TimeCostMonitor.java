package com.ynthm.demo.web.web.util.cost;

import org.springframework.util.StopWatch;

/**
 * SLF4J extensions Profiler 可以结合日志使用
 *
 * @author Ethan Wang
 */
public class TimeCostMonitor {
  private final StopWatch stopWatch;

  public TimeCostMonitor(StopWatch stopWatch) {
    this.stopWatch = stopWatch;
  }

  public void start(String taskName) {
    stopWatch.start(taskName);
  }

  public void stop() {
    stopWatch.stop();
  }

  public String prettyPrint() {
    return stopWatch.prettyPrint();
  }
}
