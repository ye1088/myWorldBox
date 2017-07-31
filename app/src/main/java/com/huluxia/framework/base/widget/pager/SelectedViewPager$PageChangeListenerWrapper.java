package com.huluxia.framework.base.widget.pager;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.lang.ref.WeakReference;
import java.util.List;

class SelectedViewPager$PageChangeListenerWrapper {
    private final OnPageChangeListener mPageListener;
    private final WeakReference<SelectedViewPager> mPager;
    private OnPageChangeListener mWrapperPageListener = new OnPageChangeListener() {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (SelectedViewPager$PageChangeListenerWrapper.this.mPageListener != null) {
                SelectedViewPager$PageChangeListenerWrapper.this.mPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        public void onPageSelected(int position) {
            if (SelectedViewPager$PageChangeListenerWrapper.this.mPageListener != null) {
                SelectedViewPager$PageChangeListenerWrapper.this.mPageListener.onPageSelected(position);
            }
            SelectedViewPager pager = (SelectedViewPager) SelectedViewPager$PageChangeListenerWrapper.this.mPager.get();
            if (pager != null && pager.getAdapter() != null && (pager.getAdapter() instanceof PagerSelectedAdapter)) {
                PagerSelectedAdapter adapter = (PagerSelectedAdapter) pager.getAdapter();
                PagerFragment fragment = adapter.getPosFragment(position);
                if (fragment != null) {
                    fragment.onSelected(position);
                }
                List<PagerFragment> fragmentList = adapter.excludePosFragment(position);
                if (!UtilsFunction.empty(fragmentList)) {
                    for (PagerFragment item : fragmentList) {
                        if (item != null) {
                            item.onUnSelected(adapter.indexOfFragment(item));
                        }
                    }
                }
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                SelectedViewPager pager = (SelectedViewPager) SelectedViewPager$PageChangeListenerWrapper.this.mPager.get();
                if (!(pager == null || pager.getAdapter() == null || !(pager.getAdapter() instanceof PagerSelectedAdapter))) {
                    PagerSelectedAdapter adapter = (PagerSelectedAdapter) pager.getAdapter();
                    int position = pager.getCurrentItem();
                    PagerFragment fragment = adapter.getPosFragment(position);
                    if (fragment != null) {
                        fragment.onPageScrollComplete(position);
                    }
                }
            }
            if (SelectedViewPager$PageChangeListenerWrapper.this.mPageListener != null) {
                SelectedViewPager$PageChangeListenerWrapper.this.mPageListener.onPageScrollStateChanged(state);
            }
        }
    };

    public SelectedViewPager$PageChangeListenerWrapper(SelectedViewPager viewPager, OnPageChangeListener listener) {
        this.mPager = new WeakReference(viewPager);
        this.mPageListener = listener;
    }

    public OnPageChangeListener getWrapperPageListener() {
        return this.mWrapperPageListener;
    }
}
