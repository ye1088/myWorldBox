package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;

protected class PullToRefreshListView$InternalListView extends ListView implements EmptyViewMethodAccessor {
    private boolean mAddedLvFooter = false;
    final /* synthetic */ PullToRefreshListView this$0;

    public PullToRefreshListView$InternalListView(PullToRefreshListView this$0, Context context, AttributeSet attrs) {
        this.this$0 = this$0;
        super(context, attrs);
    }

    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setAdapter(ListAdapter adapter) {
        if (!(PullToRefreshListView.access$000(this.this$0) == null || this.mAddedLvFooter)) {
            addFooterView(PullToRefreshListView.access$000(this.this$0), null, false);
            this.mAddedLvFooter = true;
        }
        super.setAdapter(adapter);
    }

    public void setEmptyView(View emptyView) {
        this.this$0.setEmptyView(emptyView);
    }

    public void setEmptyViewInternal(View emptyView) {
        super.setEmptyView(emptyView);
    }
}
