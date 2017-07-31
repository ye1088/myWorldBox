package com.huluxia.module.profile;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: ProfileModule */
class g$1 implements Listener<String> {
    final /* synthetic */ g aCs;

    g$1(g this$0) {
        this.aCs = this$0;
    }

    public void onResponse(String response) {
        try {
            e info = (e) Json.parseJsonObject(response, e.class);
            EventNotifyCenter.notifyEvent(h.class, h.aqY, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, h.aqY, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
