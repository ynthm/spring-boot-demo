package com.ynthm.demo.web.web.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * A Publisher
 *
 * @author Ynthm Wang
 * @version 1.0
 */
@Component
public class CustomSpringEventPublisher {
  private final ApplicationEventPublisher applicationEventPublisher;

  public CustomSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publishCustomEvent(final String message) {
    System.out.println("Publishing custom event. ");
    CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
    applicationEventPublisher.publishEvent(customSpringEvent);
  }
}
