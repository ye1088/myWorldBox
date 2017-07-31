package com.tencent.mm.sdk.event;

public abstract class IListener {
    private final int priority;

    public IListener(int i) {
        this.priority = i;
    }

    public abstract boolean callback(IEvent iEvent);

    public int getPriority() {
        return this.priority;
    }
}
