package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$26 implements ErrorListener {
    final /* synthetic */ int axz;

    z$26(int i) {
        this.axz = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 793, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(false), null});
    }
}
