package com.hianalytics.android.v1;

import android.content.Context;
import android.os.Handler;
import com.hianalytics.android.a.a.a;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class b {
    private static ScheduledExecutorService dU = Executors.newScheduledThreadPool(1);
    private static boolean dV = false;
    private static int dW = 0;

    public static void G(Context context) {
        if (context == null) {
            a.h();
            return;
        }
        Handler bp = a.bp();
        if (bp != null) {
            bp.post(new d(context, 2, System.currentTimeMillis()));
            new StringBuilder(String.valueOf(context.getClass().getName())).append(" onReport !").toString();
        }
        a.h();
    }

    public static void b(Context context, int i) {
        if (i == 0) {
            try {
                dV = false;
                dU.shutdown();
                dU = Executors.newScheduledThreadPool(1);
            } catch (Exception e) {
                "setReportTimer() throw exception:" + e.getMessage();
                a.h();
                e.printStackTrace();
            }
        } else if (!dV) {
            dV = true;
            a.h();
            dU.scheduleAtFixedRate(new d(context, 2, System.currentTimeMillis()), (long) i, (long) i, TimeUnit.SECONDS);
        } else if (dV && i != dW) {
            dW = i;
            a.h();
            dU.shutdown();
            ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
            dU = newScheduledThreadPool;
            newScheduledThreadPool.scheduleAtFixedRate(new d(context, 2, System.currentTimeMillis()), (long) i, (long) i, TimeUnit.SECONDS);
        }
    }

    public static void e(Long l) {
        if (l.longValue() >= 30) {
            a.a(l);
        }
    }

    public static void f(Long l) {
        if (l.longValue() >= 24) {
            a.b(Long.valueOf((l.longValue() * 60) * 60));
        }
    }

    public static void g(Long l) {
        if (l.longValue() >= 1000) {
            a.c(l);
        }
    }

    public static void h(long j) {
        if (j >= 30) {
            a.d(Long.valueOf(60 * j));
        }
    }

    public static void i(int i) {
        if (i >= 0) {
            a.a(i);
        }
    }

    public static void onEvent(Context context, String str, String str2) {
        if (context == null) {
            a.h();
        } else if (str == null || str.equals("")) {
            a.h();
        } else if (str2 == null || str2.equals("")) {
            a.h();
        } else {
            Handler bp = a.bp();
            if (bp != null) {
                bp.post(new a(context, str, str2, System.currentTimeMillis()));
                new StringBuilder(String.valueOf(context.getClass().getName())).append(" onEvent!").toString();
            }
            a.h();
        }
    }

    public static void onPause(Context context) {
        if (context == null) {
            a.h();
            return;
        }
        Handler bp = a.bp();
        if (bp != null) {
            bp.post(new d(context, 0, System.currentTimeMillis()));
            new StringBuilder(String.valueOf(context.getClass().getName())).append(" onPause() !").toString();
        }
        a.h();
    }

    public static void onResume(Context context) {
        if (context == null) {
            a.h();
            return;
        }
        Handler bp = a.bp();
        if (bp != null) {
            bp.post(new d(context, 1, System.currentTimeMillis()));
            new StringBuilder(String.valueOf(context.getClass().getName())).append(" onResume() !").toString();
        }
        a.h();
    }

    public static void t(boolean z) {
        a.a(z);
    }
}
