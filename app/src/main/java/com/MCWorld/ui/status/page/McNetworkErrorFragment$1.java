package com.MCWorld.ui.status.page;

import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;

class McNetworkErrorFragment$1 extends CallbackHandler {
    final /* synthetic */ McNetworkErrorFragment bjt;

    McNetworkErrorFragment$1(McNetworkErrorFragment this$0) {
        this.bjt = this$0;
    }

    @MessageHandler(message = 0)
    public void onRecvThemeChanged(int themeMode) {
        McNetworkErrorFragment.a(this.bjt, themeMode);
    }
}
