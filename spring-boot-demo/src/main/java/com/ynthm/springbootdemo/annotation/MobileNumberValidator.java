package com.ynthm.springbootdemo.annotation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.ynthm.springbootdemo.domain.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** Author : Ynthm 手机号验证使用 https://github.com/google/libphonenumber/releases */
public class MobileNumberValidator implements ConstraintValidator<MobileNumber, PhoneNumber> {
  @Override
  public void initialize(MobileNumber constraintAnnotation) {}

  @Override
  public boolean isValid(PhoneNumber phoneNumber, ConstraintValidatorContext context) {

    if (phoneNumber.getLocale() == null || phoneNumber.getValue() == null) {
      return false;
    }

    try {
      PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
      return phoneNumberUtil.isValidNumber(
          phoneNumberUtil.parse(phoneNumber.getValue(), phoneNumber.getLocale()));
    } catch (NumberParseException e) {
      return false;
    }
  }
}
