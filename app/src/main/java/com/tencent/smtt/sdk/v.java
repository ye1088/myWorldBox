package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.p;
import java.util.Map;

class v extends WebViewClient {
    private static String c = null;
    private WebViewClient a;
    private WebView b;

    private static class a implements HttpAuthHandler {
        private android.webkit.HttpAuthHandler a;

        a(android.webkit.HttpAuthHandler httpAuthHandler) {
            this.a = httpAuthHandler;
        }

        public void cancel() {
            this.a.cancel();
        }

        public void proceed(String str, String str2) {
            this.a.proceed(str, str2);
        }

        public boolean useHttpAuthUsernamePassword() {
            return this.a.useHttpAuthUsernamePassword();
        }
    }

    private static class b implements SslErrorHandler {
        android.webkit.SslErrorHandler a;

        b(android.webkit.SslErrorHandler sslErrorHandler) {
            this.a = sslErrorHandler;
        }

        public void cancel() {
            this.a.cancel();
        }

        public void proceed() {
            this.a.proceed();
        }
    }

    private static class c implements SslError {
        android.net.http.SslError a;

        c(android.net.http.SslError sslError) {
            this.a = sslError;
        }

        public boolean addError(int i) {
            return this.a.addError(i);
        }

        public SslCertificate getCertificate() {
            return this.a.getCertificate();
        }

        public int getPrimaryError() {
            return this.a.getPrimaryError();
        }

        public boolean hasError(int i) {
            return this.a.hasError(i);
        }
    }

    private class d implements WebResourceRequest {
        final /* synthetic */ v a;
        private String b;
        private boolean c;
        private boolean d;
        private String e;
        private Map<String, String> f;

        public d(v vVar, String str, boolean z, boolean z2, String str2, Map<String, String> map) {
            this.a = vVar;
            this.b = str;
            this.c = z;
            this.d = z2;
            this.e = str2;
            this.f = map;
        }

        public String getMethod() {
            return this.e;
        }

        public Map<String, String> getRequestHeaders() {
            return this.f;
        }

        public Uri getUrl() {
            return Uri.parse(this.b);
        }

        public boolean hasGesture() {
            return this.d;
        }

        public boolean isForMainFrame() {
            return this.c;
        }
    }

    v(WebView webView, WebViewClient webViewClient) {
        this.b = webView;
        this.a = webViewClient;
    }

    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        this.b.a(webView);
        this.a.doUpdateVisitedHistory(this.b, str, z);
    }

    public void onFormResubmission(WebView webView, Message message, Message message2) {
        this.b.a(webView);
        this.a.onFormResubmission(this.b, message, message2);
    }

    public void onLoadResource(WebView webView, String str) {
        this.b.a(webView);
        this.a.onLoadResource(this.b, str);
    }

    public void onPageFinished(WebView webView, String str) {
        if (c == null) {
            p a = p.a();
            if (a != null) {
                a.a(true);
                c = Boolean.toString(true);
            }
        }
        this.b.a(webView);
        WebView webView2 = this.b;
        webView2.a++;
        this.a.onPageFinished(this.b, str);
        if ("com.qzone".equals(webView.getContext().getApplicationInfo().packageName)) {
            this.b.a(webView.getContext());
        }
        TbsLog.app_extra("SystemWebViewClient", webView.getContext());
        com.tencent.smtt.sdk.b.d.a(this.b);
        if (this.b.getContext() != null && TbsShareManager.isThirdPartyApp(this.b.getContext()) && !TbsShareManager.forceLoadX5FromTBSDemo(this.b.getContext()) && !TbsDownloader.needDownload(this.b.getContext(), false)) {
        }
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        this.b.a(webView);
        this.a.onPageStarted(this.b, str, bitmap);
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.b.a(webView);
        this.a.onReceivedError(this.b, i, str, str2);
    }

    public void onReceivedHttpAuthRequest(WebView webView, android.webkit.HttpAuthHandler httpAuthHandler, String str, String str2) {
        this.b.a(webView);
        this.a.onReceivedHttpAuthRequest(this.b, new a(httpAuthHandler), str, str2);
    }

    @TargetApi(12)
    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        if (VERSION.SDK_INT >= 12) {
            this.b.a(webView);
            this.a.onReceivedLoginRequest(this.b, str, str2, str3);
        }
    }

    @TargetApi(8)
    public void onReceivedSslError(WebView webView, android.webkit.SslErrorHandler sslErrorHandler, android.net.http.SslError sslError) {
        if (VERSION.SDK_INT >= 8) {
            this.b.a(webView);
            this.a.onReceivedSslError(this.b, new b(sslErrorHandler), new c(sslError));
        }
    }

    public void onScaleChanged(WebView webView, float f, float f2) {
        this.b.a(webView);
        this.a.onScaleChanged(this.b, f, f2);
    }

    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        this.b.a(webView);
        this.a.onTooManyRedirects(this.b, message, message2);
    }

    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        this.b.a(webView);
        this.a.onUnhandledKeyEvent(this.b, keyEvent);
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, android.webkit.WebResourceRequest webResourceRequest) {
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        if (webResourceRequest == null) {
            return null;
        }
        com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest = this.a.shouldInterceptRequest(this.b, new d(this, webResourceRequest.getUrl().toString(), webResourceRequest.isForMainFrame(), webResourceRequest.hasGesture(), webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders()));
        if (shouldInterceptRequest == null) {
            return null;
        }
        WebResourceResponse webResourceResponse = new WebResourceResponse(shouldInterceptRequest.getMimeType(), shouldInterceptRequest.getEncoding(), shouldInterceptRequest.getData());
        webResourceResponse.setResponseHeaders(shouldInterceptRequest.getResponseHeaders());
        int statusCode = shouldInterceptRequest.getStatusCode();
        String reasonPhrase = shouldInterceptRequest.getReasonPhrase();
        if (statusCode == webResourceResponse.getStatusCode() && (reasonPhrase == null || reasonPhrase.equals(webResourceResponse.getReasonPhrase()))) {
            return webResourceResponse;
        }
        webResourceResponse.setStatusCodeAndReasonPhrase(statusCode, reasonPhrase);
        return webResourceResponse;
    }

    @TargetApi(11)
    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        if (VERSION.SDK_INT < 11) {
            return null;
        }
        com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest = this.a.shouldInterceptRequest(this.b, str);
        return shouldInterceptRequest != null ? new WebResourceResponse(shouldInterceptRequest.getMimeType(), shouldInterceptRequest.getEncoding(), shouldInterceptRequest.getData()) : null;
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        this.b.a(webView);
        return this.a.shouldOverrideKeyEvent(this.b, keyEvent);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        this.b.a(webView);
        return this.a.shouldOverrideUrlLoading(this.b, str);
    }
}
