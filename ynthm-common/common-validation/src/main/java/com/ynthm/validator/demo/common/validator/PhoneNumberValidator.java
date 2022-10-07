package com.ynthm.validator.demo.common.validator;

import com.ynthm.common.enums.BaseResultCode;
import com.ynthm.common.exception.BaseException;
import com.ynthm.validator.demo.common.util.PhoneUtil;
import com.ynthm.validator.demo.common.util.ValidationUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ethan
 */
public class PhoneNumberValidator implements ConstraintValidator<VerifyPhoneNumber, PhoneNumber> {

  @Override
  public boolean isValid(PhoneNumber value, ConstraintValidatorContext context) {
    boolean result = false;
    try {
      Integer areaCode = value.getAreaCode();
      final Long phoneNumber = value.getNumber();

      if (areaCode == null) {
        areaCode = 86;
      }

      if (PhoneUtil.checkPhoneNumber(phoneNumber, areaCode)) {
        result = true;
      } else {
        ValidationUtil.addMessageToContext(context, "phoneNumber", "非法的电话号码");
      }
    } catch (final Exception e) {
      throw new BaseException(BaseResultCode.VALID_ERROR, e);
    }
    return result;
  }
}
