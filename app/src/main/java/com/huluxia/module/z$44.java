package com.huluxia.module;

import com.huluxia.data.profile.e.a;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;

/* compiled from: StudioModule */
class z$44 implements ErrorListener {
    final /* synthetic */ a axF;
    final /* synthetic */ int axy;

    z$44(a aVar, int i) {
        this.axF = aVar;
        this.axy = i;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, 777, new Object[]{Boolean.valueOf(false), null, this.axF, Integer.valueOf(this.axy)});
    }
}
