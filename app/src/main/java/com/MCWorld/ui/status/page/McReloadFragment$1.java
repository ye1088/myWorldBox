package com.MCWorld.ui.status.page;

import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;

class McReloadFragment$1 extends CallbackHandler {
    final /* synthetic */ McReloadFragment bjv;

    McReloadFragment$1(McReloadFragment this$0) {
        this.bjv = this$0;
    }

    @MessageHandler(message = 0)
    public void onRecvThemeChanged(int themeMode) {
        McReloadFragment.a(this.bjv, themeMode);
    }
}
