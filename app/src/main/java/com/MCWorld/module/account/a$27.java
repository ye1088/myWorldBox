package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.w;

/* compiled from: AccountModule */
class a$27 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$27(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEventUiThread(h.class, h.arJ, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            EventNotifyCenter.notifyEventUiThread(h.class, h.arJ, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arJ, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
