package com.huluxia.mconline.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.mconline.gameloc.http.h;
import com.huluxia.module.n;

/* compiled from: OnlineModule */
class a$3 implements Listener<String> {
    final /* synthetic */ a aml;
    final /* synthetic */ long val$start;

    a$3(a this$0, long j) {
        this.aml = this$0;
        this.val$start = j;
    }

    public void onResponse(String response) {
        try {
            h info = (h) Json.parseJsonObject(response, h.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.axh, new Object[]{Boolean.valueOf(false), Long.valueOf(this.val$start), null});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.axh, new Object[]{Boolean.valueOf(true), Long.valueOf(this.val$start), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.axh, new Object[]{Boolean.valueOf(false), Long.valueOf(this.val$start), null});
        }
    }
}
