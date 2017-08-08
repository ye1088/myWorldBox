package com.MCWorld.ui.mctool;

import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.service.i;

/* compiled from: MapDownloadInterceptor */
class b$1 extends CallbackHandler {
    final /* synthetic */ b bbn;

    b$1(b this$0) {
        this.bbn = this$0;
    }

    @MessageHandler(message = 262)
    public void onUnzip(String url) {
        if (url.equals(b.a(this.bbn).url)) {
            EventNotifyCenter.remove(b.b(this.bbn));
            b.c(this.bbn);
            i.ki(b.d(this.bbn));
        }
    }
}
