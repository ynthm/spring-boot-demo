package com.ynthm.validator.demo.common.util;

import org.hibernate.validator.HibernateValidator;

import javax.validation.*;
import java.util.Set;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class ValidationUtil {

  private ValidationUtil() {}
  /** 开启快速结束模式 failFast (true) */
  private static final Validator VALIDATOR;

  static {
    try (ValidatorFactory validatorFactory =
        Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(false)
            .buildValidatorFactory()) {
      VALIDATOR = validatorFactory.getValidator();
    }
  }

  /**
   * 校验对象
   *
   * @param t bean
   * @param groups 校验组
   * @return ValidResult
   */
  public static <T> ValidResult validateBean(T t, Class<?>... groups) {
    ValidResult result = new ValidResult();
    Set<ConstraintViolation<T>> violationSet = VALIDATOR.validate(t, groups);
    boolean hasError = violationSet != null && !violationSet.isEmpty();
    result.setHasErrors(hasError);
    if (hasError) {
      for (ConstraintViolation<T> violation : violationSet) {
        result.addError(violation.getPropertyPath().toString(), violation.getMessage());
      }
    }
    return result;
  }
  /**
   * 校验 bean 的某一个属性
   *
   * @param obj bean
   * @param propertyName 属性名称
   * @return ValidResult
   */
  public static <T> ValidResult validateProperty(T obj, String propertyName) {
    ValidResult result = new ValidResult();
    Set<ConstraintViolation<T>> violationSet = VALIDATOR.validateProperty(obj, propertyName);
    boolean hasError = violationSet != null && !violationSet.isEmpty();
    result.setHasErrors(hasError);
    if (hasError) {
      for (ConstraintViolation<T> violation : violationSet) {
        result.addError(propertyName, violation.getMessage());
      }
    }
    return result;
  }

  public static void addMessageToContext(
      ConstraintValidatorContext context, String property, String message) {
    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate(message)
        .addPropertyNode(property)
        .addConstraintViolation();
  }
}
