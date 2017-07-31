package com.tencent.smtt.sdk;

class d implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ c b;

    d(c cVar, boolean z) {
        this.b = cVar;
        this.a = z;
    }

    public void run() {
        this.b.c.onReceiveValue(Boolean.valueOf(this.a));
    }
}
