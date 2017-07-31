package com.huluxia.utils;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.log.HLog;
import com.xiaomi.mipush.sdk.MiPushClient;

/* compiled from: AnimationController */
public class a {
    public final int bjA = 0;
    public final int bjB = 1;
    public final int bjC = 2;
    public final int bjD = 3;
    public final int bjE = 4;
    public final int bjF = 5;
    public final int bjG = 6;
    public final int bjH = 7;
    private int bjI = 1;
    private int bjJ = 2;
    public final int bjw = 1;
    public final int bjx = 2;
    public final int bjy = 0;
    public final int bjz = -1;

    /* compiled from: AnimationController */
    class a implements AnimationListener {
        final /* synthetic */ a bjQ;
        View bjT = null;
        b bjU = null;

        public a(a this$0, View consultView, b callBackInterface) {
            this.bjQ = this$0;
            this.bjT = consultView;
            this.bjU = callBackInterface;
        }

        public void onAnimationEnd(Animation arg0) {
            if (this.bjU != null) {
                this.bjU.au(null);
            }
            this.bjT.setVisibility(0);
        }

        public void onAnimationRepeat(Animation arg0) {
        }

        public void onAnimationStart(Animation arg0) {
        }
    }

    /* compiled from: AnimationController */
    class b implements AnimationListener {
        final /* synthetic */ a bjQ;
        View view = null;

        b(a this$0, View view) {
            this.bjQ = this$0;
            this.view = view;
        }

        public void onAnimationEnd(Animation arg0) {
        }

        public void onAnimationRepeat(Animation arg0) {
        }

        public void onAnimationStart(Animation arg0) {
            if (this.view != null) {
                this.view.setVisibility(8);
            }
        }
    }

    /* compiled from: AnimationController */
    class c implements AnimationListener {
        final /* synthetic */ a bjQ;
        View bjT = null;
        b bjU = null;
        View view = null;

        public c(a this$0, View view, View consultView, b callBackInterface) {
            this.bjQ = this$0;
            this.view = view;
            this.bjT = consultView;
            this.bjU = callBackInterface;
        }

        public void onAnimationEnd(Animation arg0) {
            if (this.bjT != null) {
                if (this.bjU != null) {
                    this.bjU.au(null);
                }
                this.bjT.setVisibility(0);
            }
        }

        public void onAnimationRepeat(Animation arg0) {
        }

        public void onAnimationStart(Animation arg0) {
            if (this.view != null) {
                this.view.setVisibility(4);
            }
            if (this.bjT == null) {
            }
        }

        public void v(View consultView, long durationMillis, long delayMillis) {
            float xRatio = ((float) this.view.getWidth()) / ((float) consultView.getWidth());
            float yRatio = ((float) this.view.getHeight()) / ((float) consultView.getHeight());
            HLog.debug("", "viewWH:(" + this.view.getWidth() + MiPushClient.ACCEPT_TIME_SEPARATOR + this.view.getHeight() + "),consultViewWH:(" + consultView.getWidth() + MiPushClient.ACCEPT_TIME_SEPARATOR + consultView.getHeight() + "),ratio:(" + xRatio + MiPushClient.ACCEPT_TIME_SEPARATOR + yRatio + ")", new Object[0]);
            Animation animation = new ScaleAnimation(xRatio, 1.0f, yRatio, 1.0f, 1, 0.5f, 1, 0.5f);
            animation.setAnimationListener(new a(this.bjQ, consultView, this.bjU));
            this.bjQ.a(consultView, animation, durationMillis, delayMillis);
        }
    }

    /* compiled from: AnimationController */
    private class d implements AnimationListener {
        final /* synthetic */ a bjQ;
        private View view;

