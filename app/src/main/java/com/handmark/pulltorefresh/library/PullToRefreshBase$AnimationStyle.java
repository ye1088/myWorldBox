package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.content.res.TypedArray;
import com.handmark.pulltorefresh.library.internal.FlipLoadingLayout;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.handmark.pulltorefresh.library.internal.RotateLoadingLayout;

public enum PullToRefreshBase$AnimationStyle {
    ROTATE,
    FLIP;

    static PullToRefreshBase$AnimationStyle getDefault() {
        return ROTATE;
    }

    static PullToRefreshBase$AnimationStyle mapIntToValue(int modeInt) {
        switch (modeInt) {
            case 1:
                return FLIP;
            default:
                return ROTATE;
        }
    }

    LoadingLayout createLoadingLayout(Context context, PullToRefreshBase$Mode mode, PullToRefreshBase$Orientation scrollDirection, TypedArray attrs) {
        switch (this) {
            case FLIP:
                return new FlipLoadingLayout(context, mode, scrollDirection, attrs);
            default:
                return new RotateLoadingLayout(context, mode, scrollDirection, attrs);
        }
    }
}
