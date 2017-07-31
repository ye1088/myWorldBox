package com.huluxia.module;

import com.huluxia.data.studio.c;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$25 implements Listener<String> {
    final /* synthetic */ int axz;

    z$25(int i) {
        this.axz = i;
    }

    public void onResponse(String response) {
        try {
            c info = (c) Json.parseJsonObject(response, c.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEvent(h.class, 793, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(false), info});
                return;
            }
            info.setUrl();
            EventNotifyCenter.notifyEvent(h.class, 793, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, 793, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(false), null});
        }
    }
}
