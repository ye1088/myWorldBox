package com.MCWorld.module;

import com.MCWorld.data.cdn.a;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$5 implements Listener<String> {
    final /* synthetic */ k atq;

    k$5(k this$0) {
        this.atq = this$0;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, n.axf, new Object[]{(a) Json.parseJsonObject(response, a.class)});
        } catch (Exception e) {
            HLog.error(this, "requestCDNHostInfo e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.axf, null);
        }
    }
}
