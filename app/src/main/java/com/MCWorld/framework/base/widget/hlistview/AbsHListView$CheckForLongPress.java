package com.MCWorld.framework.base.widget.hlistview;

import android.view.View;

class AbsHListView$CheckForLongPress extends AbsHListView$WindowRunnnable implements Runnable {
    final /* synthetic */ AbsHListView this$0;

    private AbsHListView$CheckForLongPress(AbsHListView absHListView) {
        this.this$0 = absHListView;
        super(absHListView);
    }

    public void run() {
        View child = this.this$0.getChildAt(this.this$0.mMotionPosition - this.this$0.mFirstPosition);
        if (child != null) {
            int longPressPosition = this.this$0.mMotionPosition;
            long longPressId = this.this$0.mAdapter.getItemId(this.this$0.mMotionPosition);
            boolean handled = false;
            if (sameWindow() && !this.this$0.mDataChanged) {
                handled = this.this$0.performLongPress(child, longPressPosition, longPressId);
            }
            if (handled) {
                this.this$0.mTouchMode = -1;
                this.this$0.setPressed(false);
                child.setPressed(false);
                return;
            }
            this.this$0.mTouchMode = 2;
        }
    }
}
