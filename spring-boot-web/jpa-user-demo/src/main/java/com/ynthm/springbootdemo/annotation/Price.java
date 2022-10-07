package com.ynthm.springbootdemo.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.*;

/**
 * @author : Ynthm 组合方式定制化的 constraint
 */
@Min(0)
@Max(10000)
@Constraint(validatedBy = {})
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
  String message() default "错误的价格";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