        public d(a aVar, View view) {
            this.bjQ = aVar;
            this.view = view;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            this.view.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private void a(Animation animation, int interpolatorType, long durationMillis, long delayMillis) {
        switch (interpolatorType) {
            case 0:
                animation.setInterpolator(new LinearInterpolator());
                break;
            case 1:
                animation.setInterpolator(new AccelerateInterpolator());
                break;
            case 2:
                animation.setInterpolator(new DecelerateInterpolator());
                break;
            case 3:
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case 4:
                animation.setInterpolator(new BounceInterpolator());
                break;
            case 5:
                animation.setInterpolator(new OvershootInterpolator());
                break;
            case 6:
                animation.setInterpolator(new AnticipateInterpolator());
                break;
            case 7:
                animation.setInterpolator(new AnticipateOvershootInterpolator());
                break;
        }
        animation.setDuration(durationMillis);
        animation.setStartOffset(delayMillis);
    }

    public void a(PaintView view, long durationMillis, long delayMillis, PaintView consultView, RelativeLayout layout, b callBackInterface) {
        if (view != null && consultView != null) {
            final PaintView img = a(view, layout);
            img.setVisibility(0);
            final PaintView paintView = consultView;
            final PaintView paintView2 = view;
            final b bVar = callBackInterface;
            final long j = durationMillis;
            final long j2 = delayMillis;
            img.post(new Runnable(this) {
                final /* synthetic */ a bjQ;

                public void run() {
                    int[] viewXY = new int[2];
                    img.getLocationOnScreen(viewXY);
                    int[] consultViewXY = new int[2];
                    paintView.setImageDrawable(paintView2.getDrawable());
                    paintView.getLocationOnScreen(consultViewXY);
                    int locationX = (-(viewXY[0] - (consultViewXY[0] + (paintView.getWidth() / 2)))) - (paintView.getWidth() / 2);
                    int locationY = (-(viewXY[1] - (consultViewXY[1] + (paintView.getHeight() / 2)))) - (paintView.getHeight() / 2);
                    TranslateAnimation animation = new TranslateAnimation(0.0f, (float) locationX, 0.0f, (float) locationY);
                    animation.setInterpolator(new AccelerateDecelerateInterpolator());
                    float ratio = ((float) paintView.getWidth()) / ((float) img.getWidth());
                    ScaleAnimation animation1 = new ScaleAnimation(1.0f, ratio, 1.0f, ratio, 0, (float) locationX, 0, (float) locationY);
                    Animation set = new AnimationSet(false);
                    set.addAnimation(animation);
                    set.addAnimation(animation1);
                    animation.setAnimationListener(new c(this.bjQ, img, paintView, bVar));
                    this.bjQ.a(img, set, j, j2);
                }
            });
        }
    }

    public PaintView a(PaintView img, RelativeLayout layout) {
        PaintView copy = new PaintView(layout.getContext());
        int[] loc = new int[2];
        img.getLocationOnScreen(loc);
        copy.setLayoutParams(img.getLayoutParams());
        copy.setImageDrawable(img.getDrawable());
        MarginLayoutParams mp = new MarginLayoutParams(img.getLayoutParams().width, img.getLayoutParams().height);
        mp.setMargins(loc[0], loc[1] - (img.getLayoutParams().height / 2), 0, 0);
        layout.addView(copy, new LayoutParams(mp));
        copy.setVisibility(8);
        return copy;
    }

    private void a(View view, Animation animation, long durationMillis, long delayMillis) {
        a(animation, -1, durationMillis, delayMillis);
        if (view.getVisibility() == 8) {
            view.setVisibility(0);
        }
        view.startAnimation(animation);
    }

    private void b(View view, Animation animation, long durationMillis, long delayMillis) {
        a(animation, -1, durationMillis, delayMillis);
        animation.setAnimationListener(new d(this, view));
        view.startAnimation(animation);
    }

    public void p(View view) {
        view.setVisibility(0);
    }

    public void q(View view) {
        view.setVisibility(8);
    }

    public void r(View view) {
        view.setVisibility(4);
    }

    public void a(View view, long durationMillis, long delayMillis) {
        a(view, new AlphaAnimation(0.0f, 1.0f), durationMillis, delayMillis);
    }

    public void b(View view, long durationMillis, long delayMillis) {
        b(view, new AlphaAnimation(1.0f, 0.0f), durationMillis, delayMillis);
    }

    public void c(View view, long durationMillis, long delayMillis) {
        a(view, new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, -1.0f, 2, 0.0f), durationMillis, delayMillis);
    }

    public void d(View view, long durationMillis, long delayMillis) {
        b(view, new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, -1.0f), durationMillis, delayMillis);
    }

    public void e(View view, long durationMillis, long delayMillis) {
        a(view, new TranslateAnimation(2, 1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f), durationMillis, delayMillis);
    }

