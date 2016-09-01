package com.xpg.common.useful;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int getCurrentHour() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return instance.get(11);
    }

    public static int getCurrentMin() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return instance.get(12);
    }

    public static int getCurrentSec() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        return instance.get(13);
    }

    public static String getDateCN(Date date) {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date);
    }

    public static String getStringFromCurrentTime(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm").format(date);
    }

    public static int hourCastToMin(int i) {
        return i * 60;
    }

    public static int minCastToHour(int i) {
        return i / 60;
    }

    public static int minCastToHourMore(int i) {
        return i % 60;
    }
}
