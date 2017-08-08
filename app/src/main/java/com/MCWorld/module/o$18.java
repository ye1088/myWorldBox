package com.MCWorld.module;

import com.MCWorld.data.map.f;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: ModuleRequestWrapper */
class o$18 implements Listener<String> {
    final /* synthetic */ int axp;

    o$18(int i) {
        this.axp = i;
    }

    public void onResponse(String response) {
        try {
            f info = (f) Json.parseJsonObject(response, f.class);
            EventNotifyCenter.notifyEvent(n.class, n.avZ, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.axp), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(n.class, n.avZ, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axp), null});
        }
    }
}
