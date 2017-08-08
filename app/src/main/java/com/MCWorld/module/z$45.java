package com.MCWorld.module;

import com.MCWorld.data.profile.e.a;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$45 implements Listener<w> {
    final /* synthetic */ a axF;
    final /* synthetic */ int axy;

    z$45(a aVar, int i) {
        this.axF = aVar;
        this.axy = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 777, new Object[]{Boolean.valueOf(false), info, this.axF, Integer.valueOf(this.axy)});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 777, new Object[]{Boolean.valueOf(true), info, this.axF, Integer.valueOf(this.axy)});
    }
}
