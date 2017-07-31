package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;

class PooledUnsafeHeapByteBuf$1 extends Recycler<PooledUnsafeHeapByteBuf> {
    PooledUnsafeHeapByteBuf$1() {
    }

    protected PooledUnsafeHeapByteBuf newObject(Handle<PooledUnsafeHeapByteBuf> handle) {
        return new PooledUnsafeHeapByteBuf(handle, 0, null);
    }
}
