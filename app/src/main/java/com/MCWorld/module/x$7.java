package com.MCWorld.module;

import com.MCWorld.data.map.f;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: SkinModule */
class x$7 implements Listener<String> {
    final /* synthetic */ x axw;

    x$7(x this$0) {
        this.axw = this$0;
    }

    public void onResponse(String response) {
        try {
            HLog.verbose(this, "requestSkinSearch response = " + response, new Object[0]);
            f info = (f) Json.parseJsonObject(response, f.class);
            EventNotifyCenter.notifyEvent(n.class, n.avL, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestSkinSearch e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.avL, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
