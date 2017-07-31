package com.huluxia.version;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;

/* compiled from: VersionModule */
class h$1 implements ErrorListener {
    final /* synthetic */ h boB;

    h$1(h this$0) {
        this.boB = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 774, new Object[]{Boolean.valueOf(false), null});
    }
}
