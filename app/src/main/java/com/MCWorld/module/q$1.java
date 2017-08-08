package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import hlx.ui.recommendapp.a;

/* compiled from: RecommendAppModule */
class q$1 implements Listener<String> {
    final /* synthetic */ int axo;

    q$1(int i) {
        this.axo = i;
    }

    public void onResponse(String response) {
        try {
            a info = (a) Json.parseJsonObject(response, a.class);
            EventNotifyCenter.notifyEvent(n.class, 302, new Object[]{Boolean.valueOf(true), Integer.valueOf(this.axo), info});
        } catch (Exception e) {
            HLog.error(this, "requestRecommendApp e = " + e + ", response = " + response, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, 302, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.axo), null});
        }
    }
}
