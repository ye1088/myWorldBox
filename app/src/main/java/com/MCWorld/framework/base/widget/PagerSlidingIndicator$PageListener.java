package com.MCWorld.framework.base.widget;

import android.support.v4.view.ViewPager.OnPageChangeListener;

class PagerSlidingIndicator$PageListener implements OnPageChangeListener {
    final /* synthetic */ PagerSlidingIndicator this$0;

    private PagerSlidingIndicator$PageListener(PagerSlidingIndicator pagerSlidingIndicator) {
        this.this$0 = pagerSlidingIndicator;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        PagerSlidingIndicator.access$102(this.this$0, position);
        PagerSlidingIndicator.access$402(this.this$0, positionOffset);
        PagerSlidingIndicator.access$200(this.this$0, position, (int) (((float) PagerSlidingIndicator.access$500(this.this$0).getChildAt(position).getWidth()) * positionOffset));
        this.this$0.invalidate();
        if (this.this$0.delegatePageListener != null) {
            this.this$0.delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            PagerSlidingIndicator.access$200(this.this$0, this.this$0.pager.getCurrentItem(), 0);
        }
        if (this.this$0.delegatePageListener != null) {
            this.this$0.delegatePageListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageSelected(int position) {
        if (this.this$0.delegatePageListener != null) {
            this.this$0.delegatePageListener.onPageSelected(position);
        }
        PagerSlidingIndicator.access$300(this.this$0, position);
    }
}
