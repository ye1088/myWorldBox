package com.huluxia;

import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;

/* compiled from: StatisticsDownload */
class s$2 extends CallbackHandler {
    final /* synthetic */ s lK;

    s$2(s this$0) {
        this.lK = this$0;
    }

    @MessageHandler(message = 265)
    public void onInstallApk(String url, String apkPath, String signature) {
    }

    @MessageHandler(message = 263)
    public void onDownloadComplete(String url) {
        this.lK.S(url);
    }
}
