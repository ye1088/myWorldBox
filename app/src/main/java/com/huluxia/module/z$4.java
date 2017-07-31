package com.huluxia.module;

import com.huluxia.data.profile.d;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$4 implements Listener<d> {
    final /* synthetic */ int axy;

    z$4(int i) {
        this.axy = i;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((d) obj);
    }

    public void a(d info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, 786, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.axy)});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, 786, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.axy)});
    }
}
