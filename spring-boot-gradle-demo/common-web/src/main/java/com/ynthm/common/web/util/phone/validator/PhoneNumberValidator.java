package com.ynthm.common.web.util.phone.validator;

import cn.hutool.core.bean.BeanUtil;
import com.ynthm.common.web.util.phone.PhoneUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** @author ethan */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Object> {

  private String areaCodeField;
  private String phoneNumberField;

  @Override
  public void initialize(PhoneNumber constraintAnnotation) {
    this.areaCodeField = constraintAnnotation.areaCode();
    this.phoneNumberField = constraintAnnotation.phoneNumber();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    boolean result = false;
    try {

      final Object firstObj = BeanUtil.getProperty(value, areaCodeField);
      final Object secondObj = BeanUtil.getProperty(value, phoneNumberField);
      if (firstObj == null || secondObj == null) {
        return false;
      }
      int areaCode = Integer.parseInt(firstObj.toString());
      Long phoneNumber = Long.parseLong(secondObj.toString());
      if (PhoneUtil.checkPhoneNumber(phoneNumber, areaCode)) {
        result = true;
      }
    } catch (final Exception e) {
      // e
    }
    return result;
  }
}
