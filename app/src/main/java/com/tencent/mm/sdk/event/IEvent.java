package com.tencent.mm.sdk.event;

public interface IEvent {
    public static final int EVENT_SCOPE_APPLICATION = 2;
    public static final int EVENT_SCOPE_SESSION = 1;
    public static final int EVENT_SCOPE_TEMPL = 0;

    String getId();

    String getScope();

    boolean isOrder();

    void oncomplete();
}
