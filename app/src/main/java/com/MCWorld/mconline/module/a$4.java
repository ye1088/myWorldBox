package com.MCWorld.mconline.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

/* compiled from: OnlineModule */
class a$4 implements ErrorListener {
    final /* synthetic */ a aml;
    final /* synthetic */ long val$start;

    a$4(a this$0, long j) {
        this.aml = this$0;
        this.val$start = j;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.axh, new Object[]{Boolean.valueOf(false), Long.valueOf(this.val$start), null});
    }
}
