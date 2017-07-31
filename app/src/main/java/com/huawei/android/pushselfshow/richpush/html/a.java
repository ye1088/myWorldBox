package com.huawei.android.pushselfshow.richpush.html;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.huawei.android.pushselfshow.utils.d;

public class a implements OnTouchListener {
    private static int j = 0;
    private static int k = 1;
    private static int l = 2;
    private View a;
    private ImageView b;
    private ImageView c;
    private ImageView d;
    private ImageView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private Activity m;

    public a(Activity activity) {
        this.m = activity;
    }

    @SuppressLint({"NewApi"})
    private void a(View view, int i) {
        if (view != null && view.isClickable()) {
            TextView textView;
            if (view == this.c) {
                textView = this.g;
            } else if (view == this.b) {
                textView = this.f;
            } else if (view == this.d) {
                textView = this.h;
            } else if (view == this.e) {
                textView = this.i;
            } else {
                return;
            }
            if (textView != null) {
                float f = k == i ? 0.5f : l == i ? 0.3f : 1.0f;
                view.setAlpha(f);
                textView.setAlpha(f);
            }
        }
    }

    private void a(ImageView imageView, boolean z) {
        if (imageView != null) {
            imageView.setClickable(z);
        }
    }

    public void a() {
        if (this.b != null) {
            this.b.setOnTouchListener(this);
        }
        if (this.c != null) {
            this.c.setOnTouchListener(this);
        }
        if (this.d != null) {
            this.d.setOnTouchListener(this);
        }
        if (this.e != null) {
            this.e.setOnTouchListener(this);
        }
    }

    public void a(View view) {
        this.a = view.findViewById(d.d(this.m, "hwpush_bottom_bar"));
        this.b = (ImageView) this.a.findViewById(d.d(this.m, "hwpush_bt_back_img"));
        this.c = (ImageView) this.a.findViewById(d.d(this.m, "hwpush_bt_forward_img"));
        this.d = (ImageView) this.a.findViewById(d.d(this.m, "hwpush_bt_refresh_img"));
        this.e = (ImageView) this.a.findViewById(d.d(this.m, "hwpush_bt_collect_img"));
        this.f = (TextView) this.a.findViewById(d.d(this.m, "hwpush_bt_back_txt"));
        this.g = (TextView) this.a.findViewById(d.d(this.m, "hwpush_bt_forward_txt"));
        this.h = (TextView) this.a.findViewById(d.d(this.m, "hwpush_bt_refresh_txt"));
        this.i = (TextView) this.a.findViewById(d.d(this.m, "hwpush_bt_collect_txt"));
        a(this.b, l);
        a(this.c, l);
        com.huawei.android.pushselfshow.utils.a.a(this.m, this.f);
        com.huawei.android.pushselfshow.utils.a.a(this.m, this.g);
        com.huawei.android.pushselfshow.utils.a.a(this.m, this.h);
        com.huawei.android.pushselfshow.utils.a.a(this.m, this.i);
    }

    public void a(ImageView imageView) {
        a((View) imageView, l);
        a(imageView, false);
    }

    public void b(ImageView imageView) {
        a(imageView, true);
        a((View) imageView, j);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            a(view, j);
        } else {
            a(view, k);
        }
        return false;
    }
}
