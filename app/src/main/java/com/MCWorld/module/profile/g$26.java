package com.MCWorld.module.profile;

import android.content.Context;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: ProfileModule */
class g$26 implements Listener<w> {
    final /* synthetic */ g aCs;
    final /* synthetic */ int aCx;
    final /* synthetic */ Context val$context;

    g$26(g this$0, int i, Context context) {
        this.aCs = this$0;
        this.aCx = i;
        this.val$context = context;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.arz, new Object[]{Boolean.valueOf(false), info, Integer.valueOf(this.aCx), this.val$context});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.arz, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.aCx), this.val$context});
    }
}
