package com.MCWorld.widget.viewpager;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.List;

public class AutoViewPagerAdapter extends PagerAdapter {
    public List<View> aOg;
    private int bFA = 0;
    private int bFB = 0;
    private boolean bFC = true;
    private ViewPager bfV;
    private Handler handler = new Handler(this) {
        final /* synthetic */ AutoViewPagerAdapter bFD;

        {
            this.bFD = this$0;
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    this.bFD.PY();
                    this.bFD.bM(this.bFD.interval);
                    return;
                default:
                    return;
            }
        }
    };
    private long interval = 3000;
    private int size = 0;

    public AutoViewPagerAdapter(List<View> mListViews, ViewPager vp, int size, long interval) {
        this.aOg = mListViews;
        this.bfV = vp;
        if (size == 0) {
            size = 1;
        }
        this.size = size;
        this.interval = interval;
    }

    public void destroyItem(View arg0, int arg1, Object arg2) {
        if (this.aOg != null && this.aOg.size() > arg1 && this.aOg.get(arg1) != null) {
            ((InnerViewPager) arg0).removeView((View) this.aOg.get(arg1));
        }
    }

    public int getCount() {
        return this.aOg.size();
    }

    public Object instantiateItem(View arg0, int arg1) {
        ((InnerViewPager) arg0).addView((View) this.aOg.get(arg1), 0);
        return this.aOg.get(arg1);
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void b(List<View> mListViews, int size) {
        this.aOg.clear();
        this.aOg.addAll(mListViews);
        if (size == 0) {
            size = 1;
        }
        this.size = size;
        this.bFC = true;
        this.bFA = 0;
    }

    public void bM(long delayTimeInMills) {
        this.handler.removeMessages(0);
        this.handler.sendEmptyMessageDelayed(0, delayTimeInMills);
    }

    private void PY() {
        if (this.bFC) {
            if (this.size != 0) {
                this.bFA = (this.bFA + 1) % this.size;
            }
            this.bfV.setCurrentItem(this.bFA);
        }
    }

    public int getCurrentItem() {
        return this.bFA;
    }

    public void setCurrentItem(int currentItem) {
        this.bFA = currentItem;
    }

    public boolean PZ() {
        return this.bFC;
    }

    public void f(boolean autoScroll, boolean reset) {
        this.bFC = autoScroll;
        if (this.bFC && reset) {
            bM(this.interval);
        }
    }
}
