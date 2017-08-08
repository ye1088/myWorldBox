package com.MCWorld.module.account;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: AccountModule */
class a$32 implements ErrorListener {
    final /* synthetic */ a aBH;

    a$32(a this$0) {
        this.aBH = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arL, new Object[]{Boolean.valueOf(false), null});
    }
}
