package io.netty.buffer;

import java.nio.ByteOrder;

final class UnreleasableByteBuf extends WrappedByteBuf {
    private SwappedByteBuf swappedBuf;

    UnreleasableByteBuf(ByteBuf buf) {
        super(buf);
    }

    public ByteBuf order(ByteOrder endianness) {
        if (endianness == null) {
            throw new NullPointerException("endianness");
        } else if (endianness == order()) {
            return this;
        } else {
            ByteBuf swappedBuf = this.swappedBuf;
            if (swappedBuf == null) {
                swappedBuf = new SwappedByteBuf(this);
                this.swappedBuf = swappedBuf;
            }
            return swappedBuf;
        }
    }

    public ByteBuf asReadOnly() {
        return new UnreleasableByteBuf(this.buf.asReadOnly());
    }

    public ByteBuf readSlice(int length) {
        return new UnreleasableByteBuf(this.buf.readSlice(length));
    }

    public ByteBuf readRetainedSlice(int length) {
        return new UnreleasableByteBuf(this.buf.readRetainedSlice(length));
    }

    public ByteBuf slice() {
        return new UnreleasableByteBuf(this.buf.slice());
    }

    public ByteBuf retainedSlice() {
        return new UnreleasableByteBuf(this.buf.retainedSlice());
    }

    public ByteBuf slice(int index, int length) {
        return new UnreleasableByteBuf(this.buf.slice(index, length));
    }

    public ByteBuf retainedSlice(int index, int length) {
        return new UnreleasableByteBuf(this.buf.retainedSlice(index, length));
    }

    public ByteBuf duplicate() {
        return new UnreleasableByteBuf(this.buf.duplicate());
    }

    public ByteBuf retainedDuplicate() {
        return new UnreleasableByteBuf(this.buf.retainedDuplicate());
    }

    public ByteBuf retain(int increment) {
        return this;
    }

    public ByteBuf retain() {
        return this;
    }

    public ByteBuf touch() {
        return this;
    }

    public ByteBuf touch(Object hint) {
        return this;
    }

    public boolean release() {
        return false;
    }

    public boolean release(int decrement) {
        return false;
    }
}
