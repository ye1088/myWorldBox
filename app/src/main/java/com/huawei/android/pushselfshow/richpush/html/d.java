package com.huawei.android.pushselfshow.richpush.html;

import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.huawei.android.pushagent.c.a.e;

class d extends WebChromeClient {
    final /* synthetic */ HtmlViewer a;

    d(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void onProgressChanged(WebView webView, int i) {
        if (HtmlViewer.a(this.a) != null) {
            if (HtmlViewer.a(this.a).canGoBack()) {
                HtmlViewer.e(this.a).b(HtmlViewer.d(this.a));
            } else {
                HtmlViewer.e(this.a).a(HtmlViewer.d(this.a));
            }
            if (HtmlViewer.a(this.a).canGoForward()) {
                HtmlViewer.e(this.a).b(HtmlViewer.f(this.a));
            } else {
                HtmlViewer.e(this.a).a(HtmlViewer.f(this.a));
            }
        }
        if (i < 5) {
            i = 5;
        }
        this.a.setProgress(i);
        super.onProgressChanged(webView, i);
    }

    public void onReceivedTitle(WebView webView, String str) {
        super.onReceivedTitle(webView, str);
        e.a("PushSelfShowLog", "onReceivedTitle:" + str);
        if (!TextUtils.isEmpty(str)) {
            HtmlViewer.g(this.a).setText(str);
        }
    }
}
