package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

final class c extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ ValueCallback c;

    c(Context context, String str, ValueCallback valueCallback) {
        this.a = context;
        this.b = str;
        this.c = valueCallback;
    }

    public void run() {
        i a = i.a(true);
        a.a(this.a);
        boolean z = false;
        if (a.b()) {
            z = a.a().a(this.a, this.b);
        }
        new Handler(Looper.getMainLooper()).post(new d(this, z));
    }
}
