package com.MCWorld.module.profile;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: ProfileModule */
class g$8 implements ErrorListener {
    final /* synthetic */ g aCs;

    g$8(g this$0) {
        this.aCs = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(h.class, h.arc, new Object[]{Boolean.valueOf(false), "访问失败\n网络问题"});
    }
}
