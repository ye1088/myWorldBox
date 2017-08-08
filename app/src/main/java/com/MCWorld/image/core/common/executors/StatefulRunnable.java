package com.MCWorld.image.core.common.executors;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class StatefulRunnable<T> implements Runnable {
    protected static final int STATE_CREATED = 0;
    protected static final int STATE_FAILED = 4;
    protected static final int STATE_STARTED = 1;
    protected static final int xZ = 2;
    protected static final int ya = 3;
    protected final AtomicInteger yb = new AtomicInteger(0);

    protected abstract T getResult() throws Exception;

    public final void run() {
        if (this.yb.compareAndSet(0, 1)) {
            try {
                T result = getResult();
                this.yb.set(3);
                try {
                    o(result);
                } finally {
                    p(result);
                }
            } catch (Exception e) {
                this.yb.set(4);
                i(e);
            }
        }
    }

    public void cancel() {
        if (this.yb.compareAndSet(0, 2)) {
            iq();
        }
    }

    protected void o(T t) {
    }

    protected void i(Exception e) {
    }

    protected void iq() {
    }

    protected void p(T t) {
    }
}
