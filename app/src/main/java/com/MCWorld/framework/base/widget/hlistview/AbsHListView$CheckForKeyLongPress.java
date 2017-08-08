package com.MCWorld.framework.base.widget.hlistview;

import android.view.View;

class AbsHListView$CheckForKeyLongPress extends AbsHListView$WindowRunnnable implements Runnable {
    final /* synthetic */ AbsHListView this$0;

    private AbsHListView$CheckForKeyLongPress(AbsHListView absHListView) {
        this.this$0 = absHListView;
        super(absHListView);
    }

    public void run() {
        if (this.this$0.isPressed() && this.this$0.mSelectedPosition >= 0) {
            View v = this.this$0.getChildAt(this.this$0.mSelectedPosition - this.this$0.mFirstPosition);
            if (this.this$0.mDataChanged) {
                this.this$0.setPressed(false);
                if (v != null) {
                    v.setPressed(false);
                    return;
                }
                return;
            }
            boolean handled = false;
            if (sameWindow()) {
                handled = this.this$0.performLongPress(v, this.this$0.mSelectedPosition, this.this$0.mSelectedColId);
            }
            if (handled) {
                this.this$0.setPressed(false);
                v.setPressed(false);
            }
        }
    }
}
