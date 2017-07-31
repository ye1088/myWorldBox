package com.huawei.android.pushagent.c.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import com.huawei.android.microkernel.MKService;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.f;
import com.huawei.android.pushagent.c.a.h;

public class b extends Thread {
    private static b a;
    private static long b = 0;
    private static Handler c = null;
    private static WakeLock d = null;
    private static boolean e = false;
    private static long f = 0;

    static {
        a = null;
        a = new b();
        a.start();
    }

    public static void a() {
        b = System.currentTimeMillis();
    }

    private static void a(Context context) {
        d = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "pushagentPoxy");
    }

    public static void a(Context context, Intent intent) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = 0;
        try {
            if (d == null) {
                a(context);
            }
            while (c == null && i <= 60) {
                if (i % 20 == 0) {
                    e.a("PushLogAC2705", "when send msg handler is null, waitTimes:" + i);
                }
                i++;
                sleep(10);
            }
            if (d != null) {
                d.acquire(500);
            }
            if (c != null) {
                c.postDelayed(new c(context, intent), 1);
            }
        } catch (InterruptedException e) {
            e.d("PushLogAC2705", "call handleEvent cause InterruptedException:" + e.toString());
        } catch (Exception e2) {
            e.d("PushLogAC2705", "call handleEvent cause Exception:" + e2.toString());
        }
        e.a("PushLogAC2705", "PushProxy cost " + (System.currentTimeMillis() - currentTimeMillis) + "ms dealwith " + intent);
    }

    private boolean a(Context context, String str) {
        int q = a.q(context);
        CharSequence r = a.r(context);
        if ("android.intent.action.PACKAGE_ADDED".equals(str) || "android.intent.action.PACKAGE_REMOVED".equals(str) || q == 0 || TextUtils.isEmpty(r)) {
            if (d.a(context)) {
                a.a(context, true);
                q = 1;
            } else {
                q = 2;
                a.a(context, false);
            }
            e.a("PushLogAC2705", "After voting, My service Stats = " + q);
            new h(context, "pushConfig").a("NeedMyServiceRun", Integer.valueOf(q));
        }
        return 1 == q;
    }

    private void b(Context context, Intent intent) {
        if (intent != null) {
            if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                c(context, intent);
            }
            com.huawei.android.pushagent.c.h.a(context);
            if (a(context, intent.getAction())) {
                e.a("PushLogAC2705", "my pkg " + context.getPackageName() + " need deal with:" + intent);
                if (!e(context, intent)) {
                    if (b(context)) {
                        e.a("PushLogAC2705", "enter checkBackUp()");
                        a();
                        a.f(context);
                        a.p(context);
                    }
                    d(context, intent);
                    return;
                }
                return;
            }
            e.a("PushLogAC2705", "need not current " + context.getPackageName() + " to depose, so exit receiver");
            a.a(context, 3);
            PushService.b();
        }
    }

    private boolean b(Context context) {
        return System.currentTimeMillis() < b || (com.huawei.android.pushagent.b.b.a.a(context).N() * 1000) + b < System.currentTimeMillis();
    }

    private static void c(Context context, Intent intent) {
        Object obj = "";
        Uri data = intent.getData();
        if (data != null) {
            obj = data.getSchemeSpecificPart();
        }
        String packageName = context.getPackageName();
        e.a("PushLogAC2705", "the Reinstall pkgName:" + obj + ", current PkgName:" + packageName);
        if (!TextUtils.isEmpty(obj) && obj.equals(packageName)) {
            a.a(context, 1);
            e.a("PushLogAC2705", "after apk reinstalled , stop pushservice:" + String.valueOf(context.stopService(new Intent(context, PushService.class))));
        }
    }

    private void d(Context context, Intent intent) {
        if (context != null && intent != null) {
            Intent intent2;
            if (MKService.getAppContext() == null) {
                intent2 = new Intent(context, PushService.class);
                intent2.fillIn(intent, 7);
            } else {
                intent2 = new Intent();
                e.a("PushLogAC2705", "MKService.getAppContext() is" + MKService.getAppContext());
                intent2.setComponent(new ComponentName(context, "com.huawei.deviceCloud.microKernel.push.PushMKService"));
                intent2.fillIn(intent, 7);
            }
            intent2.setPackage(context.getPackageName());
            e.a("PushLogAC2705", "serviceIntent is" + intent2.toURI());
            context.startService(intent2);
        }
    }

    private boolean e(Context context, Intent intent) {
        e.a("PushLogAC2705", "enter needDelayIntent");
        if (!TextUtils.isEmpty(f.a(context, "device_info", "deviceId"))) {
            e.a("PushLogAC2705", "local deviceId is not empty");
            return false;
        } else if (e) {
            long currentTimeMillis = System.currentTimeMillis() - f;
            if (0 >= currentTimeMillis || currentTimeMillis > 60000) {
                e.a("PushLogAC2705", "second enter, no deed to wait");
                return false;
            }
            e.a("PushLogAC2705", "second enter, waitting 1 minute");
            intent.setPackage(context.getPackageName());
            com.huawei.android.pushagent.c.a.a.c(context, intent, 60000);
            return true;
        } else {
            e = true;
            if (TextUtils.isEmpty(a.c(context))) {
                e.a("PushLogAC2705", "first enter, imei is empty, begin to wait 1 minute");
                f = System.currentTimeMillis();
                intent.setPackage(context.getPackageName());
                com.huawei.android.pushagent.c.a.a.c(context, intent, 60000);
                return true;
            }
            e.a("PushLogAC2705", "first enter, imei is not empty, no deed to wait");
            return false;
        }
    }

    public void run() {
        Looper.prepare();
        if (c == null) {
            c = new Handler();
        }
        Looper.loop();
    }
}
