package com.huluxia.widget.x5web;

import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

class X5WebView$1 extends WebViewClient {
    final /* synthetic */ X5WebView bHB;

    X5WebView$1(X5WebView this$0) {
        this.bHB = this$0;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onReceivedHttpAuthRequest(WebView webview, HttpAuthHandler httpAuthHandlerhost, String host, String realm) {
        boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
    }
}
