package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$9 implements ErrorListener {
    final /* synthetic */ int axz;

    z$9(int i) {
        this.axz = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.asn, new Object[]{Integer.valueOf(this.axz), Boolean.valueOf(false), null});
    }
}
