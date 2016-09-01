package com.gizwits.framework.utils;

import java.util.regex.Pattern;

class RegexUtils {
    static boolean flag = false;
    static String regex = "";

    RegexUtils() {
    }

    private static boolean check(String str, String str2) {
        try {
            flag = Pattern.compile(str2).matcher(str).matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkCellphone(String str) {
        return check(str, "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");
    }

    public static boolean checkEmail(String str) {
        return check(str, "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    public static boolean checkPassword(String str) {
        return check(str, "^[\\x10-\\x1f\\x21-\\x7f]+$");
    }

    public static boolean isEmpty(String str) {
        return str == null || str == "" || str.trim().equals("");
    }
}
