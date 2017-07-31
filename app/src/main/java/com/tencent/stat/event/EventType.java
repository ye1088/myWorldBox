package com.tencent.stat.event;

import com.tencent.smtt.sdk.TbsListener.ErrorCode;

public enum EventType {
    PAGE_VIEW(1),
    SESSION_ENV(2),
    ERROR(3),
    CUSTOM(1000),
    ADDITION(ErrorCode.ERROR_HOSTAPP_UNAVAILABLE),
    MONITOR_STAT(ErrorCode.ERROR_FORCE_SYSWEBVIEW);
    
    private int v;

    private EventType(int i) {
        this.v = i;
    }

    public int GetIntValue() {
        return this.v;
    }
}
