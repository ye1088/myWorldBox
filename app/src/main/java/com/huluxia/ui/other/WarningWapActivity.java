package com.huluxia.ui.other;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.module.ab;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.o;

public class WarningWapActivity extends HTBaseActivity {
    private WebViewClient aJC = new WebViewClient(this) {
        final /* synthetic */ WarningWapActivity bdq;

        {
            this.bdq = this$0;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            t.q(this.bdq.bdp, url);
            this.bdq.bdp.finish();
            return true;
        }
    };
    private WebView aJr;
    private String bdo;
    private WarningWapActivity bdp;
    private String url;

    private class a implements DownloadListener {
        final /* synthetic */ WarningWapActivity bdq;

        private a(WarningWapActivity warningWapActivity) {
            this.bdq = warningWapActivity;
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            t.q(this.bdq.bdp, url);
            this.bdq.bdp.finish();
        }
    }

    public class b {
        final /* synthetic */ WarningWapActivity bdq;
        Context mContext;

        b(WarningWapActivity this$0, Context c) {
            this.bdq = this$0;
            this.mContext = c;
        }

        @JavascriptInterface
        public void startLogin() {
            t.an(this.bdq);
        }

        @JavascriptInterface
        public void close() {
            HLog.info(this.bdq.bdp, "close", new Object[0]);
            this.bdq.bdp.finish();
        }

        @JavascriptInterface
        public void IP() {
            o.bV(this.bdq.bdo);
            this.bdq.bdp.finish();
        }
    }

    private class c extends WebChromeClient {
        final /* synthetic */ WarningWapActivity bdq;

        private c(WarningWapActivity warningWapActivity) {
            this.bdq = warningWapActivity;
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                this.bdq.bdp.cs(false);
            } else {
                this.bdq.bdp.cs(true);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.fragment_card_game);
        this.bdp = this;
        this.bdo = getIntent().getStringExtra("url");
        ej(hlx.data.localstore.a.bKA_TIPS);
        this.aJr = (WebView) findViewById(g.webview);
        this.aJr.getSettings().setJavaScriptEnabled(true);
        this.aJr.addJavascriptInterface(new b(this, this), "Android");
        this.aJr.getSettings().setUseWideViewPort(true);
        this.aJr.getSettings().setLoadWithOverviewMode(true);
        this.aJr.getSettings().setBuiltInZoomControls(false);
        this.aJr.getSettings().setSupportZoom(false);
        this.aJr.setInitialScale(39);
        this.aJr.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        this.aJr.getSettings().setDefaultTextEncodingName("utf-8");
        this.aJr.getSettings().setAppCacheEnabled(true);
        this.aJr.getSettings().setCacheMode(-1);
        this.aJr.setWebChromeClient(new c());
        this.aJr.setDownloadListener(new a());
        this.aJr.setWebViewClient(this.aJC);
        FM();
        this.url = String.format("%s?_url=%s", new Object[]{ab.aAz, this.bdo});
    }

    protected void onResume() {
        super.onResume();
        this.aJr.loadUrl(this.url);
    }

    private void FM() {
        this.aIs.setVisibility(8);
        ImageButton imgBtn = (ImageButton) findViewById(g.sys_header_right_img);
        imgBtn.setImageResource(f.ic_header_refresh);
        imgBtn.setVisibility(0);
        imgBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ WarningWapActivity bdq;

            {
                this.bdq = this$0;
            }

            public void onClick(View v) {
                this.bdq.aJr.reload();
            }
        });
        this.aIR.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ WarningWapActivity bdq;

            {
                this.bdq = this$0;
            }

            public void onClick(View v) {
                this.bdq.bdp.finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !this.aJr.canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        this.bdp.finish();
        return true;
    }
}
