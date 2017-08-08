package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.topic.b;

/* compiled from: ProfileModule */
class g$35 implements Listener<b> {
    final /* synthetic */ String aBQ;
    final /* synthetic */ long aCj;
    final /* synthetic */ g aCs;

    g$35(g this$0, String str, long j) {
        this.aCs = this$0;
        this.aBQ = str;
        this.aCj = j;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((b) obj);
    }

    public void a(b info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.arr, new Object[]{Boolean.valueOf(false), this.aBQ, null, Long.valueOf(this.aCj)});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.arr, new Object[]{Boolean.valueOf(true), this.aBQ, info, Long.valueOf(this.aCj)});
    }
}
