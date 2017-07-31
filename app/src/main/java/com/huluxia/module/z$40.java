package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$40 implements ErrorListener {
    final /* synthetic */ int axB;
    final /* synthetic */ z axD;

    z$40(z this$0, int i) {
        this.axD = this$0;
        this.axB = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.asy, new Object[]{Integer.valueOf(this.axB), Boolean.valueOf(false), null});
    }
}
