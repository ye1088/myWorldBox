package com.huluxia.module;

import com.huluxia.data.map.f;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: ModuleRequestWrapper */
class o$5 implements Listener<String> {
    final /* synthetic */ int axp;

    o$5(int i) {
        this.axp = i;
    }

    public void onResponse(String response) {
        try {
            f info = (f) Json.parseJsonObject(response, f.class);
            EventNotifyCenter.notifyEvent(n.class, n.awS, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.axp), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.awS, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axp), null});
        }
    }
}
