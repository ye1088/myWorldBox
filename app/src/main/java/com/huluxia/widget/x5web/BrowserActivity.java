package com.huluxia.widget.x5web;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.framework.base.http.toolbox.entity.utils.TextUtils;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.utils.aw;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebSettings.PluginState;
import com.tencent.smtt.sdk.WebSettings.RenderPriority;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import java.util.Timer;
import java.util.TimerTask;

public class BrowserActivity extends HTBaseActivity {
    private static final String TAG = "BrowserActivity";
    private static final int aHa = 14;
    public static final String aJm = "title";
    public static final String aJn = "url";
    private static final String bGT = "http://www.huluxia.com";
    public static final int bHb = 0;
    public static final int bHc = 1;
    private PopupWindow ZY;
    private ImageButton aQD;
    private final int ajA = 255;
    private X5WebView bGM;
    private ViewGroup bGN;
    private ImageButton bGO;
    private ImageButton bGP;
    private ImageButton bGQ;
    private ImageButton bGR;
    private ImageButton bGS;
    private boolean bGU = false;
    private final int bGV = 120;
    private ProgressBar bGW = null;
    private ValueCallback<Uri> bGX;
    private String bGY;
    private BrowserActivity bGZ;
    private ImageButton bHa;
    private final int bHd = 0;
    private int bHe = 0;
    private Handler bHf = new Handler(this) {
        final /* synthetic */ BrowserActivity bHg;

        {
            this.bHg = this$0;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (this.bHg.bGU) {
                        String testUrl = "file:///sdcard/outputHtml/html/" + Integer.toString(this.bHg.bHe) + ".html";
                        if (this.bHg.bGM != null) {
                            this.bHg.bGM.loadUrl(testUrl);
                        }
                        this.bHg.bHe = this.bHg.bHe + 1;
                        break;
                    }
                    return;
                case 1:
                    this.bHg.init();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private class a implements DownloadListener {
        final /* synthetic */ BrowserActivity bHg;

        private a(BrowserActivity browserActivity) {
            this.bHg = browserActivity;
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            t.q(this.bHg.bGZ, url);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(-3);
        this.bGZ = this;
        this.bGY = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(this.bGY) && (this.bGY.toLowerCase().startsWith("www") || this.bGY.toLowerCase().startsWith("bbs"))) {
            this.bGY = "http://" + this.bGY;
        }
        QC();
        try {
            if (Integer.parseInt(VERSION.SDK) >= 11) {
                getWindow().setFlags(16777216, 16777216);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(i.activity_browser);
        this.bGN = (ViewGroup) findViewById(g.webView1);
        QB();
        Qz();
        this.bHf.sendEmptyMessageDelayed(1, 10);
    }

    private void Qz() {
        X5WebView.setSmallWebViewEnabled(true);
    }

    private void b(WebView view) {
        if (view.canGoBack()) {
            this.aQD.setAlpha(255);
        } else {
            this.aQD.setAlpha(120);
        }
        if (view.canGoForward()) {
            this.bGO.setAlpha(255);
        } else {
            this.bGO.setAlpha(120);
        }
        if (view.getUrl() == null || !view.getUrl().equalsIgnoreCase(bGT)) {
            this.bGQ.setAlpha(255);
            this.bGQ.setEnabled(true);
            return;
        }
        this.bGQ.setAlpha(120);
        this.bGQ.setEnabled(false);
    }

    private void QA() {
        this.bGW = (ProgressBar) findViewById(g.progressBar1);
        this.bGW.setMax(100);
        this.bGW.setProgressDrawable(getResources().getDrawable(f.bg_video_recroder_progressbar));
    }

    private void init() {
        this.bGM = new X5WebView(this);
        this.bGN.addView(this.bGM, new LayoutParams(-1, -1));
        QA();
        this.bGM.setWebViewClient(new WebViewClient(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                this.bHg.bHf.sendEmptyMessageDelayed(0, 5000);
                if (Integer.parseInt(VERSION.SDK) >= 16) {
                    this.bHg.b(view);
                }
            }
        });
        this.bGM.setWebChromeClient(new WebChromeClient(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public void onReceivedTitle(WebView view, String title) {
                if (title != null) {
                    this.bHg.bGZ.ej(aw.W(title, 12));
                }
            }

            public void onProgressChanged(WebView view, int newProgress) {
                this.bHg.bGW.setProgress(newProgress);
                if (this.bHg.bGW != null && newProgress != 100) {
                    this.bHg.bGW.setVisibility(0);
                } else if (this.bHg.bGW != null) {
                    this.bHg.bGW.setVisibility(8);
                }
            }
        });
        this.bGM.setDownloadListener(new a());
        WebSettings webSetting = this.bGM.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).getPath());
        webSetting.setPluginState(PluginState.ON_DEMAND);
        webSetting.setRenderPriority(RenderPriority.HIGH);
        long time = System.currentTimeMillis();
        if (this.bGY == null) {
            this.bGM.loadUrl(bGT);
        } else {
            this.bGM.loadUrl(this.bGY);
        }
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    private void QB() {
        this.aQD = (ImageButton) findViewById(g.btnBack1);
        this.bGO = (ImageButton) findViewById(g.btnForward1);
        this.bGP = (ImageButton) findViewById(g.btnExit1);
        this.bGQ = (ImageButton) findViewById(g.btnHome1);
        if (Integer.parseInt(VERSION.SDK) >= 16) {
            this.aQD.setAlpha(120);
            this.bGO.setAlpha(120);
            this.bGQ.setAlpha(120);
        }
        this.bGQ.setEnabled(false);
        this.aQD.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public void onClick(View v) {
                if (this.bHg.bGM != null && this.bHg.bGM.canGoBack()) {
                    this.bHg.bGM.goBack();
                }
            }
        });
        this.bGO.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public void onClick(View v) {
                if (this.bHg.bGM != null && this.bHg.bGM.canGoForward()) {
                    this.bHg.bGM.goForward();
                }
            }
        });
        this.bGQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public void onClick(View v) {
                if (this.bHg.bGM != null) {
                    this.bHg.bGM.loadUrl(this.bHg.bGY == null ? BrowserActivity.bGT : this.bHg.bGY);
                }
            }
        });
        this.bGP.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public void onClick(View v) {
                this.bHg.bGZ.finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.bGM == null || !this.bGM.canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        this.bGM.goBack();
        if (Integer.parseInt(VERSION.SDK) >= 16) {
            b(this.bGM);
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case 0:
                    if (this.bGX != null) {
                        Uri result;
                        if (data == null || resultCode != -1) {
                            result = null;
                        } else {
                            result = data.getData();
                        }
                        this.bGX.onReceiveValue(result);
                        this.bGX = null;
                        return;
                    }
                    return;
                case 1:
                    String path = data.getData().getPath();
                    return;
                default:
                    return;
            }
        } else if (resultCode == 0 && this.bGX != null) {
            this.bGX.onReceiveValue(null);
            this.bGX = null;
        }
    }

    protected void onNewIntent(Intent intent) {
        if (intent != null && this.bGM != null && intent.getData() != null) {
            this.bGM.loadUrl(intent.getData().toString());
        }
    }

    protected void onDestroy() {
        if (this.bGM != null) {
            this.bGM.setVisibility(8);
            new Timer().schedule(new TimerTask(this) {
                final /* synthetic */ BrowserActivity bHg;

                {
                    this.bHg = this$0;
                }

                public void run() {
                    this.bHg.runOnUiThread(new 1(this));
                }
            }, ViewConfiguration.getZoomControlsTimeout() + 1000);
        }
        super.onDestroy();
    }

    private void QC() {
        this.aIs.setVisibility(8);
        ej("");
        final String url = this.bGY == null ? bGT : this.bGY;
        View popupView = LayoutInflater.from(this).inflate(i.menu_wap_activity, null);
        popupView.findViewById(g.tv_browser).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BrowserActivity bHg;

            public void onClick(View v) {
                if (v.getId() == g.tv_browser) {
                    t.q(this.bHg.bGZ, url);
                }
            }
        });
        this.ZY = new PopupWindow(popupView, -2, -2);
        this.ZY.setFocusable(true);
        this.ZY.setOutsideTouchable(true);
        this.ZY.setBackgroundDrawable(new ColorDrawable(0));
        this.aIQ = (ImageButton) findViewById(g.sys_header_right_second_img);
        this.aIQ.setImageResource(f.ic_header_refresh);
        this.aIQ.setVisibility(0);
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public void onClick(View v) {
                if (this.bHg.bGM != null) {
                    this.bHg.bGM.reload();
                }
            }
        });
        Drawable drawable = getResources().getDrawable(f.btn_nav_close_selector);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.aIR.setCompoundDrawables(drawable, null, null, null);
        this.bHa = (ImageButton) findViewById(g.sys_header_right_img);
        this.bHa.setVisibility(0);
        this.bHa.setImageResource(f.ic_menu);
        this.bHa.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BrowserActivity bHg;

            {
                this.bHg = this$0;
            }

            public void onClick(View v) {
                this.bHg.ZY.showAsDropDown(this.bHg.bHa, at.dipToPx(this.bHg.bGZ, 12), 0);
            }
        });
    }

    protected void ej(String text) {
        if (text == null) {
            this.aIR.setText("");
        } else {
            this.aIR.setText(text);
        }
    }
}
