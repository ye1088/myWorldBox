package com.MCWorld.image.pipeline.memory;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Throwables;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.core.common.memory.MemoryTrimType;
import com.MCWorld.image.core.common.memory.a;
import com.MCWorld.image.core.common.references.b;
import com.MCWorld.image.core.common.references.c;
import java.util.concurrent.Semaphore;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: SharedByteArray */
public class v implements a {
    @VisibleForTesting
    final int IC;
    @VisibleForTesting
    final int IE;
    @VisibleForTesting
    final b<byte[]> IG;
    @VisibleForTesting
    final Semaphore IH;
    private final c<byte[]> xe;

    public v(com.MCWorld.image.core.common.memory.b memoryTrimmableRegistry, t params) {
        boolean z = false;
        Preconditions.checkNotNull(memoryTrimmableRegistry);
        Preconditions.checkArgument(params.Is > 0);
        if (params.It >= params.Is) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.IE = params.It;
        this.IC = params.Is;
        this.IG = new b();
        this.IH = new Semaphore(1);
        this.xe = new c<byte[]>(this) {
            final /* synthetic */ v II;

            {
                this.II = this$0;
            }

            public /* synthetic */ void release(Object obj) {
                s((byte[]) obj);
            }

            public void s(byte[] unused) {
                this.II.IH.release();
            }
        };
        memoryTrimmableRegistry.a(this);
    }

    public com.MCWorld.image.core.common.references.a<byte[]> cA(int size) {
        boolean z;
        boolean z2 = true;
        if (size > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Size must be greater than zero");
        if (size > this.IE) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "Requested size is too big");
        this.IH.acquireUninterruptibly();
        try {
            return com.MCWorld.image.core.common.references.a.a(getByteArray(size), this.xe);
        } catch (Throwable t) {
            this.IH.release();
            RuntimeException propagate = Throwables.propagate(t);
        }
    }

    private byte[] getByteArray(int requestedSize) {
        int bucketedSize = cs(requestedSize);
        byte[] byteArray = (byte[]) this.IG.get();
        if (byteArray == null || byteArray.length < bucketedSize) {
            return cK(bucketedSize);
        }
        return byteArray;
    }

    public void b(MemoryTrimType trimType) {
        if (this.IH.tryAcquire()) {
            try {
                this.IG.clear();
            } finally {
                this.IH.release();
            }
        }
    }

    @VisibleForTesting
    int cs(int size) {
        return Integer.highestOneBit(Math.max(size, this.IC) - 1) * 2;
    }

    private synchronized byte[] cK(int size) {
        byte[] byteArray;
        this.IG.clear();
        byteArray = new byte[size];
        this.IG.set(byteArray);
        return byteArray;
    }
}
