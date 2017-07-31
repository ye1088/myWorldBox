package com.tencent.mm.sdk.event;

import android.os.Looper;

public final class EventPoolFactory {
    public static IEventPool impl = null;

    public interface IEventPool {
        boolean add(String str, IListener iListener);

        void asyncPublish(IEvent iEvent);

        void asyncPublish(IEvent iEvent, Looper looper);

        void publish(IEvent iEvent);

        void release(int i);

        boolean remove(String str, IListener iListener);
    }

    public static final IEventPool getImpl() {
        return impl;
    }

    public static final void setImpl(IEventPool iEventPool) {
        impl = iEventPool;
    }
}
