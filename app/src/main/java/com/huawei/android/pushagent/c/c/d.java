package com.huawei.android.pushagent.c.c;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.h;
import java.util.List;

public class d {
    private static long a(ResolveInfo resolveInfo, String str) {
        long j = -1;
        if (resolveInfo != null) {
            try {
                j = Long.parseLong(b(resolveInfo, str));
            } catch (NumberFormatException e) {
                e.a("PushLogAC2705", str + " is not set in " + a(resolveInfo));
            }
        }
        return j;
    }

    private static ResolveInfo a(Context context, String str, String str2) {
        List b = a.b(context, str);
        if (b == null || b.size() == 0) {
            e.d("PushLogAC2705", "no push service install, may be system Err!! pkgName:" + context.getPackageName());
            return null;
        }
        e.a("PushLogAC2705", "begin to get the hight Version package, have action:" + str);
        ResolveInfo a = a(b, str2);
        if (a != null) {
            return a;
        }
        e.d("PushLogAC2705", "there is no hightVersion PushService, maybe system Err!! pkgName:" + context.getPackageName());
        return null;
    }

    private static ResolveInfo a(List list, String str) {
        ResolveInfo resolveInfo = null;
        if (!(list == null || list.size() == 0)) {
            for (ResolveInfo resolveInfo2 : list) {
                ResolveInfo resolveInfo22;
                if (!a(resolveInfo22, resolveInfo, str)) {
                    resolveInfo22 = resolveInfo;
                }
                resolveInfo = resolveInfo22;
            }
            if (resolveInfo != null) {
                e.a("PushLogAC2705", "after getHighVersion pushService pkgName=" + (resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.packageName : resolveInfo.activityInfo.packageName));
            }
        }
        return resolveInfo;
    }

    private static String a(ResolveInfo resolveInfo) {
        if (resolveInfo != null) {
            return resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.packageName : resolveInfo.activityInfo.packageName;
        } else {
            e.b("PushLogAC2705", "ResolveInfo is null , cannot get packageName");
            return null;
        }
    }

    private static boolean a() {
        if (-2 != a.a(1, 3) && !e.c()) {
            return true;
        }
        e.a("PushLogAC2705", "not support ctrlsocket.");
        return false;
    }

    public static boolean a(Context context) {
        try {
            String b = b(context);
            new h(context, "pushConfig").a("votedPackageName", b);
            e.a("PushLogAC2705", "start push service ,the highPkgName is :" + b);
            if (!(context.getPackageName().equals(b) || b == null)) {
                if ("android".equals(b)) {
                    e.a("PushLogAC2705", "pushservice start by framework");
                } else {
                    context.sendBroadcast(new Intent("com.huawei.intent.action.PUSH").putExtra("EXTRA_INTENT_TYPE", "com.huawei.intent.action.PUSH_ON").putExtra("Remote_Package_Name", b).setFlags(32).setPackage(b));
                }
            }
            return context.getPackageName().equals(b);
        } catch (SecurityException e) {
            e.c("PushLogAC2705", "startService SecurityException:" + e.getMessage());
            return false;
        } catch (Throwable e2) {
            e.c("PushLogAC2705", "isVotedSelf exception: " + e2.toString(), e2);
            return false;
        }
    }

    public static boolean a(Context context, String str) {
        e.a("PushLogAC2705", "isPkgInstall(pkgName:" + str + " context:" + context);
        return b(context, str) != null;
    }

    private static boolean a(ResolveInfo resolveInfo, ResolveInfo resolveInfo2, String str) {
        if (resolveInfo2 == null) {
            return true;
        }
        if (resolveInfo == null) {
            return false;
        }
        String a = a(resolveInfo);
        long a2 = a(resolveInfo, str);
        long a3 = a(resolveInfo2, str);
        String str2 = resolveInfo2.serviceInfo != null ? resolveInfo2.serviceInfo.packageName : resolveInfo2.activityInfo.packageName;
        e.a("PushLogAC2705", "the curPkgName(" + a + ")version is:" + a2 + "the oldPkgName (" + str2 + ")version is:" + a3);
        if (a2 > a3) {
            return true;
        }
        if (a2 != a3) {
            return false;
        }
        boolean z = a != null && a.compareTo(str2) > 0;
        return z;
    }

    private static PackageInfo b(Context context, String str) {
        e.a("PushLogAC2705", "getPkgVersion(pkgName:" + str + " context:" + context);
        try {
            return context.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    private static String b(Context context) {
        e.a("PushLogAC2705", " choose the high version for push service");
        if (a.o(context)) {
            return "android";
        }
        long c = c(context);
        if (228 > c || !a()) {
            ResolveInfo a = a(context, "com.huawei.android.push.intent.REGISTER", "CS_cloud_version");
            long a2 = a(a, "CS_cloud_version");
            e.a("PushLogAC2705", "the getHightMetaPackageName return version is :" + a2 + " curApkVersion:" + c);
            String str;
            if (c >= a2) {
                str = "com.huawei.android.pushagent";
                e.a("PushLogAC2705", "the push APK version (" + c + ")is hight,use APK for push service");
                return str;
            }
            str = a(a);
            e.a("PushLogAC2705", "use the hight version(" + a2 + " )for push service, highPkgName is :" + str);
            return str;
        }
        e.b("PushLogAC2705", "support ctrlsocket.");
        return "com.huawei.android.pushagent";
    }

    private static String b(ResolveInfo resolveInfo, String str) {
        Bundle bundle = resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.metaData : resolveInfo.activityInfo.metaData;
        return bundle == null ? null : bundle.getString(str);
    }

    private static long c(Context context) {
        Exception exception;
        long j = -1000;
        try {
            List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new Intent("com.huawei.android.push.intent.REGISTER").setPackage("com.huawei.android.pushagent"), com.huluxia.module.h.arA);
            if (!(queryBroadcastReceivers == null || queryBroadcastReceivers.size() == 0)) {
                try {
                    String str = ((ResolveInfo) queryBroadcastReceivers.get(0)).serviceInfo != null ? ((ResolveInfo) queryBroadcastReceivers.get(0)).serviceInfo.packageName : ((ResolveInfo) queryBroadcastReceivers.get(0)).activityInfo.packageName;
                    if (str == null || !str.equals("com.huawei.android.pushagent")) {
                        j = 228;
                        e.a("PushLogAC2705", "start to get Apk version , the APK version is :" + j);
                    } else {
                        j = a((ResolveInfo) queryBroadcastReceivers.get(0), "CS_cloud_version");
                        e.a("PushLogAC2705", "start to get Apk version , the APK version is :" + j);
                    }
                } catch (Exception e) {
                    Exception exception2 = e;
                    j = 228;
                    exception = exception2;
                    e.d("PushLogAC2705", "get Apk version faild ,Exception e= " + exception.toString());
                    e.a("PushLogAC2705", "start to get Apk version , the APK version is :" + j);
                    return j;
                }
            }
        } catch (Exception e2) {
            exception = e2;
            e.d("PushLogAC2705", "get Apk version faild ,Exception e= " + exception.toString());
            e.a("PushLogAC2705", "start to get Apk version , the APK version is :" + j);
            return j;
        }
        return j;
    }
}
