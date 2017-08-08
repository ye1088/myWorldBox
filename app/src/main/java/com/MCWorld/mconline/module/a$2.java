package com.MCWorld.mconline.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;

/* compiled from: OnlineModule */
class a$2 implements ErrorListener {
    final /* synthetic */ Object amk;
    final /* synthetic */ a aml;

    a$2(a this$0, Object obj) {
        this.aml = this$0;
        this.amk = obj;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.axg, new Object[]{Boolean.valueOf(false), null, this.amk});
    }
}