    public void f(View view, long durationMillis, long delayMillis) {
        a(view, new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 1.0f, 2, 0.0f), durationMillis, delayMillis);
    }

    public void g(View view, long durationMillis, long delayMillis) {
        TranslateAnimation animation = new TranslateAnimation(2, 1.0f, 2, 0.0f, 2, 1.0f, 2, 0.0f);
        ScaleAnimation animation1 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 2, 0.5f, 2, 0.5f);
        Animation set = new AnimationSet(false);
        set.addAnimation(animation);
        set.addAnimation(animation1);
        a(view, set, durationMillis, delayMillis);
    }

    public void h(View view, long durationMillis, long delayMillis) {
        TranslateAnimation animation = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 1.0f, 2, 0.0f);
        Animation set = new AnimationSet(false);
        set.addAnimation(animation);
        a(view, set, durationMillis, delayMillis);
    }

    public void i(View view, long durationMillis, long delayMillis) {
        a(view, new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, 1.0f), durationMillis, delayMillis);
    }

    public void j(View view, long durationMillis, long delayMillis) {
        b(view, new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f), durationMillis, delayMillis);
    }

    public void k(View view, long durationMillis, long delayMillis) {
        TranslateAnimation animation = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(alphaAnim);
        b(view, animationSet, durationMillis, delayMillis);
    }

    public void l(View view, long durationMillis, long delayMillis) {
        a(view, new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f), durationMillis, delayMillis);
    }

    public void m(View view, long durationMillis, long delayMillis) {
        b(view, new TranslateAnimation(2, 0.0f, 2, 1.0f, 2, 0.0f, 2, 0.0f), durationMillis, delayMillis);
    }

    public void n(View view, long durationMillis, long delayMillis) {
        a(view, new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 2, 0.5f, 2, 0.5f), durationMillis, delayMillis);
    }

    public void o(View view, long durationMillis, long delayMillis) {
        b(view, new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 2, 0.5f, 2, 0.5f), durationMillis, delayMillis);
    }

    public void p(View view, long durationMillis, long delayMillis) {
        a(view, new RotateAnimation(-90.0f, 0.0f, 1, 0.0f, 1, 1.0f), durationMillis, delayMillis);
    }

    public void q(View view, long durationMillis, long delayMillis) {
        b(view, new RotateAnimation(0.0f, 90.0f, 1, 0.0f, 1, 1.0f), durationMillis, delayMillis);
    }

    public void r(View view, long durationMillis, long delayMillis) {
        ScaleAnimation animation1 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
        RotateAnimation animation2 = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        Animation animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        a(view, animation, durationMillis, delayMillis);
    }

    public void s(View view, long durationMillis, long delayMillis) {
        ScaleAnimation animation1 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        RotateAnimation animation2 = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        b(view, animation, durationMillis, delayMillis);
    }

    public void t(View view, long durationMillis, long delayMillis) {
        TranslateAnimation animation1 = new TranslateAnimation(2, 1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
        AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
        Animation animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        a(view, animation, durationMillis, delayMillis);
    }

    public void u(View view, long durationMillis, long delayMillis) {
        TranslateAnimation animation1 = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
        AlphaAnimation animation2 = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(animation1);
        animation.addAnimation(animation2);
        b(view, animation, durationMillis, delayMillis);
    }

    public void a(View view, long durationMillis, long delayMillis, View consultView, int type) {
        if (view != null && consultView != null) {
            TranslateAnimation animation;
            ScaleAnimation animation1;
            int[] viewXY = new int[2];
            view.getLocationOnScreen(viewXY);
            int[] consultViewXY = new int[2];
            consultView.getLocationOnScreen(consultViewXY);
            int locationX = Math.abs(viewXY[0] - (consultViewXY[0] + (consultView.getWidth() / 2)));
            int locationY = Math.abs(viewXY[1] - (consultViewXY[1] + (consultView.getHeight() / 2)));
            if (type == this.bjI) {
                animation = new TranslateAnimation((float) locationX, 0.0f, (float) locationY, 0.0f);
                animation1 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 0, (float) locationX, 0, (float) locationY);
            } else {
                animation = new TranslateAnimation(0.0f, (float) locationX, 0.0f, (float) locationY);
                animation1 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 0, (float) locationX, 0, (float) locationY);
                animation1.setAnimationListener(new b(this, view));
            }
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            Animation set = new AnimationSet(false);
            set.addAnimation(animation);
            set.addAnimation(animation1);
            a(view, set, durationMillis, delayMillis);
        }
    }

    public void a(View view, long durationMillis, long delayMillis, int position_x, int position_y, int width, AnimationListener listener) {
        if (view != null) {
        }
    }

    public void a(View view, long durationMillis) {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(durationMillis);
        view.startAnimation(animation);
    }

    public void b(View view, long durationMillis, long delayMillis, int position_x, int position_y, int width, AnimationListener listener) {
        if (view != null) {
        }
    }

    public void a(View view, long durationMillis, long delayMillis, View consultView, int type, OnClickListener click) {
        if (view != null && consultView != null) {
            TranslateAnimation animation;
            ScaleAnimation animation1;
            int[] viewXY = new int[2];
            view.getLocationOnScreen(viewXY);
            int[] consultViewXY = new int[2];
            consultView.getLocationOnScreen(consultViewXY);
            int locationX = Math.abs(viewXY[0] - (consultViewXY[0] + (consultView.getWidth() / 2)));
            int locationY = Math.abs(viewXY[1] - (consultViewXY[1] + (consultView.getHeight() / 2)));
            if (type == this.bjI) {
                animation = new TranslateAnimation((float) (-locationX), 0.0f, (float) locationY, 0.0f);
                animation1 = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 0, (float) (-locationX), 0, (float) locationY);
            } else {
                animation = new TranslateAnimation(0.0f, (float) (-locationX), 0.0f, (float) locationY);
                animation1 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 0, (float) (-locationX), 0, (float) locationY);
                animation1.setAnimationListener(new b(this, view));
                final OnClickListener onClickListener = click;
                final View view2 = consultView;
                animation.setAnimationListener(new AnimationListener(this) {
                    final /* synthetic */ a bjQ;

                    public void onAnimationStart(Animation arg0) {
                    }

                    public void onAnimationRepeat(Animation arg0) {
                    }

                    public void onAnimationEnd(Animation arg0) {
                        if (onClickListener != null) {
                            onClickListener.onClick(view2);
                        }
                    }
                });
            }
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            Animation set = new AnimationSet(false);
            set.addAnimation(animation);
            set.addAnimation(animation1);
            a(view, set, durationMillis, delayMillis);
        }
    }
}
