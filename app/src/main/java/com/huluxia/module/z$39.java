package com.huluxia.module;

import com.huluxia.data.profile.e.a;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$39 implements Listener<w> {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;
    final /* synthetic */ a axE;

    z$39(z this$0, int i, a aVar) {
        this.axD = this$0;
        this.axB = i;
        this.axE = aVar;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.asx, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), info, this.axE});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.asx, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(true), info, this.axE});
    }
}
