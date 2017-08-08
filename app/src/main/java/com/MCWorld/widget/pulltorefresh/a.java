package com.MCWorld.widget.pulltorefresh;

/* compiled from: ILoadingLayout */
public interface a {
    int getContentHeight();

    int getScroll();

    void pullToRefreshImpl();

    void refreshingImpl();

    void releaseToRefreshImpl();

    void resetImpl();

    void setScroll(int i);
}
