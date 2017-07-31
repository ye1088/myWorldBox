package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

@TargetApi(9)
final class PullToRefreshListView$InternalListViewSDK9 extends PullToRefreshListView$InternalListView {
    final /* synthetic */ PullToRefreshListView this$0;

    public PullToRefreshListView$InternalListViewSDK9(PullToRefreshListView this$0, Context context, AttributeSet attrs) {
        this.this$0 = this$0;
        super(this$0, context, attrs);
    }

    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        OverscrollHelper.overScrollBy(this.this$0, deltaX, scrollX, deltaY, scrollY, isTouchEvent);
        return returnValue;
    }
}
