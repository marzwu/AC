package com.xpg.common.useful;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtils {
    public static final String ENCODING_UTF8 = "utf-8";

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static StringBuffer getBuffer() {
        return new StringBuffer(50);
    }

    public static StringBuffer getBuffer(int i) {
        return new StringBuffer(i);
    }

    public static String getRandomStr(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();
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

    public static boolean isEmpty(String str) {
        return str == null || str == "" || str.trim().equals("");
    }

    public static boolean isNumEmpty(String str) {
        return str == null || str == "" || str.trim().equals("") || str.trim().equals("0");
    }

    public static String sqliteEscape(String str) {
        return str.replace("/", "//").replace("'", "''").replace("[", "/[").replace("]", "/]").replace("%", "/%").replace("&", "/&").replace("_", "/_").replace("(", "/(").replace(")", "/)");
    }

    public static String sqliteUnEscape(String str) {
        return str.replace("//", "/").replace("''", "'").replace("/[", "[").replace("/]", "]").replace("/%", "%").replace("/&", "&").replace("/_", "_").replace("/(", "(").replace("/)", ")");
    }

    public static boolean toBoolean(String str) {
        return !isEmpty(str) && str.equals("true");
    }

    public static float toFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return 0.0f;
        }
    }

    public static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
