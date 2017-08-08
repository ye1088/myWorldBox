package com.MCWorld.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class ScaleHeadPullToRefreshListView extends PullToRefreshListView {
    static final int len = 200;
    float bDg;
    float bDh;
    View bEN;
    View bEO;
    b bEP;
    int bEQ;
    int bER;
    int bES;
    int bET;
    boolean bEU;
    int bEV = 0;
    int bEW = 0;
    int left;
    private Scroller mScroller;
    int top;

    public ScaleHeadPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mScroller = new Scroller(context);
        this.bEO = (View) this.bEv;
    }

    public void b(View bgView, int scale_h, int origin_h) {
        this.bEN = bgView;
        this.bEV = scale_h;
        this.bEW = origin_h;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        int action = ev.getAction();
        if (!this.mScroller.isFinished()) {
            return super.onTouchEvent(ev);
        }
        if (this.bEO == null) {
            return super.onTouchEvent(ev);
        }
        switch (action) {
            case 0:
                if (this.bEN != null) {
                    this.left = this.bEN.getLeft();
                    this.top = this.bEN.getBottom();
                    this.bES = getWidth();
                    this.bET = getHeight();
                    this.bEQ = this.bEN.getHeight();
                    this.bDg = x;
                    this.bDh = y;
                    this.bEP = new b(this.bEN.getLeft(), this.bEN.getBottom(), this.bEN.getLeft(), this.bEN.getBottom() + 200);
                    break;
                }
                break;
            case 1:
                if (this.bEN != null) {
                    this.bEU = true;
                    this.bEN.setLayoutParams(new LayoutParams(this.bEN.getWidth(), this.bEW));
                    invalidate();
                    break;
                }
                break;
            case 2:
                if (this.bEN != null && this.bEO.getTop() >= 0) {
                    if (this.bEP != null) {
                        int t = (int) (y - this.bDh);
                        if (t > 0 && this.bEV > this.bEQ) {
                            this.bEQ += t;
                        }
                        this.bEQ = this.bEQ > this.bEV ? this.bEV : this.bEQ;
                        this.bEN.setLayoutParams(new LayoutParams(this.bEN.getWidth(), this.bEQ));
                    }
                    this.bEU = false;
                    break;
                }
        }
        return super.dispatchTouchEvent(ev);
    }
}
