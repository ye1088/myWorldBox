package com.huluxia.framework.base.widget.hlistview;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.widget.ListAdapter;

@TargetApi(14)
class AbsHListView$ListItemAccessibilityDelegate extends AccessibilityDelegateCompat {
    final /* synthetic */ AbsHListView this$0;

    AbsHListView$ListItemAccessibilityDelegate(AbsHListView this$0) {
        this.this$0 = this$0;
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        int position = this.this$0.getPositionForView(host);
        ListAdapter adapter = (ListAdapter) this.this$0.getAdapter();
        if (position != -1 && adapter != null && this.this$0.isEnabled() && adapter.isEnabled(position)) {
            if (position == this.this$0.getSelectedItemPosition()) {
                info.setSelected(true);
                info.addAction(8);
            } else {
                info.addAction(4);
            }
            if (this.this$0.isClickable()) {
                info.addAction(16);
                info.setClickable(true);
            }
            if (this.this$0.isLongClickable()) {
                info.addAction(32);
                info.setLongClickable(true);
            }
        }
    }

    public boolean performAccessibilityAction(View host, int action, Bundle arguments) {
        if (super.performAccessibilityAction(host, action, arguments)) {
            return true;
        }
        int position = this.this$0.getPositionForView(host);
        ListAdapter adapter = (ListAdapter) this.this$0.getAdapter();
        if (position == -1 || adapter == null) {
            return false;
        }
        if (!this.this$0.isEnabled() || !adapter.isEnabled(position)) {
            return false;
        }
        long id = this.this$0.getItemIdAtPosition(position);
        switch (action) {
            case 4:
                if (this.this$0.getSelectedItemPosition() == position) {
                    return false;
                }
                this.this$0.setSelection(position);
                return true;
            case 8:
                if (this.this$0.getSelectedItemPosition() != position) {
                    return false;
                }
                this.this$0.setSelection(-1);
                return true;
            case 16:
                return this.this$0.isClickable() ? this.this$0.performItemClick(host, position, id) : false;
            case 32:
                return this.this$0.isLongClickable() ? this.this$0.performLongPress(host, position, id) : false;
            default:
                return false;
        }
    }
}
