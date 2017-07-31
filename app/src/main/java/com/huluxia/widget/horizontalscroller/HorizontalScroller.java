package com.huluxia.widget.horizontalscroller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class HorizontalScroller extends HorizontalScrollView {
    private LinearLayout aVl;

    public HorizontalScroller(Context context) {
        super(context);
        bH(context);
    }

    public HorizontalScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        bH(context);
    }

    public HorizontalScroller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        bH(context);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        removeAllViews();
        bH(getContext());
    }

    private void bH(Context context) {
        this.aVl = new LinearLayout(context);
        this.aVl.setOrientation(0);
        addView(this.aVl, new LayoutParams(-1, -1));
    }

    public void setAdapter(BaseAdapter adapter) {
        this.aVl.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            this.aVl.addView(adapter.getView(i, null, null));
        }
    }
}
