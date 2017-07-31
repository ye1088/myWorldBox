package com.huluxia.framework.base.widget.hlistview;

import android.view.View;
import android.widget.ListAdapter;

class AbsHListView$PerformClick extends AbsHListView$WindowRunnnable implements Runnable {
    int mClickMotionPosition;
    final /* synthetic */ AbsHListView this$0;

    private AbsHListView$PerformClick(AbsHListView absHListView) {
        this.this$0 = absHListView;
        super(absHListView);
    }

    public void run() {
        if (!this.this$0.mDataChanged) {
            ListAdapter adapter = this.this$0.mAdapter;
            int motionPosition = this.mClickMotionPosition;
            if (adapter != null && this.this$0.mItemCount > 0 && motionPosition != -1 && motionPosition < adapter.getCount() && sameWindow()) {
                View view = this.this$0.getChildAt(motionPosition - this.this$0.mFirstPosition);
                if (view != null) {
                    this.this$0.performItemClick(view, motionPosition, adapter.getItemId(motionPosition));
                }
            }
        }
    }
}
