package com.MCWorld.widget.x5web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebSettings.PluginState;
import com.tencent.smtt.sdk.WebSettings.RenderPriority;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import java.util.HashMap;
import java.util.Map;

public class X5WebView extends WebView {
    public static final int bHq = 0;
    private static boolean bHt = false;
    TextView aRh;
    private WebChromeClient bHA = new 2(this);
    private String bHr = "";
    private WebView bHs;
    private boolean bHu = false;
    private Map<String, Object> bHv;
    private TextView bHw;
    LayoutParams bHx;
    private RelativeLayout bHy;
    private WebViewClient bHz = new 1(this);

    @SuppressLint({"SetJavaScriptEnabled"})
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        setWebViewClientExtension(new b(this));
        setWebViewClient(this.bHz);
        setWebChromeClient(this.bHA);
        WebStorage webStorage = WebStorage.getInstance();
        QH();
        getView().setClickable(true);
        getView().setOnTouchListener(new 3(this));
    }

    private void QH() {
        WebSettings webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(PluginState.ON_DEMAND);
        webSetting.setRenderPriority(RenderPriority.HIGH);
        webSetting.setCacheMode(2);
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    public static void setSmallWebViewEnabled(boolean enabled) {
        bHt = enabled;
    }

    public void a(a jsBridgeBundle) {
        if (this.bHv == null) {
            this.bHv = new HashMap(5);
        }
        if (jsBridgeBundle != null) {
            this.bHv.put(a.bHl + jsBridgeBundle.QF() + "-" + "method" + jsBridgeBundle.getMethodName(), jsBridgeBundle);
        }
    }

    private boolean aB(String methodName, String blockName) {
        String tag = a.bHl + blockName + "-" + "method" + methodName;
        if (this.bHv == null || !this.bHv.containsKey(tag)) {
            return false;
        }
        ((a) this.bHv.get(tag)).QE();
        return true;
    }

    private boolean gT(String msg) {
        if (msg == null || !msg.startsWith(a.bHn)) {
            return false;
        }
        return true;
    }

    public boolean a(MotionEvent ev, View view) {
        boolean r = super.super_dispatchTouchEvent(ev);
        Log.d("Bran", "dispatchTouchEvent " + ev.getAction() + " " + r);
        return r;
    }

    public boolean b(MotionEvent ev, View view) {
        return super.super_onInterceptTouchEvent(ev);
    }

    protected void a(int l, int t, int oldl, int oldt, View view) {
        super_onScrollChanged(l, t, oldl, oldt);
    }

    protected void a(int scrollX, int scrollY, boolean clampedX, boolean clampedY, View view) {
        if (getContext() instanceof RefreshActivity) {
            if (this.bHw == null) {
                this.bHw = (TextView) ((Activity) getContext()).findViewById(g.refreshText);
                this.bHx = (LayoutParams) this.bHw.getLayoutParams();
                this.bHy = (RelativeLayout) ((Activity) getContext()).findViewById(g.refreshPool);
            }
            if (this.bHu && !clampedY) {
                reload();
            }
            if (clampedY) {
                this.bHu = true;
            } else {
                this.bHu = false;
            }
        }
        super_onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    protected void G(View view) {
        super_computeScroll();
    }

    protected boolean a(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent, View view) {
        if ((getContext() instanceof RefreshActivity) && this.bHu) {
            if ((this.bHy.getTop() + (-deltaY)) / 2 < 255) {
                this.bHw.setAlpha((float) ((this.bHy.getTop() + (-deltaY)) / 2));
            } else {
                this.bHw.setAlpha(255.0f);
            }
            this.bHy.layout(this.bHy.getLeft(), this.bHy.getTop() + (-deltaY), this.bHy.getRight(), this.bHy.getBottom() + (-deltaY));
            layout(getLeft(), getTop() + ((-deltaY) / 2), getRight(), getBottom() + ((-deltaY) / 2));
        }
        return super_overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    public void setTitle(TextView title) {
        this.aRh = title;
    }

    protected boolean c(MotionEvent event, View view) {
        if ((getContext() instanceof RefreshActivity) && event.getAction() == 1 && this.bHw != null) {
            this.bHu = false;
            if (VERSION.SDK_INT >= 11) {
                this.bHw.setAlpha(0.0f);
            }
            this.bHy.layout(this.bHy.getLeft(), 0, this.bHy.getRight(), this.bHy.getBottom());
            layout(getLeft(), 0, getRight(), getBottom());
        }
        return super_onTouchEvent(event);
    }
}
