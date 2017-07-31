package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import hlx.module.resources.b;

/* compiled from: StudioModule */
class z$22 implements Listener<String> {
    z$22() {
    }

    public void onResponse(String response) {
        try {
            b info = (b) Json.parseJsonObject(response, b.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(h.class, 791, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            EventNotifyCenter.notifyEvent(h.class, 791, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, 791, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
