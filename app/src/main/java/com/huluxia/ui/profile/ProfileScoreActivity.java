package com.huluxia.ui.profile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.framework.base.image.Config.NetFormat;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.l;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.as;
import com.huluxia.utils.aw;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.d;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;

public class ProfileScoreActivity extends HTBaseActivity {
    public static final String aJm = "title";
    public static final String aJn = "url";
    public static final String aJo = "UseWideView";
    public static final String aJp = "flag";
    private static final String aJv = "file:///android_asset/load_page_fail.html";
    public static int bhd = 0;
    public static int bhe = 1;
    public static final String bhf = "info";
    private static final String bhg = "http://bb.huluxia.com/bbs/jifen.html";
    private static final String bhh = "http://bb.huluxia.com/bbs/hulu.html";
    private WebViewClient aJC = new WebViewClient(this) {
        final /* synthetic */ ProfileScoreActivity bhi;

        {
            this.bhi = this$0;
        }

        @SuppressLint({"NewApi"})
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!(url.indexOf("/wap.php") == -1 && url.indexOf("wap=1") == -1)) {
                if (this.bhi.aJu.contains(url)) {
                    for (int i = this.bhi.aJt.size() - 1; i >= 0; i--) {
                        if (!((String) this.bhi.aJt.get(i)).equals(url)) {
                            this.bhi.aJt.remove(i);
                            this.bhi.aJu.remove(url);
                        }
                    }
                } else {
                    this.bhi.aJt.add(url);
                    this.bhi.aJu.add(url);
                }
            }
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            this.bhi.cs(false);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            this.bhi.cs(false);
            view.clearView();
            this.bhi.d(view, ProfileScoreActivity.aJv);
        }
    };
    private RelativeLayout aJq;
    private WebView aJr;
    private boolean aJs;
    private ArrayList<String> aJt = new ArrayList();
    private HashSet<String> aJu = new HashSet();
    private boolean aJw = false;
    private int tf = 0;
    private String url;

    private class a implements DownloadListener {
        final /* synthetic */ ProfileScoreActivity bhi;

        private a(ProfileScoreActivity profileScoreActivity) {
            this.bhi = profileScoreActivity;
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            t.q(this.bhi, url);
        }
    }

    private class b extends WebChromeClient {
        final /* synthetic */ ProfileScoreActivity bhi;

        private b(ProfileScoreActivity profileScoreActivity) {
            this.bhi = profileScoreActivity;
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 100) {
                this.bhi.cs(true);
            } else {
                this.bhi.cs(false);
            }
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            this.bhi.cs(false);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_score);
        ProfileInfo info = (ProfileInfo) getIntent().getParcelableExtra(bhf);
        this.aJs = getIntent().getBooleanExtra("UseWideView", true);
        this.tf = getIntent().getIntExtra("flag", bhd);
        a(info, this.tf);
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
        this.aIQ.setImageResource(f.ic_header_refresh);
        this.aIQ.setVisibility(0);
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileScoreActivity bhi;

            {
                this.bhi = this$0;
            }

            public void onClick(View v) {
                if (this.bhi.aJr == null) {
                    this.bhi.finish();
                } else if (this.bhi.aJr.getUrl() == null || this.bhi.url == null || !ProfileScoreActivity.aJv.equals(this.bhi.aJr.getUrl())) {
                    this.bhi.aJr.reload();
                } else {
                    this.bhi.aJr.loadUrl(this.bhi.url);
                }
            }
        });
        Drawable drawable = getResources().getDrawable(f.btn_nav_close_selector);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.aIR.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileScoreActivity bhi;

            {
                this.bhi = this$0;
            }

            public void onClick(View arg0) {
                this.bhi.finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    private void d(final WebView view, final String url) {
        if (view != null) {
            ((Activity) view.getContext()).runOnUiThread(new Runnable(this) {
                final /* synthetic */ ProfileScoreActivity bhi;

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

    private void a(ProfileInfo info, int flag) {
        EmojiTextView nick = (EmojiTextView) findViewById(g.nick);
        nick.setText(aw.go(info.getNick()));
        nick.setTextColor(as.g(this, info.getRole(), info.getGender()));
        ((PaintView) findViewById(g.avatar)).setUri(UtilUri.getUriOrNull(info.getAvatar()), NetFormat.FORMAT_80).radius(5.0f).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
        TextView tv_score = (TextView) findViewById(g.tv_score);
        a(info);
        b(info);
        if (flag == bhd) {
            this.url = bhg;
            Drawable drawable = getResources().getDrawable(f.ic_space_jifen);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_score.setCompoundDrawables(null, null, drawable, null);
            tv_score.setText(String.valueOf(info.getIntegral()));
            ej("贡献值");
            return;
        }
        this.url = bhh;
        tv_score.setText(String.valueOf(info.getCredits()));
        ej("葫芦数");
    }

    private void a(ProfileInfo user) {
        ((TextView) findViewById(g.user_age)).setText(Integer.toString(user.getAge()));
        View genderBg = findViewById(g.rl_sex_age);
        ImageView genderMark = (ImageView) findViewById(g.userlist_gender_mark);
        if (user.getGender() == 1) {
            genderBg.setBackgroundResource(f.bg_gender_female);
            genderMark.setImageResource(f.user_female);
            return;
        }
        genderBg.setBackgroundResource(f.bg_gender_male);
        genderMark.setImageResource(f.user_male);
    }

    @TargetApi(16)
    private void b(ProfileInfo user) {
        View honor_flag = findViewById(g.honor_flag);
        if (user.getIdentityColor() != 0) {
            GradientDrawable bgShape = (GradientDrawable) honor_flag.getBackground();
            ((TextView) findViewById(g.tv_honor)).setText(user.getIdentityTitle());
            honor_flag.setVisibility(0);
            return;
        }
        honor_flag.setVisibility(8);
    }
}
