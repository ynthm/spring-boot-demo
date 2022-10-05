package com.ynthm.validator.demo.common.validator;

import com.ynthm.validator.demo.common.ResultCode;
import com.ynthm.validator.demo.common.exception.BaseException;
import com.ynthm.validator.demo.common.util.PhoneUtil;
import com.ynthm.validator.demo.common.util.ValidationUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** @author ethan */
public class PhoneNumberValidator implements ConstraintValidator<VerifyPhoneNumber, PhoneNumber> {

  @Override
  public boolean isValid(PhoneNumber value, ConstraintValidatorContext context) {
    boolean result = false;
    try {
      final Integer areaCode = value.getAreaCode();
      final Long phoneNumber = value.getPhoneNumber();

      if (areaCode == null) {
        // 自定义验证错误信息
        ValidationUtil.addMessageToContext(
            context, "areaCode", "{javax.validation.constraints.NotNull.message}");
        return false;
      }

      if (phoneNumber == null) {
        ValidationUtil.addMessageToContext(context, "phoneNumber", "请输入参数");
        return false;
      }

      if (PhoneUtil.checkPhoneNumber(phoneNumber, areaCode)) {
        result = true;
      }
    } catch (final Exception e) {
      throw new BaseException(ResultCode.VALID_ERROR, e);
    }
    return result;
  }
}
