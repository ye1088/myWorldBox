package com.huluxia.framework.base.widget.hlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;

public class AbsHListView$LayoutParams extends LayoutParams {
    public boolean forceAdd;
    public long itemId = -1;
    public boolean recycledHeaderFooter;
    public int scrappedFromPosition;
    public int viewType;

    public AbsHListView$LayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public AbsHListView$LayoutParams(int w, int h) {
        super(w, h);
    }

    public AbsHListView$LayoutParams(int w, int h, int viewType) {
        super(w, h);
        this.viewType = viewType;
    }

    public AbsHListView$LayoutParams(LayoutParams source) {
        super(source);
    }
}
