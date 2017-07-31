package com.huluxia.utils;

import hlx.data.localstore.a;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* compiled from: UtilsTime */
public class az {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String bnj = "yy-MM-dd";
    private static final String bnk = "yy年MM月dd日";
    private static final String bnl = "HH:mm";

    public static String bB(long seconds) {
        if (seconds < 60) {
            return "刚刚";
        }
        if (seconds < 3600) {
            return (seconds / 60) + "分钟前";
        }
        if (seconds < 86400) {
            return ((seconds / 60) / 60) + "小时前";
        }
        return (((seconds / 60) / 60) / 24) + "天前";
    }

    public static String bC(long seconds) {
        if (seconds < 300) {
            return "刚刚";
        }
        if (seconds < 3600) {
            return (seconds / 60) + "分钟前";
        }
        if (seconds < 86400) {
            return ((seconds / 60) / 60) + "小时前";
        }
        if (seconds < 2592000) {
            return (((seconds / 60) / 60) / 24) + "天前";
        }
        if (seconds < 31104000) {
            return ((((seconds / 60) / 60) / 24) / 30) + "月前";
        }
        if (seconds < 62208000) {
            return "1年前";
        }
        if (seconds < 93312000) {
            return "2年前";
        }
        if (seconds < 124416000) {
            return "3年前";
        }
        return "4年前";
    }

    public static String bD(long seconds) {
        long diffseconds = (System.currentTimeMillis() - seconds) / 1000;
        if (diffseconds < 300) {
            return "刚刚";
        }
        if (diffseconds < 3600) {
            return (diffseconds / 60) + "分钟前";
        }
        if (diffseconds < 7200) {
            return ((diffseconds / 60) / 60) + "小时前";
        }
        return a(seconds, true, false);
    }

    public static String bE(long seconds) {
        return new SimpleDateFormat("M月d日 hh:mm", Locale.getDefault()).format(new Date(seconds));
    }

    public static String bF(long seconds) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(seconds));
    }

    public static String bG(long meters) {
        if (meters <= 10) {
            return "0.01km";
        }
        if (meters <= 10000) {
            return String.format(Locale.getDefault(), "%.2fkm", new Object[]{Double.valueOf(((double) meters) / 1000.0d)});
        } else if (meters <= 100000) {
            return String.format(Locale.getDefault(), "%.1fkm", new Object[]{Double.valueOf(((double) meters) / 1000.0d)});
        } else {
            return String.format(Locale.getDefault(), "%.0fkm", new Object[]{Double.valueOf(((double) meters) / 1000.0d)});
        }
    }

    public static String bH(long time) {
        return a(time, false, false);
    }

    private static String a(long time, boolean chineseDate, boolean displayToday) {
        Calendar nowCal = Calendar.getInstance();
        Date timeD = new Date(time);
        String timeStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(timeD);
        if (timeStr.equals(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(nowCal.getTime()))) {
            return new StringBuilder(displayToday ? "今天 " : "").append(new SimpleDateFormat(bnl, Locale.getDefault()).format(timeD)).toString();
        }
        nowCal.add(5, -1);
        if (timeStr.equals(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(nowCal.getTime()))) {
            return "昨天 " + new SimpleDateFormat(bnl, Locale.getDefault()).format(timeD);
        }
        nowCal.add(5, -1);
        if (timeStr.equals(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(nowCal.getTime()))) {
            return "前天 " + new SimpleDateFormat(bnl, Locale.getDefault()).format(timeD);
        }
        nowCal.add(5, 3);
        if (timeStr.equals(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(nowCal.getTime()))) {
            return "明天 " + new SimpleDateFormat(bnl, Locale.getDefault()).format(timeD);
        }
        nowCal.add(5, 1);
        if (timeStr.equals(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(nowCal.getTime()))) {
            return "后天 " + new SimpleDateFormat(bnl, Locale.getDefault()).format(timeD);
        }
        return new SimpleDateFormat(chineseDate ? bnk : bnj, Locale.getDefault()).format(timeD);
    }

    public static String gz(String createTime) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date(1000 * Long.parseLong(createTime)));
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public static String lw(int second) {
        String _StrTime = "";
        int _day = second / 86400;
        int _hour = (second / 3600) % 24;
        int _minute = (second / 60) % 60;
        int _second = second % 60;
        if (_day > 0) {
            _StrTime = _StrTime + _day + "天";
        }
        if (_hour > 9) {
            _StrTime = _StrTime + _hour + a.bLs;
        } else if (_hour > 0) {
            _StrTime = _StrTime + "0时";
        }
        if (_minute > 9) {
            _StrTime = _StrTime + _minute + a.bLt;
        } else if (_minute > 0) {
            _StrTime = _StrTime + "0" + _minute + a.bLt;
        }
        if (_second > 9) {
            return _StrTime + _second + a.bLu;
        }
        return _StrTime + "0" + _second + a.bLu;
    }
}
