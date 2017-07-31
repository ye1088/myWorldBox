package com.huluxia.image.core.common.references;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

@VisibleForTesting
public class SharedReference<T> {
    @GuardedBy("itself")
    private static final Map<Object, Integer> yt = new IdentityHashMap();
    @GuardedBy("this")
    private int mRefCount = 1;
    @GuardedBy("this")
    private T mValue;
    private final c<T> xe;

    public static class NullReferenceException extends RuntimeException {
        public NullReferenceException() {
            super("Null shared reference");
        }
    }

    public SharedReference(T value, c<T> resourceReleaser) {
        this.mValue = Preconditions.checkNotNull(value);
        this.xe = (c) Preconditions.checkNotNull(resourceReleaser);
        q(value);
    }

    private static void q(Object value) {
        synchronized (yt) {
            Integer count = (Integer) yt.get(value);
            if (count == null) {
                yt.put(value, Integer.valueOf(1));
            } else {
                yt.put(value, Integer.valueOf(count.intValue() + 1));
            }
        }
    }

    private static void r(Object value) {
        synchronized (yt) {
            Integer count = (Integer) yt.get(value);
            if (count == null) {
                HLog.warn("SharedReference", "No entry in sLiveObjects for value of type %s", new Object[]{value.getClass()});
            } else if (count.intValue() == 1) {
                yt.remove(value);
            } else {
                yt.put(value, Integer.valueOf(count.intValue() - 1));
            }
        }
    }

    public synchronized T get() {
        return this.mValue;
    }

    public synchronized boolean isValid() {
        return this.mRefCount > 0;
    }

    public static boolean a(SharedReference<?> ref) {
        return ref != null && ref.isValid();
    }

    public synchronized void iz() {
        iC();
        this.mRefCount++;
    }

    public void iA() {
        if (iB() == 0) {
            T deleted;
            synchronized (this) {
                deleted = this.mValue;
                this.mValue = null;
            }
            this.xe.release(deleted);
            r(deleted);
        }
    }

    private synchronized int iB() {
        iC();
        Preconditions.checkArgument(this.mRefCount > 0);
        this.mRefCount--;
        return this.mRefCount;
    }

    private void iC() {
        if (!a(this)) {
            throw new NullReferenceException();
        }
    }

    public synchronized int iD() {
        return this.mRefCount;
    }
}
