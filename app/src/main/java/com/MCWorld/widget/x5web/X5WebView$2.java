package com.MCWorld.widget.x5web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.MCWorld.bbs.b.g;
import com.MCWorld.module.h;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient$CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebView$WebViewTransport;
import com.tencent.smtt.sdk.WebViewClient;

class X5WebView$2 extends WebChromeClient {
    final /* synthetic */ X5WebView bHB;
    View bHC;
    View bHD;
    IX5WebChromeClient$CustomViewCallback bHE;

    X5WebView$2(X5WebView this$0) {
        this.bHB = this$0;
    }

    public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
        return super.onJsConfirm(arg0, arg1, arg2, arg3);
    }

    public void onShowCustomView(View view, IX5WebChromeClient$CustomViewCallback customViewCallback) {
        FrameLayout normalView = (FrameLayout) ((Activity) this.bHB.getContext()).findViewById(g.web_filechooser);
        ViewGroup viewGroup = (ViewGroup) normalView.getParent();
        viewGroup.removeView(normalView);
        viewGroup.addView(view);
        this.bHC = view;
        this.bHD = normalView;
        this.bHE = customViewCallback;
    }

    public void onHideCustomView() {
        if (this.bHE != null) {
            this.bHE.onCustomViewHidden();
            this.bHE = null;
        }
        if (this.bHC != null) {
            ViewGroup viewGroup = (ViewGroup) this.bHC.getParent();
            viewGroup.removeView(this.bHC);
            viewGroup.addView(this.bHD);
        }
    }

    public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
        Intent i = new Intent("android.intent.action.GET_CONTENT");
        i.addCategory("android.intent.category.OPENABLE");
        i.setType("*/*");
        ((Activity) this.bHB.getContext()).startActivityForResult(Intent.createChooser(i, "choose files"), 0);
        super.openFileChooser(uploadFile, acceptType, captureType);
    }

    public boolean onCreateWindow(WebView arg0, boolean arg1, boolean arg2, Message msg) {
        if (X5WebView.access$000()) {
            WebView$WebViewTransport webViewTransport = msg.obj;
            WebView webView = new WebView(this, this.bHB.getContext()) {
                final /* synthetic */ X5WebView$2 bHF;

                protected void onDraw(Canvas canvas) {
                    super.onDraw(canvas);
                    Paint paint = new Paint();
                    paint.setColor(-16711936);
                    paint.setTextSize(15.0f);
                    canvas.drawText("新建窗口", 10.0f, 10.0f, paint);
                }
            };
            webView.setWebViewClient(new WebViewClient(this) {
                final /* synthetic */ X5WebView$2 bHF;

                {
                    this.bHF = this$1;
                }

                public boolean shouldOverrideUrlLoading(WebView arg0, String arg1) {
                    arg0.loadUrl(arg1);
                    return true;
                }
            });
            LayoutParams lp = new LayoutParams(400, h.arp);
            lp.gravity = 17;
            this.bHB.addView(webView, lp);
            webViewTransport.setWebView(webView);
            msg.sendToTarget();
        }
        return true;
    }

    public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
        Log.i("yuanhaizhou", "setX5webview = null");
        return super.onJsAlert(null, "www.baidu.com", "aa", arg3);
    }

    public boolean onJsPrompt(WebView arg0, String arg1, String arg2, String arg3, JsPromptResult arg4) {
        if (!X5WebView.a(this.bHB, arg1)) {
            return super.onJsPrompt(arg0, arg1, arg2, arg3, arg4);
        }
        if (X5WebView.a(this.bHB, arg2, arg3)) {
            return true;
        }
        return false;
    }

    public void onReceivedTitle(WebView arg0, String arg1) {
        super.onReceivedTitle(arg0, arg1);
        Log.i("yuanhaizhou", "webpage title is " + arg1);
    }
}
