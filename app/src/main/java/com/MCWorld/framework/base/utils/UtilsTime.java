package com.MCWorld.framework.base.utils;

import android.content.Context;
import android.util.Log;
import com.MCWorld.framework.R$string;
import com.MCWorld.framework.base.widget.datetimepicker.SimpleMonthView;
import com.MCWorld.image.core.common.util.e;
import java.util.Calendar;
import java.util.IllegalFormatException;
import org.apache.tools.ant.taskdefs.WaitFor.Unit;
import org.apache.tools.ant.types.selectors.DepthSelector;

public class UtilsTime {
    public static final long DAYS_OF_MONTH = 30;
    public static final long DAYS_OF_YEAR = 365;
    public static final long HOURS_OF_DAY = 24;
    public static final long MILLIS_OF_SECOND = 1000;
    public static final long MINUTES_OF_HOUR = 60;
    public static final long MONTHS_OF_YEAR = 12;
    public static final long SECONDS_OF_MINUTE = 60;

    public static class DAYS {
        public static long toMillis(long days) {
            return UtilsTime.checkOverflow(days, 86400000);
        }

        public static long toSeconds(long days) {
            return UtilsTime.checkOverflow(days, 86400);
        }

        public static long toMinutes(long days) {
            return UtilsTime.checkOverflow(days, 1440);
        }

        public static long toHours(long days) {
            return UtilsTime.checkOverflow(days, 24);
        }

        public static long toMonths(long days) {
            return days / 30;
        }

        public static long toYears(long days) {
            return days / 365;
        }
    }

    public static class HOURS {
        public static long toMillis(long hours) {
            return UtilsTime.checkOverflow(hours, 3600000);
        }

        public static long toSeconds(long hours) {
            return UtilsTime.checkOverflow(hours, 3600);
        }

        public static long toMinutes(long hours) {
            return UtilsTime.checkOverflow(hours, 60);
        }

        public static long toDays(long hours) {
            return hours / 24;
        }

        public static long toMonths(long hours) {
            return toDays(hours) / 30;
        }

        public static long toYears(long hours) {
            return toDays(hours) / 365;
        }
    }

    public static class MILLIS {
        public static long toSeconds(long millis) {
            return millis / 1000;
        }

        public static long toMinutes(long millis) {
            return toSeconds(millis) / 60;
        }

        public static long toHours(long millis) {
            return toMinutes(millis) / 60;
        }

        public static long toDays(long millis) {
            return toHours(millis) / 24;
        }

        public static long toMonths(long millis) {
            return toDays(millis) / 30;
        }

        public static long toYears(long millis) {
            return toDays(millis) / 365;
        }
    }

    public static class MINUTES {
        public static long toMillis(long minutes) {
            return UtilsTime.checkOverflow(minutes, 60000);
        }

        public static long toSeconds(long minutes) {
            return UtilsTime.checkOverflow(minutes, 60);
        }

        public static long toHours(long minutes) {
            return minutes / 60;
        }

        public static long toDays(long minutes) {
            return toHours(minutes) / 24;
        }

        public static long toMonths(long minutes) {
            return toDays(minutes) / 30;
        }

        public static long toYears(long minutes) {
            return toDays(minutes) / 365;
        }
    }

    public static class MONTHS {
        public static long toMillis(long months) {
            return UtilsTime.checkOverflow(months, 2592000000L);
        }

        public static long toSeconds(long months) {
            return UtilsTime.checkOverflow(months, 2592000);
        }

        public static long toMinutes(long months) {
            return UtilsTime.checkOverflow(months, 43200);
        }

        public static long toHours(long months) {
            return UtilsTime.checkOverflow(months, 720);
        }

        public static long toDays(long months) {
            return UtilsTime.checkOverflow(months, 30);
        }

        public static long toYears(long months) {
            return months / 12;
        }
    }

    public static class SECONDS {
        public static long toMillis(long seconds) {
            return UtilsTime.checkOverflow(seconds, 1000);
        }

        public static long toMinutes(long seconds) {
            return seconds / 60;
        }

        public static long toHours(long seconds) {
            return toMinutes(seconds) / 60;
        }

        public static long toDays(long seconds) {
            return toHours(seconds) / 24;
        }

        public static long toMonths(long seconds) {
            return toDays(seconds) / 30;
        }

        public static long toYears(long seconds) {
            return toDays(seconds) / 365;
        }
    }

    public static class YEARS {
        public static long toMillis(long years) {
            return UtilsTime.checkOverflow(years, 31536000000L);
        }

        public static long toSeconds(long years) {
            return UtilsTime.checkOverflow(years, 31536000);
        }

        public static long toMinutes(long years) {
            return UtilsTime.checkOverflow(years, 525600);
        }

        public static long toHours(long years) {
            return UtilsTime.checkOverflow(years, 8760);
        }

        public static long toDays(long years) {
            return UtilsTime.checkOverflow(years, 365);
        }

        public static long toMonths(long years) {
            return UtilsTime.checkOverflow(years, 12);
        }
    }

    public static boolean isSameDay(long millis1, long millis2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(millis1);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(millis2);
        if (c1.get(1) == c2.get(1) && c1.get(2) == c2.get(2) && c1.get(5) == c2.get(5)) {
            return true;
        }
        return false;
    }

    public static boolean isSameWeek(long millis1, long millis2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(millis1);
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(millis2);
        if (c1.get(1) == c2.get(1) && c1.get(3) == c2.get(3)) {
            return true;
        }
        return false;
    }

    public static int getYear(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(1);
    }

    public static int getMonth(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(2) + 1;
    }

