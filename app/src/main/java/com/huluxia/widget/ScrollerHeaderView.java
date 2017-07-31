package com.huluxia.widget;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import com.huluxia.framework.base.log.HLog;

public class ScrollerHeaderView extends RelativeLayout {
    private static final String TAG = "ScrollerHeaderView";
    private OverScroller bul;
    private int bum = 0;
    private int bun = Integer.MAX_VALUE;
    private int buo;
    private int mTouchSlop;

    public ScrollerHeaderView(Context context) {
        super(context);
        init(context);
    }

    public ScrollerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.bul = new OverScroller(context, new DecelerateInterpolator());
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.bul.computeScrollOffset()) {
            scrollTo(this.bul.getCurrX(), this.bul.getCurrY());
            invalidate();
        }
    }

    public void lM(int deltaY) {
        HLog.debug(TAG, "scrollByDeltaY " + deltaY, new Object[0]);
        int startY = this.buo + deltaY;
        if (startY > this.bun - this.bum) {
            if (this.buo < this.bun - this.bum) {
                deltaY = (this.bun - this.bum) - this.buo;
                startY = this.buo + deltaY;
            } else {
                return;
            }
        }
        if (startY < 0) {
            if (this.buo > 0) {
                deltaY = 0 - this.buo;
                startY = 0;
            } else {
                return;
            }
        }
        this.bul.startScroll(0, this.buo, 0, deltaY);
        this.buo = startY;
        invalidate();
    }

    public void setHeaderHeight(int headerHeight) {
        this.bun = headerHeight;
    }

    public int getHeaderHeight() {
        return this.bun;
    }

    public void setTrickHeight(int trickHeight) {
        this.bum = trickHeight;
    }
}
