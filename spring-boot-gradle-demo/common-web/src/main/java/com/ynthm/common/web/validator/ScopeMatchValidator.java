package com.ynthm.common.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** @author ynthm */
public class ScopeMatchValidator implements ConstraintValidator<ScopeMatch, Object> {

  private Set<String> scopes = new HashSet<>();

  @Override
  public void initialize(ScopeMatch constraintAnnotation) {
    scopes = new HashSet<>(Arrays.asList(constraintAnnotation.scopes()));
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    return !scopes.isEmpty() && scopes.contains(String.valueOf(value));
  }
}
