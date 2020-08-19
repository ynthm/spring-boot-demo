package com.ynthm.validator.demo.common.util;

import com.ynthm.validator.demo.common.ResultCode;
import com.ynthm.validator.demo.common.exception.BaseException;
import com.ynthm.validator.demo.entity.User;
import org.slf4j.MDC;

import java.util.Locale;

/** @author ethan */
public class UserUtil {

  private UserUtil() {}

  private static final ThreadLocal<User> TL_USER = new ThreadLocal<>();

  private static final ThreadLocal<Locale> TL_LOCALE =
      ThreadLocal.withInitial(
          () ->
              // 语言的默认值
              Locale.SIMPLIFIED_CHINESE);

  public static final String KEY_USER = "user";

  public static void setUser(User user) {
    TL_USER.set(user);

    // 把用户信息放到log4j
    MDC.put(KEY_USER, user.getUsername());
  }

  /**
   * 如果没有登录，返回null
   *
   * @return
   */
  public static User getUserIfLogin() {
    return TL_USER.get();
  }

  /**
   * 如果没有登录会抛出异常
   *
   * @return 用户
   */
  public static User getUser() {
    User user = TL_USER.get();

    if (user == null) {
      throw new BaseException(ResultCode.USERNAME_NOT_FOUND);
    }

    return user;
  }

  public static long getUserId() {
    return getUser().getId();
  }

  public static void setLocale(Locale locale) {
    TL_LOCALE.set(locale);
  }

  public static Locale getLocale() {
    return TL_LOCALE.get();
  }

  public static void clearAllUserInfo() {
    TL_USER.remove();
    TL_LOCALE.remove();

    MDC.remove(KEY_USER);
  }

  /**
   * 是否管理员
   *
   * @return
   */
  public static boolean isAdmin() {
    //    List<Role> roles = getUser().getRoles();
    //
    //    for (Role role : roles) {
    //      if (Roles.ADMIN.equals(role.getName())) {
    //        return true;
    //      }
    //    }

    return false;
  }
}
