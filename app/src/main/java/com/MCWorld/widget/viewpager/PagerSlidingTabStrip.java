package com.MCWorld.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import com.MCWorld.bbs.b;
import com.MCWorld.framework.base.widget.PagerSlidingIndicator;
import com.simple.colorful.a.a;
import com.simple.colorful.c;
import com.simple.colorful.d;

public class PagerSlidingTabStrip extends PagerSlidingIndicator implements c {
    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public a b(a builder) {
        return builder;
    }

    public void FG() {
        setTextColorResource(d.p(getContext(), 16842808));
        setIndicatorColorResource(d.p(getContext(), b.c.textColorGreen));
        setViewPager(this.pager);
    }
}
