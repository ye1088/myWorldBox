package com.huluxia.module;

import com.huluxia.data.server.a;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

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
