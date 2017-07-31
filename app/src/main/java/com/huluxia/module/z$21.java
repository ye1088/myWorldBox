package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$21 implements Listener<w> {
    final /* synthetic */ int axA;
    final /* synthetic */ int axB;
    final /* synthetic */ int val$position;

    z$21(int i, int i2, int i3) {
        this.val$position = i;
        this.axA = i2;
        this.axB = i3;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 789, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.val$position), Integer.valueOf(this.axA), Integer.valueOf(this.axB)});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 789, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.val$position), Integer.valueOf(this.axA), Integer.valueOf(this.axB)});
    }
}
