package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: AccountModule */
class a$7 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$7(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            EventNotifyCenter.notifyEvent(h.class, h.arn, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEvent(h.class, h.arn, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
