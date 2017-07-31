package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.internal.PlatformDependent;

final class PooledUnsafeHeapByteBuf extends PooledHeapByteBuf {
    private static final Recycler<PooledUnsafeHeapByteBuf> RECYCLER = new 1();

    static PooledUnsafeHeapByteBuf newUnsafeInstance(int maxCapacity) {
        PooledUnsafeHeapByteBuf buf = (PooledUnsafeHeapByteBuf) RECYCLER.get();
        buf.reuse(maxCapacity);
        return buf;
    }

    private PooledUnsafeHeapByteBuf(Handle<PooledUnsafeHeapByteBuf> recyclerHandle, int maxCapacity) {
        super(recyclerHandle, maxCapacity);
    }

    protected byte _getByte(int index) {
        return UnsafeByteBufUtil.getByte((byte[]) this.memory, idx(index));
    }

    protected short _getShort(int index) {
        return UnsafeByteBufUtil.getShort((byte[]) this.memory, idx(index));
    }

    protected short _getShortLE(int index) {
        return UnsafeByteBufUtil.getShortLE((byte[]) this.memory, idx(index));
    }

    protected int _getUnsignedMedium(int index) {
        return UnsafeByteBufUtil.getUnsignedMedium((byte[]) this.memory, idx(index));
    }

    protected int _getUnsignedMediumLE(int index) {
        return UnsafeByteBufUtil.getUnsignedMediumLE((byte[]) this.memory, idx(index));
    }

    protected int _getInt(int index) {
        return UnsafeByteBufUtil.getInt((byte[]) this.memory, idx(index));
    }

    protected int _getIntLE(int index) {
        return UnsafeByteBufUtil.getIntLE((byte[]) this.memory, idx(index));
    }

    protected long _getLong(int index) {
        return UnsafeByteBufUtil.getLong((byte[]) this.memory, idx(index));
    }

    protected long _getLongLE(int index) {
        return UnsafeByteBufUtil.getLongLE((byte[]) this.memory, idx(index));
    }

    protected void _setByte(int index, int value) {
        UnsafeByteBufUtil.setByte((byte[]) this.memory, idx(index), value);
    }

    protected void _setShort(int index, int value) {
        UnsafeByteBufUtil.setShort((byte[]) this.memory, idx(index), value);
    }

    protected void _setShortLE(int index, int value) {
        UnsafeByteBufUtil.setShortLE((byte[]) this.memory, idx(index), value);
    }

    protected void _setMedium(int index, int value) {
        UnsafeByteBufUtil.setMedium((byte[]) this.memory, idx(index), value);
    }

    protected void _setMediumLE(int index, int value) {
        UnsafeByteBufUtil.setMediumLE((byte[]) this.memory, idx(index), value);
    }

    protected void _setInt(int index, int value) {
        UnsafeByteBufUtil.setInt((byte[]) this.memory, idx(index), value);
    }

    protected void _setIntLE(int index, int value) {
        UnsafeByteBufUtil.setIntLE((byte[]) this.memory, idx(index), value);
    }

    protected void _setLong(int index, long value) {
        UnsafeByteBufUtil.setLong((byte[]) this.memory, idx(index), value);
    }

    protected void _setLongLE(int index, long value) {
        UnsafeByteBufUtil.setLongLE((byte[]) this.memory, idx(index), value);
    }

    public ByteBuf setZero(int index, int length) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.setZero(index, length);
        }
        _setZero(index, length);
        return this;
    }

    public ByteBuf writeZero(int length) {
        if (PlatformDependent.javaVersion() < 7) {
            return super.writeZero(length);
        }
        ensureWritable(length);
        int wIndex = this.writerIndex;
        _setZero(wIndex, length);
        this.writerIndex = wIndex + length;
        return this;
    }

    private void _setZero(int index, int length) {
        checkIndex(index, length);
        UnsafeByteBufUtil.setZero((byte[]) this.memory, idx(index), length);
    }

    @Deprecated
    protected SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeHeapSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }
}
