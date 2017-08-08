package com.MCWorld.module;

import com.MCWorld.data.server.a;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: ModuleRequestWrapper */
class o$14 implements Listener<String> {
    final /* synthetic */ int axo;

    o$14(int i) {
        this.axo = i;
    }

    public void onResponse(String response) {
        try {
            a info = (a) Json.parseJsonObject(response, a.class);
            EventNotifyCenter.notifyEvent(n.class, n.awZ, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.axo), info});
        } catch (Exception e) {
            HLog.error(this, "requestMapRanking e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.awZ, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axo), null});
        }
    }
}
