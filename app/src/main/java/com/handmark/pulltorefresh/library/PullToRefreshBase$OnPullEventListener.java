package com.handmark.pulltorefresh.library;

import android.view.View;

public interface PullToRefreshBase$OnPullEventListener<V extends View> {
    void onPullEvent(PullToRefreshBase<V> pullToRefreshBase, PullToRefreshBase$State pullToRefreshBase$State, PullToRefreshBase$Mode pullToRefreshBase$Mode);
}
