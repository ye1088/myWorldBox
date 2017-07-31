package com.huluxia.module;

import com.huluxia.data.server.a;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: ModuleRequestWrapper */
class o$16 implements Listener<String> {
    final /* synthetic */ int axp;

    o$16(int i) {
        this.axp = i;
    }

    public void onResponse(String response) {
        try {
            a info = (a) Json.parseJsonObject(response, a.class);
            EventNotifyCenter.notifyEvent(n.class, 3077, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.axp), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, 3077, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axp), null});
        }
    }
}
