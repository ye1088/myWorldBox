package com.MCWorld.module;

import com.MCWorld.data.server.a.a;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: ServerModule */
class v$7 implements Listener<String> {
    final /* synthetic */ v axu;

    v$7(v this$0) {
        this.axu = this$0;
    }

    public void onResponse(String response) {
        try {
            a item = (a) ((com.MCWorld.data.server.a) Json.parseJsonObject(response, com.MCWorld.data.server.a.class)).serverList.get(0);
            EventNotifyCenter.notifyEvent(n.class, 258, new Object[]{Boolean.valueOf(true), item});
        } catch (Exception e) {
            HLog.error(this, "requestServerInfoItem onErrorResponse e = " + e + " , response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 258, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
