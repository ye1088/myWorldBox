package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class UnpooledHeapByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    byte[] array;
    private ByteBuffer tmpNioBuf;

    protected UnpooledHeapByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        this(alloc, new byte[initialCapacity], 0, 0, maxCapacity);
    }

    protected UnpooledHeapByteBuf(ByteBufAllocator alloc, byte[] initialArray, int maxCapacity) {
        this(alloc, initialArray, 0, initialArray.length, maxCapacity);
    }

    private UnpooledHeapByteBuf(ByteBufAllocator alloc, byte[] initialArray, int readerIndex, int writerIndex, int maxCapacity) {
        super(maxCapacity);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        } else if (initialArray == null) {
            throw new NullPointerException("initialArray");
        } else if (initialArray.length > maxCapacity) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialArray.length), Integer.valueOf(maxCapacity)}));
        } else {
            this.alloc = alloc;
            setArray(initialArray);
            setIndex(readerIndex, writerIndex);
        }
    }

    private void setArray(byte[] initialArray) {
        this.array = initialArray;
        this.tmpNioBuf = null;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public boolean isDirect() {
        return false;
    }

    public int capacity() {
        ensureAccessible();
        return this.array.length;
    }

    public ByteBuf capacity(int newCapacity) {
        ensureAccessible();
        if (newCapacity < 0 || newCapacity > maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + newCapacity);
        }
        int oldCapacity = this.array.length;
        byte[] newArray;
        if (newCapacity > oldCapacity) {
            newArray = new byte[newCapacity];
            System.arraycopy(this.array, 0, newArray, 0, this.array.length);
            setArray(newArray);
        } else if (newCapacity < oldCapacity) {
            newArray = new byte[newCapacity];
            int readerIndex = readerIndex();
            if (readerIndex < newCapacity) {
                int writerIndex = writerIndex();
                if (writerIndex > newCapacity) {
                    writerIndex = newCapacity;
                    writerIndex(newCapacity);
                }
                System.arraycopy(this.array, readerIndex, newArray, readerIndex, writerIndex - readerIndex);
            } else {
                setIndex(newCapacity, newCapacity);
            }
            setArray(newArray);
        }
        return this;
    }

    public boolean hasArray() {
        return true;
    }

    public byte[] array() {
        ensureAccessible();
        return this.array;
    }

    public int arrayOffset() {
        return 0;
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.capacity());
        if (dst.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.array, index, dst.memoryAddress() + ((long) dstIndex), (long) length);
        } else if (dst.hasArray()) {
            getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
        } else {
            dst.setBytes(dstIndex, this.array, index, length);
        }
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.length);
        System.arraycopy(this.array, index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        ensureAccessible();
        dst.put(this.array, index, Math.min(capacity() - index, dst.remaining()));
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
        ensureAccessible();
        out.write(this.array, index, length);
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
        ensureAccessible();
        return getBytes(index, out, length, false);
    }

    public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
        ensureAccessible();
        return getBytes(index, out, position, length, false);
    }

    private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
        ByteBuffer tmpBuf;
        ensureAccessible();
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = ByteBuffer.wrap(this.array);
        }
        return out.write((ByteBuffer) tmpBuf.clear().position(index).limit(index + length));
    }

    private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
        ensureAccessible();
        return out.write((ByteBuffer) (internal ? internalNioBuffer() : ByteBuffer.wrap(this.array)).clear().position(index).limit(index + length), position);
    }

    public int readBytes(GatheringByteChannel out, int length) throws IOException {
        checkReadableBytes(length);
        int readBytes = getBytes(this.readerIndex, out, length, true);
        this.readerIndex += readBytes;
        return readBytes;
    }

    public int readBytes(FileChannel out, long position, int length) throws IOException {
        checkReadableBytes(length);
        int readBytes = getBytes(this.readerIndex, out, position, length, true);
        this.readerIndex += readBytes;
        return readBytes;
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.capacity());
        if (src.hasMemoryAddress()) {
            PlatformDependent.copyMemory(src.memoryAddress() + ((long) srcIndex), this.array, index, (long) length);
        } else if (src.hasArray()) {
            setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
        } else {
            src.getBytes(srcIndex, this.array, index, length);
        }
        return this;
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.length);
        System.arraycopy(src, srcIndex, this.array, index, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        ensureAccessible();
        src.get(this.array, index, src.remaining());
        return this;
    }

    public int setBytes(int index, InputStream in, int length) throws IOException {
        ensureAccessible();
        return in.read(this.array, index, length);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
        ensureAccessible();
        try {
            return in.read((ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length));
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
        ensureAccessible();
        try {
            return in.read((ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length), position);
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuffer nioBuffer(int index, int length) {
        ensureAccessible();
        return ByteBuffer.wrap(this.array, index, length).slice();
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        checkIndex(index, length);
        return (ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length);
    }

    public byte getByte(int index) {
        ensureAccessible();
        return _getByte(index);
    }

    protected byte _getByte(int index) {
        return HeapByteBufUtil.getByte(this.array, index);
    }

    public short getShort(int index) {
        ensureAccessible();
        return _getShort(index);
    }

    protected short _getShort(int index) {
        return HeapByteBufUtil.getShort(this.array, index);
    }

    public short getShortLE(int index) {
        ensureAccessible();
        return _getShortLE(index);
    }

    protected short _getShortLE(int index) {
        return HeapByteBufUtil.getShortLE(this.array, index);
    }

    public int getUnsignedMedium(int index) {
        ensureAccessible();
        return _getUnsignedMedium(index);
    }

    protected int _getUnsignedMedium(int index) {
        return HeapByteBufUtil.getUnsignedMedium(this.array, index);
    }

    public int getUnsignedMediumLE(int index) {
        ensureAccessible();
        return _getUnsignedMediumLE(index);
    }

    protected int _getUnsignedMediumLE(int index) {
        return HeapByteBufUtil.getUnsignedMediumLE(this.array, index);
    }

    public int getInt(int index) {
        ensureAccessible();
        return _getInt(index);
    }

    protected int _getInt(int index) {
        return HeapByteBufUtil.getInt(this.array, index);
    }

    public int getIntLE(int index) {
        ensureAccessible();
        return _getIntLE(index);
    }

    protected int _getIntLE(int index) {
        return HeapByteBufUtil.getIntLE(this.array, index);
    }

    public long getLong(int index) {
        ensureAccessible();
        return _getLong(index);
    }

    protected long _getLong(int index) {
        return HeapByteBufUtil.getLong(this.array, index);
    }

    public long getLongLE(int index) {
        ensureAccessible();
        return _getLongLE(index);
    }

    protected long _getLongLE(int index) {
        return HeapByteBufUtil.getLongLE(this.array, index);
    }

    public ByteBuf setByte(int index, int value) {
        ensureAccessible();
        _setByte(index, value);
        return this;
    }

    protected void _setByte(int index, int value) {
        HeapByteBufUtil.setByte(this.array, index, value);
    }

    public ByteBuf setShort(int index, int value) {
        ensureAccessible();
        _setShort(index, value);
        return this;
    }

    protected void _setShort(int index, int value) {
        HeapByteBufUtil.setShort(this.array, index, value);
    }

    public ByteBuf setShortLE(int index, int value) {
        ensureAccessible();
        _setShortLE(index, value);
        return this;
    }

    protected void _setShortLE(int index, int value) {
        HeapByteBufUtil.setShortLE(this.array, index, value);
    }

    public ByteBuf setMedium(int index, int value) {
        ensureAccessible();
        _setMedium(index, value);
        return this;
    }

    protected void _setMedium(int index, int value) {
        HeapByteBufUtil.setMedium(this.array, index, value);
    }

    public ByteBuf setMediumLE(int index, int value) {
        ensureAccessible();
        _setMediumLE(index, value);
        return this;
    }

    protected void _setMediumLE(int index, int value) {
        HeapByteBufUtil.setMediumLE(this.array, index, value);
    }

    public ByteBuf setInt(int index, int value) {
        ensureAccessible();
        _setInt(index, value);
        return this;
    }

    protected void _setInt(int index, int value) {
        HeapByteBufUtil.setInt(this.array, index, value);
    }

    public ByteBuf setIntLE(int index, int value) {
        ensureAccessible();
        _setIntLE(index, value);
        return this;
    }

    protected void _setIntLE(int index, int value) {
        HeapByteBufUtil.setIntLE(this.array, index, value);
    }

    public ByteBuf setLong(int index, long value) {
        ensureAccessible();
        _setLong(index, value);
        return this;
    }

    protected void _setLong(int index, long value) {
        HeapByteBufUtil.setLong(this.array, index, value);
    }

    public ByteBuf setLongLE(int index, long value) {
        ensureAccessible();
        _setLongLE(index, value);
        return this;
    }

    protected void _setLongLE(int index, long value) {
        HeapByteBufUtil.setLongLE(this.array, index, value);
    }

    public ByteBuf copy(int index, int length) {
        checkIndex(index, length);
        byte[] copiedArray = new byte[length];
        System.arraycopy(this.array, index, copiedArray, 0, length);
        return new UnpooledHeapByteBuf(alloc(), copiedArray, maxCapacity());
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf != null) {
            return tmpNioBuf;
        }
        tmpNioBuf = ByteBuffer.wrap(this.array);
        this.tmpNioBuf = tmpNioBuf;
        return tmpNioBuf;
    }

    protected void deallocate() {
        this.array = null;
    }

    public ByteBuf unwrap() {
        return null;
    }
}
