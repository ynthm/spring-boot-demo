package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.util.ValidResult;
import com.ynthm.validator.demo.common.util.ValidationUtil;
import org.junit.jupiter.api.Test;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
class PersonTest {

  @Test
  void getName() {
    Person person = new Person();
    ValidResult validResult1 = ValidationUtil.validateBean(person);
    if (validResult1.hasErrors()) {
      String errors = validResult1.getErrors();
      System.out.println(errors);
    }
  }
}
