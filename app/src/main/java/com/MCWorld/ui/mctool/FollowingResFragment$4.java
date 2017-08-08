package com.MCWorld.ui.mctool;

import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;

class FollowingResFragment$4 extends CallbackHandler {
    final /* synthetic */ FollowingResFragment baW;

    FollowingResFragment$4(FollowingResFragment this$0) {
        this.baW = this$0;
    }

    @MessageHandler(message = 258)
    public void onProgress(String url, String path, ProgressInfo progressInfo) {
        FollowingResFragment.j(this.baW);
    }

    @MessageHandler(message = 256)
    public void onDownloadSucc(String url, String path) {
        switch (FollowingResFragment.f(this.baW)) {
            case 1:
            case 2:
            case 3:
            case 4:
                if (FollowingResFragment.h(this.baW) != null && true == FollowingResFragment.k(this.baW) && true == FollowingResFragment.l(this.baW)) {
                    FollowingResFragment.h(this.baW).eu(url);
                    return;
                } else if (FollowingResFragment.h(this.baW) != null) {
                    FollowingResFragment.h(this.baW).notifyDataSetChanged();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    @MessageHandler(message = 259)
    public void onDownloadCancel(String url, String path) {
        FollowingResFragment.j(this.baW);
    }

    @MessageHandler(message = 257)
    public void onDownloadError(String url, String path, Object context) {
        FollowingResFragment.j(this.baW);
    }
}
