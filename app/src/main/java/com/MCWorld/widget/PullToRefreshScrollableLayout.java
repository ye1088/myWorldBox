package com.MCWorld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.MCWorld.framework.R;
import ru.noties.scrollable.ScrollableLayout;
import ru.noties.scrollable.b;

public class PullToRefreshScrollableLayout extends PullToRefreshBase<ScrollableLayout> {
    protected /* synthetic */ View createRefreshableView(Context context, AttributeSet attributeSet) {
        return d(context, attributeSet);
    }

    public PullToRefreshScrollableLayout(Context context) {
        super(context);
    }

    public PullToRefreshScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshScrollableLayout(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshScrollableLayout(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected ScrollableLayout d(Context context, AttributeSet attrs) {
        ScrollableLayout scrollView = new ScrollableLayout(context, attrs);
        scrollView.setId(R.id.scrollview);
        return scrollView;
    }

    protected boolean isReadyForPullStart() {
        b canScrollVerticallyDelegate = ((ScrollableLayout) this.mRefreshableView).getCanScrollVerticallyDelegate();
        boolean canScrollVertically = false;
        if (canScrollVerticallyDelegate != null) {
            canScrollVertically = canScrollVerticallyDelegate.canScrollVertically(-1);
        }
        return ((ScrollableLayout) this.mRefreshableView).getScrollY() == 0 && !canScrollVertically;
    }

    protected boolean isReadyForPullEnd() {
        View scrollViewChild = ((ScrollableLayout) this.mRefreshableView).getChildAt(0);
        if (scrollViewChild == null) {
            return false;
        }
        if (((ScrollableLayout) this.mRefreshableView).getScrollY() >= scrollViewChild.getHeight() - getHeight()) {
            return true;
        }
        return false;
    }
}
