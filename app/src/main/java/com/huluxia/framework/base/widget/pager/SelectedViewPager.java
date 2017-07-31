package com.huluxia.framework.base.widget.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;

public class SelectedViewPager extends ViewPager {
    private PageChangeListenerWrapper mWrapper;

    public SelectedViewPager(Context context) {
        super(context);
    }

    public SelectedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(null);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mWrapper = new PageChangeListenerWrapper(this, listener);
        super.setOnPageChangeListener(this.mWrapper.getWrapperPageListener());
        if (getAdapter() == null || !(getAdapter() instanceof PagerSelectedAdapter)) {
            this.mWrapper.getWrapperPageListener().onPageSelected(getCurrentItem());
            this.mWrapper.getWrapperPageListener().onPageScrollStateChanged(0);
            return;
        }
        ((PagerSelectedAdapter) getAdapter()).setSelectedInitialize(true);
    }

    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (this.mWrapper != null) {
            this.mWrapper.getWrapperPageListener().onPageSelected(0);
            this.mWrapper.getWrapperPageListener().onPageScrollStateChanged(0);
        } else if (getAdapter() != null && (getAdapter() instanceof PagerSelectedAdapter)) {
            ((PagerSelectedAdapter) getAdapter()).setSelectedInitialize(true);
        }
    }
}
