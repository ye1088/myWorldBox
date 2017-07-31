package com.huluxia.ui.status.page;

import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;

class McLoadingFragment$1 extends CallbackHandler {
    final /* synthetic */ McLoadingFragment bjs;

    McLoadingFragment$1(McLoadingFragment this$0) {
        this.bjs = this$0;
    }

    @MessageHandler(message = 0)
    public void onRecvThemeChanged(int themeMode) {
        McLoadingFragment.a(this.bjs, themeMode);
    }
}
