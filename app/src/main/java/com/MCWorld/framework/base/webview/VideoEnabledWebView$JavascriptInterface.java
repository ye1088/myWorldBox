package com.MCWorld.framework.base.webview;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class VideoEnabledWebView$JavascriptInterface {
    final /* synthetic */ VideoEnabledWebView this$0;

    public VideoEnabledWebView$JavascriptInterface(VideoEnabledWebView this$0) {
        this.this$0 = this$0;
    }

    @JavascriptInterface
    public void notifyVideoEnd() {
        Log.d("___", "GOT IT");
        new Handler(Looper.getMainLooper()).post(new 1(this));
    }
}
