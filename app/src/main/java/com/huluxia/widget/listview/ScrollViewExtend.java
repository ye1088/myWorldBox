package com.huluxia.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollViewExtend extends ScrollView {
    private float bAb;
    private float bAc;
    private float bAd;
    private float bAe;
    public int factor = 1;

    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                this.bAc = 0.0f;
                this.bAb = 0.0f;
                this.bAd = ev.getX();
                this.bAe = ev.getY();
                break;
            case 2:
                float curX = ev.getX();
                float curY = ev.getY();
                this.bAb += Math.abs(curX - this.bAd);
                this.bAc += Math.abs(curY - this.bAe);
                if (this.bAb * ((float) this.factor) <= this.bAc) {
                    this.bAd = curX;
                    this.bAe = curY;
                    break;
                }
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
