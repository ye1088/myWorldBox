package com.tencent.smtt.utils;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import android.widget.TextView;
import com.tencent.smtt.sdk.TbsConfig;

public class TbsLog {
    private static boolean a = false;
    private static TbsLogClient b = null;

    static {
        if (b == null) {
            setTbsLogClient(TbsLogClient.getIntance());
        }
    }

    public static void app_extra(String str, Context context) {
        int i = 0;
        try {
            Context applicationContext = context.getApplicationContext();
            String[] strArr = new String[]{TbsConfig.APP_DEMO, TbsConfig.APP_QB, "com.tencent.mm", "com.tencent.mobileqq", TbsConfig.APP_DEMO_TEST, "com.qzone"};
            String[] strArr2 = new String[]{"DEMO", "QB", "WX", "QQ", "TEST", "QZ"};
            while (i < strArr.length) {
                if (applicationContext.getPackageName().contains(strArr[i])) {
                    i(str, "app_extra pid:" + Process.myPid() + "; APP_TAG:" + strArr2[i] + "!");
                    break;
                }
                i++;
            }
            if (i == strArr.length) {
                i(str, "app_extra pid:" + Process.myPid() + "; APP_TAG:OTHER!");
            }
        } catch (Throwable th) {
            w(str, "app_extra exception:" + Log.getStackTraceString(th));
        }
    }

    public static void d(String str, String str2) {
        b.d(str, "TBS:" + str2);
    }

    public static void d(String str, String str2, boolean z) {
        d(str, str2);
        if (a && z) {
            b.showLog(str + ": " + str2);
        }
    }

    public static void e(String str, String str2) {
        b.e(str, "TBS:" + str2);
        b.writeLog("(E)-" + str + "-TBS:" + str2);
    }

    public static void e(String str, String str2, boolean z) {
        e(str, str2);
        if (a && z) {
            b.showLog(str + ": " + str2);
        }
    }

    public static String getTbsLogFilePath() {
        return TbsLogClient.c != null ? TbsLogClient.c.getAbsolutePath() : null;
    }

    public static void i(String str, String str2) {
        b.i(str, "TBS:" + str2);
        b.writeLog("(I)-" + str + "-TBS:" + str2);
    }

    public static void i(String str, String str2, boolean z) {
        i(str, str2);
        if (a && z) {
            b.showLog(str + ": " + str2);
        }
    }

    public static void setLogView(TextView textView) {
        if (textView != null) {
            b.setLogView(textView);
        }
    }

    public static boolean setTbsLogClient(TbsLogClient tbsLogClient) {
        if (tbsLogClient == null) {
            return false;
        }
        b = tbsLogClient;
        return true;
    }

    public static void v(String str, String str2) {
        b.v(str, "TBS:" + str2);
    }

    public static void v(String str, String str2, boolean z) {
        v(str, str2);
        if (a && z) {
            b.showLog(str + ": " + str2);
        }
    }

    public static void w(String str, String str2) {
        b.w(str, "TBS:" + str2);
        b.writeLog("(W)-" + str + "-TBS:" + str2);
    }

    public static void w(String str, String str2, boolean z) {
        w(str, str2);
        if (a && z) {
            b.showLog(str + ": " + str2);
        }
    }
}
