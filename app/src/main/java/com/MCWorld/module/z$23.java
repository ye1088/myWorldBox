package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$23 implements ErrorListener {
    final /* synthetic */ int axy;
    final /* synthetic */ int val$position;

    z$23(int i, int i2) {
        this.val$position = i;
        this.axy = i2;
    }

    public void onErrorResponse(VolleyError error) {
        if (this.val$position == 257) {
            EventNotifyCenter.notifyEvent(h.class, h.arZ, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.axy)});
        } else if (this.val$position == 258) {
            EventNotifyCenter.notifyEvent(h.class, h.asa, new Object[]{Boolean.valueOf(false), null, Integer.valueOf(this.axy)});
        }
    }
}
