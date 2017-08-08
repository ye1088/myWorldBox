package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: AccountModule */
class a$25 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$25(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        try {
            c info = (c) Json.parseJsonObject(response, c.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEventUiThread(h.class, h.arI, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            EventNotifyCenter.notifyEventUiThread(h.class, h.arI, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arI, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
