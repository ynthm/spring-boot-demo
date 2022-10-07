package com.ynthm.common.web.validator;

import cn.hutool.core.util.IdcardUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** @author ethan */
public class IdentityCardNumberValidator
    implements ConstraintValidator<IdentityCardNumber, Object> {
  @Override
  public void initialize(IdentityCardNumber constraintAnnotation) {}

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return IdcardUtil.isValidCard(String.valueOf(value));
  }
}
