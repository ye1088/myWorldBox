package com.MCWorld.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.MCWorld.data.profile.a;
import com.MCWorld.widget.Constants.MiVer;
import com.MCWorld.widget.Constants.Model;
import com.MCWorld.widget.Constants.PushWay;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import net.lingala.zip4j.util.e;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacpp.swscale;

/* compiled from: UtilsSystem */
public class ax {
    private static String bnf = null;
    public static final int bng = 5;
    public static final int bnh = 6;

    public static int dipToPx(Context context, int dip) {
        return (int) ((((float) dip) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static PushWay ME() {
        MiVer mi = MG();
        if (mi == MiVer.miv5 || mi == MiVer.miv6) {
            return PushWay.XIAOMI;
        }
        if ("HUAWEI".equals(Build.MANUFACTURER)) {
            return PushWay.HUAWEI;
        }
        return PushWay.XIAOMI;
    }

    public static int pxToDip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void a(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, 0);
    }

    public static String bt(Context c) {
        if (bnf != null && bnf.length() > 0) {
            return bnf;
        }
        try {
            bnf = ((TelephonyManager) c.getSystemService(a.qe)).getDeviceId();
        } catch (Exception e) {
        }
        if (bnf == null) {
            bnf = "none";
        }
        return bnf;
    }

    public static void a(Activity activity, Class start, int resName, int resImage) {
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra("android.intent.extra.shortcut.NAME", activity.getString(resName));
        shortcutintent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(activity, resImage));
        shortcutintent.putExtra("android.intent.extra.shortcut.INTENT", new Intent(activity, start));
        activity.sendBroadcast(shortcutintent);
    }

    public static boolean MF() {
        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            new RandomAccessFile("/proc/cpuinfo", e.clf).read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
            if (strInfo.contains("Intel")) {
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static void bu(Context c) {
        c.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + c.getPackageName())));
    }

    public static boolean bv(Context c) {
        String selfName = c.getPackageName();
        PackageManager pm = c.getApplicationContext().getPackageManager();
        List<ApplicationInfo> appList = pm.getInstalledApplications(8192);
        pm.getInstalledPackages(0);
        for (ApplicationInfo appinfo : appList) {
            if (appinfo.processName.equals(selfName)) {
                if ((appinfo.flags & avutil.AV_CPU_FLAG_AVXSLOW) + (appinfo.flags & swscale.SWS_CPU_CAPS_SSE2) > 0) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    public static void bw(Context c) {
        try {
            String packName = gy("cjmkmxzxksqczrxtycqntqr");
            String actName = gy("cjmkmxzxkpqrmcqntqrkrjjtkRjjtManagqmqntActxvxty");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(packName, actName));
            intent.setFlags(268435456);
            c.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static MiVer MG() {
        String resultText = "";
        String propText = gy("rjkmxzxkzxkvqrsxjnkcjdq");
        try {
            resultText = (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{propText});
            if (resultText.equals("3")) {
                return MiVer.miv5;
            }
            if (resultText.equals("4")) {
                return MiVer.miv6;
            }
            return MiVer.nomi;
        } catch (Exception e) {
            return MiVer.nomi;
        }
    }

    private static String gy(String text) {
        return text.replace("x", "i").replace("X", "I").replace("j", "o").replace("J", "O").replace("z", "u").replace("q", "e").replace("Q", "E").replace("k_dialog_class", ".");
    }

    public static void bx(Context c) {
        String packName = gy("cjmkmxzxksqczrxtycqntqr");
        if (UtilsFile.fl(packName)) {
            String actName = gy("cjmkmxzxkpqrmcqntqrkpqrmxssxjnskAppPqrmxssxjnsQdxtjrActxvxty");
            Intent intent = new Intent(gy("mxzxkxntqntkactxjnkAPP_PQRM_QDITOR"));
            intent.setClassName(packName, actName);
            intent.putExtra("extra_pkgname", c.getPackageName());
            if (isIntentAvailable(c, intent) && (c instanceof Activity)) {
                try {
                    ((Activity) c).startActivityForResult(intent, 2);
                    return;
                } catch (ActivityNotFoundException e) {
                    bu(c);
                    return;
                }
            }
            bu(c);
            return;
        }
        bu(c);
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        boolean z = true;
        if (intent == null) {
            return false;
        }
        if (context.getPackageManager().queryIntentActivities(intent, 1).size() <= 0) {
            z = false;
        }
        return z;
    }

    public static Model MH() {
        MiVer mi = MG();
        if (mi == MiVer.miv5 || mi == MiVer.miv6) {
            return Model.XIAOMI;
        }
        if ("HUAWEI".equals(Build.MANUFACTURER)) {
            return Model.HUAWEI;
        }
        return Model.BAIDU;
    }

    public static void P(Context context, String packName) {
        ((ActivityManager) context.getSystemService("activity")).killBackgroundProcesses(packName);
    }
}
