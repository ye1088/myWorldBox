package com.integralblue.httpresponsecache.compat.libcore.net.http;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.http.impl.cookie.DateUtils;

public final class HttpDate {
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMATS = new String[]{DateUtils.PATTERN_RFC1036, DateUtils.PATTERN_ASCTIME, "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            rfc1123.setTimeZone(TimeZone.getTimeZone("UTC"));
            return rfc1123;
        }
    };

    public static Date parse(String value) {
        try {
            return ((DateFormat) STANDARD_DATE_FORMAT.get()).parse(value);
        } catch (ParseException e) {
            String[] arr$ = BROWSER_COMPATIBLE_DATE_FORMATS;
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                try {
                    return new SimpleDateFormat(arr$[i$], Locale.US).parse(value);
                } catch (ParseException e2) {
                    i$++;
                }
            }
            return null;
        }
    }

    public static String format(Date value) {
        return ((DateFormat) STANDARD_DATE_FORMAT.get()).format(value);
    }
}
