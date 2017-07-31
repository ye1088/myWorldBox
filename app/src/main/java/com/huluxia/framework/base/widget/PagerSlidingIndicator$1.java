package com.huluxia.framework.base.widget;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class PagerSlidingIndicator$1 implements OnGlobalLayoutListener {
    final /* synthetic */ PagerSlidingIndicator this$0;

    PagerSlidingIndicator$1(PagerSlidingIndicator this$0) {
        this.this$0 = this$0;
    }

    @SuppressLint({"NewApi"})
    public void onGlobalLayout() {
        if (VERSION.SDK_INT < 16) {
            this.this$0.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            this.this$0.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        PagerSlidingIndicator.access$102(this.this$0, this.this$0.pager.getCurrentItem());
        PagerSlidingIndicator.access$200(this.this$0, PagerSlidingIndicator.access$100(this.this$0), 0);
        PagerSlidingIndicator.access$300(this.this$0, PagerSlidingIndicator.access$100(this.this$0));
    }
}
