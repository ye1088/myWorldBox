package com.MCWorld.widget.pulltorefresh;

/* synthetic */ class PullToRefreshListView$1 {
    static final /* synthetic */ int[] bEL = new int[PullToRefreshState.values().length];

    static {
        try {
            bEL[PullToRefreshState.REFRESHING.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            bEL[PullToRefreshState.RELEASE_To_REFRESH.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            bEL[PullToRefreshState.PULL_To_REFRESH.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            bEL[PullToRefreshState.DONE.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
    }
}
