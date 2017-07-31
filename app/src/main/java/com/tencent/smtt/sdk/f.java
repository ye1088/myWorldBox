package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;

final class f extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ Handler b;

    f(Context context, Handler handler) {
        this.a = context;
        this.b = handler;
    }

    public void run() {
        if (aa.a().b(this.a) == 0) {
            aa.a().a(this.a, true);
        }
        i a = i.a(true);
        a.a(this.a, false, true);
        if (TbsShareManager.isThirdPartyApp(this.a)) {
            if (!TbsShareManager.forceLoadX5FromTBSDemo(this.a.getApplicationContext())) {
                TbsDownloader.needDownload(this.a, false);
            }
            if (!(WebView.mSysWebviewCreated || QbSdk.d)) {
                QbSdk.a = false;
            }
        }
        boolean b = a.b();
        this.b.sendEmptyMessage(3);
        if (b) {
            this.b.sendEmptyMessage(1);
        } else {
            this.b.sendEmptyMessage(2);
        }
    }
}
