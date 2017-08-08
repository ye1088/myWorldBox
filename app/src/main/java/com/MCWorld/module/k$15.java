package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: MapModule */
class k$15 implements ErrorListener {
    final /* synthetic */ Object amk;
    final /* synthetic */ int atr;
    final /* synthetic */ int ats;

    k$15(int i, int i2, Object obj) {
        this.atr = i;
        this.ats = i2;
        this.amk = obj;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEvent(n.class, 528, new Object[]{Boolean.valueOf(false), Integer.valueOf(this.atr), Integer.valueOf(this.ats), null, "获取数据失败", this.amk});
    }
}
