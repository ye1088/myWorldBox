package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

final class UnpooledUnsafeHeapByteBuf extends UnpooledHeapByteBuf {
    UnpooledUnsafeHeapByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        super(alloc, initialCapacity, maxCapacity);
    }

    public byte getByte(int index) {
        checkIndex(index);
        return _getByte(index);
    }

    protected byte _getByte(int index) {
        return UnsafeByteBufUtil.getByte(this.array, index);
    }

    public short getShort(int index) {
        checkIndex(index, 2);
        return _getShort(index);
    }

    protected short _getShort(int index) {
        return UnsafeByteBufUtil.getShort(this.array, index);
    }

    public short getShortLE(int index) {
        checkIndex(index, 2);
        return _getShortLE(index);
    }

    protected short _getShortLE(int index) {
        return UnsafeByteBufUtil.getShortLE(this.array, index);
    }

    public int getUnsignedMedium(int index) {
        checkIndex(index, 3);
        return _getUnsignedMedium(index);
    }

    protected int _getUnsignedMedium(int index) {
        return UnsafeByteBufUtil.getUnsignedMedium(this.array, index);
    }

    public int getUnsignedMediumLE(int index) {
        checkIndex(index, 3);
        return _getUnsignedMediumLE(index);
    }

    protected int _getUnsignedMediumLE(int index) {
        return UnsafeByteBufUtil.getUnsignedMediumLE(this.array, index);
    }

    public int getInt(int index) {
        checkIndex(index, 4);
        return _getInt(index);
    }

    protected int _getInt(int index) {
        return UnsafeByteBufUtil.getInt(this.array, index);
    }

    public int getIntLE(int index) {
        checkIndex(index, 4);
        return _getIntLE(index);
    }

    protected int _getIntLE(int index) {
        return UnsafeByteBufUtil.getIntLE(this.array, index);
    }

    public long getLong(int index) {
        checkIndex(index, 8);
        return _getLong(index);
    }

    protected long _getLong(int index) {
        return UnsafeByteBufUtil.getLong(this.array, index);
    }

    public long getLongLE(int index) {
        checkIndex(index, 8);
        return _getLongLE(index);
    }

    protected long _getLongLE(int index) {
        return UnsafeByteBufUtil.getLongLE(this.array, index);
    }

    public ByteBuf setByte(int index, int value) {
        checkIndex(index);
        _setByte(index, value);
        return this;
    }

    protected void _setByte(int index, int value) {
        UnsafeByteBufUtil.setByte(this.array, index, value);
    }

    public ByteBuf setShort(int index, int value) {
        checkIndex(index, 2);
        _setShort(index, value);
        return this;
    }

    protected void _setShort(int index, int value) {
        UnsafeByteBufUtil.setShort(this.array, index, value);
    }

    public ByteBuf setShortLE(int index, int value) {
        checkIndex(index, 2);
        _setShortLE(index, value);
        return this;
    }

    protected void _setShortLE(int index, int value) {
        UnsafeByteBufUtil.setShortLE(this.array, index, value);
    }

    public ByteBuf setMedium(int index, int value) {
        checkIndex(index, 3);
        _setMedium(index, value);
        return this;
    }

    protected void _setMedium(int index, int value) {
        UnsafeByteBufUtil.setMedium(this.array, index, value);
    }

    public ByteBuf setMediumLE(int index, int value) {
        checkIndex(index, 3);
        _setMediumLE(index, value);
        return this;
    }

    protected void _setMediumLE(int index, int value) {
        UnsafeByteBufUtil.setMediumLE(this.array, index, value);
    }

    public ByteBuf setInt(int index, int value) {
        checkIndex(index, 4);
        _setInt(index, value);
        return this;
    }

    protected void _setInt(int index, int value) {
        UnsafeByteBufUtil.setInt(this.array, index, value);
    }

    public ByteBuf setIntLE(int index, int value) {
        checkIndex(index, 4);
        _setIntLE(index, value);
        return this;
    }

    protected void _setIntLE(int index, int value) {
        UnsafeByteBufUtil.setIntLE(this.array, index, value);
    }

    public ByteBuf setLong(int index, long value) {
        checkIndex(index, 8);
        _setLong(index, value);
        return this;
    }

    protected void _setLong(int index, long value) {
        UnsafeByteBufUtil.setLong(this.array, index, value);
    }

    public ByteBuf setLongLE(int index, long value) {
        checkIndex(index, 8);
        _setLongLE(index, value);
        return this;
    }

    protected void _setLongLE(int index, long value) {
        UnsafeByteBufUtil.setLongLE(this.array, index, value);
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
        UnsafeByteBufUtil.setZero(this.array, index, length);
    }

    @Deprecated
    protected SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeHeapSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }
}
