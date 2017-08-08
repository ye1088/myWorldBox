package com.MCWorld.module;

import com.MCWorld.data.map.e;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: WoodModule */
class ac$5 implements Listener<String> {
    final /* synthetic */ ac aBD;

    ac$5(ac this$0) {
        this.aBD = this$0;
    }

    public void onResponse(String response) {
        try {
            e info = (e) Json.parseJsonObject(response, e.class);
            EventNotifyCenter.notifyEvent(n.class, n.avO, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            HLog.error(this, "requestWoodDetail e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.avO, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
