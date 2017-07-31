package com.huluxia.module.profile;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: ProfileModule */
class g$5 implements Listener<String> {
    final /* synthetic */ g aCs;

    g$5(g this$0) {
        this.aCs = this$0;
    }

    public void onResponse(String response) {
        try {
            b record = (b) Json.parseJsonObject(response, b.class);
            if (record == null || !record.isSucc()) {
                EventNotifyCenter.notifyEvent(h.class, h.ard, new Object[]{Boolean.valueOf(false), null, record.msg});
                return;
            }
            EventNotifyCenter.notifyEvent(h.class, h.ard, new Object[]{Boolean.valueOf(true), record, null});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, h.ard, new Object[]{Boolean.valueOf(false), null, "访问失败"});
        }
    }
}
