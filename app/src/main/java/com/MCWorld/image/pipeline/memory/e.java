package com.MCWorld.image.pipeline.memory;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import java.util.LinkedList;
import java.util.Queue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@VisibleForTesting
@NotThreadSafe
/* compiled from: Bucket */
class e<V> {
    public final int HF;
    public final int HG;
    final Queue HH;
    private int HI;

    public e(int itemSize, int maxLength, int inUseLength) {
        boolean z;
        boolean z2 = true;
        Preconditions.checkState(itemSize > 0);
        if (maxLength >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z);
        if (inUseLength < 0) {
            z2 = false;
        }
        Preconditions.checkState(z2);
        this.HF = itemSize;
        this.HG = maxLength;
        this.HH = new LinkedList();
        this.HI = inUseLength;
    }

    public boolean nS() {
        return this.HI + nT() > this.HG;
    }

    int nT() {
        return this.HH.size();
    }

    @Nullable
    public V get() {
        V value = pop();
        if (value != null) {
            this.HI++;
        }
        return value;
    }

    @Nullable
    public V pop() {
        return this.HH.poll();
    }

    public void nU() {
        this.HI++;
    }

    public void release(V value) {
        Preconditions.checkNotNull(value);
        Preconditions.checkState(this.HI > 0);
        this.HI--;
        L(value);
    }

    void L(V value) {
        this.HH.add(value);
    }

    public void nV() {
        Preconditions.checkState(this.HI > 0);
        this.HI--;
    }

    public int hn() {
        return this.HI;
    }
}
