package com.handmark.pulltorefresh.library;

public enum PullToRefreshBase$Mode {
    DISABLED(0),
    PULL_FROM_START(1),
    PULL_FROM_END(2),
    BOTH(3),
    MANUAL_REFRESH_ONLY(4);
    
    public static PullToRefreshBase$Mode PULL_DOWN_TO_REFRESH;
    public static PullToRefreshBase$Mode PULL_UP_TO_REFRESH;
    private int mIntValue;

    static {
        PULL_DOWN_TO_REFRESH = PULL_FROM_START;
        PULL_UP_TO_REFRESH = PULL_FROM_END;
    }

    static PullToRefreshBase$Mode mapIntToValue(int modeInt) {
        for (PullToRefreshBase$Mode value : values()) {
            if (modeInt == value.getIntValue()) {
                return value;
            }
        }
        return getDefault();
    }

    static PullToRefreshBase$Mode getDefault() {
        return PULL_FROM_START;
    }

    private PullToRefreshBase$Mode(int modeInt) {
        this.mIntValue = modeInt;
    }

    boolean permitsPullToRefresh() {
        return (this == DISABLED || this == MANUAL_REFRESH_ONLY) ? false : true;
    }

    public boolean showHeaderLoadingLayout() {
        return this == PULL_FROM_START || this == BOTH;
    }

    public boolean showFooterLoadingLayout() {
        return this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY;
    }

    int getIntValue() {
        return this.mIntValue;
    }
}
