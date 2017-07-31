package com.baidu.mapapi;

import android.os.Handler;
import android.os.Message;

class t extends Handler {
    final /* synthetic */ s a;

    t(s sVar) {
        this.a = sVar;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.a.b();
                return;
            case 2:
                this.a.c();
                return;
            default:
                return;
        }
    }
}
