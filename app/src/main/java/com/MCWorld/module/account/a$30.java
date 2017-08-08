package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: AccountModule */
class a$30 implements ErrorListener {
    final /* synthetic */ a aBH;

    a$30(a this$0) {
        this.aBH = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arK, new Object[]{Boolean.valueOf(false), null});
    }
}
