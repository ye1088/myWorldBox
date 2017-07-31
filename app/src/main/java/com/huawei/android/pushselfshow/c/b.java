package com.huawei.android.pushselfshow.c;

import android.R.drawable;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.b.a;
import java.util.Date;
import org.bytedeco.javacpp.avutil;

public class b {
    private static int a = 0;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"InlinedApi"})
    private static float a(android.content.Context r4) {
        /*
        r0 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r0 = com.huawei.android.pushselfshow.utils.a.a(r4, r0);
        r1 = (float) r0;
        r0 = r4.getResources();	 Catch:{ Exception -> 0x0036 }
        r2 = 17104901; // 0x1050005 float:2.4428256E-38 double:8.450944E-317;
        r0 = r0.getDimension(r2);	 Catch:{ Exception -> 0x0036 }
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0041;
    L_0x0017:
        r2 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1));
        if (r2 <= 0) goto L_0x0041;
    L_0x001b:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "getRescaleBitmapSize:";
        r2 = r2.append(r3);
        r2 = r2.append(r0);
        r2 = r2.toString();
        com.huawei.android.pushagent.c.a.e.a(r1, r2);
        return r0;
    L_0x0036:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a.e.c(r2, r0);
    L_0x0041:
        r0 = r1;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.c.b.a(android.content.Context):float");
    }

    public static int a(Context context, String str, String str2, Object obj) {
        int i;
        Throwable e;
        Throwable th;
        try {
            String str3 = context.getPackageName() + ".R";
            e.a("PushSelfShowLog", "try to refrect " + str3 + " typeName is " + str2);
            Class[] classes = Class.forName(str3).getClasses();
            e.a("PushSelfShowLog", "sonClassArr length " + classes.length);
            for (Class cls : classes) {
                e.a("PushSelfShowLog", "sonTypeClass,query sonclass is  %s", new Object[]{cls.getName().substring(str3.length() + 1) + " sonClass.getName() is" + cls.getName()});
                if (str2.equals(cls.getName().substring(str3.length() + 1))) {
                    break;
                }
            }
            Class cls2 = null;
            if (cls2 != null) {
                i = cls2.getField(str).getInt(obj);
                try {
                    e.a("PushSelfShowLog", " refect res id is %s", new Object[]{"" + i});
                } catch (ClassNotFoundException e2) {
                    e = e2;
                    e.c("PushSelfShowLog", "ClassNotFound failed,", e);
                    return i;
                } catch (NoSuchFieldException e3) {
                    e = e3;
                    e.c("PushSelfShowLog", "NoSuchFieldException failed,", e);
                    return i;
                } catch (IllegalAccessException e4) {
                    e = e4;
                    e.c("PushSelfShowLog", "IllegalAccessException failed,", e);
                    return i;
                } catch (IllegalArgumentException e5) {
                    e = e5;
                    e.c("PushSelfShowLog", "IllegalArgumentException failed,", e);
                    return i;
                } catch (IndexOutOfBoundsException e6) {
                    e = e6;
                    e.c("PushSelfShowLog", "IndexOutOfBoundsException failed,", e);
                    return i;
                } catch (Exception e7) {
                    e = e7;
                    e.c("PushSelfShowLog", "  failed,", e);
                    return i;
                }
                return i;
            }
            e.a("PushSelfShowLog", "sonTypeClass is null");
            String str4 = context.getPackageName() + ".R$" + str2;
            e.a("PushSelfShowLog", "try to refrect 2 " + str4 + " typeName is " + str2);
            i = Class.forName(str4).getField(str).getInt(obj);
            e.a("PushSelfShowLog", " refect res id 2 is %s", new Object[]{"" + i});
            return i;
        } catch (Throwable e8) {
            th = e8;
            i = 0;
            e = th;
            e.c("PushSelfShowLog", "ClassNotFound failed,", e);
            return i;
        } catch (Throwable e82) {
            th = e82;
            i = 0;
            e = th;
            e.c("PushSelfShowLog", "NoSuchFieldException failed,", e);
            return i;
        } catch (Throwable e822) {
            th = e822;
            i = 0;
            e = th;
            e.c("PushSelfShowLog", "IllegalAccessException failed,", e);
            return i;
        } catch (Throwable e8222) {
            th = e8222;
            i = 0;
            e = th;
            e.c("PushSelfShowLog", "IllegalArgumentException failed,", e);
            return i;
        } catch (Throwable e82222) {
            th = e82222;
            i = 0;
            e = th;
            e.c("PushSelfShowLog", "IndexOutOfBoundsException failed,", e);
            return i;
        } catch (Throwable e822222) {
            th = e822222;
            i = 0;
            e = th;
            e.c("PushSelfShowLog", "  failed,", e);
            return i;
        }
    }

    public static Notification a(Context context, a aVar, int i, int i2, int i3) {
        Notification notification = new Notification();
        notification.icon = b(context, aVar);
        int i4 = context.getApplicationInfo().labelRes;
        notification.tickerText = aVar.p;
        notification.when = System.currentTimeMillis();
        notification.flags |= 16;
        notification.defaults |= 1;
        Intent intent = new Intent("com.huawei.intent.action.PUSH");
        intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "1").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(268435456);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i2, intent, avutil.AV_CPU_FLAG_AVXSLOW);
        notification.contentIntent = broadcast;
        Intent intent2 = new Intent("com.huawei.intent.action.PUSH");
        intent2.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "2").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(268435456);
        notification.deleteIntent = PendingIntent.getBroadcast(context, i3, intent2, avutil.AV_CPU_FLAG_AVXSLOW);
        if (aVar.r == null || "".equals(aVar.r)) {
            notification.setLatestEventInfo(context, context.getResources().getString(i4), aVar.p, broadcast);
        } else {
            notification.setLatestEventInfo(context, aVar.r, aVar.p, broadcast);
        }
        i4 = context.getResources().getIdentifier("icon", "id", "android");
        Bitmap c = c(context, aVar);
        if (!(i4 == 0 || notification.contentView == null || c == null)) {
            notification.contentView.setImageViewBitmap(i4, c);
        }
        return c.a(context, notification, i, aVar, c);
    }

    public static void a(Context context, int i) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (notificationManager != null) {
                notificationManager.cancel(i);
            }
        } catch (Exception e) {
            e.d("PushSelfShowLog", "removeNotifiCationById err:" + e.toString());
        }
    }

    public static void a(Context context, Intent intent) {
        int i = 0;
        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            if (intent.hasExtra("selfshow_notify_id")) {
                i = intent.getIntExtra("selfshow_notify_id", 0) + 3;
            }
            e.a("PushSelfShowLog", "setDelayAlarm(cancel) alarmNotityId " + i + " and intent is " + intent.toURI());
            Intent intent2 = new Intent("com.huawei.intent.action.PUSH");
            intent2.setPackage(context.getPackageName()).setFlags(32);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent2, 536870912);
            if (broadcast != null) {
                e.a("PushSelfShowLog", "  alarm cancel");
                alarmManager.cancel(broadcast);
                return;
            }
            e.a("PushSelfShowLog", "alarm not exist");
        } catch (Exception e) {
            e.d("PushSelfShowLog", "cancelAlarm err:" + e.toString());
        }
    }

    public static void a(Context context, Intent intent, long j, int i) {
        try {
            e.a("PushSelfShowLog", "enter setDelayAlarm(intent:" + intent.toURI() + " interval:" + j + "ms, context:" + context);
            ((AlarmManager) context.getSystemService("alarm")).set(0, System.currentTimeMillis() + j, PendingIntent.getBroadcast(context, i, intent, avutil.AV_CPU_FLAG_AVXSLOW));
        } catch (Throwable e) {
            e.a("PushSelfShowLog", "set DelayAlarm error", e);
        }
    }

    public static synchronized void a(Context context, a aVar) {
        synchronized (b.class) {
            if (!(context == null || aVar == null)) {
                try {
                    e.a("PushSelfShowLog", " showNotification , the msg id = " + aVar.l);
                    com.huawei.android.pushselfshow.utils.a.a(2, 180);
                    if (a == 0) {
                        a = (context.getPackageName() + new Date().toString()).hashCode();
                    }
                    int i = a;
                    int i2 = a + 1;
                    a = i2;
                    int i3 = a + 1;
                    a = i3;
                    int i4 = a + 1;
                    a = i4;
                    Notification b = com.huawei.android.pushselfshow.utils.a.b() ? b(context, aVar, i, i2, i3) : a(context, aVar, i, i2, i3);
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                    if (!(notificationManager == null || b == null)) {
                        notificationManager.notify(i, b);
                        if (aVar.e > 0) {
                            Intent intent = new Intent("com.huawei.intent.action.PUSH");
                            intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "-1").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(32);
                            a(context, intent, (long) aVar.e, i4);
                            e.a("PushSelfShowLog", "setDelayAlarm alarmNotityId" + i4 + " and intent is " + intent.toURI());
                        }
                        com.huawei.android.pushselfshow.utils.a.a(context, "0", aVar);
                    }
                } catch (Exception e) {
                    e.d("PushSelfShowLog", "showNotification error " + e.toString());
                }
            }
        }
    }

    private static int b(Context context, a aVar) {
        int i = 0;
        if (context == null || aVar == null) {
            e.b("PushSelfShowLog", "enter getSmallIconId, context or msg is null");
            return 0;
        }
        if (aVar.s != null && aVar.s.length() > 0) {
            i = a(context, aVar.s, "drawable", new drawable());
            e.a("PushSelfShowLog", context.getPackageName() + "  msg.statusIcon is " + aVar.s + ",and icon is " + i);
            if (i == 0) {
                i = context.getResources().getIdentifier(aVar.s, "drawable", "android");
            }
            e.a("PushSelfShowLog", "msg.statusIcon is " + aVar.s + ",and icon is " + i);
        }
        if (i != 0) {
            return i;
        }
        i = context.getApplicationInfo().icon;
        if (i != 0) {
            return i;
        }
        i = context.getResources().getIdentifier("btn_star_big_on", "drawable", "android");
        return i == 0 ? 17301651 : i;
    }

    public static Notification b(Context context, a aVar, int i, int i2, int i3) {
        Builder builder = new Builder(context);
        builder.setSmallIcon(b(context, aVar));
        int i4 = context.getApplicationInfo().labelRes;
        builder.setTicker(aVar.p);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setDefaults(1);
        Intent intent = new Intent("com.huawei.intent.action.PUSH");
        intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "1").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(268435456);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i2, intent, avutil.AV_CPU_FLAG_AVXSLOW);
        Intent intent2 = new Intent("com.huawei.intent.action.PUSH");
        intent2.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "2").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(268435456);
        PendingIntent broadcast2 = PendingIntent.getBroadcast(context, i3, intent2, avutil.AV_CPU_FLAG_AVXSLOW);
        if (aVar.r == null || "".equals(aVar.r)) {
            builder.setContentTitle(context.getResources().getString(i4));
        } else {
            builder.setContentTitle(aVar.r);
        }
        builder.setContentText(aVar.p);
        builder.setContentIntent(broadcast);
        builder.setDeleteIntent(broadcast2);
        Bitmap c = c(context, aVar);
        if (c != null) {
            builder.setLargeIcon(c);
        }
        return c.a(context, builder, i, aVar, c) == null ? null : builder.getNotification();
    }

    private static Bitmap c(Context context, a aVar) {
        if (context == null || aVar == null) {
            return null;
        }
        try {
            if (aVar.q != null && aVar.q.length() > 0) {
                com.huawei.android.pushselfshow.utils.c.a aVar2 = new com.huawei.android.pushselfshow.utils.c.a();
                int i = 0;
                if (!aVar.q.equals("" + aVar.a())) {
                    i = a(context, aVar.q, "drawable", new drawable());
                    if (i == 0) {
                        i = context.getResources().getIdentifier(aVar.q, "drawable", "android");
                    }
                    e.a("PushSelfShowLog", "msg.notifyIcon is " + aVar.q + ",and defaultIcon is " + i);
                }
                if (i != 0) {
                    return BitmapFactory.decodeResource(context.getResources(), i);
                }
                Bitmap a = aVar2.a(context, aVar.q);
                e.a("PushSelfShowLog", "get bitmap from new downloaded ");
                if (a != null) {
                    e.a("PushSelfShowLog", "height:" + a.getHeight() + ",width:" + a.getWidth());
                    float a2 = a(context);
                    a = aVar2.a(context, a, a2, a2);
                }
                if (a != null) {
                    return a;
                }
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "" + e.toString(), e);
        }
        return null;
    }
}
