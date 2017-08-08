package com.MCWorld.framework.base.widget.hlistview;

public interface AbsHListView$OnScrollListener {
    public static final int SCROLL_STATE_FLING = 2;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

    void onScroll(AbsHListView absHListView, int i, int i2, int i3);

    void onScrollStateChanged(AbsHListView absHListView, int i);
}
