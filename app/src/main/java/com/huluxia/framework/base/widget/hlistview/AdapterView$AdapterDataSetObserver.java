package com.huluxia.framework.base.widget.hlistview;

import android.database.DataSetObserver;
import android.os.Parcelable;

class AdapterView$AdapterDataSetObserver extends DataSetObserver {
    private Parcelable mInstanceState = null;
    final /* synthetic */ AdapterView this$0;

    AdapterView$AdapterDataSetObserver(AdapterView this$0) {
        this.this$0 = this$0;
    }

    public void onChanged() {
        this.this$0.mDataChanged = true;
        this.this$0.mOldItemCount = this.this$0.mItemCount;
        this.this$0.mItemCount = this.this$0.getAdapter().getCount();
        if (!this.this$0.getAdapter().hasStableIds() || this.mInstanceState == null || this.this$0.mOldItemCount != 0 || this.this$0.mItemCount <= 0) {
            this.this$0.rememberSyncState();
        } else {
            AdapterView.access$000(this.this$0, this.mInstanceState);
            this.mInstanceState = null;
        }
        this.this$0.checkFocus();
        this.this$0.requestLayout();
    }

    public void onInvalidated() {
        this.this$0.mDataChanged = true;
        if (this.this$0.getAdapter().hasStableIds()) {
            this.mInstanceState = AdapterView.access$100(this.this$0);
        }
        this.this$0.mOldItemCount = this.this$0.mItemCount;
        this.this$0.mItemCount = 0;
        this.this$0.mSelectedPosition = -1;
        this.this$0.mSelectedColId = Long.MIN_VALUE;
        this.this$0.mNextSelectedPosition = -1;
        this.this$0.mNextSelectedColId = Long.MIN_VALUE;
        this.this$0.mNeedSync = false;
        this.this$0.checkFocus();
        this.this$0.requestLayout();
    }

    public void clearSavedState() {
        this.mInstanceState = null;
    }
}
