package com.tencent.smtt.sdk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.export.external.proxy.X5ProxyWebViewClient;
import com.tencent.smtt.sdk.b.d;
import com.tencent.smtt.utils.TbsLog;

class p extends X5ProxyWebViewClient {
    private static String c = null;
    private WebViewClient a;
    private WebView b;

    public p(IX5WebViewClient iX5WebViewClient, WebView webView, WebViewClient webViewClient) {
        super(iX5WebViewClient);
        this.b = webView;
        this.a = webViewClient;
        this.a.a = this;
    }

    public void a(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(this.b.b(), 0, 0, str, bitmap);
    }

    public void a(String str) {
        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(str));
        intent.addFlags(268435456);
        try {
            if (this.b.getContext() != null) {
                this.b.getContext().startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doUpdateVisitedHistory(IX5WebViewBase iX5WebViewBase, String str, boolean z) {
        this.b.a(iX5WebViewBase);
        this.a.doUpdateVisitedHistory(this.b, str, z);
    }

    public void onDetectedBlankScreen(IX5WebViewBase iX5WebViewBase, String str, int i) {
        this.b.a(iX5WebViewBase);
        this.a.onDetectedBlankScreen(str, i);
    }

    public void onFormResubmission(IX5WebViewBase iX5WebViewBase, Message message, Message message2) {
        this.b.a(iX5WebViewBase);
        this.a.onFormResubmission(this.b, message, message2);
    }

    public void onLoadResource(IX5WebViewBase iX5WebViewBase, String str) {
        this.b.a(iX5WebViewBase);
        this.a.onLoadResource(this.b, str);
    }

    public void onPageFinished(IX5WebViewBase iX5WebViewBase, int i, int i2, String str) {
        if (c == null) {
            com.tencent.smtt.utils.p a = com.tencent.smtt.utils.p.a();
            if (a != null) {
                a.a(false);
                c = Boolean.toString(false);
            }
        }
        this.b.a(iX5WebViewBase);
        WebView webView = this.b;
        webView.a++;
        this.a.onPageFinished(this.b, str);
        if ("com.qzone".equals(iX5WebViewBase.getView().getContext().getApplicationInfo().packageName)) {
            this.b.a(iX5WebViewBase.getView().getContext());
        }
        TbsLog.app_extra("SmttWebViewClient", iX5WebViewBase.getView().getContext());
        try {
            super.onPageFinished(iX5WebViewBase, i, i2, str);
        } catch (Exception e) {
        }
        d.a(this.b);
        if (this.b.getContext() != null && TbsShareManager.isThirdPartyApp(this.b.getContext()) && !TbsShareManager.forceLoadX5FromTBSDemo(this.b.getContext()) && !TbsDownloader.needDownload(this.b.getContext(), false)) {
        }
    }

    public void onPageFinished(IX5WebViewBase iX5WebViewBase, String str) {
        onPageFinished(iX5WebViewBase, 0, 0, str);
    }

    public void onPageStarted(IX5WebViewBase iX5WebViewBase, int i, int i2, String str, Bitmap bitmap) {
        this.b.a(iX5WebViewBase);
        this.a.onPageStarted(this.b, str, bitmap);
    }

    public void onPageStarted(IX5WebViewBase iX5WebViewBase, String str, Bitmap bitmap) {
        onPageStarted(iX5WebViewBase, 0, 0, str, bitmap);
    }

    public void onReceivedError(IX5WebViewBase iX5WebViewBase, int i, String str, String str2) {
        if (i < -15) {
            if (i == -17) {
                i = -1;
            } else {
                return;
            }
        }
        this.b.a(iX5WebViewBase);
        this.a.onReceivedError(this.b, i, str, str2);
    }

    public void onReceivedHttpAuthRequest(IX5WebViewBase iX5WebViewBase, HttpAuthHandler httpAuthHandler, String str, String str2) {
        this.b.a(iX5WebViewBase);
        this.a.onReceivedHttpAuthRequest(this.b, httpAuthHandler, str, str2);
    }

    public void onReceivedHttpError(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        this.b.a(iX5WebViewBase);
        this.a.onReceivedHttpError(this.b, webResourceRequest, webResourceResponse);
    }

    public void onReceivedLoginRequest(IX5WebViewBase iX5WebViewBase, String str, String str2, String str3) {
        this.b.a(iX5WebViewBase);
        this.a.onReceivedLoginRequest(this.b, str, str2, str3);
    }

    public void onReceivedSslError(IX5WebViewBase iX5WebViewBase, SslErrorHandler sslErrorHandler, SslError sslError) {
        this.b.a(iX5WebViewBase);
        this.a.onReceivedSslError(this.b, sslErrorHandler, sslError);
    }

    public void onScaleChanged(IX5WebViewBase iX5WebViewBase, float f, float f2) {
        this.b.a(iX5WebViewBase);
        this.a.onScaleChanged(this.b, f, f2);
    }

    public void onTooManyRedirects(IX5WebViewBase iX5WebViewBase, Message message, Message message2) {
        this.b.a(iX5WebViewBase);
        this.a.onTooManyRedirects(this.b, message, message2);
    }

    public void onUnhandledKeyEvent(IX5WebViewBase iX5WebViewBase, KeyEvent keyEvent) {
        this.b.a(iX5WebViewBase);
        this.a.onUnhandledKeyEvent(this.b, keyEvent);
    }

    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest) {
        this.b.a(iX5WebViewBase);
        return this.a.shouldInterceptRequest(this.b, webResourceRequest);
    }

    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase iX5WebViewBase, WebResourceRequest webResourceRequest, Bundle bundle) {
        this.b.a(iX5WebViewBase);
        return this.a.shouldInterceptRequest(this.b, webResourceRequest, bundle);
    }

    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase iX5WebViewBase, String str) {
        this.b.a(iX5WebViewBase);
        return this.a.shouldInterceptRequest(this.b, str);
    }

    public boolean shouldOverrideKeyEvent(IX5WebViewBase iX5WebViewBase, KeyEvent keyEvent) {
        this.b.a(iX5WebViewBase);
        return this.a.shouldOverrideKeyEvent(this.b, keyEvent);
    }

    public boolean shouldOverrideUrlLoading(IX5WebViewBase iX5WebViewBase, String str) {
        this.b.a(iX5WebViewBase);
        boolean shouldOverrideUrlLoading = this.a.shouldOverrideUrlLoading(this.b, str);
        if (!shouldOverrideUrlLoading) {
            if (str.startsWith("wtai://wp/mc;")) {
                this.b.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(WebView.SCHEME_TEL + str.substring("wtai://wp/mc;".length()))));
                return true;
            } else if (str.startsWith(WebView.SCHEME_TEL)) {
                a(str);
                return true;
            }
        }
        return shouldOverrideUrlLoading;
    }
}
