package io.netty.buffer;

import io.netty.util.ResourceLeak;
import java.nio.ByteOrder;

final class SimpleLeakAwareCompositeByteBuf extends WrappedCompositeByteBuf {
    private final ResourceLeak leak;

    SimpleLeakAwareCompositeByteBuf(CompositeByteBuf wrapped, ResourceLeak leak) {
        super(wrapped);
        this.leak = leak;
    }

    public boolean release() {
        boolean deallocated = super.release();
        if (deallocated) {
            this.leak.close();
        }
        return deallocated;
    }

    public boolean release(int decrement) {
        boolean deallocated = super.release(decrement);
        if (deallocated) {
            this.leak.close();
        }
        return deallocated;
    }

    public ByteBuf order(ByteOrder endianness) {
        this.leak.record();
        return order() == endianness ? this : new SimpleLeakAwareByteBuf(super.order(endianness), this.leak);
    }

    public ByteBuf slice() {
        return new SimpleLeakAwareByteBuf(super.slice(), this.leak);
    }

    public ByteBuf retainedSlice() {
        return new SimpleLeakAwareByteBuf(super.retainedSlice(), this.leak);
    }

    public ByteBuf slice(int index, int length) {
        return new SimpleLeakAwareByteBuf(super.slice(index, length), this.leak);
    }

    public ByteBuf retainedSlice(int index, int length) {
        return new SimpleLeakAwareByteBuf(super.retainedSlice(index, length), this.leak);
    }

    public ByteBuf duplicate() {
        return new SimpleLeakAwareByteBuf(super.duplicate(), this.leak);
    }

    public ByteBuf retainedDuplicate() {
        return new SimpleLeakAwareByteBuf(super.retainedDuplicate(), this.leak);
    }

    public ByteBuf readSlice(int length) {
        return new SimpleLeakAwareByteBuf(super.readSlice(length), this.leak);
    }

    public ByteBuf readRetainedSlice(int length) {
        return new SimpleLeakAwareByteBuf(super.readRetainedSlice(length), this.leak);
    }

    public ByteBuf asReadOnly() {
        return new SimpleLeakAwareByteBuf(super.asReadOnly(), this.leak);
    }
}
