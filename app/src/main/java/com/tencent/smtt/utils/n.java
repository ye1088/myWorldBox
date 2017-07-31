package com.tencent.smtt.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.smtt.sdk.TbsConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public class n {
    private static String a = "9397";
    private static String b = "";
    private static String c = "PP";
    private static String d = "PPVC";
    private static boolean e = false;
    private static boolean f = false;

    private static String a() {
        return a;
    }

    public static String a(Context context) {
        if (!b.equals("")) {
            return b;
        }
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
            c = applicationInfo.packageName;
            d = String.valueOf(packageInfo.versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String str = "0";
        String str2 = TbsConfig.TBS_SDK_VERSIONNAME;
        String str3 = null;
        if (c.equals("com.tencent.mm")) {
            str3 = a.a(context.getApplicationContext(), "com.tencent.mm.BuildInfo.CLIENT_VERSION");
        }
        b = a(context, (byte) 0, str, true, str2, c, str3 != null ? str3 : d);
        return b;
    }

    private static String a(Context context, byte b, String str, boolean z, String str2, String str3, String str4) {
        Object obj = null;
        String str5 = "x5Version";
        Object obj2 = null;
        String str6 = "blinkVersion";
        switch (b) {
            case (byte) 1:
                int i = 1;
                str5 = str;
                break;
            case (byte) 2:
                int i2 = 1;
                str6 = str;
                break;
        }
        String str7 = "QBVersion";
        String str8 = "1.1";
        CharSequence charSequence = "11111";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("QV").append(SimpleComparison.EQUAL_TO_OPERATION).append("2");
        a(stringBuilder, "PL", "ADR");
        if (z) {
            a(stringBuilder, "PR", "TBS");
        } else {
            a(stringBuilder, "PR", "QB");
        }
        a(stringBuilder, "PB", "GE");
        a(stringBuilder, "VE", "B1");
        if (z) {
            a(stringBuilder, "VN", str2);
        } else {
            a(stringBuilder, "VN", str7);
        }
        if (obj != null) {
            a(stringBuilder, "CO", "X5");
            a(stringBuilder, "COVN", str5);
        } else if (obj2 != null) {
            a(stringBuilder, "CO", "Blink");
            a(stringBuilder, "COVN", str6);
        } else {
            a(stringBuilder, "CO", "AMTT");
            a(stringBuilder, "COVN", str);
        }
        a(stringBuilder, "RF", "PRI");
        if (z) {
            a(stringBuilder, "PP", str3);
            a(stringBuilder, "PPVC", str4);
        }
        a(stringBuilder, "RL", b(context) + WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD + c(context));
        String b2 = b();
        try {
            Object str9 = new String(b2.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e) {
            str6 = b2;
        }
        if (!TextUtils.isEmpty(str9)) {
            a(stringBuilder, "MO", str9);
        }
        if (e(context)) {
            a(stringBuilder, "DE", "PAD");
        } else {
            a(stringBuilder, "DE", "PHONE");
        }
        b2 = VERSION.RELEASE;
        try {
            str9 = new String(b2.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e2) {
            str6 = b2;
        }
        if (!TextUtils.isEmpty(str9)) {
            a(stringBuilder, "OS", str9);
        }
        a(stringBuilder, "API", VERSION.SDK_INT + "");
        b2 = "CHID";
        if (TextUtils.isEmpty(charSequence)) {
            str6 = "0";
        } else {
            CharSequence charSequence2 = charSequence;
        }
        a(stringBuilder, b2, str6);
        a(stringBuilder, "LCID", a());
        return stringBuilder.toString();
    }

    private static String a(String str) {
        String str2 = "";
        try {
            str2 = (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{str});
            return str2 != null ? str2 : str2;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void a(StringBuilder stringBuilder, String str, String str2) {
        stringBuilder.append("&").append(str).append(SimpleComparison.EQUAL_TO_OPERATION).append(str2);
    }

    private static int b(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    private static String b() {
        return " " + Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", "") + " ";
    }

    private static int c(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    private static String c() {
        return a("ro.build.version.newbee.display");
    }

    private static int d(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.densityDpi;
    }

    private static boolean e(Context context) {
        if (e) {
            return f;
        }
        f = (Math.min(b(context), c(context)) * 160) / d(context) >= 700;
        e = true;
        return f;
    }
}
