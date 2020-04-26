package com.ynthm.springbootdemo.util;

import com.ynthm.springbootdemo.exception.ApiException;
import com.ynthm.springbootdemo.exception.ValidateException;
import org.springframework.context.MessageSource;

/**
 * Author : Ynthm
 */
public class CheckUtil {

    private static MessageSource resources;

    public static void setResources(MessageSource resources) {
        CheckUtil.resources = resources;
    }

    public static void check(boolean condition, String msgKey, Object... args) {
        if (!condition) {
            fail(msgKey, args);
        }
    }

    public static void notEmpty(String str, String msgKey, Object... args) {
        if (str == null || str.isEmpty()) {
            fail(msgKey, args);
        }
    }

    public static void notNull(Object obj, String msgKey, Object... args) {
        if (obj == null) {
            fail(msgKey, args);
        }
    }

    private static void fail(String msgKey, Object... args) {
        throw new ValidateException(args);
    }


    public static String getMessage(ApiException e) {
        return resources.getMessage(e.getErrorCode().getMsgCode(), e.getParams(), UserContentUtil.getLocale());
    }
}
