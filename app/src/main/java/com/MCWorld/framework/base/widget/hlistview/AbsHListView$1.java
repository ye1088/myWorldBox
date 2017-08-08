package com.MCWorld.framework.base.widget.hlistview;

import android.view.View;

class AbsHListView$1 implements Runnable {
    final /* synthetic */ AbsHListView this$0;
    final /* synthetic */ View val$child;
    final /* synthetic */ AbsHListView$PerformClick val$performClick;

    AbsHListView$1(AbsHListView this$0, View view, AbsHListView$PerformClick absHListView$PerformClick) {
        this.this$0 = this$0;
        this.val$child = view;
        this.val$performClick = absHListView$PerformClick;
    }

    public void run() {
        this.this$0.mTouchMode = -1;
        this.val$child.setPressed(false);
        this.this$0.setPressed(false);
        if (!this.this$0.mDataChanged) {
            this.val$performClick.run();
        }
    }
}
