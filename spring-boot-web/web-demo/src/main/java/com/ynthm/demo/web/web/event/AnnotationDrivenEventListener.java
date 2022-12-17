package com.ynthm.demo.web.web.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Annotation-Driven Event Listener
 *
 * @author Ynthm Wang
 * @version 1.0
 */
@Slf4j
@Component
public class AnnotationDrivenEventListener {
  @EventListener
  public void handleContextStart(ContextStartedEvent cse) {
    log.info("Handling context started event.");
  }

  @EventListener
  public void handleApplicationReady(ApplicationReadyEvent are) {
    log.info("ApplicationReadyEvent");
  }
}
