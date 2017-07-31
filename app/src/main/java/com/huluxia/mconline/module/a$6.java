package com.huluxia.mconline.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;

/* compiled from: OnlineModule */
class a$6 implements ErrorListener {
    final /* synthetic */ a aml;

    a$6(a this$0) {
        this.aml = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, n.axi, new Object[]{Boolean.valueOf(false), null});
    }
}
