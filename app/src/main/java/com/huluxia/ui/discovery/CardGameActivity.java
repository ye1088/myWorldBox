package com.huluxia.ui.discovery;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.j;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.DoNotStrip;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import java.lang.reflect.InvocationTargetException;

public class CardGameActivity extends HTBaseLoadingActivity {
    private WebViewClient aJC = new WebViewClient(this) {
        final /* synthetic */ CardGameActivity aRv;

        {
            this.aRv = this$0;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
    private View aJc;
    private WebView aJr;
    private boolean aJw = false;
    private String aRt = "false";
    private View aRu;
    private CallbackHandler eY = new CallbackHandler(this) {
        final /* synthetic */ CardGameActivity aRv;

        {
            this.aRv = this$0;
        }

        @MessageHandler(message = 545)
        public void onLogin() {
            String key = j.ep().getToken();
            this.aRv.aJr.loadUrl(String.format("%s/view/game/3card_index?_key=%s&firstload=1", new Object[]{com.huluxia.http.base.a.rN, key}));
        }
    };

    @DoNotStrip
    public class WebAppInterface {
        final /* synthetic */ CardGameActivity aRv;
        Context mContext;

        @DoNotStrip
        WebAppInterface(CardGameActivity this$0, Context c) {
            this.aRv = this$0;
            this.mContext = c;
        }

        @JavascriptInterface
        @DoNotStrip
        public void setIndex(String val) {
            this.aRv.aRt = val;
        }

        @JavascriptInterface
        @DoNotStrip
        public void startLogin() {
            t.an(this.aRv);
        }
    }

    private class a implements DownloadListener {
        final /* synthetic */ CardGameActivity aRv;

        private a(CardGameActivity cardGameActivity) {
            this.aRv = cardGameActivity;
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            t.q(this.aRv, url);
        }
    }

    private class b extends WebChromeClient {
        final /* synthetic */ CardGameActivity aRv;

        private b(CardGameActivity cardGameActivity) {
            this.aRv = cardGameActivity;
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                this.aRv.aJc.setVisibility(8);
            } else {
                this.aRv.aJc.setVisibility(0);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(h.class, this.eY);
        setContentView(i.fragment_card_game);
        Fd();
        Fm();
    }

    private void Fd() {
        this.aIs.setVisibility(0);
        this.aIR.setVisibility(0);
        this.aIR.setText("买定离手");
        this.aIR.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CardGameActivity aRv;

            {
                this.aRv = this$0;
            }

            public void onClick(View v) {
                if ("false".equals(this.aRv.aRt) && this.aRv.aJr.canGoBack()) {
                    this.aRv.aJr.goBack();
                } else {
                    this.aRv.finish();
                }
            }
        });
    }

    public void onBackPressed() {
        if ("false".equals(this.aRt) && this.aJr.canGoBack()) {
            this.aJr.goBack();
        } else {
            finish();
        }
    }

    private void Fm() {
        this.aJr = (WebView) findViewById(g.webview);
        this.aJc = findViewById(g.loading);
        this.aRu = findViewById(g.web_back);
        this.aRu.setVisibility(8);
        this.aJr.getSettings().setJavaScriptEnabled(true);
        this.aJr.addJavascriptInterface(new WebAppInterface(this, this), "Android");
        this.aJr.getSettings().setUseWideViewPort(true);
        this.aJr.getSettings().setLoadWithOverviewMode(true);
        this.aJr.getSettings().setBuiltInZoomControls(false);
        this.aJr.getSettings().setSupportZoom(false);
        this.aJr.setInitialScale(39);
        this.aJr.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        this.aJr.getSettings().setDefaultTextEncodingName("utf-8");
        this.aJr.getSettings().setAppCacheEnabled(true);
        this.aJr.getSettings().setCacheMode(-1);
        this.aJr.setWebChromeClient(new b());
        this.aJr.setDownloadListener(new a());
        this.aJr.setWebViewClient(this.aJC);
        String key = j.ep().getToken();
        this.aJr.loadUrl(String.format("%s/view/game/3card_index?_key=%s&firstload=1", new Object[]{com.huluxia.http.base.a.rN, key}));
    }

    public void onPause() {
        super.onPause();
        if (this.aJr != null) {
            try {
                this.aJr.getClass().getMethod("onPause", new Class[0]).invoke(this.aJr, (Object[]) null);
                this.aJw = true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.aJr != null) {
            try {
                if (this.aJw) {
                    this.aJr.getClass().getMethod("onResume", new Class[0]).invoke(this.aJr, (Object[]) null);
                }
                this.aJw = false;
                this.aJr.reload();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.eY);
        if (this.aJr != null) {
            this.aJr.getSettings().setBuiltInZoomControls(true);
            this.aJr.setVisibility(8);
            ViewGroup parent = (ViewGroup) this.aJr.getParent();
            if (parent != null) {
                parent.removeView(this.aJr);
            }
            this.aJr.removeAllViews();
            this.aJr.destroy();
            this.aJr = null;
        }
        this.aJw = false;
    }
}
