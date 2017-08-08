package com.MCWorld.module;

import com.MCWorld.data.server.ServerResCountInfo;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: ServerModule */
class v$1 implements Listener<String> {
    final /* synthetic */ v axu;

    v$1(v this$0) {
        this.axu = this$0;
    }

    public void onResponse(String response) {
        try {
            EventNotifyCenter.notifyEvent(n.class, 273, new Object[]{(ServerResCountInfo) Json.parseJsonObject(response, ServerResCountInfo.class)});
        } catch (Exception e) {
            HLog.error(this, "requestResCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 273, null);
        }
    }
}
