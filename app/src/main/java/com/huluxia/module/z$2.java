package com.huluxia.module;

import com.huluxia.data.map.f;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$2 implements Listener<f> {
    final /* synthetic */ int atr;
    final /* synthetic */ int axy;

    z$2(int i, int i2) {
        this.axy = i;
        this.atr = i2;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((f) obj);
    }

    public void a(f response) {
        if (response == null || !response.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 790, new Object[]{Integer.valueOf(this.axy), Integer.valueOf(this.atr), Boolean.valueOf(false), response});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 790, new Object[]{Integer.valueOf(this.axy), Integer.valueOf(this.atr), Boolean.valueOf(true), response});
    }
}
