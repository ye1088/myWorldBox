package io.netty.buffer;

import io.netty.util.Recycler.Handle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class AbstractPooledDerivedByteBuf extends AbstractReferenceCountedByteBuf {
    private AbstractByteBuf buffer;
    private final Handle<AbstractPooledDerivedByteBuf> recyclerHandle;

    AbstractPooledDerivedByteBuf(Handle<? extends AbstractPooledDerivedByteBuf> recyclerHandle) {
        super(0);
        this.recyclerHandle = recyclerHandle;
    }

    public final AbstractByteBuf unwrap() {
        return this.buffer;
    }

    final <U extends AbstractPooledDerivedByteBuf> U init(AbstractByteBuf unwrapped, ByteBuf wrapped, int readerIndex, int writerIndex, int maxCapacity) {
        wrapped.retain();
        this.buffer = unwrapped;
        try {
            maxCapacity(maxCapacity);
            setIndex0(readerIndex, writerIndex);
            setRefCnt(1);
            U castThis = this;
            wrapped = null;
            return castThis;
        } finally {
            if (wrapped != null) {
                this.buffer = null;
                wrapped.release();
            }
        }
    }

    protected final void deallocate() {
        ByteBuf wrapped = unwrap();
        this.recyclerHandle.recycle(this);
        wrapped.release();
    }

    public final ByteBufAllocator alloc() {
        return unwrap().alloc();
    }

    @Deprecated
    public final ByteOrder order() {
        return unwrap().order();
    }

    public boolean isReadOnly() {
        return unwrap().isReadOnly();
    }

    public final boolean isDirect() {
        return unwrap().isDirect();
    }

    public boolean hasArray() {
        return unwrap().hasArray();
    }

    public byte[] array() {
        return unwrap().array();
    }

    public boolean hasMemoryAddress() {
        return unwrap().hasMemoryAddress();
    }

    public final int nioBufferCount() {
        return unwrap().nioBufferCount();
    }

    public final ByteBuffer internalNioBuffer(int index, int length) {
        return nioBuffer(index, length);
    }

    public final ByteBuf retainedSlice() {
        int index = readerIndex();
        return retainedSlice(index, writerIndex() - index);
    }
}
