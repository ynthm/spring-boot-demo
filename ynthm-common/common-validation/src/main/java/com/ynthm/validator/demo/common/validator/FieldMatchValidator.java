package com.ynthm.validator.demo.common.validator;

import com.ynthm.common.exception.BaseException;
import com.ynthm.common.util.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author ethan
 */
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
      final Object firstObj =
          BeanUtil.getPropertyIntroSpector(value.getClass(), value, firstFieldName);

      final Object secondObj =
          BeanUtil.getPropertyIntroSpector(value.getClass(), value, secondFieldName);

      return Objects.equals(firstObj, secondObj);
    } catch (final Exception e) {
      throw new BaseException(e);
    }
  }
}
