package com.ynthm.demo.web.web.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * A Simple Application Event
 *
 * @author Ynthm Wang
 * @version 1.0
 */
@Getter
public class CustomSpringEvent extends ApplicationEvent {
  private String message;

  public CustomSpringEvent(Object source, String message) {
    super(source);
    this.message = message;
  }
}
