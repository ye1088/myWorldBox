package com.huawei.android.pushselfshow.richpush.html;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.huawei.android.pushselfshow.utils.d;
import java.io.File;

class e extends WebViewClient {
    final /* synthetic */ HtmlViewer a;

    e(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void onLoadResource(WebView webView, String str) {
        super.onLoadResource(webView, str);
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        com.huawei.android.pushagent.c.a.e.a("PushSelfShowLog", "onPageFinished:" + str + ",title:" + webView.getTitle());
        String title = webView.getTitle();
        if (title != null && title.endsWith(".html")) {
            HtmlViewer.g(this.a).setText(HtmlViewer.b(this.a).getString(d.a(HtmlViewer.b(this.a), "hwpush_richmedia")));
        }
        try {
            if (HtmlViewer.a(this.a) != null && !str.equals(HtmlViewer.b(this.a).getFilesDir().getPath() + File.separator + "PushService" + File.separator + "richpush" + File.separator + "error.html")) {
                title = "";
                if ("text/html_local".equals(HtmlViewer.h(this.a).E)) {
                    title = (("var newscript = document.createElement(\"script\");" + "newscript.src=\"" + this.a.prepareJS(str) + "\";") + "newscript.onload=function(){ try {onDeviceReady();}catch(err){}};") + "document.body.appendChild(newscript);";
                } else {
                    title = (("var newscript = document.createElement(\"script\");" + "newscript.src=\"http://open.hicloud.com/android/push1.0.js\";") + "newscript.onload=function(){ try { onDeviceReady();}catch(err){}};") + "document.body.appendChild(newscript);";
                }
                com.huawei.android.pushagent.c.a.e.a("PushSelfShowLog", "load js " + title);
                HtmlViewer.a(this.a).loadUrl("javascript:" + title);
            }
        } catch (Throwable e) {
            com.huawei.android.pushagent.c.a.e.a("PushSelfShowLog", "onPageFinished load err " + e.toString(), e);
        }
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        com.huawei.android.pushagent.c.a.e.a("PushSelfShowLog", "onPageStarted:" + str);
        this.a.setProgress(5);
        HtmlViewer.g(this.a).setText(HtmlViewer.b(this.a).getString(d.a(HtmlViewer.b(this.a), "hwpush_richmedia")));
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        com.huawei.android.pushagent.c.a.e.d("PushSelfShowLog", "onReceivedSslError");
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        try {
            if (!str.startsWith(com.tencent.smtt.sdk.WebView.SCHEME_MAILTO) && !str.startsWith(com.tencent.smtt.sdk.WebView.SCHEME_TEL) && !str.startsWith("smsto:") && !str.startsWith("sms:") && !str.startsWith("geo:")) {
                return false;
            }
            HtmlViewer.b(this.a).startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            return true;
        } catch (Throwable e) {
            com.huawei.android.pushagent.c.a.e.a("PushSelfShowLog", "", e);
        }
    }
}
