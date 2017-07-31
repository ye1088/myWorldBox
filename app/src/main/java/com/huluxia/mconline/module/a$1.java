package com.huluxia.mconline.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.mconline.gameloc.http.h;
import com.huluxia.module.n;

/* compiled from: OnlineModule */
class a$1 implements Listener<String> {
    final /* synthetic */ Object amk;
    final /* synthetic */ a aml;

    a$1(a this$0, Object obj) {
        this.aml = this$0;
        this.amk = obj;
    }

    public void onResponse(String response) {
        try {
            h info = (h) Json.parseJsonObject(response, h.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(n.class, n.axg, new Object[]{Boolean.valueOf(false), info, this.amk});
                return;
            }
            EventNotifyCenter.notifyEvent(n.class, n.axg, new Object[]{Boolean.valueOf(true), info, this.amk});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.axg, new Object[]{Boolean.valueOf(false), null, this.amk});
        }
    }
}
