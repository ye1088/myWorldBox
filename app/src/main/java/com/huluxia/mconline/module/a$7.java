package com.huluxia.mconline.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.mconline.gameloc.http.d;
import com.huluxia.module.n;

/* compiled from: OnlineModule */
class a$7 implements Listener<String> {
    final /* synthetic */ a aml;

    a$7(a this$0) {
        this.aml = this$0;
    }

    public void onResponse(String response) {
        try {
            d info = (d) Json.parseJsonObject(response, d.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.axk, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.axk, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.axk, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
