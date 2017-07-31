package com.huluxia.module;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$14 implements ErrorListener {
    z$14() {
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 788, new Object[]{Boolean.valueOf(false), null});
    }
}
