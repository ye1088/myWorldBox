package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;

/* compiled from: SeedModule */
class u$4 implements ErrorListener {
    u$4() {
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEventUiThread(n.class, 301, new Object[]{Boolean.valueOf(false), null, "加载种子分类失败"});
    }
}
