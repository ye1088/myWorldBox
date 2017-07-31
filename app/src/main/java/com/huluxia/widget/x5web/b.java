package com.huluxia.widget.x5web;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebViewCallbackClient;
import java.util.HashMap;

/* compiled from: X5WebViewEventHandler */
public class b extends ProxyWebViewClientExtension implements IX5WebChromeClientExtension {
    private WebViewCallbackClient bHG = new WebViewCallbackClient(this) {
        final /* synthetic */ b bHH;

        {
            this.bHH = this$0;
        }

        public boolean onTouchEvent(MotionEvent event, View view) {
            Log.i("yuanhaizhou", "tbs_onTouchEvent view is " + view.getClass().toString());
            return this.bHH.bHi.c(event, view);
        }

        public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent, View view) {
            return this.bHH.bHi.a(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent, view);
        }

        public void computeScroll(View view) {
            this.bHH.bHi.G(view);
        }

        public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY, View view) {
            this.bHH.bHi.a(scrollX, scrollY, clampedX, clampedY, view);
        }

        public void onScrollChanged(int l, int t, int oldl, int oldt, View view) {
            this.bHH.bHi.a(l, t, oldl, oldt, view);
        }

        public boolean dispatchTouchEvent(MotionEvent ev, View view) {
            return this.bHH.bHi.a(ev, view);
        }

        public boolean onInterceptTouchEvent(MotionEvent ev, View view) {
            return this.bHH.bHi.b(ev, view);
        }
    };
    private X5WebView bHi;

    public b(X5WebView webView) {
        this.bHi = webView;
        this.bHi.setWebViewCallbackClient(this.bHG);
    }

    public void acquireWakeLock() {
    }

    public void addFlashView(View arg0, LayoutParams arg1) {
    }

    public void exitFullScreenFlash() {
    }

    public Context getApplicationContex() {
        return null;
    }

    public View getVideoLoadingProgressView() {
        return null;
    }

    public Object getX5WebChromeClientInstance() {
        return null;
    }

    public void h5videoExitFullScreen(String arg0) {
    }

    public void h5videoRequestFullScreen(String arg0) {
    }

    public boolean onAddFavorite(IX5WebViewExtension arg0, String arg1, String arg2, JsResult arg3) {
        return false;
    }

    public void onAllMetaDataFinished(IX5WebViewExtension arg0, HashMap<String, String> hashMap) {
    }

    public void onBackforwardFinished(int arg0) {
    }

    public void onHitTestResultFinished(IX5WebViewExtension arg0, HitTestResult arg1) {
        Log.i("yuanhaizhou", "onHitTestResultFinished");
    }

    public void onHitTestResultForPluginFinished(IX5WebViewExtension arg0, HitTestResult arg1, Bundle arg2) {
        arg1.getData();
        Log.i("yuanhaizhou", "onHitTestResultForPluginFinished");
    }

    public boolean onPageNotResponding(Runnable arg0) {
        return false;
    }

    public void onPrepareX5ReadPageDataFinished(IX5WebViewExtension arg0, HashMap<String, String> hashMap) {
    }

    public void onPromptNotScalable(IX5WebViewExtension arg0) {
    }

    public void onPromptScaleSaved(IX5WebViewExtension arg0) {
    }

    public boolean onSavePassword(String arg0, String arg1, String arg2, boolean arg3, Message arg4) {
        return false;
    }

    public void onX5ReadModeAvailableChecked(HashMap<String, String> hashMap) {
    }

    public void releaseWakeLock() {
    }

    public void requestFullScreenFlash() {
    }

    public Object onMiscCallBack(String method, Bundle bundle) {
        return null;
    }

    public boolean onTouchEvent(MotionEvent event, View view) {
        return this.bHG.onTouchEvent(event, view);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev, View view) {
        return this.bHG.onInterceptTouchEvent(ev, view);
    }

    public boolean dispatchTouchEvent(MotionEvent ev, View view) {
        return this.bHG.dispatchTouchEvent(ev, view);
    }

    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent, View view) {
        return this.bHG.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent, view);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt, View view) {
        this.bHG.onScrollChanged(l, t, oldl, oldt, view);
    }

    public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY, View view) {
        this.bHG.onOverScrolled(scrollX, scrollY, clampedX, clampedY, view);
    }

    public void computeScroll(View view) {
        this.bHG.computeScroll(view);
    }

    public void onPrintPage() {
    }

    public boolean onSavePassword(ValueCallback<String> valueCallback, String arg1, String arg2, String arg3, String arg4, String arg5, boolean arg6) {
        return false;
    }

    public void openFileChooser(ValueCallback<Uri[]> valueCallback, String arg1, String arg2) {
    }

    public void onColorModeChanged(long arg0) {
    }
}
