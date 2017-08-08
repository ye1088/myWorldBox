package com.MCWorld.module;

import com.MCWorld.data.map.MapResCountInfo;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$9 implements Listener<String> {
    final /* synthetic */ k atq;

    k$9(k this$0) {
        this.atq = this$0;
    }

    public void onResponse(String response) {
        try {
            HLog.verbose(this, "requestResCount response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 518, new Object[]{(MapResCountInfo) Json.parseJsonObject(response, MapResCountInfo.class)});
        } catch (Exception e) {
            HLog.error(this, "requestResCount e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 518, null);
        }
    }
}
