package com.huawei.android.pushselfshow.richpush.favorites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.huawei.android.pushselfshow.utils.a;
import com.huawei.android.pushselfshow.utils.d;

public class b implements OnTouchListener {
    private static int f = 0;
    private static int g = 1;
    private static int h = 2;
    private View a;
    private ImageView b;
    private ImageView c;
    private TextView d;
    private TextView e;
    private Activity i;

    public b(Activity activity) {
        this.i = activity;
    }

    @SuppressLint({"NewApi"})
    private void a(View view, int i) {
        if (view != null && view.isClickable()) {
            TextView textView;
            if (view == this.b) {
                textView = this.d;
            } else if (view == this.c) {
                textView = this.e;
            } else {
                return;
            }
            if (textView != null) {
                float f = g == i ? 0.5f : h == i ? 0.3f : 1.0f;
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
    }

    public void a(View view) {
        this.a = view.findViewById(d.d(this.i, "hwpush_bottom_bar"));
        this.b = (ImageView) this.a.findViewById(d.d(this.i, "hwpush_bt_delete_img"));
        this.c = (ImageView) this.a.findViewById(d.d(this.i, "hwpush_bt_selectall_img"));
        this.d = (TextView) this.a.findViewById(d.d(this.i, "hwpush_bt_delete_txt"));
        this.e = (TextView) this.a.findViewById(d.d(this.i, "hwpush_bt_selectall_txt"));
        a.a(this.i, this.d);
        a.a(this.i, this.e);
    }

    public void a(ImageView imageView) {
        a((View) imageView, h);
        a(imageView, false);
    }

    public void b() {
        if (this.a != null) {
            this.a.setVisibility(0);
        }
    }

    public void b(ImageView imageView) {
        a(imageView, true);
        a((View) imageView, f);
    }

    public void c() {
        if (this.a != null) {
            this.a.setVisibility(8);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            a(view, f);
        } else {
            a(view, g);
        }
        return false;
    }
}
