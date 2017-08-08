package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$16 implements ErrorListener {
    final /* synthetic */ int axy;

    z$16(int i) {
        this.axy = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 785, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.axy)});
    }
}
