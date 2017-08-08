package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import hlx.ui.mapseed.b;

/* compiled from: SeedModule */
class u$1 implements Listener<String> {
    final /* synthetic */ int axo;

    u$1(int i) {
        this.axo = i;
    }

    public void onResponse(String response) {
        try {
            b info = (b) Json.parseJsonObject(response, b.class);
            EventNotifyCenter.notifyEvent(n.class, 298, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.axo), info});
        } catch (Exception e) {
            HLog.error(this, "requestSeed e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 298, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axo), null});
        }
    }
}
