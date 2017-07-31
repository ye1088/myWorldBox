package com.huluxia.controller.resource.zip;

import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;

/* compiled from: HpkUnzipHistory */
class c$1 extends CallbackHandler {
    final /* synthetic */ c oI;

    c$1(c this$0) {
        this.oI = this$0;
    }

    @MessageHandler(message = 261)
    public void onReload() {
        AsyncTaskCenter.getInstance().executeSingleThread(new 1(this));
    }
}
