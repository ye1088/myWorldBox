package com.baidu.mapapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.baidu.mapapi.d.a;
import com.huluxia.module.p;
import com.tencent.open.SocialConstants;
import com.tencent.smtt.sdk.WebView;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class PlaceCaterActivity extends Activity implements a {
    static ImageView c;
    static DisplayMetrics n;
    static Hashtable<Integer, View> o = new Hashtable();
    static Handler p = new q();
    private static int q = -2;
    private static int r = -1;
    private static int s = 10;
    private static int t = 5;
    private static int u = 1;
    private static int v = -7566196;
    private static int w = -12487463;
    private static int x = -1710619;
    TextView a;
    TextView b;
    LinearLayout d;
    TextView e;
    TextView f;
    TextView g;
    TextView h;
    TextView i;
    TextView j;
    TextView k;
    TextView l;
    LinearLayout m;

    private Bitmap a(String str) {
        try {
            return BitmapFactory.decodeStream(getAssets().open(str));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void a(LinearLayout linearLayout, List<h> list) {
        if (list != null && list.size() > 0) {
            this.m.removeAllViews();
            o.clear();
            int size = list.size();
            int i = (size / 2) + (size % 2);
            for (int i2 = 0; i2 < i; i2++) {
                View linearLayout2 = new LinearLayout(this);
                linearLayout2.setLayoutParams(new LayoutParams(r, q));
                linearLayout.addView(linearLayout2);
                View linearLayout3 = new LinearLayout(this);
                linearLayout3.setOrientation(0);
                linearLayout3.setLayoutParams(new LayoutParams(r, q));
                linearLayout3.setPadding(20, 5, 5, 5);
                linearLayout2.addView(linearLayout3);
                ((LinearLayout.LayoutParams) linearLayout3.getLayoutParams()).weight = 1.0f;
                View imageView = new ImageView(this);
                imageView.setLayoutParams(new LayoutParams((int) (22.0f * n.density), (int) (22.0f * n.density)));
                imageView.setTag(Integer.valueOf(i2 * 2));
                d.a(linearLayout.hashCode(), (i2 * 2) + 1, h.a.replaceAll("#replace#", ((h) list.get(i2 * 2)).d), this);
                o.put(Integer.valueOf((i2 * 2) + 1), imageView);
                linearLayout3.addView(imageView);
                ((LinearLayout.LayoutParams) imageView.getLayoutParams()).gravity = 17;
                imageView = new TextView(this);
                imageView.setTag(list.get(i2 * 2));
                imageView.setPadding(s, s, s, s);
                imageView.setLayoutParams(new LayoutParams(q, q));
                imageView.setClickable(true);
                imageView.setText(((h) list.get(i2 * 2)).b);
                imageView.setTextColor(w);
                imageView.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ PlaceCaterActivity a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View view) {
                        h hVar = (h) view.getTag();
                        this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(hVar.c)));
                        s.a().a("place_cater_moreinfo_click", "{\"cat\":\"" + hVar.b + "\"}");
                    }
                });
                linearLayout3.addView(imageView);
                ((LinearLayout.LayoutParams) imageView.getLayoutParams()).gravity = 17;
                if ((i2 * 2) + 1 < size) {
                    linearLayout3 = new LinearLayout(this);
                    linearLayout3.setPadding(20, 5, 5, 5);
                    linearLayout3.setLayoutParams(new LayoutParams(r, q));
                    linearLayout2.addView(linearLayout3);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
                    layoutParams.weight = 1.0f;
                    layoutParams.gravity = 17;
                    linearLayout2 = new ImageView(this);
                    linearLayout2.setLayoutParams(new LayoutParams((int) (22.0f * n.density), (int) (22.0f * n.density)));
                    list.get((i2 * 2) + 1);
                    d.a(linearLayout.hashCode(), ((i2 * 2) + 1) + 1, h.a.replaceAll("#replace#", ((h) list.get((i2 * 2) + 1)).d), this);
                    o.put(Integer.valueOf(((i2 * 2) + 1) + 1), linearLayout2);
                    linearLayout3.addView(linearLayout2);
                    ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).gravity = 16;
                    linearLayout2 = new TextView(this);
                    linearLayout2.setTag(list.get((i2 * 2) + 1));
                    linearLayout2.setPadding(s, s, s, s);
                    linearLayout2.setClickable(true);
                    linearLayout2.setTextColor(w);
                    linearLayout2.setText(((h) list.get((i2 * 2) + 1)).b);
                    linearLayout2.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ PlaceCaterActivity a;

                        {
                            this.a = r1;
                        }

                        public void onClick(View view) {
                            h hVar = (h) view.getTag();
                            this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(hVar.c)));
                            s.a().a("place_cater_moreinfo_click", "{\"cat\":\"" + hVar.b + "\"}");
                        }
                    });
                    linearLayout3.addView(linearLayout2);
                    ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).gravity = 17;
                }
            }
        }
    }

    void a(float f) {
        if (this.d != null) {
            this.d.removeAllViews();
            int i = (int) f;
            for (int i2 = 0; i2 < 5; i2++) {
                View imageView;
                if (i2 < i) {
                    imageView = new ImageView(this);
                    imageView.setImageBitmap(a("star_light.png"));
                    imageView.setLayoutParams(new LayoutParams((int) (n.density * 20.0f), (int) (n.density * 20.0f)));
                    imageView.setPadding(1, 1, 1, 1);
                    this.d.addView(imageView);
                } else {
                    imageView = new ImageView(this);
                    imageView.setImageBitmap(a("star_gray.png"));
                    imageView.setLayoutParams(new LayoutParams((int) (n.density * 20.0f), (int) (n.density * 20.0f)));
                    imageView.setPadding(1, 1, 1, 1);
                    this.d.addView(imageView);
                }
            }
            View textView = new TextView(this);
            textView.setLayoutParams(new LayoutParams(q, q));
            textView.setText(Float.toString(f));
            textView.setPadding(10, 0, 10, 0);
            textView.setTextColor(-16777216);
            this.d.addView(textView);
        }
    }

    void a(DisplayMetrics displayMetrics) {
        View linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(-3355444);
        linearLayout.setPadding(1, 1, 1, 1);
        View linearLayout2 = new LinearLayout(this);
        linearLayout2.setPadding(1, 1, 1, 1);
        linearLayout2.setBackgroundColor(-1);
        linearLayout2.setLayoutParams(new LayoutParams(r, q));
        linearLayout2.setOrientation(1);
        linearLayout.addView(linearLayout2);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.rightMargin = u;
        layoutParams.bottomMargin = u;
        layoutParams.topMargin = u;
        layoutParams.leftMargin = u;
        this.a = new TextView(this);
        this.a.setLayoutParams(new LayoutParams(r, q));
        this.a.setTextSize(18.0f);
        this.a.setText("");
        this.a.setPadding(t, t, t, t);
        this.a.setTextColor(-16777216);
        this.a.setTypeface(Typeface.DEFAULT, 1);
        linearLayout2.addView(this.a);
        ((LinearLayout.LayoutParams) this.a.getLayoutParams()).leftMargin = 1;
        this.b = new TextView(this);
        this.b.setLayoutParams(new LayoutParams(r, q));
        this.b.setTextSize(16.0f);
        this.b.setPadding(s, s, s, s);
        this.b.setTextColor(v);
        linearLayout2.addView(this.b);
        linearLayout2 = new LinearLayout(this);
        linearLayout2.setBackgroundColor(-1);
        linearLayout2.setLayoutParams(new LayoutParams(-1, -2));
        linearLayout.addView(linearLayout2);
        layoutParams = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.topMargin = u;
        layoutParams.rightMargin = u;
        layoutParams.bottomMargin = u;
        layoutParams.leftMargin = u;
        c = new ImageView(this);
        c.setPadding(5, 5, 5, 5);
        c.setLayoutParams(new LayoutParams((int) (120.0f * displayMetrics.density), (int) (90.0f * displayMetrics.density)));
        linearLayout2.addView(c);
        View linearLayout3 = new LinearLayout(this);
        linearLayout3.setOrientation(1);
        linearLayout3.setLayoutParams(new LayoutParams(r, q));
        linearLayout3.setPadding(s, s, s, s);
        linearLayout2.addView(linearLayout3);
        ((LinearLayout.LayoutParams) linearLayout3.getLayoutParams()).gravity = 16;
        this.d = new LinearLayout(this);
        this.d.setPadding(2, 2, 2, 2);
        this.d.setOrientation(0);
        linearLayout3.addView(this.d);
        View linearLayout4 = new LinearLayout(this);
        linearLayout4.setPadding(2, 2, 2, 2);
        linearLayout4.setLayoutParams(new LayoutParams(q, q));
        linearLayout3.addView(linearLayout4);
        linearLayout2 = new TextView(this);
        linearLayout2.setTextColor(v);
        linearLayout2.setTextSize(16.0f);
        linearLayout2.setText("参考价：");
        linearLayout4.addView(linearLayout2);
        this.e = new TextView(this);
        this.e.setTextColor(-4712681);
        this.e.setTextSize(16.0f);
        linearLayout4.addView(this.e);
        linearLayout4 = new LinearLayout(this);
        linearLayout4.setPadding(2, 2, 2, 2);
        linearLayout3.addView(linearLayout4);
        this.f = new TextView(this);
        this.f.setPadding(0, 0, 5, 0);
        this.f.setText("口味:3.0");
        this.f.setTextColor(v);
        this.f.setTextSize(12.0f);
        linearLayout4.addView(this.f);
        this.g = new TextView(this);
        this.g.setPadding(0, 0, 5, 0);
        this.g.setText("服务:3.0");
        this.g.setTextColor(v);
        this.g.setTextSize(12.0f);
        linearLayout4.addView(this.g);
        this.h = new TextView(this);
        this.h.setPadding(0, 0, 5, 0);
        this.h.setText("环境:3.0");
        this.h.setTextColor(v);
        this.h.setTextSize(12.0f);
        linearLayout4.addView(this.h);
        linearLayout2 = new LinearLayout(this);
        linearLayout2.setBackgroundColor(-1);
        linearLayout2.setPadding(5, 5, 5, 5);
        linearLayout2.setLayoutParams(new LayoutParams(-1, -2));
        linearLayout2.setOrientation(0);
        linearLayout.addView(linearLayout2);
        layoutParams = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.topMargin = u;
        layoutParams.rightMargin = u;
        layoutParams.bottomMargin = u;
        layoutParams.leftMargin = u;
        linearLayout2.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PlaceCaterActivity a;

            {
                this.a = r1;
            }

            public void onClick(View view) {
                try {
                    this.a.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(WebView.SCHEME_TEL + this.a.i.getText().toString().trim())));
                    s.a().a("place_telbutton_click", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        linearLayout4 = new ImageView(this);
        linearLayout4.setPadding(5, 5, 5, 5);
        linearLayout4.setLayoutParams(new LayoutParams((int) (35.0f * displayMetrics.density), (int) (35.0f * displayMetrics.density)));
        linearLayout4.setImageBitmap(a("iconphone.png"));
        linearLayout2.addView(linearLayout4);
        ((LinearLayout.LayoutParams) linearLayout4.getLayoutParams()).gravity = 16;
        this.i = new TextView(this);
        this.i.setTextColor(-16777216);
        this.i.setText("(010)4343243");
        this.i.setPadding(5, 5, 5, 5);
        this.i.setTextSize(16.0f);
        this.i.setLayoutParams(new LayoutParams(q, q));
        linearLayout2.addView(this.i);
        layoutParams = (LinearLayout.LayoutParams) this.i.getLayoutParams();
        layoutParams.weight = 1.0f;
        layoutParams.gravity = 16;
        linearLayout4 = new ImageView(this);
        linearLayout4.setLayoutParams(new LayoutParams(q, q));
        linearLayout4.setImageBitmap(a("arrow.png"));
        linearLayout4.setPadding(5, 5, 5, 10);
        linearLayout2.addView(linearLayout4);
        ((LinearLayout.LayoutParams) linearLayout4.getLayoutParams()).gravity = 16;
        linearLayout2 = new LinearLayout(this);
        linearLayout2.setBackgroundColor(x);
        linearLayout2.setLayoutParams(new LayoutParams(r, q));
        linearLayout2.setOrientation(1);
        linearLayout.addView(linearLayout2);
        layoutParams = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.topMargin = u;
        layoutParams.rightMargin = u;
        layoutParams.bottomMargin = u;
        layoutParams.leftMargin = u;
        linearLayout4 = new TextView(this);
        linearLayout4.setTextSize(18.0f);
        linearLayout4.setText("商户简介");
        linearLayout4.setPadding(t, t, t, t);
        linearLayout4.setTextColor(-16777216);
        linearLayout4.setLayoutParams(new LayoutParams(q, q));
        linearLayout2.addView(linearLayout4);
        this.j = new TextView(this);
        this.j.setBackgroundColor(-1);
        this.j.setTextColor(v);
        this.j.setPadding(s, s, s, s);
        this.j.setTextSize(16.0f);
        this.j.setLayoutParams(new LayoutParams(r, q));
        linearLayout2.addView(this.j);
        this.k = new TextView(this);
        this.k.setBackgroundColor(-1);
        this.k.setTextColor(v);
        this.k.setPadding(s, s, s, s);
        this.k.setTextSize(16.0f);
        this.k.setLayoutParams(new LayoutParams(r, q));
        linearLayout2.addView(this.k);
        linearLayout2 = new LinearLayout(this);
        linearLayout2.setBackgroundColor(x);
        linearLayout2.setOrientation(1);
        linearLayout2.setLayoutParams(new LayoutParams(r, q));
        linearLayout.addView(linearLayout2);
        layoutParams = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.topMargin = u;
        layoutParams.rightMargin = u;
        layoutParams.bottomMargin = u;
        layoutParams.leftMargin = u;
        linearLayout4 = new TextView(this);
        linearLayout4.setLayoutParams(new LayoutParams(r, q));
        linearLayout4.setText("评论信息");
        linearLayout4.setPadding(t, t, t, t);
        linearLayout4.setTextColor(-16777216);
        linearLayout4.setTextSize(18.0f);
        linearLayout2.addView(linearLayout4);
        this.l = new TextView(this);
        this.l.setPadding(s, s, s, s);
        this.l.setBackgroundColor(-1);
        this.l.setLayoutParams(new LayoutParams(r, q));
        this.l.setTextSize(16.0f);
        this.l.setTextColor(v);
        linearLayout2.addView(this.l);
        linearLayout2 = new LinearLayout(this);
        linearLayout2.setBackgroundColor(x);
        linearLayout2.setOrientation(1);
        linearLayout2.setLayoutParams(new LayoutParams(r, q));
        linearLayout.addView(linearLayout2);
        layoutParams = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.topMargin = u;
        layoutParams.rightMargin = u;
        layoutParams.bottomMargin = u;
        layoutParams.leftMargin = u;
        linearLayout4 = new TextView(this);
        linearLayout4.setLayoutParams(new LayoutParams(q, q));
        linearLayout4.setTextSize(18.0f);
        linearLayout4.setPadding(t, t, t, t);
        linearLayout4.setTextColor(-16777216);
        linearLayout4.setText("查看更多");
        linearLayout2.addView(linearLayout4);
        this.m = new LinearLayout(this);
        this.m.setOrientation(1);
        this.m.setBackgroundColor(-1);
        this.m.setLayoutParams(new LayoutParams(r, q));
        linearLayout2.addView(this.m);
        linearLayout2 = new ScrollView(this);
        linearLayout2.setPadding(5, 5, 0, 5);
        linearLayout2.setLayoutParams(new LayoutParams(r, r));
        linearLayout2.setBackgroundColor(-526345);
        linearLayout2.addView(linearLayout);
        ((FrameLayout.LayoutParams) linearLayout.getLayoutParams()).rightMargin = 5;
        setContentView(linearLayout2);
    }

    void a(l lVar) {
        this.a.setText(lVar.a);
        this.b.setText("地址：" + lVar.b);
        this.e.setText("￥" + lVar.g);
        this.f.setText("口味:" + lVar.h);
        this.g.setText("服务:" + lVar.j);
        this.h.setText("环境:" + lVar.i);
        this.i.setText(lVar.c);
        if (lVar.l == null || "".equals(lVar.l)) {
            this.j.setVisibility(8);
        } else {
            this.j.setVisibility(0);
            this.j.setText("推荐菜：" + lVar.l);
        }
        if (lVar.k == null || "".equals(lVar.k)) {
            this.k.setVisibility(8);
        } else {
            this.k.setVisibility(0);
            this.k.setText("商户描述：" + lVar.k);
        }
        if (lVar.m == null || "".equals(lVar.m)) {
            this.l.setVisibility(8);
        } else {
            this.l.setVisibility(0);
            this.l.setText(lVar.m);
        }
        if (lVar.e != null) {
            d.a(c.hashCode(), 0, lVar.e, this);
        }
        float f = 0.0f;
        try {
            f = Float.valueOf(lVar.f).floatValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        a(f);
        a(this.m, lVar.o);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        l lVar = new l();
        lVar.a = extras.getString("name");
        lVar.b = extras.getString("addr");
        lVar.c = extras.getString("tel");
        lVar.d = extras.getString(p.UID);
        lVar.e = extras.getString("image");
        lVar.f = extras.getString("overall_rating");
        lVar.g = extras.getString("price");
        lVar.h = extras.getString("taste_rating");
        lVar.i = extras.getString("enviroment_raing");
        lVar.j = extras.getString("service_rating");
        lVar.k = extras.getString(SocialConstants.PARAM_COMMENT);
        lVar.l = extras.getString("recommendation");
        lVar.m = extras.getString("review");
        lVar.n = extras.getString("user_logo");
        String[] stringArray = extras.getStringArray("aryMoreLinkName");
        String[] stringArray2 = extras.getStringArray("aryMoreLinkUrl");
        String[] stringArray3 = extras.getStringArray("aryMoreLinkCnName");
        if (!(stringArray == null || stringArray2 == null)) {
            for (int i = 0; i < stringArray2.length; i++) {
                if (!"dianping".equals(stringArray[i])) {
                    h hVar = new h();
                    hVar.d = stringArray[i];
                    hVar.c = stringArray2[i];
                    hVar.b = stringArray3[i];
                    lVar.o.add(hVar);
                }
            }
        }
        if (lVar.c == null || "".equals(lVar.c)) {
            s.a().a("place_notel_show", null);
        } else {
            s.a().a("place_tel_show", null);
        }
        n = getResources().getDisplayMetrics();
        a(n);
        a(lVar);
    }

    public void onError(int i, int i2, String str, Object obj) {
        Log.d("kal", "onError  :  url=" + str);
    }

    public void onOk(int i, int i2, String str, Object obj) {
        Message obtainMessage;
        if (i == c.hashCode()) {
            obtainMessage = p.obtainMessage(1);
            obtainMessage.obj = obj;
            obtainMessage.sendToTarget();
        } else if (i == this.m.hashCode()) {
            obtainMessage = p.obtainMessage(2);
            obtainMessage.obj = obj;
            obtainMessage.arg1 = i2;
            obtainMessage.sendToTarget();
        }
    }
}
