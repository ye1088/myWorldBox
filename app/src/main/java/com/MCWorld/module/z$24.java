package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$24 implements ErrorListener {
    z$24() {
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 791, new Object[]{Boolean.valueOf(false), null});
    }
}
