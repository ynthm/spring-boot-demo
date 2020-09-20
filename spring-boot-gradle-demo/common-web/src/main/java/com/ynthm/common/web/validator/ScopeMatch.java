package com.ynthm.common.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** @author ynthm */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ScopeMatchValidator.class)
public @interface ScopeMatch {

  String message() default "value is not in scope";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /** @return */
  String[] scopes();
}
