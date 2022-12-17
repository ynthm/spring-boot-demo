package com.ynthm.demo.web.web.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * A Listener
 *
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
  @Override
  public void onApplicationEvent(CustomSpringEvent event) {
    log.info("Received spring custom event - {}", event.getMessage());
  }
}
