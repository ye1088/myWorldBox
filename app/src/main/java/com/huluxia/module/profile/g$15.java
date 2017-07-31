package com.huluxia.module.profile;

import android.content.Context;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;
import com.huluxia.module.w;

/* compiled from: ProfileModule */
class g$15 implements Listener<w> {
    final /* synthetic */ g aCs;
    final /* synthetic */ int aCv;
    final /* synthetic */ Context val$context;

    g$15(g this$0, int i, Context context) {
        this.aCs = this$0;
        this.aCv = i;
        this.val$context = context;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(h.class, h.arv, new Object[]{info, null, Integer.valueOf(this.aCv), this.val$context});
            return;
        }
        EventNotifyCenter.notifyEvent(h.class, h.arv, new Object[]{Boolean.valueOf(true), info, Integer.valueOf(this.aCv), this.val$context});
    }
}
