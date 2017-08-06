package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.onlineconfig.UmengOnlineConfigureListener;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.analytics.social.e;
import java.util.HashMap;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;
import u.aly.bi;
import u.aly.bj;

public class MobclickAgent {
    private static final String a = "input map is null";
    private static final d b = new d();

    public static void setAutoLocation(boolean z) {
    }

    public static void setCatchUncaughtExceptions(boolean z) {
        AnalyticsConfig.CATCH_EXCEPTION = z;
    }

    public static void setWrapper(String str, String str2) {
        b.a(str, str2);
    }

    public static void setSessionContinueMillis(long j) {
        AnalyticsConfig.kContinueSessionMillis = j;
    }

    public static void setEnableEventBuffer(boolean z) {
        AnalyticsConfig.ENABLE_MEMORY_BUFFER = z;
    }

    public static void setOnlineConfigureListener(UmengOnlineConfigureListener umengOnlineConfigureListener) {
        b.a(umengOnlineConfigureListener);
    }

    public static d getAgent() {
        return b;
    }

    public static void setOpenGLContext(GL10 gl10) {
        if (gl10 != null) {
            String[] a = bi.a(gl10);
            if (a.length == 2) {
                AnalyticsConfig.GPU_VENDER = a[0];
                AnalyticsConfig.GPU_RENDERER = a[1];
            }
        }
    }

    public static void openActivityDurationTrack(boolean z) {
        AnalyticsConfig.ACTIVITY_DURATION_OPEN = z;
    }

    public static void onPageStart(String str) {
        if (TextUtils.isEmpty(str)) {
            bj.b(a.e, "pageName is null or empty");
        } else {
            b.a(str);
        }
    }

    public static void onPageEnd(String str) {
        if (TextUtils.isEmpty(str)) {
            bj.b(a.e, "pageName is null or empty");
        } else {
            b.b(str);
        }
    }

    public static void setDebugMode(boolean z) {
        bj.a = z;
        e.v = z;
    }

    public static void onPause(Context context) {
        b.c(context);
    }

    public static void onResume(Context context) {
        if (context == null) {
            bj.b(a.e, "unexpected null context in onResume");
        } else {
            b.b(context);
        }
    }

    public static void onResume(Context context, String str, String str2) {
        if (context == null) {
            bj.b(a.e, "unexpected null context in onResume");
        } else if (str == null || str.length() == 0) {
            bj.b(a.e, "unexpected empty appkey in onResume");
        } else {
            AnalyticsConfig.setAppkey(str);
            AnalyticsConfig.setChannel(str2);
            b.b(context);
        }
    }

    public static void reportError(Context context, String str) {
        b.a(context, str);
    }

    public static void reportError(Context context, Throwable th) {
        b.a(context, th);
    }

    public static void flush(Context context) {
        b.d(context);
    }

    public static void onEvent(Context context, String str) {
        b.a(context, str, null, -1, 1);
    }

    public static void onEvent(Context context, String str, int i) {
        b.a(context, str, null, -1, i);
    }

    public static void onEvent(Context context, String str, String str2, int i) {
        if (TextUtils.isEmpty(str2)) {
            bj.a(a.e, "label is null or empty");
        } else {
            b.a(context, str, str2, -1, i);
        }
    }

    public static void onEvent(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            bj.a(a.e, "label is null or empty");
        } else {
            b.a(context, str, str2, -1, 1);
        }
    }

    public static void onEvent(Context context, String str, Map<String, String> map) {
        if (map == null) {
            bj.b(a.e, a);
            return;
        }
        b.a(context, str, new HashMap(map), -1);
    }

    public static void onEventDuration(Context context, String str, long j) {
        if (j <= 0) {
            bj.a(a.e, "duration is not valid in onEventDuration");
        } else {
            b.a(context, str, null, j, 1);
        }
    }

    public static void onEventDuration(Context context, String str, String str2, long j) {
        if (TextUtils.isEmpty(str2)) {
            bj.a(a.e, "label is null or empty");
        } else if (j <= 0) {
            bj.a(a.e, "duration is not valid in onEventDuration");
        } else {
            b.a(context, str, str2, j, 1);
        }
    }

    public static void onEventDuration(Context context, String str, Map<String, String> map, long j) {
        if (j <= 0) {
            bj.a(a.e, "duration is not valid in onEventDuration");
        } else if (map == null) {
            bj.b(a.e, a);
        } else {
            b.a(context, str, new HashMap(map), j);
        }
    }

    public static void onEventValue(Context context, String str, Map<String, String> map, int i) {
        Map hashMap;
        if (map == null) {
            hashMap = new HashMap();
        } else {
            hashMap = new HashMap(map);
        }
        hashMap.put("__ct__", Integer.valueOf(i));
        b.a(context, str, hashMap, -1);
    }

    public static void onEventBegin(Context context, String str) {
        b.a(context, str, null);
    }

    public static void onEventEnd(Context context, String str) {
        b.b(context, str, null);
    }

    public static void onEventBegin(Context context, String str, String str2) {
        b.a(context, str, str2);
    }

    public static void onEventEnd(Context context, String str, String str2) {
        b.b(context, str, str2);
    }

    public static void onKVEventBegin(Context context, String str, Map<String, String> map, String str2) {
        if (map == null) {
            bj.b(a.e, a);
        } else {
            b.a(context, str, new HashMap(map), str2);
        }
    }

    public static void onKVEventEnd(Context context, String str, String str2) {
        b.c(context, str, str2);
    }

    public static void onSocialEvent(Context context, String str, UMPlatformData... uMPlatformDataArr) {
        if (context == null) {
            bj.b(a.e, "context is null in onShareEvent");
            return;
        }
        e.e = "3";
        UMSocialService.share(context, str, uMPlatformDataArr);
    }

    public static void onSocialEvent(Context context, UMPlatformData... uMPlatformDataArr) {
        if (context == null) {
            bj.b(a.e, "context is null in onShareEvent");
            return;
        }
        e.e = "3";
        UMSocialService.share(context, uMPlatformDataArr);
    }

    public static String getConfigParams(Context context, String str) {
        return g.a(context).g().getString(str, "");
    }

    public static void updateOnlineConfig(Context context, String str, String str2) {
        if (str == null || str.length() == 0) {
            bj.b(a.e, "unexpected empty appkey in onResume");
            return;
        }
        AnalyticsConfig.setAppkey(str);
        AnalyticsConfig.setChannel(str2);
        b.a(context);
    }

    public static void updateOnlineConfig(Context context) {
        b.a(context);
    }

    public static void setUserID(Context context, String str, String str2, Gender gender, int i) {
        if (TextUtils.isEmpty(str)) {
            bj.c(a.e, "userID is null or empty");
            str = null;
        }
        if (TextUtils.isEmpty(str2)) {
            bj.a(a.e, "id source is null or empty");
            str2 = null;
        }
        if (i <= 0 || i >= 200) {
            bj.a(a.e, "not a_isRightVersion valid age!");
            i = -1;
        }
        g.a(context).a(str, str2, i, gender.value);
    }

    public static void onKillProcess(Context context) {
        b.e(context);
    }
}
