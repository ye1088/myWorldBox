package com.MCWorld.framework.base.widget.hlistview;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.ViewConfiguration;

final class AbsHListView$CheckForTap implements Runnable {
    final /* synthetic */ AbsHListView this$0;

    AbsHListView$CheckForTap(AbsHListView this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (this.this$0.mTouchMode == 0) {
            this.this$0.mTouchMode = 1;
            View child = this.this$0.getChildAt(this.this$0.mMotionPosition - this.this$0.mFirstPosition);
            if (child != null && !child.hasFocusable()) {
                this.this$0.mLayoutMode = 0;
                if (this.this$0.mDataChanged) {
                    this.this$0.mTouchMode = 2;
                    return;
                }
                child.setPressed(true);
                this.this$0.setPressed(true);
                this.this$0.layoutChildren();
                this.this$0.positionSelector(this.this$0.mMotionPosition, child);
                this.this$0.refreshDrawableState();
                int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                boolean longClickable = this.this$0.isLongClickable();
                if (this.this$0.mSelector != null) {
                    Drawable d = this.this$0.mSelector.getCurrent();
                    if (d != null && (d instanceof TransitionDrawable)) {
                        if (longClickable) {
                            ((TransitionDrawable) d).startTransition(longPressTimeout);
                        } else {
                            ((TransitionDrawable) d).resetTransition();
                        }
                    }
                }
                if (longClickable) {
                    if (AbsHListView.access$500(this.this$0) == null) {
                        AbsHListView.access$502(this.this$0, new AbsHListView$CheckForLongPress(this.this$0));
                    }
                    AbsHListView.access$500(this.this$0).rememberWindowAttachCount();
                    this.this$0.postDelayed(AbsHListView.access$500(this.this$0), (long) longPressTimeout);
                    return;
                }
                this.this$0.mTouchMode = 2;
            }
        }
    }
}
