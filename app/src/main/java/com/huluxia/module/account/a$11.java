package com.huluxia.module.account;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;
import com.huluxia.module.w;

/* compiled from: AccountModule */
class a$11 implements Listener<String> {
    final /* synthetic */ a aBH;

    a$11(a this$0) {
        this.aBH = this$0;
    }

    public void onResponse(String response) {
        try {
            w info = (w) Json.parseJsonObject(response, w.class);
            if (info == null || !info.isSucc()) {
                EventNotifyCenter.notifyEventUiThread(h.class, h.arG, new Object[]{Boolean.valueOf(false), info});
                return;
            }
            EventNotifyCenter.notifyEventUiThread(h.class, h.arG, new Object[]{Boolean.valueOf(true), info});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.arG, new Object[]{Boolean.valueOf(false), null});
        }
    }
}
