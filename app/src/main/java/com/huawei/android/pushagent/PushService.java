package com.huawei.android.pushagent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;
import com.huawei.android.microkernel.MKService;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.b.e.d;
import com.huawei.android.pushagent.b.f.b;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.g;
import java.util.Iterator;
import java.util.LinkedList;

public class PushService extends MKService {
    private static String a = "PushLogAC2705";
    private static PushService d = null;
    private LinkedList b = new LinkedList();
    private b c;
    private a e = new a(this);
    private long f = 0;
    private boolean g = false;
    private Context h = null;

    class a extends BroadcastReceiver {
        final /* synthetic */ PushService a;

        a(PushService pushService) {
            this.a = pushService;
        }

        public void a() {
            com.huawei.android.pushagent.c.a.a.a(this.a.h, "com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT");
            com.huawei.android.pushagent.c.a.a.a(this.a.h, new Intent("com.huawei.intent.action.PUSH").putExtra("EXTRA_INTENT_TYPE", "com.huawei.android.push.intent.HEARTBEAT_REQ").putExtra("heartbeat_interval", 2592000000L).setPackage(this.a.h.getPackageName()));
        }

        public void a(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            for (String addAction : g.a()) {
                intentFilter.addAction(addAction);
            }
            context.registerReceiver(this, intentFilter);
        }

        public void onReceive(Context context, Intent intent) {
            try {
                PushService.a(intent);
            } catch (Throwable e) {
                e.c(PushService.a, "call PushInnerReceiver:onReceive cause " + e.toString(), e);
            }
        }
    }

    public static synchronized PushService a() {
        PushService pushService;
        synchronized (PushService.class) {
            pushService = d;
        }
        return pushService;
    }

    public static void a(Intent intent) {
        try {
            PushService a = a();
            if (a == null) {
                e.d(a, "sendBroadcast error, pushService is null");
                return;
            }
            e.a(a, "broadcast(),and mReceivers  " + d.b.size());
            a.b(intent);
        } catch (Throwable e) {
            e.c(a, "call PushService:broadcast() cause " + e.toString(), e);
        }
    }

    private void a(com.huawei.android.pushagent.b.a aVar, IntentFilter intentFilter) {
        this.b.add(aVar);
    }

    public static void b() {
        if (d != null) {
            d.g = true;
            d.stopService();
        }
    }

    private synchronized void b(Intent intent) {
        if (intent == null) {
            e.a(a, "when broadcastToProcess, intent is null");
        } else {
            e.a(a, "broadcastToProcess, intent is:" + intent.getAction());
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                this.c.a((com.huawei.android.pushagent.b.a) it.next(), intent);
            }
        }
    }

    private static synchronized void b(PushService pushService) {
        synchronized (PushService.class) {
            d = pushService;
        }
    }

    private void d() {
        try {
            e.a(a, "initProcess(),and mReceivers  " + this.b.size());
            a(new com.huawei.android.pushagent.b.c.a(this.h), null);
            a(new d(this.h), null);
            a(new com.huawei.android.pushagent.b.f.a(this.h), null);
            a(new b(this.h), null);
            this.e.a(this.h);
        } catch (Throwable e) {
            e.a(a, "Exception:registerMyReceiver: " + e.toString(), e);
            stopService();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        this.h = getContext();
        Thread.setDefaultUncaughtExceptionHandler(new a(this));
        super.onCreate();
        try {
            e.a(this.h);
            e.b(a, "PushService:onCreate()");
            this.f = System.currentTimeMillis();
            try {
                this.c = new b(this.h);
                this.c.start();
                int i = 0;
                while (this.c.a == null) {
                    int i2 = i + 1;
                    if (i > 80) {
                        e.d(a, "call mReceiverDispatcher run after " + i2 + ", " + " but handler is null");
                        stopService();
                        return;
                    }
                    Thread.sleep(100);
                    if (i2 % 10 == 0) {
                        e.a(a, "wait hander created: " + i2);
                        i = i2;
                    } else {
                        i = i2;
                    }
                }
                com.huawei.android.pushagent.b.a.a.a(this.h);
                b(this);
                d();
            } catch (Throwable e) {
                e.a(a, "create ReceiverDispatcher thread or get channelMgr exception ,stopself, " + e.toString(), e);
                stopService();
            }
        } catch (Throwable e2) {
            e.a(a, "Exception:Log.init: " + e2.toString(), e2);
            stopService();
        }
    }

    public void onDestroy() {
        long j = 0;
        e.b(a, "enter PushService:onDestroy(), needExitService is:" + this.g);
        try {
            b(new Intent("com.huawei.android.push.intent.inner.STOP_SERVICE").putExtra("Remote_Package_Name", this.h.getPackageName()).setPackage(this.h.getPackageName()));
        } catch (Throwable e) {
            e.c(a, "call PushService:onDestroy() in broadcastToProcess cause " + e.toString(), e);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e2) {
            e.d(a, e2.toString());
        }
        try {
            if (this.e != null) {
                this.e.a();
                this.h.unregisterReceiver(this.e);
            }
        } catch (Throwable e3) {
            e.c(a, "call PushService:onDestroy() in unregisterReceiver cause " + e3.toString(), e3);
        }
        try {
            if (!(this.c == null || this.c.a == null)) {
                this.c.a.getLooper().quit();
            }
        } catch (Throwable e32) {
            e.c(a, "call PushService:onDestroy() in unregisterReceiver cause " + e32.toString(), e32);
        }
        if (!this.g) {
            long a = System.currentTimeMillis() - this.f > com.huawei.android.pushagent.b.b.a.a(this.h).w() * 1000 ? 0 : c.a(this.h, "run_time_less_times", 0) + 1;
            if (a == 0) {
                j = com.huawei.android.pushagent.b.b.a.a(this.h).x() * 1000;
            } else if (a == 1) {
                j = com.huawei.android.pushagent.b.b.a.a(this.h).y() * 1000;
            } else if (a == 2) {
                j = com.huawei.android.pushagent.b.b.a.a(this.h).z() * 1000;
            } else if (a >= 3) {
                j = com.huawei.android.pushagent.b.b.a.a(this.h).A() * 1000;
            }
            e.a(a, "next start time will be " + (j / 1000) + " seconds later" + " run_time_less_times is " + a + "times");
            c.a(this.h, new com.huawei.android.pushagent.a.a("run_time_less_times", Long.class, Long.valueOf(a)));
            com.huawei.android.pushagent.c.a.a.c(this.h, new Intent("com.huawei.intent.action.PUSH_ON").setPackage(this.h.getPackageName()), j);
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            e.b(a, "PushService onStartCommand");
            if ("com.huawei.android.pushagent".equals(this.h.getPackageName())) {
                e.c(a, "apk provide pushservice");
                Toast.makeText(this.h, "HwPushService.apk error!", 1).show();
                return 2;
            } else if (intent != null) {
                e.a(a, "onStartCommand, intent is:" + intent.toURI());
                b(intent);
                return 1;
            } else {
                e.b(a, "onStartCommand, intent is null ,mybe restart service called by android system");
                return 1;
            }
        } catch (Throwable e) {
            e.c(a, "call PushService:onStartCommand() cause " + e.toString(), e);
            return 1;
        }
    }
}
