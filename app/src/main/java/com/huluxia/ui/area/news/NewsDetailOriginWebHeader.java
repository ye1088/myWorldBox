package com.huluxia.ui.area.news;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.google.gson.reflect.TypeToken;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.j;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.BaseHttpMgr;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.module.h;
import com.huluxia.module.news.c;
import com.huluxia.t;
import com.huluxia.utils.UtilsDownloadImage;
import com.huluxia.utils.UtilsDownloadImage.a;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.UtilsMenu;
import com.huluxia.utils.o;
import com.simple.colorful.d;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class NewsDetailOriginWebHeader extends RelativeLayout {
    private static final String TAG = "NewsDetailOriginWebHeader";
    private CookieManager aHE;
    private String aHF;
    private int aHG = 0;
    private int aHH = ((UtilsScreen.getScreenHeight(getContext()) * 3) / 4);
    private CommonMenuDialog aHI = null;
    private WebViewClient aHJ = new WebViewClient(this) {
        final /* synthetic */ NewsDetailOriginWebHeader aHK;

        {
            this.aHK = this$0;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            this.aHK.aHF = url;
            Uri uri = Uri.parse(url);
            if (uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https")) {
                this.aHK.b(view, url);
                return super.shouldOverrideUrlLoading(view, url);
            }
            try {
                this.aHK.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } catch (ActivityNotFoundException e) {
                HLog.error(NewsDetailOriginWebHeader.TAG, "activity not found " + e, new Object[0]);
            }
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            this.aHK.c(view, url);
            EventNotifyCenter.notifyEventUiThread(h.class, h.asG, new Object[]{Boolean.valueOf(true)});
        }

        public void a(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            EventNotifyCenter.notifyEventUiThread(h.class, h.asG, new Object[]{Boolean.valueOf(false)});
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            EventNotifyCenter.notifyEventUiThread(h.class, h.asG, new Object[]{Boolean.valueOf(false)});
        }

        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            HLog.debug(NewsDetailOriginWebHeader.TAG, "oldScale " + oldScale + ", newScale " + newScale, new Object[0]);
            this.aHK.mScale = newScale;
            this.aHK.Fn();
        }
    };
    private String aHt;
    private HashMap<String, String> aHv;
    private HashMap<String, String> aHw;
    private WebChromeClient mChromeClient = new WebChromeClient(this) {
        final /* synthetic */ NewsDetailOriginWebHeader aHK;

        {
            this.aHK = this$0;
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    };
    private float mScale = 1.0f;
    private WebView mWebView;

    public NewsDetailOriginWebHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_new_detail_web_header, this, true);
        this.mWebView = (WebView) findViewById(g.webview);
        Fm();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getLayoutParams() == null) {
            return;
        }
        if (getLayoutParams().height == 0 || getLayoutParams().height == -2) {
            getLayoutParams().height = this.aHH;
        }
    }

    public void setNews(c news) {
        if (news != null && !UtilsFunction.empty(news.uri)) {
            try {
                JSONObject uriObj = new JSONObject(news.uri.replaceFirst("template=", ""));
                this.aHt = uriObj.getString("templateId");
                this.aHv = new HashMap((Map) Json.getGson().fromJson(uriObj.getJSONObject("params").toString(), new TypeToken<Map<String, String>>(this) {
                    final /* synthetic */ NewsDetailOriginWebHeader aHK;

                    {
                        this.aHK = this$0;
                    }
                }.getType()));
                Fk();
                this.aHw = new HashMap((Map) Json.getGson().fromJson(uriObj.getJSONObject("extras").toString(), new TypeToken<Map<String, String>>(this) {
                    final /* synthetic */ NewsDetailOriginWebHeader aHK;

                    {
                        this.aHK = this$0;
                    }
                }.getType()));
                this.mWebView.addJavascriptInterface(new TransferObj(this, this.aHv, this.aHw), "obj");
                Fl();
            } catch (Exception e) {
                HLog.error(TAG, "parse news err " + e, new Object[0]);
            }
        }
    }

    private void Fk() {
        if (this.aHv != null) {
            if (!this.aHv.containsKey(BaseHttpMgr.PARAM_APP_VERSION)) {
                this.aHv.put(BaseHttpMgr.PARAM_APP_VERSION, UtilsVersion.getVersionString(AppConfig.getInstance().getAppContext()));
            }
            if (!this.aHv.containsKey(BaseHttpMgr.PARAM_DEVICE_CODE)) {
                this.aHv.put(BaseHttpMgr.PARAM_DEVICE_CODE, o.getDeviceId());
            }
            if (!this.aHv.containsKey("versioncode")) {
                this.aHv.put("versioncode", String.valueOf(UtilsVersion.getVersionCode(AppConfig.getInstance().getAppContext())));
            }
            if (!this.aHv.containsKey(BaseHttpMgr.PARAM_MARKET_ID)) {
                this.aHv.put(BaseHttpMgr.PARAM_MARKET_ID, String.valueOf(HTApplication.bJ()));
            }
            if (!this.aHv.containsKey("_key")) {
                String token = j.ep().getToken();
                HashMap hashMap = this.aHv;
                String str = "_key";
                if (token == null) {
                    token = "";
                }
                hashMap.put(str, token);
            }
        }
    }

    public void Fl() {
        String newsHtml = this.aHt;
        if (HTApplication.DEBUG) {
            newsHtml = this.aHt + "_test";
        }
        if (d.RB()) {
            newsHtml = "night_" + this.aHt;
        }
        String templatePath = String.format("file:///android_asset/themes/%s/%s.html", new Object[]{this.aHt, newsHtml});
        this.aHF = templatePath;
        this.mWebView.loadUrl(templatePath);
    }

    public void recycle() {
        if (this.mWebView != null) {
            this.mWebView.loadUrl("about:blank");
            this.mWebView.getSettings().setBuiltInZoomControls(true);
            this.mWebView.setVisibility(8);
            ViewGroup parent = (ViewGroup) this.mWebView.getParent();
            if (parent != null) {
                parent.removeView(this.mWebView);
            }
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
    }

    public void pause() {
        if (this.mWebView != null && UtilsVersion.hasHoneycomb()) {
            this.mWebView.onPause();
        }
    }

    public void resume() {
        if (this.mWebView != null && UtilsVersion.hasHoneycomb()) {
            this.mWebView.onResume();
        }
    }

    private void Fm() {
        this.aHE = CookieManager.getInstance();
        this.aHE.setAcceptCookie(true);
        WebSettings webSettings = this.mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(-1);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (VERSION.SDK_INT >= 11) {
            webSettings.setPluginState(PluginState.ON);
            this.mWebView.getSettings().setDisplayZoomControls(false);
        }
        this.mWebView.setWebViewClient(this.aHJ);
        this.mWebView.setWebChromeClient(this.mChromeClient);
    }

    public void refresh() {
        if (this.mWebView == null || UtilsFunction.empty(this.aHF)) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.asG, new Object[]{Boolean.valueOf(false)});
            return;
        }
        this.mWebView.loadUrl(this.aHF);
    }

    protected void b(WebView view, String url) {
    }

    protected void c(WebView view, String url) {
        this.aHF = url;
    }

    private void Fn() {
        if (this.mWebView != null && getLayoutParams() != null) {
            if (this.mScale == 1.0f) {
                this.mScale = this.mWebView.getScale();
            }
            if (this.mScale == 0.0f) {
                this.mScale = 1.0f;
            }
            this.mWebView.getLayoutParams().height = -1;
            this.mWebView.setVisibility(0);
            int height = (int) (((float) this.aHG) * this.mScale);
            HLog.debug(TAG, "js height " + this.aHG + ", scale " + this.mScale + ", height " + height, new Object[0]);
            getLayoutParams().height = height;
            requestLayout();
        }
    }

    private void eh(final String imageUrl) {
        if (this.aHI == null || !this.aHI.isDialogShowing()) {
            this.aHI = UtilsMenu.b(getContext(), new CommonMenuDialogListener(this) {
                final /* synthetic */ NewsDetailOriginWebHeader aHK;

                public void pressMenuById(int inIndex, Object object) {
                    switch (inIndex) {
                        case 0:
                            this.aHK.aHI.dismissDialog();
                            this.aHK.ei(imageUrl);
                            return;
                        default:
                            return;
                    }
                }
            });
            this.aHI.updateCurFocusIndex(-1);
            this.aHI.showMenu(null, null);
        }
    }

    private void ei(String imageUrl) {
        new UtilsDownloadImage(new a(this) {
            final /* synthetic */ NewsDetailOriginWebHeader aHK;

            {
                this.aHK = this$0;
            }

            public void onDownloadFinish(int result) {
                if (result == 1) {
                    t.f(String.format("图片已保存至%s文件夹", new Object[]{UtilsFile.KP()}), AppConfig.getInstance().getAppContext().getResources().getColor(b.d.white));
                    return;
                }
                t.f("保存图片到手机失败", AppConfig.getInstance().getAppContext().getResources().getColor(b.d.white));
            }
        }).execute(new String[]{imageUrl});
    }
}
