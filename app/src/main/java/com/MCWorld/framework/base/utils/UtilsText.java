package com.MCWorld.framework.base.utils;

import android.support.v4.media.session.PlaybackStateCompat;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class UtilsText {
    public static String getTimeLength(long lenth) {
        long hour = 0;
        if (lenth > 3600) {
            hour = (lenth / 60) / 60;
            lenth %= 3600;
        }
        long min = lenth / 60;
        long sec = lenth % 60;
        StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append(hour < 10 ? "0" + hour : String.valueOf(hour)).append(":");
        }
        sb.append(min < 10 ? "0" + min : String.valueOf(min));
        sb.append(":");
        sb.append(sec < 10 ? "0" + sec : String.valueOf(sec));
        return sb.toString();
    }

    public static String getTimeAgo(long ts) {
        int diff = (int) ((System.currentTimeMillis() - ts) / 1000);
        if (diff < 0) {
            return "刚刚";
        }
        int ago = diff / 60;
        if (ago == 0) {
            return String.format("%d秒前", new Object[]{Integer.valueOf(diff % 60)});
        } else if (ago < 60) {
            return String.format("%d分钟前", new Object[]{Integer.valueOf(diff / 60)});
        } else {
            if (diff / 3600 < 24) {
                return String.format("%d小时前", new Object[]{Integer.valueOf(diff / 3600)});
            }
            return String.format("%d天前", new Object[]{Integer.valueOf((diff / 3600) / 24)});
        }
    }

    public static String getDay(long ts) {
        return new SimpleDateFormat("MM-dd").format(new Date(ts));
    }

    public static String getYmd(long ts) {
        return new SimpleDateFormat("yy-MM-dd").format(new Date(ts));
    }

    public static String getFileLength(long bytelength) {
        if (bytelength < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return String.format("%d b", new Object[]{Long.valueOf(bytelength)});
        } else if (bytelength < 1048576) {
            float kb = ((float) bytelength) / 1024.0f;
            return String.format("%.1f kb", new Object[]{Float.valueOf(kb)});
        } else if (bytelength < 1073741824) {
            float mb = (((float) bytelength) / 1024.0f) / 1024.0f;
            return String.format("%.1f m", new Object[]{Float.valueOf(mb)});
        } else {
            float gb = ((((float) bytelength) / 1024.0f) / 1024.0f) / 1024.0f;
            return String.format("%.1f G", new Object[]{Float.valueOf(gb)});
        }
    }

    public static String getFileLengthWith2Float(long bytelength) {
        if (bytelength < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return String.format("%.2f B", new Object[]{Float.valueOf((float) bytelength)});
        } else if (bytelength < 1048576) {
            float kb = ((float) bytelength) / 1024.0f;
            return String.format("%.2f KB", new Object[]{Float.valueOf(kb)});
        } else if (bytelength < 1073741824) {
            float mb = (((float) bytelength) / 1024.0f) / 1024.0f;
            return String.format("%.2f MB", new Object[]{Float.valueOf(mb)});
        } else {
            float gb = ((((float) bytelength) / 1024.0f) / 1024.0f) / 1024.0f;
            return String.format("%.2f GB", new Object[]{Float.valueOf(gb)});
        }
    }

    public static String getUrlWithParam(String url, Map<String, String> param) {
        if (UtilsFunction.empty(param)) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        for (String key : param.keySet()) {
            try {
                if (!UtilsFunction.empty((CharSequence) param.get(key))) {
                    sb.append(key).append(SimpleComparison.EQUAL_TO_OPERATION).append(URLEncoder.encode((String) param.get(key), "UTF-8")).append("&");
                }
            } catch (UnsupportedEncodingException e) {
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String getNumText(long num) {
        if (num < 10000) {
            return String.valueOf(num);
        }
        if (num < 100000000) {
            long thousand = (num % 10000) / 1000;
            if (num / 10000 > 100) {
                return String.format("%d万", new Object[]{Long.valueOf(num / 10000)});
            }
            return String.format("%d.%d万", new Object[]{Long.valueOf(num / 10000), Long.valueOf(thousand)});
        }
        long tenMillion = (num % 100000000) / 10000000;
        return String.format("%d.%d亿", new Object[]{Long.valueOf(num / 100000000), Long.valueOf(tenMillion)});
    }
}
