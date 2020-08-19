package com.ynthm.validator.demo.common.validator;

import cn.hutool.core.bean.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** @author ethan */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

  private String firstFieldName;
  private String secondFieldName;

  @Override
  public void initialize(FieldMatch constraintAnnotation) {
    this.firstFieldName = constraintAnnotation.first();
    this.secondFieldName = constraintAnnotation.second();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {

      final Object firstObj = BeanUtil.getProperty(value, firstFieldName);
      final Object secondObj = BeanUtil.getProperty(value, secondFieldName);

      return firstObj == null && secondObj == null
          || firstObj != null && firstObj.equals(secondObj);
    } catch (final Exception e) {
      // e
    }
    return true;
  }
}
