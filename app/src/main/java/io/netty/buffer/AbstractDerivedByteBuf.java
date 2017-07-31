package io.netty.buffer;

import java.nio.ByteBuffer;

@Deprecated
public abstract class AbstractDerivedByteBuf extends AbstractByteBuf {
    protected AbstractDerivedByteBuf(int maxCapacity) {
        super(maxCapacity);
    }

    public final int refCnt() {
        return unwrap().refCnt();
    }

    public final ByteBuf retain() {
        unwrap().retain();
        return this;
    }

    public final ByteBuf retain(int increment) {
        unwrap().retain(increment);
        return this;
    }

    public final ByteBuf touch() {
        unwrap().touch();
        return this;
    }

    public final ByteBuf touch(Object hint) {
        unwrap().touch(hint);
        return this;
    }

    public final boolean release() {
        return unwrap().release();
    }

    public final boolean release(int decrement) {
        return unwrap().release(decrement);
    }

    public boolean isReadOnly() {
        return unwrap().isReadOnly();
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        return nioBuffer(index, length);
    }

    public ByteBuffer nioBuffer(int index, int length) {
        return unwrap().nioBuffer(index, length);
    }
}
