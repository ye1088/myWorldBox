package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$3 implements ErrorListener {
    final /* synthetic */ int axy;

    z$3(int i) {
        this.axy = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 786, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.axy)});
    }
}
