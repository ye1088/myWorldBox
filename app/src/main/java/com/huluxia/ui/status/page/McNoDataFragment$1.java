package com.huluxia.ui.status.page;

import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;

class McNoDataFragment$1 extends CallbackHandler {
    final /* synthetic */ McNoDataFragment bju;

    McNoDataFragment$1(McNoDataFragment this$0) {
        this.bju = this$0;
    }

    @MessageHandler(message = 0)
    public void onRecvThemeChanged(int themeMode) {
        McNoDataFragment.a(this.bju, themeMode);
    }
}
