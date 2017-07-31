package com.tencent.smtt.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class x extends Handler {
    x(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 100:
                TbsDownloader.a(true);
                return;
            default:
                return;
        }
    }
}
