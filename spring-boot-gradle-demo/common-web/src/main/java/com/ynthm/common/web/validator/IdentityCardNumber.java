package com.ynthm.common.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 身份证号码验证
 *
 * @author ethan
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentityCardNumberValidator.class)
public @interface IdentityCardNumber {

  String message() default "身份证号码不合法";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
