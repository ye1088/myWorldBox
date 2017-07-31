package com.huluxia.ui.mctool;

import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;

class FollowingResFragment$5 extends CallbackHandler {
    final /* synthetic */ FollowingResFragment baW;

    FollowingResFragment$5(FollowingResFragment this$0) {
        this.baW = this$0;
    }

    @MessageHandler(message = 256)
    public void onWorkPrepare(String url) {
        FollowingResFragment.j(this.baW);
    }

    @MessageHandler(message = 257)
    public void onWorkWait(String url) {
        FollowingResFragment.j(this.baW);
    }

    @MessageHandler(message = 262)
    public void onUnzipComplete(String url) {
        FollowingResFragment.j(this.baW);
    }

    @MessageHandler(message = 263)
    public void onDownloadComplete(String url) {
        FollowingResFragment.j(this.baW);
    }
}
