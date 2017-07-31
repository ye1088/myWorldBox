package com.huawei.android.pushagent.c;

import android.content.Context;
import com.huawei.android.pushagent.c.a.e;

final class d implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;

    d(Context context, String str, String str2) {
        this.a = context;
        this.b = str;
        this.c = str2;
    }

    public void run() {
        try {
            if (this.a != null) {
                e.b("PushLogAC2705", "run normal sendHiAnalytics");
                if (c.e(this.a)) {
                    Class cls = Class.forName("com.hianalytics.android.v1.b");
                    cls.getMethod("onEvent", new Class[]{Context.class, String.class, String.class}).invoke(cls, new Object[]{this.a, this.b, this.c});
                    cls.getMethod("onReport", new Class[]{Context.class}).invoke(cls, new Object[]{this.a});
                    e.a("PushLogAC2705", "send HiAnalytics msg,PS =" + this.b);
                    return;
                }
                e.b("PushLogAC2705", "not allowed to sendHiAnalytics!");
                return;
            }
            e.d("PushLogAC2705", "context is null when sendHiAnalytics");
        } catch (Throwable e) {
            e.d("PushLogAC2705", "sendHiAnalytics IllegalAccessException ", e);
        } catch (Throwable e2) {
            e.d("PushLogAC2705", "sendHiAnalytics IllegalArgumentException ", e2);
        } catch (Throwable e22) {
            e.d("PushLogAC2705", "sendHiAnalytics InvocationTargetException", e22);
        } catch (Throwable e222) {
            e.d("PushLogAC2705", "sendHiAnalytics NoSuchMethodException", e222);
        } catch (Throwable e2222) {
            e.d("PushLogAC2705", "sendHiAnalytics ClassNotFoundException", e2222);
        } catch (Throwable e22222) {
            e.d("PushLogAC2705", "sendHiAnalytics Exception", e22222);
        }
    }
}
