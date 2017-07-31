package com.huluxia.version;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;

/* compiled from: VersionMemCache */
class g$2 extends CallbackHandler {
    final /* synthetic */ g boy;

    g$2(g this$0) {
        this.boy = this$0;
    }

    @MessageHandler(message = 265)
    public void onDbOpen() {
        HLog.info("VersionMemCache", "db open recv", new Object[0]);
        g.b(this.boy);
    }
}
