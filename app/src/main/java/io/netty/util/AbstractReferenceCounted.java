package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class AbstractReferenceCounted implements ReferenceCounted {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater;
    private volatile int refCnt = 1;

    protected abstract void deallocate();

    static {
        AtomicIntegerFieldUpdater<AbstractReferenceCounted> updater = PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCounted.class, "refCnt");
        if (updater == null) {
            updater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
        }
        refCntUpdater = updater;
    }

    public final int refCnt() {
        return this.refCnt;
    }

    protected final void setRefCnt(int refCnt) {
        this.refCnt = refCnt;
    }

    public ReferenceCounted retain() {
        int refCnt;
        int nextCnt;
        do {
            refCnt = this.refCnt;
            nextCnt = refCnt + 1;
            if (nextCnt <= 1) {
                throw new IllegalReferenceCountException(refCnt, 1);
            }
        } while (!refCntUpdater.compareAndSet(this, refCnt, nextCnt));
        return this;
    }

    public ReferenceCounted retain(int increment) {
        if (increment <= 0) {
            throw new IllegalArgumentException("increment: " + increment + " (expected: > 0)");
        }
        int refCnt;
        int nextCnt;
        do {
            refCnt = this.refCnt;
            nextCnt = refCnt + increment;
            if (nextCnt <= increment) {
                throw new IllegalReferenceCountException(refCnt, increment);
            }
        } while (!refCntUpdater.compareAndSet(this, refCnt, nextCnt));
        return this;
    }

    public ReferenceCounted touch() {
        return touch(null);
    }

    public boolean release() {
        int refCnt;
        do {
            refCnt = this.refCnt;
            if (refCnt == 0) {
                throw new IllegalReferenceCountException(0, -1);
            }
        } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt - 1));
        if (refCnt != 1) {
            return false;
        }
        deallocate();
        return true;
    }

    public boolean release(int decrement) {
        if (decrement <= 0) {
            throw new IllegalArgumentException("decrement: " + decrement + " (expected: > 0)");
        }
        int refCnt;
        do {
            refCnt = this.refCnt;
            if (refCnt < decrement) {
                throw new IllegalReferenceCountException(refCnt, -decrement);
            }
        } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement));
        if (refCnt != decrement) {
            return false;
        }
        deallocate();
        return true;
    }
}
