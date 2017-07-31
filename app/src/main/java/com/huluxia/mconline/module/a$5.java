package com.huluxia.mconline.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.mconline.gameloc.http.h;
import com.huluxia.module.n;

/* compiled from: OnlineModule */
class a$5 implements Listener<String> {
    final /* synthetic */ a aml;

    a$5(a this$0) {
        this.aml = this$0;
    }

    public void onResponse(String response) {
        try {
            h info = (h) Json.parseJsonObject(response, h.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.axi, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.axi, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.axi, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
