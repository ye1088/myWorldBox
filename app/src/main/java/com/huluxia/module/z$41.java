package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$41 implements Listener<w> {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;

    z$41(z this$0, int i) {
        this.axD = this$0;
        this.axB = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asy, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), info});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asy, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(true), info});
    }
}
