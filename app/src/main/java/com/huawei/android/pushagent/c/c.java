package com.huawei.android.pushagent.c;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import com.huawei.android.pushagent.c.a.e;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class c {
    public static void a(Context context) {
        a(context, "push_plugin", c(context));
    }

    private static void a(Context context, String str, String str2) {
        new Thread(new d(context, str, str2)).start();
    }

    private static String c(Context context) {
        String str = "|";
        str = a.a(context);
        String str2 = Build.MODEL;
        String str3 = Build.DISPLAY;
        return new StringBuffer().append(context.getPackageName()).append("|").append(d(context)).append("|").append(str).append("|").append(str2).append("|").append(str3).append("|").append("PushPlugin").append("|").append(2705).append("|").append(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE).format(new Date())).toString();
    }

    private static String d(Context context) {
        String str = "0.0";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Throwable e) {
            e.a("PushLogAC2705", "package not exist", e);
            return str;
        } catch (Throwable e2) {
            e.c("PushLogAC2705", "getApkVersionName error", e2);
            return str;
        }
    }

    private static boolean e(Context context) {
        boolean z = true;
        int i = -1;
        if (context == null) {
            return false;
        }
        try {
            i = Secure.getInt(context.getContentResolver(), "user_experience_involved", -1);
            e.a("PushLogAC2705", "settingMainSwitch:" + i);
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
        if (i != 1) {
            z = false;
        }
        return z;
    }
}
