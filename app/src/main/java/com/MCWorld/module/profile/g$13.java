package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: ProfileModule */
class g$13 implements Listener<w> {
    final /* synthetic */ g aCs;

    g$13(g this$0) {
        this.aCs = this$0;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((w) obj);
    }

    public void a(w info) {
        if (info == null || !info.isSucc()) {
            String msg;
            if (info == null) {
                msg = "举报失败，请稍后重试";
            } else {
                msg = info.msg;
            }
            EventNotifyCenter.notifyEventUiThread(h.class, h.arp, new Object[]{Boolean.valueOf(false), info.msg});
            return;
        }
        EventNotifyCenter.notifyEventUiThread(h.class, h.arp, new Object[]{Boolean.valueOf(true), "举报成功，等待处理"});
    }
}
