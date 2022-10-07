package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.util.ValidResult;
import com.ynthm.validator.demo.common.util.ValidationUtil;
import com.ynthm.validator.demo.common.validator.PhoneNumber;
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

  @Test
  void testAccount() {
    Account account = new Account();
    ValidResult validResult = ValidationUtil.validateBean(account);
    if (validResult.hasErrors()) {
      String errors = validResult.getErrors();
      System.out.println(errors);
    }

    account.setPhoneNumber(new PhoneNumber().setNumber(123123L));
    ValidResult validResult1 = ValidationUtil.validateBean(account);
    if (validResult1.hasErrors()) {
      String errors = validResult1.getErrors();
      System.out.println(errors);
    }
  }
}