    public static int getDayOfMonth(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(5);
    }

    public static int getDayOfWeek(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(7);
    }

    public static int getHourOf12HClock(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(10);
    }

    public static int getHourOf24HClock(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(11);
    }

    public static int getMinute(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(12);
    }

    public static int getSecond(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(13);
    }

    public static long getExpireDeadTime(long millisToExpire) {
        return System.currentTimeMillis() + millisToExpire;
    }

    public static String getTimeStringFromMillis(long timeMillis) {
        return getTimeStringFromMillis(timeMillis, "%04d-%02d-%02d %02d:%02d:%2d");
    }

    public static String getTimeStringFromMillis(long timeMillis, String format) {
        if (format == null || format.length() == 0) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        int day = cal.get(5);
        int hour = cal.get(11);
        int min = cal.get(12);
        int sec = cal.get(13);
        String timeString = null;
        try {
            return String.format(format, new Object[]{Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day), Integer.valueOf(hour), Integer.valueOf(min), Integer.valueOf(sec)});
        } catch (IllegalFormatException e) {
            e.printStackTrace();
            return timeString;
        }
    }

    public static String getFormatTimeString(long timeMillis, String format) {
        if (format == null || format.length() == 0) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        int day = cal.get(5);
        int hour = cal.get(11);
        int min = cal.get(12);
        int sec = cal.get(13);
        try {
            return format.replaceAll(SimpleMonthView.VIEW_PARAMS_YEAR, String.valueOf(year)).replaceAll("mon", month < 10 ? "0" + month : "" + month).replaceAll(Unit.DAY, day < 10 ? "0" + day : "" + day).replaceAll(Unit.HOUR, hour < 10 ? "0" + hour : "" + hour).replaceAll(DepthSelector.MIN_KEY, min < 10 ? "0" + min : "" + min).replaceAll("sec", sec < 10 ? "0" + sec : "" + sec);
        } catch (Exception e) {
            Log.e("TimeUtils", "getFormatTimeString error", e);
            return null;
        }
    }

    public static String getPostTimeString(Context context, long timeMillis, boolean showToday, boolean showSecond) {
        if (context == null) {
            return null;
        }
        String sSpace = " ";
        String sToday = context.getString(R$string.str_today);
        String sYesterday = context.getString(R$string.str_yesterday);
        String sBeforeYesterday = context.getString(R$string.str_day_before_yesterday);
        String sShortDateFormat = "%d月%d日";
        String sDateFormat = "%d年%d月%d日";
        String sTimeFormat = "%02d:%02d:%02d";
        String sShortTimeFormat = "%02d:%02d";
        Calendar current = Calendar.getInstance();
        Calendar post = Calendar.getInstance();
        current.setTimeInMillis(System.currentTimeMillis());
        post.setTimeInMillis(timeMillis);
        int diffDays = current.get(6) - post.get(6);
        boolean isSameYear = post.get(1) == current.get(1);
        StringBuilder builder = new StringBuilder();
        if (diffDays > 0 || !isSameYear) {
            if (diffDays > 0 && diffDays <= 2) {
                if (diffDays != 1) {
                    sYesterday = sBeforeYesterday;
                }
                builder.append(sYesterday);
                builder.append(sSpace);
            } else if (isSameYear) {
                builder.append(String.format(sShortDateFormat, new Object[]{Integer.valueOf(post.get(2) + 1), Integer.valueOf(post.get(5))}));
                builder.append(sSpace);
            } else {
                builder.append(String.format(sDateFormat, new Object[]{Integer.valueOf(post.get(1)), Integer.valueOf(post.get(2) + 1), Integer.valueOf(post.get(5))}));
                builder.append(sSpace);
            }
        } else if (showToday) {
            builder.append(sToday);
            builder.append(sSpace);
        }
        if (showSecond) {
            builder.append(String.format(sTimeFormat, new Object[]{Integer.valueOf(post.get(11)), Integer.valueOf(post.get(12)), Integer.valueOf(post.get(13))}));
        } else {
            builder.append(String.format(sShortTimeFormat, new Object[]{Integer.valueOf(post.get(11)), Integer.valueOf(post.get(12))}));
        }
        return builder.toString();
    }

    private static long checkOverflow(long t, long scale) {
        if (t > Long.MAX_VALUE / scale) {
            return Long.MAX_VALUE;
        }
        if (t < Long.MIN_VALUE / scale) {
            return Long.MIN_VALUE;
        }
        return t * scale;
    }

    public static String calculatPlayTime(int milliSecondTime) {
        String sh;
        String sm;
        String ss;
        int hour = milliSecondTime / 3600000;
        int minute = (milliSecondTime - (((hour * 60) * 60) * 1000)) / e.yQ;
        int seconds = ((milliSecondTime - (((hour * 60) * 60) * 1000)) - ((minute * 60) * 1000)) / 1000;
        if (seconds >= 60) {
            seconds %= 60;
            minute += seconds / 60;
        }
        if (minute >= 60) {
            minute %= 60;
            hour += minute / 60;
        }
        if (hour < 10) {
            sh = "0" + String.valueOf(hour);
        } else {
            sh = String.valueOf(hour);
        }
        if (minute < 10) {
            sm = "0" + String.valueOf(minute);
        } else {
            sm = String.valueOf(minute);
        }
        if (seconds < 10) {
            ss = "0" + String.valueOf(seconds);
        } else {
            ss = String.valueOf(seconds);
        }
        if (hour <= 0) {
            return sm + ":" + ss;
        }
        return sh + ":" + sm + ":" + ss;
    }
}
