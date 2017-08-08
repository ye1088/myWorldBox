package com.MCWorld.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.tools.ant.util.DateUtils;

/* compiled from: UtilsDate */
public class v {
    private static SimpleDateFormat bkS = null;
    private static String bkT = DateUtils.ISO8601_DATE_PATTERN;

    public static String getCurrentDate() {
        Date d = new Date();
        bkS = new SimpleDateFormat(bkT);
        return bkS.format(d);
    }

    public static String br(long time) {
        Date d = new Date(time);
        bkS = new SimpleDateFormat(bkT);
        return bkS.format(d);
    }

    public static String i(long time, long defaultTime) {
        if (time == 0) {
            return br(new Date().getTime());
        }
        return br(time);
    }

    public static long fB(String time) {
        bkS = new SimpleDateFormat(bkT);
        Date date = new Date();
        try {
            date = bkS.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String KM() {
        String currentDate = getCurrentDate();
        bkS = new SimpleDateFormat(bkT);
        Date date = new Date();
        try {
            date = bkS.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime());
    }
}
