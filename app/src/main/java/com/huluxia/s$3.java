package com.huluxia;

import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.GameInfo;
import java.util.Map.Entry;

/* compiled from: StatisticsDownload */
class s$3 extends CallbackHandler {
    final /* synthetic */ s lK;

    s$3(s this$0) {
        this.lK = this$0;
    }

    @MessageHandler(message = 3)
    public void onRecvDownloadInfo(boolean succ, long appId, Object context) {
        for (Entry<String, GameInfo> entry : s.a(this.lK).entrySet()) {
            if (((GameInfo) entry.getValue()).appid == appId) {
                this.lK.T((String) entry.getKey());
                return;
            }
        }
    }
}
