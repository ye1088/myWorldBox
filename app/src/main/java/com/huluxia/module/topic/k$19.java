package com.huluxia.module.topic;

import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.a;
import com.huluxia.module.h;

/* compiled from: TopicModule2 */
class k$19 implements Listener<l> {
    final /* synthetic */ k aCN;
    final /* synthetic */ Object rB;

    k$19(k this$0, Object obj) {
        this.aCN = this$0;
        this.rB = obj;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((l) obj);
    }

    public void a(l info) {
        HLog.debug(this, "getVcode response %s", new Object[]{info});
        if (a.isDataSucc(info)) {
            EventNotifyCenter.notifyEventUiThread(h.class, 772, new Object[]{Boolean.valueOf(true), info, this.rB});
            return;
        }
        EventNotifyCenter.notifyEventUiThread(h.class, 772, new Object[]{Boolean.valueOf(false), info, this.rB});
    }
}
