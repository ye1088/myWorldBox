package com.MCWorld.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.t;
import com.MCWorld.utils.aw;
import com.simple.colorful.d;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;

public class WapActivity extends HTBaseActivity {
    public static int aJA = 3;
    public static final String aJm = "title";
    public static final String aJn = "url";
    public static final String aJo = "UseWideView";
    public static final String aJp = "flag";
    private static final String aJv = "file:///android_asset/load_page_fail.html";
    public static int aJx = 0;
    public static int aJy = 1;
    public static int aJz = 2;
    private WapActivity aJB;
    private WebViewClient aJC = new WebViewClient(this) {
        final /* synthetic */ WapActivity aJD;

        {
            this.aJD = this$0;
        }

        @SuppressLint({"NewApi"})
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!(url.indexOf("/wap.php") == -1 && url.indexOf("wap=1") == -1)) {
                if (this.aJD.aJu.contains(url)) {
                    for (int i = this.aJD.aJt.size() - 1; i >= 0; i--) {
                        if (!((String) this.aJD.aJt.get(i)).equals(url)) {
                            this.aJD.aJt.remove(i);
                            this.aJD.aJu.remove(url);
                        }
                    }
                } else {
                    this.aJD.aJt.add(url);
                    this.aJD.aJu.add(url);
                }
            }
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            this.aJD.aJB.cs(false);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            this.aJD.aJB.cs(false);
            view.clearView();
            this.aJD.d(view, WapActivity.aJv);
        }
    };
    private RelativeLayout aJq;
    private WebView aJr;
    private boolean aJs;
    private ArrayList<String> aJt = new ArrayList();
    private HashSet<String> aJu = new HashSet();
    private boolean aJw = false;
    private int tf;
    private String url;

    private class a implements DownloadListener {
        final /* synthetic */ WapActivity aJD;

        private a(WapActivity wapActivity) {
            this.aJD = wapActivity;
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            t.q(this.aJD.aJB, url);
        }
    }

    private class b extends WebChromeClient {
        final /* synthetic */ WapActivity aJD;

        private b(WapActivity wapActivity) {
            this.aJD = wapActivity;
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 100) {
                this.aJD.aJB.cs(true);
            } else {
                this.aJD.aJB.cs(false);
            }
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            this.aJD.aJB.cs(false);
            if (title == null) {
                return;
            }
            if (WapActivity.aJA == this.aJD.aJB.tf) {
                String txt = title;
                int beginIdx = title.indexOf("】") + 1;
                int endIdx = title.indexOf("—");
                if (beginIdx > 0 && endIdx > 0 && endIdx > beginIdx) {
                    txt = title.substring(beginIdx, endIdx);
                }
                this.aJD.aJB.ej(aw.W(txt, 12));
                return;
            }
            this.aJD.aJB.ej(aw.W(title, 12));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_webview);
        this.aJB = this;
        ej(getIntent().getStringExtra("title") == null ? "" : getIntent().getStringExtra("title"));
        this.url = getIntent().getStringExtra("url");
        this.aJs = getIntent().getBooleanExtra("UseWideView", true);
        this.tf = getIntent().getIntExtra("flag", aJx);
        if (aJy == this.tf) {
            findViewById(g.rly_open).setVisibility(0);
            findViewById(g.BtnStart).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ WapActivity aJD;

                {
                    this.aJD = this$0;
                }

                public void onClick(View arg0) {
                }
            });
        }
        this.aJq = (RelativeLayout) findViewById(g.webviewRelativeLayout);
        this.aJr = (WebView) findViewById(g.webview);
        this.aJr.getSettings().setJavaScriptEnabled(true);
        this.aJr.getSettings().setUseWideViewPort(this.aJs);
        this.aJr.getSettings().setLoadWithOverviewMode(true);
        this.aJr.getSettings().setBuiltInZoomControls(true);
        this.aJr.getSettings().setSupportZoom(true);
        this.aJr.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        this.aJr.getSettings().setDefaultTextEncodingName("utf-8");
        this.aJr.getSettings().setAppCacheEnabled(true);
        this.aJr.getSettings().setCacheMode(2);
        this.aJr.getSettings().setAllowFileAccess(true);
        this.aJr.getSettings().setSupportMultipleWindows(true);
        this.aJr.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.aJr.getSettings().setUserAgentString("HuluxiaGametools " + this.aJr.getSettings().getUserAgentString());
        this.aJr.setWebChromeClient(new b());
        this.aJt.add(this.url);
        this.aJu.add(this.url);
        this.aJr.loadUrl(this.url);
        cs(true);
        this.aJr.setDownloadListener(new a());
        this.aJr.setWebViewClient(this.aJC);
        FM();
    }

    private void FM() {
        this.aIs.setVisibility(8);
        this.aIQ.setImageResource(d.r(this, c.refresh));
        this.aIQ.setVisibility(0);
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ WapActivity aJD;

            {
                this.aJD = this$0;
            }

            public void onClick(View v) {
                if (this.aJD.aJr == null) {
                    this.aJD.aJB.finish();
                } else if (this.aJD.aJr.getUrl() == null || this.aJD.url == null || !WapActivity.aJv.equals(this.aJD.aJr.getUrl())) {
                    this.aJD.aJr.reload();
                } else {
                    this.aJD.aJr.loadUrl(this.aJD.url);
                }
            }
        });
        if (this.tf == aJz) {
            this.aIR.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ WapActivity aJD;

                {
                    this.aJD = this$0;
                }

                public void onClick(View arg0) {
                    a.Ft().Fw();
                }
            });
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.tf == aJz) {
            a.Ft().Fw();
            return true;
        } else if (this.tf == aJy) {
            this.aJB.finish();
            return true;
        } else if (this.aJt.size() <= 1) {
            this.aJB.finish();
            return true;
        } else {
            this.aJu.remove(this.aJt.get(this.aJt.size() - 1));
            this.aJt.remove(this.aJt.get(this.aJt.size() - 1));
            this.aJr.loadUrl((String) this.aJt.get(this.aJt.size() - 1));
            return true;
        }
    }

    private void d(final WebView view, final String url) {
        if (view != null) {
            ((Activity) view.getContext()).runOnUiThread(new Runnable(this) {
                final /* synthetic */ WapActivity aJD;

                public void run() {
                    view.loadUrl(url);
                }
            });
        }
    }

    protected void onPause() {
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

    protected void onResume() {
        super.onResume();
        if (this.aJr != null) {
            try {
                if (this.aJw) {
                    this.aJr.getClass().getMethod("onResume", new Class[0]).invoke(this.aJr, (Object[]) null);
                }
                this.aJw = false;
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

    protected void onStop() {
        super.onStop();
        if (this.aJr != null) {
            this.aJr.getSettings().setBuiltInZoomControls(true);
            this.aJr.setVisibility(8);
            this.aJq.removeView(this.aJr);
            this.aJr.removeAllViews();
            this.aJr.destroy();
            this.aJr = null;
        }
        this.aJw = false;
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.aJr != null) {
            this.aJr.getSettings().setBuiltInZoomControls(true);
            this.aJr.setVisibility(8);
            this.aJq.removeView(this.aJr);
            this.aJr.removeAllViews();
            this.aJr.destroy();
            this.aJr = null;
        }
        this.aJw = false;
    }
}
