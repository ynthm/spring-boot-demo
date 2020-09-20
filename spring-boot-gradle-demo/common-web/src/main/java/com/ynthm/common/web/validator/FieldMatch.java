package com.ynthm.common.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 2 个字段值相同
 *
 * @author ethan
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {
  String message() default "two fields must equals.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /** @return The first field */
  String first();

  /** @return The second field */
  String second();

  /**
   * Defines several <code>@FieldMatch</code> annotations on the same element 多个相同字段确认
   *
   * @see FieldMatch
   */
  @Target({TYPE, ANNOTATION_TYPE})
  @Retention(RUNTIME)
  @Documented
  @interface List {
    FieldMatch[] value();
  }
}
