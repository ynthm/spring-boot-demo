package com.ynthm.demo.web.web.event.system;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
  @Override
  public void onApplicationEvent(ContextRefreshedEvent cse) {
    System.out.println("Handling context re-freshed event. ");
  }
}
