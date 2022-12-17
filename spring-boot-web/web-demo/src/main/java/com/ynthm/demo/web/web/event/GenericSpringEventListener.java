package com.ynthm.demo.web.web.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Component
public class GenericSpringEventListener {
  @EventListener(condition = "#event.success")
  public void handleSuccessful(GenericSpringEvent<String> event) {
    System.out.println("Handling generic event (conditional).");
  }
}
