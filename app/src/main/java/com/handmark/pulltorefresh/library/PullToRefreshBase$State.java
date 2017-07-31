package com.handmark.pulltorefresh.library;

public enum PullToRefreshBase$State {
    RESET(0),
    PULL_TO_REFRESH(1),
    RELEASE_TO_REFRESH(2),
    REFRESHING(8),
    MANUAL_REFRESHING(9),
    OVERSCROLLING(16);
    
    private int mIntValue;

    static PullToRefreshBase$State mapIntToValue(int stateInt) {
        for (PullToRefreshBase$State value : values()) {
            if (stateInt == value.getIntValue()) {
                return value;
            }
        }
        return RESET;
    }

    private PullToRefreshBase$State(int intValue) {
        this.mIntValue = intValue;
    }

    int getIntValue() {
        return this.mIntValue;
    }
}
