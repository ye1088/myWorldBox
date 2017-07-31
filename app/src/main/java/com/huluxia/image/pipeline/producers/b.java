package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.log.HLog;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: BaseConsumer */
public abstract class b<T> implements j<T> {
    private boolean IZ = false;

    protected abstract void d(T t, boolean z);

    protected abstract void h(Throwable th);

    protected abstract void ns();

    public synchronized void e(@Nullable T newResult, boolean isLast) {
        if (!this.IZ) {
            this.IZ = isLast;
            try {
                d(newResult, isLast);
            } catch (Exception e) {
                j(e);
            }
        }
    }

    public synchronized void j(Throwable t) {
        if (!this.IZ) {
            this.IZ = true;
            try {
                h(t);
            } catch (Exception e) {
                j(e);
            }
        }
    }

    public synchronized void iq() {
        if (!this.IZ) {
            this.IZ = true;
            try {
                ns();
            } catch (Exception e) {
                j(e);
            }
        }
    }

    public synchronized void onProgressUpdate(float progress) {
        if (!this.IZ) {
            try {
                n(progress);
            } catch (Exception e) {
                j(e);
            }
        }
    }

    protected void n(float progress) {
    }

    protected void j(Exception e) {
        HLog.warn(getClass(), "unhandled exception", e);
    }
}
