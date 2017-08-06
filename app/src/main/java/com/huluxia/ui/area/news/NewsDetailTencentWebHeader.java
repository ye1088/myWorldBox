package com.huluxia.ui.area.news;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.PluginState;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class NewsDetailTencentWebHeader extends RelativeLayout {
    private static final String TAG = "NewsDetailTencentWebHeader";
    private String aHF;
    private int aHG = 0;
    private int aHH = ((UtilsScreen.getScreenHeight(getContext()) * 3) / 4);
    private CommonMenuDialog aHI = null;
    private WebView aHQ;
    private CookieManager aHR;
    private WebViewClient aHS = new WebViewClient(this) {
        final /* synthetic */ NewsDetailTencentWebHeader aHU;

        {
            this.aHU = this$0;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            this.aHU.aHF = url;
            Uri uri = Uri.parse(url);
            if (uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https")) {
                this.aHU.a(view, url);
                return super.shouldOverrideUrlLoading(view, url);
            }
            try {
                this.aHU.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } catch (ActivityNotFoundException e) {
                HLog.error(NewsDetailTencentWebHeader.TAG, "activity not found " + e, new Object[0]);
            }
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            this.aHU.b(view, url);
            EventNotifyCenter.notifyEventUiThread(h.class, h.asG, new Object[]{Boolean.valueOf(true)});
        }

        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            EventNotifyCenter.notifyEventUiThread(h.class, h.asG, new Object[]{Boolean.valueOf(false)});
        }

        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            HLog.debug(NewsDetailTencentWebHeader.TAG, "oldScale " + oldScale + ", newScale " + newScale, new Object[0]);
            this.aHU.mScale = newScale;
            this.aHU.Fn();
        }
    };
    private WebChromeClient aHT = new WebChromeClient(this) {
        final /* synthetic */ NewsDetailTencentWebHeader aHU;

        {
            this.aHU = this$0;
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
    private String aHt;
    private HashMap<String, String> aHv;
    private HashMap<String, String> aHw;
    private float mScale = 1.0f;

    public NewsDetailTencentWebHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_new_detail_header, this, true);
        this.aHQ = (WebView) findViewById(g.webview);
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
                    final /* synthetic */ NewsDetailTencentWebHeader aHU;

                    {
                        this.aHU = this$0;
                    }
                }.getType()));
                Fk();
                this.aHw = new HashMap((Map) Json.getGson().fromJson(uriObj.getJSONObject("extras").toString(), new TypeToken<Map<String, String>>(this) {
                    final /* synthetic */ NewsDetailTencentWebHeader aHU;

                    {
                        this.aHU = this$0;
                    }
                }.getType()));
                this.aHQ.addJavascriptInterface(new TransferObj(this, this.aHv, this.aHw), "obj");
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
                this.aHv.put(BaseHttpMgr.PARAM_MARKET_ID, String.valueOf(HTApplication.bJ_mctool_huluxia_string()));
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
        this.aHQ.loadUrl(templatePath);
    }

    public void recycle() {
        if (this.aHQ != null) {
            this.aHQ.loadUrl("about:blank");
            this.aHQ.getSettings().setBuiltInZoomControls(true);
            this.aHQ.setVisibility(8);
            ViewGroup parent = (ViewGroup) this.aHQ.getParent();
            if (parent != null) {
                parent.removeView(this.aHQ);
            }
            this.aHQ.removeAllViews();
            this.aHQ.destroy();
            this.aHQ = null;
        }
    }

    public void pause() {
        if (this.aHQ != null && UtilsVersion.hasHoneycomb()) {
            this.aHQ.onPause();
        }
    }

    public void resume() {
        if (this.aHQ != null && UtilsVersion.hasHoneycomb()) {
            this.aHQ.onResume();
        }
    }

    private void Fm() {
        this.aHR = CookieManager.getInstance();
        this.aHR.setAcceptCookie(true);
        WebSettings webSettings = this.aHQ.getSettings();
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
            this.aHQ.getSettings().setDisplayZoomControls(false);
        }
        this.aHQ.setWebViewClient(this.aHS);
        this.aHQ.setWebChromeClient(this.aHT);
    }

    public void refresh() {
        if (this.aHQ == null || UtilsFunction.empty(this.aHF)) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.asG, new Object[]{Boolean.valueOf(false)});
            return;
        }
        this.aHQ.loadUrl(this.aHF);
    }

    protected void a(WebView view, String url) {
    }

    protected void b(WebView view, String url) {
        this.aHF = url;
    }

    private void Fn() {
        if (this.aHQ != null && getLayoutParams() != null) {
            if (this.mScale == 1.0f) {
                this.mScale = this.aHQ.getScale();
            }
            if (this.mScale == 0.0f) {
                this.mScale = 1.0f;
            }
            int height = (int) (((float) this.aHG) * this.mScale);
            HLog.debug(TAG, "js height " + this.aHG + ", scale " + this.mScale + ", height " + height, new Object[0]);
            this.aHQ.getLayoutParams().height = -1;
            this.aHQ.setVisibility(0);
            getLayoutParams().height = height;
            requestLayout();
        }
    }

    private void eh(final String imageUrl) {
        if (this.aHI == null || !this.aHI.isDialogShowing()) {
            this.aHI = UtilsMenu.b(getContext(), new CommonMenuDialogListener(this) {
                final /* synthetic */ NewsDetailTencentWebHeader aHU;

                public void pressMenuById(int inIndex, Object object) {
                    switch (inIndex) {
                        case 0:
                            this.aHU.aHI.dismissDialog();
                            this.aHU.ei(imageUrl);
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
            final /* synthetic */ NewsDetailTencentWebHeader aHU;

            {
                this.aHU = this$0;
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
