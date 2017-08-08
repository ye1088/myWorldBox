package com.MCWorld.framework.base.webview;

import com.MCWorld.framework.base.webview.VideoEnabledWebView.JavascriptInterface;

class VideoEnabledWebView$JavascriptInterface$1 implements Runnable {
    final /* synthetic */ JavascriptInterface this$1;

    VideoEnabledWebView$JavascriptInterface$1(JavascriptInterface this$1) {
        this.this$1 = this$1;
    }

    public void run() {
        if (this.this$1.this$0.videoEnabledWebChromeClient != null) {
            this.this$1.this$0.videoEnabledWebChromeClient.onHideCustomView();
        }
    }
}
