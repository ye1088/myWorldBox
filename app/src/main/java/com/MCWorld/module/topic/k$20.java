package com.MCWorld.module.topic;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;

/* compiled from: TopicModule2 */
class k$20 implements ErrorListener {
    final /* synthetic */ k aCN;

    k$20(k this$0) {
        this.aCN = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        EventNotifyCenter.notifyEventUiThread(h.class, h.arg, new Object[]{Boolean.valueOf(false), null});
    }
}
