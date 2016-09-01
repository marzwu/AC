package com.gizwits.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static StringBuffer getBuffer() {
        return new StringBuffer(50);
    }

    public static StringBuffer getBuffer(int i) {
        return new StringBuffer(i);
    }

    public static String getStrDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getStrDate(long j, String str) {
        return getStrDate(new Date(j), str);
    }

    public static String getStrDate(String str, String str2) {
        return isEmpty(str) ? "" : getStrDate(new Date(Long.parseLong(str)), str2);
    }

    public static String getStrDate(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }

    public static String getStrFomat(String str, int i, boolean z) {
        if (str.length() <= i) {
            return str;
        }
        String substring = str.substring(0, i);
        return z ? new StringBuilder(String.valueOf(substring)).append("...").toString() : substring;
    }

    public static boolean isEmpty(String str) {
        return str == null || str == "" || str.trim().equals("");
    }

    public static String sqliteEscape(String str) {
        return str.replace("/", "//").replace("'", "''").replace("[", "/[").replace("]", "/]").replace("%", "/%").replace("&", "/&").replace("_", "/_").replace("(", "/(").replace(")", "/)");
    }

    public static String sqliteUnEscape(String str) {
        return str.replace("//", "/").replace("''", "'").replace("/[", "[").replace("/]", "]").replace("/%", "%").replace("/&", "&").replace("/_", "_").replace("/(", "(").replace("/)", ")");
    }

    public static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
