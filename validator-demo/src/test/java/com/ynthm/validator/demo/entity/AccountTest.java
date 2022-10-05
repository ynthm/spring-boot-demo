package com.ynthm.validator.demo.entity;

import com.ynthm.validator.demo.common.util.ValidResult;
import com.ynthm.validator.demo.common.util.ValidationUtil;
import com.ynthm.validator.demo.common.validator.PhoneNumber;
import org.junit.jupiter.api.Test;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
class AccountTest {
  @Test
  public void verifyPhoneNumber() {
    Account account = new Account();
    account.setAreaCode(-1);
    account.setPhoneNumber(null);

    ValidResult validResult = ValidationUtil.validateBean(account);
    if (validResult.hasErrors()) {
      String errors = validResult.getErrors();
      System.out.println(errors);
    }

    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setAreaCode(null);
    phoneNumber.setPhoneNumber(123L);

    ValidResult validResult1 = ValidationUtil.validateBean(phoneNumber);
    if (validResult1.hasErrors()) {
      String errors = validResult1.getErrors();
      System.out.println(errors);
    }

    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setPhoneNumber(phoneNumber);
    validResult1 = ValidationUtil.validateBean(registerRequest);
    if (validResult1.hasErrors()) {
      String errors = validResult1.getErrors();
      System.out.println(errors);
    }
  }
}
