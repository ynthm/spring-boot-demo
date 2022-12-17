package com.ynthm.demo.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(JsonFileArgumentsProvider.class)
public @interface JsonFileSource {
  String[] resources();

  String[] paths() default {};

  Class<?> clazz() default ObjectNode.class;
}
