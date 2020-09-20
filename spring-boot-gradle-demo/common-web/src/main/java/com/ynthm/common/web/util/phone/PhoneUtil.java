package com.ynthm.common.web.util.phone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

/** @author ethan */
public class PhoneUtil {
  private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

  private PhoneUtil() {}

  /**
   * 很可能是手机号码
   *
   * @param mobileNumber
   * @param locale
   * @return
   */
  public static boolean checkPhoneNumber(String mobileNumber, Locale locale) {

    if (phoneNumberUtil.isPossibleNumber(mobileNumber, Locale.SIMPLIFIED_CHINESE.getCountry())
        || phoneNumberUtil.isPossibleNumber(mobileNumber, locale.getCountry())) {
      return true;
    }
    return false;
  }

  /**
   * 根据国家代码和手机号 判断手机号是否有效
   *
   * @param mobileNumber 18588888888
   * @param region CN
   * @return 手机号是否正确
   */
  public static boolean checkPhoneNumber(String mobileNumber, String region)
      throws NumberParseException {
    Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(mobileNumber, region);

    return phoneNumberUtil.isValidNumber(phoneNumber);
  }

  /**
   * 根据国家代码和手机号 判断手机号是否有效
   *
   * @param phoneNumber 18588888888
   * @param countryCode 国家码 86
   * @return 手机号是否正确
   */
  public static boolean checkPhoneNumber(Long phoneNumber, int countryCode) {
    Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
    pn.setCountryCode(countryCode);
    pn.setNationalNumber(phoneNumber);
    return phoneNumberUtil.isValidNumber(pn);
  }
}
