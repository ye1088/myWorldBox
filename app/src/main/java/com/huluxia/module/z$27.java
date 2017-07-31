package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$27 implements ErrorListener {
    final /* synthetic */ int axC;

    z$27(int i) {
        this.axC = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.asq, new Object[]{Integer.valueOf(this.axC), Boolean.valueOf(false), null});
    }
}
