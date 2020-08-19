package com.ynthm.validator.demo.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** @author ethan */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {
  String message() default "{constraints.phone.number.rightful}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /** @return The first field */
  String areaCode() default "areaCode";

  /** @return The second field */
  String phoneNumber() default "phoneNumber";
}
