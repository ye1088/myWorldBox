package com.huawei.android.pushagent.c.a;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import com.tencent.mm.sdk.platformtools.LocaleUtil;
import org.bytedeco.javacpp.avutil;

public class a {
    private static String a = BLocation.TAG;
    private static int b = 19;

    public static void a(Context context, long j, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        if (alarmManager == null) {
            e.b(a, "get AlarmManager error");
            return;
        }
        try {
            Object[] objArr = new Object[]{Integer.valueOf(0), Long.valueOf(j), pendingIntent};
            alarmManager.getClass().getDeclaredMethod("setExact", new Class[]{Integer.TYPE, Long.TYPE, PendingIntent.class}).invoke(alarmManager, objArr);
        } catch (Throwable e) {
            e.c(a, " setExact NoSuchMethodException " + e.toString(), e);
            alarmManager.set(0, j, pendingIntent);
        } catch (Throwable e2) {
            e.c(a, " setExact IllegalAccessException " + e2.toString(), e2);
            alarmManager.set(0, j, pendingIntent);
        } catch (Throwable e22) {
            e.c(a, " setExact InvocationTargetException " + e22.toString(), e22);
            alarmManager.set(0, j, pendingIntent);
        } catch (Throwable e222) {
            e.c(a, " setExact Exception " + e222.toString(), e222);
            alarmManager.set(0, j, pendingIntent);
        }
    }

    public static void a(Context context, Intent intent) {
        e.a(a, "enter cancelAlarm(Intent=" + intent);
        ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, 0, intent, 0));
    }

    public static void a(Context context, Intent intent, long j) {
        e.a(a, "enter AlarmTools:setAlarmLoops(intent:" + intent + " interval:" + j + LocaleUtil.MALAY);
        ((AlarmManager) context.getSystemService("alarm")).setRepeating(0, System.currentTimeMillis() + j, j, PendingIntent.getBroadcast(context, 0, intent, avutil.AV_CPU_FLAG_AVXSLOW));
    }

    public static void a(Context context, String str) {
        e.a(a, "enter cancelAlarm(Action=" + str);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        Intent intent = new Intent(str);
        intent.setPackage(context.getPackageName());
        alarmManager.cancel(PendingIntent.getBroadcast(context, 0, intent, 0));
    }

    public static void b(Context context, Intent intent, long j) {
        e.a(a, "enter AlarmTools:setHeartAlarm(intent:" + intent + " interval:" + j + LocaleUtil.MALAY);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, avutil.AV_CPU_FLAG_AVXSLOW);
        if (VERSION.SDK_INT >= b) {
            a(context, System.currentTimeMillis() + j, broadcast);
        } else {
            alarmManager.setRepeating(0, System.currentTimeMillis() + j, j, broadcast);
        }
    }

    public static void c(Context context, Intent intent, long j) {
        e.a(a, "enter AlarmTools:setDelayAlarm(intent:" + intent + " interval:" + j + "ms, context:" + context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, avutil.AV_CPU_FLAG_AVXSLOW);
        if (VERSION.SDK_INT >= b) {
            a(context, System.currentTimeMillis() + j, broadcast);
        } else {
            alarmManager.set(0, System.currentTimeMillis() + j, broadcast);
        }
    }
}
