package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteOrder;

abstract class AbstractUnsafeSwappedByteBuf extends SwappedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractUnsafeSwappedByteBuf.class.desiredAssertionStatus());
    private final boolean nativeByteOrder;
    private final AbstractByteBuf wrapped;

    protected abstract int _getInt(AbstractByteBuf abstractByteBuf, int i);

    protected abstract long _getLong(AbstractByteBuf abstractByteBuf, int i);

    protected abstract short _getShort(AbstractByteBuf abstractByteBuf, int i);

    protected abstract void _setInt(AbstractByteBuf abstractByteBuf, int i, int i2);

    protected abstract void _setLong(AbstractByteBuf abstractByteBuf, int i, long j);

    protected abstract void _setShort(AbstractByteBuf abstractByteBuf, int i, short s);

    AbstractUnsafeSwappedByteBuf(AbstractByteBuf buf) {
        boolean z = true;
        super(buf);
        if ($assertionsDisabled || PlatformDependent.isUnaligned()) {
            boolean z2;
            this.wrapped = buf;
            boolean z3 = PlatformDependent.BIG_ENDIAN_NATIVE_ORDER;
            if (order() == ByteOrder.BIG_ENDIAN) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z3 != z2) {
                z = false;
            }
            this.nativeByteOrder = z;
            return;
        }
        throw new AssertionError();
    }

    public final long getLong(int index) {
        this.wrapped.checkIndex(index, 8);
        long v = _getLong(this.wrapped, index);
        return this.nativeByteOrder ? v : Long.reverseBytes(v);
    }

    public final float getFloat(int index) {
        return Float.intBitsToFloat(getInt(index));
    }

    public final double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }

    public final char getChar(int index) {
        return (char) getShort(index);
    }

    public final long getUnsignedInt(int index) {
        return ((long) getInt(index)) & 4294967295L;
    }

    public final int getInt(int index) {
        this.wrapped.checkIndex0(index, 4);
        int v = _getInt(this.wrapped, index);
        return this.nativeByteOrder ? v : Integer.reverseBytes(v);
    }

    public final int getUnsignedShort(int index) {
        return getShort(index) & 65535;
    }

    public final short getShort(int index) {
        this.wrapped.checkIndex0(index, 2);
        short v = _getShort(this.wrapped, index);
        return this.nativeByteOrder ? v : Short.reverseBytes(v);
    }

    public final ByteBuf setShort(int index, int value) {
        this.wrapped.checkIndex0(index, 2);
        _setShort(this.wrapped, index, this.nativeByteOrder ? (short) value : Short.reverseBytes((short) value));
        return this;
    }

    public final ByteBuf setInt(int index, int value) {
        this.wrapped.checkIndex0(index, 4);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        if (!this.nativeByteOrder) {
            value = Integer.reverseBytes(value);
        }
        _setInt(abstractByteBuf, index, value);
        return this;
    }

    public final ByteBuf setLong(int index, long value) {
        this.wrapped.checkIndex(index, 8);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        if (!this.nativeByteOrder) {
            value = Long.reverseBytes(value);
        }
        _setLong(abstractByteBuf, index, value);
        return this;
    }

    public final ByteBuf setChar(int index, int value) {
        setShort(index, value);
        return this;
    }

    public final ByteBuf setFloat(int index, float value) {
        setInt(index, Float.floatToRawIntBits(value));
        return this;
    }

    public final ByteBuf setDouble(int index, double value) {
        setLong(index, Double.doubleToRawLongBits(value));
        return this;
    }

    public final ByteBuf writeShort(int value) {
        this.wrapped.ensureWritable(2);
        _setShort(this.wrapped, this.wrapped.writerIndex, this.nativeByteOrder ? (short) value : Short.reverseBytes((short) value));
        AbstractByteBuf abstractByteBuf = this.wrapped;
        abstractByteBuf.writerIndex += 2;
        return this;
    }

    public final ByteBuf writeInt(int value) {
        this.wrapped.ensureWritable(4);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        int i = this.wrapped.writerIndex;
        if (!this.nativeByteOrder) {
            value = Integer.reverseBytes(value);
        }
        _setInt(abstractByteBuf, i, value);
        abstractByteBuf = this.wrapped;
        abstractByteBuf.writerIndex += 4;
        return this;
    }

    public final ByteBuf writeLong(long value) {
        this.wrapped.ensureWritable(8);
        AbstractByteBuf abstractByteBuf = this.wrapped;
        int i = this.wrapped.writerIndex;
        if (!this.nativeByteOrder) {
            value = Long.reverseBytes(value);
        }
        _setLong(abstractByteBuf, i, value);
        abstractByteBuf = this.wrapped;
        abstractByteBuf.writerIndex += 8;
        return this;
    }

    public final ByteBuf writeChar(int value) {
        writeShort(value);
        return this;
    }

    public final ByteBuf writeFloat(float value) {
        writeInt(Float.floatToRawIntBits(value));
        return this;
    }

    public final ByteBuf writeDouble(double value) {
        writeLong(Double.doubleToRawLongBits(value));
        return this;
    }
}
