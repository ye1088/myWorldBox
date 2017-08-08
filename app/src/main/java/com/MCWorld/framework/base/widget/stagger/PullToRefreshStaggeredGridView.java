package com.MCWorld.framework.base.widget.stagger;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase$AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase$Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase$Orientation;
import com.MCWorld.framework.R;
import com.MCWorld.framework.R$dimen;

public class PullToRefreshStaggeredGridView extends PullToRefreshBase<StaggeredGridView> {

    @TargetApi(9)
    final class InternalStaggeredGridViewSDK9 extends StaggeredGridView {
        public InternalStaggeredGridViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshStaggeredGridView.this, deltaX, scrollX, deltaY, scrollY, getScrollRange(), isTouchEvent);
            return returnValue;
        }

        private int getScrollRange() {
            if (getChildCount() > 0) {
                return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
            }
            return 0;
        }
    }

    public PullToRefreshStaggeredGridView(Context context) {
        super(context);
    }

    public PullToRefreshStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshStaggeredGridView(Context context, PullToRefreshBase$Mode mode) {
        super(context, mode);
    }

    public PullToRefreshStaggeredGridView(Context context, PullToRefreshBase$Mode mode, PullToRefreshBase$AnimationStyle style) {
        super(context, mode, style);
    }

    public PullToRefreshBase$Orientation getPullToRefreshScrollDirection() {
        return PullToRefreshBase$Orientation.VERTICAL;
    }

    @TargetApi(9)
    protected StaggeredGridView createRefreshableView(Context context, AttributeSet attrs) {
        StaggeredGridView stgv;
        if (VERSION.SDK_INT >= 9) {
            stgv = new InternalStaggeredGridViewSDK9(context, attrs);
        } else {
            stgv = new StaggeredGridView(context, attrs);
        }
        int margin = getResources().getDimensionPixelSize(R$dimen.stgv_margin);
        stgv.setColumnCount(2);
        stgv.setId(R.id.stgv);
        return stgv;
    }

    protected boolean isReadyForPullStart() {
        return ((StaggeredGridView) this.mRefreshableView).isTop();
    }

    protected boolean isReadyForPullEnd() {
        return false;
    }

    @TargetApi(11)
    public void setAdapter(HeaderViewListAdapter adapter) {
        if (VERSION.SDK_INT >= 11) {
            ((StaggeredGridView) this.mRefreshableView).setAdapter((ListAdapter) adapter);
        }
    }
}
