package com.huluxia.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.huluxia.bbs.b.f;

public class ViewPageIndicator extends LinearLayout {
    private final PageListener bFI = new PageListener();
    private AutoViewPagerAdapter bFJ = null;
    private int count;
    private int current;
    private ViewPager pager;

    private class PageListener implements OnPageChangeListener {
        final /* synthetic */ ViewPageIndicator bFK;

        private PageListener(ViewPageIndicator viewPageIndicator) {
            this.bFK = viewPageIndicator;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (this.bFK.bFJ != null) {
                this.bFK.bFJ.f(true, true);
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                this.bFK.setCurrent(this.bFK.pager.getCurrentItem());
            }
            if (this.bFK.bFJ == null) {
                return;
            }
            if (state == 0 || state == 2) {
                this.bFK.bFJ.f(true, true);
            }
        }

        public void onPageSelected(int position) {
            if (this.bFK.bFJ != null) {
                this.bFK.bFJ.setCurrentItem(position);
                this.bFK.bFJ.f(true, true);
            }
        }
    }

    public ViewPageIndicator(Context context) {
        super(context);
    }

    public ViewPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        this.count = this.pager.getAdapter().getCount();
        for (int i = 0; i < this.count; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(f.face_indicator);
            LayoutParams lp = new LayoutParams(-2, -2);
            lp.setMargins(0, 0, 10, 0);
            iv.setLayoutParams(lp);
            addView(iv);
        }
        this.current = 0;
        setCurrent(this.pager.getCurrentItem());
    }

    private void setCurrent(int current) {
        if (current < getChildCount()) {
            ((ImageView) getChildAt(this.current)).setImageResource(f.face_indicator);
            this.current = current;
            ((ImageView) getChildAt(this.current)).setImageResource(f.face_indicator_current);
        }
    }

    public ViewPager getViewPager() {
        return this.pager;
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        if (pager.getAdapter() instanceof AutoViewPagerAdapter) {
            this.bFJ = (AutoViewPagerAdapter) pager.getAdapter();
        }
        pager.setOnPageChangeListener(this.bFI);
        notifyDataSetChanged();
    }
}
