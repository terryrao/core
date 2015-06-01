package com.raowei.util;

/**
 * Created by terryrao on 5/25/2015.
 */
public class StringUtils {
    public static boolean isBlank (String string) {
        if (string == null || string.equals("")) {
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }
}
