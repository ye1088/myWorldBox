package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.WebView;
import com.tencent.smtt.sdk.QbSdk.PreInitCallback;

final class e extends Handler {
    final /* synthetic */ Context a;
    final /* synthetic */ PreInitCallback b;

    e(Looper looper, Context context, PreInitCallback preInitCallback) {
        this.a = context;
        this.b = preInitCallback;
        super(looper);
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                af a = i.a(true).a();
                if (a != null) {
                    a.a(this.a);
                }
                if (this.b != null) {
                    this.b.onViewInitFinished();
                    return;
                }
                return;
            case 2:
                WebView webView = new WebView(this.a);
                if (this.b != null) {
                    this.b.onViewInitFinished();
                    return;
                }
                return;
            case 3:
                if (this.b != null) {
                    this.b.onCoreInitFinished();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
