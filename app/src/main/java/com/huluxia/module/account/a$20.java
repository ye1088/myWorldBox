package com.huluxia.module.account;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: AccountModule */
class a$20 implements Listener<String> {
    final /* synthetic */ a aBH;
    final /* synthetic */ int aBO;
    final /* synthetic */ int aBP;
    final /* synthetic */ String aBQ;

    a$20(a this$0, int i, int i2, String str) {
        this.aBH = this$0;
        this.aBO = i;
        this.aBP = i2;
        this.aBQ = str;
    }

    public void onResponse(String response) {
        try {
            if (this.aBO == 1) {
                d info = (d) Json.parseJsonObject(response, d.class);
                EventNotifyCenter.notifyEventUiThread(h.class, this.aBP, new Object[]{info, this.aBQ});
                return;
            }
            e info2 = (e) Json.parseJsonObject(response, e.class);
            EventNotifyCenter.notifyEventUiThread(h.class, this.aBP, new Object[]{info2, this.aBQ});
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(h.class, this.aBP, new Object[]{null, this.aBQ});
        }
    }
}
