package com.ynthm.common.web.config;

import com.ynthm.common.web.enums.EnumeratorConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ethan Wang
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverterFactory(new EnumeratorConverterFactory());
  }
}
