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

public class UnpooledDirectByteBuf extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    private ByteBuffer buffer;
    private int capacity;
    private boolean doNotFree;
    private ByteBuffer tmpNioBuf;

    protected UnpooledDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        super(maxCapacity);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        } else if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity: " + initialCapacity);
        } else if (maxCapacity < 0) {
            throw new IllegalArgumentException("maxCapacity: " + maxCapacity);
        } else if (initialCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
        } else {
            this.alloc = alloc;
            setByteBuffer(ByteBuffer.allocateDirect(initialCapacity));
        }
    }

    protected UnpooledDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity) {
        super(maxCapacity);
        if (alloc == null) {
            throw new NullPointerException("alloc");
        } else if (initialBuffer == null) {
            throw new NullPointerException("initialBuffer");
        } else if (!initialBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        } else if (initialBuffer.isReadOnly()) {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        } else {
            int initialCapacity = initialBuffer.remaining();
            if (initialCapacity > maxCapacity) {
                throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
            }
            this.alloc = alloc;
            this.doNotFree = true;
            setByteBuffer(initialBuffer.slice().order(ByteOrder.BIG_ENDIAN));
            writerIndex(initialCapacity);
        }
    }

    protected ByteBuffer allocateDirect(int initialCapacity) {
        return ByteBuffer.allocateDirect(initialCapacity);
    }

    protected void freeDirect(ByteBuffer buffer) {
        PlatformDependent.freeDirectBuffer(buffer);
    }

    private void setByteBuffer(ByteBuffer buffer) {
        ByteBuffer oldBuffer = this.buffer;
        if (oldBuffer != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            } else {
                freeDirect(oldBuffer);
            }
        }
        this.buffer = buffer;
        this.tmpNioBuf = null;
        this.capacity = buffer.remaining();
    }

    public boolean isDirect() {
        return true;
    }

    public int capacity() {
        return this.capacity;
    }

    public ByteBuf capacity(int newCapacity) {
        ensureAccessible();
        if (newCapacity < 0 || newCapacity > maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + newCapacity);
        }
        int readerIndex = readerIndex();
        int writerIndex = writerIndex();
        int oldCapacity = this.capacity;
        ByteBuffer oldBuffer;
        ByteBuffer newBuffer;
        if (newCapacity > oldCapacity) {
            oldBuffer = this.buffer;
            newBuffer = allocateDirect(newCapacity);
            oldBuffer.position(0).limit(oldBuffer.capacity());
            newBuffer.position(0).limit(oldBuffer.capacity());
            newBuffer.put(oldBuffer);
            newBuffer.clear();
            setByteBuffer(newBuffer);
        } else if (newCapacity < oldCapacity) {
            oldBuffer = this.buffer;
            newBuffer = allocateDirect(newCapacity);
            if (readerIndex < newCapacity) {
                if (writerIndex > newCapacity) {
                    writerIndex = newCapacity;
                    writerIndex(newCapacity);
                }
                oldBuffer.position(readerIndex).limit(writerIndex);
                newBuffer.position(readerIndex).limit(writerIndex);
                newBuffer.put(oldBuffer);
                newBuffer.clear();
            } else {
                setIndex(newCapacity, newCapacity);
            }
            setByteBuffer(newBuffer);
        }
        return this;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public boolean hasArray() {
        return false;
    }

    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    public byte getByte(int index) {
        ensureAccessible();
        return _getByte(index);
    }

    protected byte _getByte(int index) {
        return this.buffer.get(index);
    }

    public short getShort(int index) {
        ensureAccessible();
        return _getShort(index);
    }

    protected short _getShort(int index) {
        return this.buffer.getShort(index);
    }

    protected short _getShortLE(int index) {
        return ByteBufUtil.swapShort(this.buffer.getShort(index));
    }

    public int getUnsignedMedium(int index) {
        ensureAccessible();
        return _getUnsignedMedium(index);
    }

    protected int _getUnsignedMedium(int index) {
        return (((getByte(index) & 255) << 16) | ((getByte(index + 1) & 255) << 8)) | (getByte(index + 2) & 255);
    }

    protected int _getUnsignedMediumLE(int index) {
        return ((getByte(index) & 255) | ((getByte(index + 1) & 255) << 8)) | ((getByte(index + 2) & 255) << 16);
    }

    public int getInt(int index) {
        ensureAccessible();
        return _getInt(index);
    }

    protected int _getInt(int index) {
        return this.buffer.getInt(index);
    }

    protected int _getIntLE(int index) {
        return ByteBufUtil.swapInt(this.buffer.getInt(index));
    }

    public long getLong(int index) {
        ensureAccessible();
        return _getLong(index);
    }

    protected long _getLong(int index) {
        return this.buffer.getLong(index);
    }

    protected long _getLongLE(int index) {
        return ByteBufUtil.swapLong(this.buffer.getLong(index));
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.capacity());
        if (dst.hasArray()) {
            getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
        } else if (dst.nioBufferCount() > 0) {
            for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
                int bbLen = bb.remaining();
                getBytes(index, bb);
                index += bbLen;
            }
        } else {
            dst.setBytes(dstIndex, this, index, length);
        }
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        getBytes(index, dst, dstIndex, length, false);
        return this;
    }

    private void getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
        ByteBuffer tmpBuf;
        checkDstIndex(index, length, dstIndex, dst.length);
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = this.buffer.duplicate();
        }
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.get(dst, dstIndex, length);
    }

    public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
        checkReadableBytes(length);
        getBytes(this.readerIndex, dst, dstIndex, length, true);
        this.readerIndex += length;
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        getBytes(index, dst, false);
        return this;
    }

    private void getBytes(int index, ByteBuffer dst, boolean internal) {
        checkIndex(index);
        if (dst == null) {
            throw new NullPointerException("dst");
        }
        ByteBuffer tmpBuf;
        int bytesToCopy = Math.min(capacity() - index, dst.remaining());
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = this.buffer.duplicate();
        }
        tmpBuf.clear().position(index).limit(index + bytesToCopy);
        dst.put(tmpBuf);
    }

    public ByteBuf readBytes(ByteBuffer dst) {
        int length = dst.remaining();
        checkReadableBytes(length);
        getBytes(this.readerIndex, dst, true);
        this.readerIndex += length;
        return this;
    }

    public ByteBuf setByte(int index, int value) {
        ensureAccessible();
        _setByte(index, value);
        return this;
    }

    protected void _setByte(int index, int value) {
        this.buffer.put(index, (byte) value);
    }

    public ByteBuf setShort(int index, int value) {
        ensureAccessible();
        _setShort(index, value);
        return this;
    }

    protected void _setShort(int index, int value) {
        this.buffer.putShort(index, (short) value);
    }

    protected void _setShortLE(int index, int value) {
        this.buffer.putShort(index, ByteBufUtil.swapShort((short) value));
    }

    public ByteBuf setMedium(int index, int value) {
        ensureAccessible();
        _setMedium(index, value);
        return this;
    }

    protected void _setMedium(int index, int value) {
        setByte(index, (byte) (value >>> 16));
        setByte(index + 1, (byte) (value >>> 8));
        setByte(index + 2, (byte) value);
    }

    protected void _setMediumLE(int index, int value) {
        setByte(index, (byte) value);
        setByte(index + 1, (byte) (value >>> 8));
        setByte(index + 2, (byte) (value >>> 16));
    }

    public ByteBuf setInt(int index, int value) {
        ensureAccessible();
        _setInt(index, value);
        return this;
    }

    protected void _setInt(int index, int value) {
        this.buffer.putInt(index, value);
    }

    protected void _setIntLE(int index, int value) {
        this.buffer.putInt(index, ByteBufUtil.swapInt(value));
    }

    public ByteBuf setLong(int index, long value) {
        ensureAccessible();
        _setLong(index, value);
        return this;
    }

    protected void _setLong(int index, long value) {
        this.buffer.putLong(index, value);
    }

    protected void _setLongLE(int index, long value) {
        this.buffer.putLong(index, ByteBufUtil.swapLong(value));
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.capacity());
        if (src.nioBufferCount() > 0) {
            for (ByteBuffer bb : src.nioBuffers(srcIndex, length)) {
                int bbLen = bb.remaining();
                setBytes(index, bb);
                index += bbLen;
            }
        } else {
            src.getBytes(srcIndex, this, index, length);
        }
        return this;
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.length);
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(index).limit(index + length);
        tmpBuf.put(src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        ensureAccessible();
        ByteBuffer tmpBuf = internalNioBuffer();
        if (src == tmpBuf) {
            src = src.duplicate();
        }
        tmpBuf.clear().position(index).limit(src.remaining() + index);
        tmpBuf.put(src);
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
        getBytes(index, out, length, false);
        return this;
    }

    private void getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
        ensureAccessible();
        if (length != 0) {
            if (this.buffer.hasArray()) {
                out.write(this.buffer.array(), this.buffer.arrayOffset() + index, length);
                return;
            }
            ByteBuffer tmpBuf;
            byte[] tmp = new byte[length];
            if (internal) {
                tmpBuf = internalNioBuffer();
            } else {
                tmpBuf = this.buffer.duplicate();
            }
            tmpBuf.clear().position(index);
            tmpBuf.get(tmp);
            out.write(tmp);
        }
    }

    public ByteBuf readBytes(OutputStream out, int length) throws IOException {
        checkReadableBytes(length);
        getBytes(this.readerIndex, out, length, true);
        this.readerIndex += length;
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
        return getBytes(index, out, length, false);
    }

    private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
        ensureAccessible();
        if (length == 0) {
            return 0;
        }
        ByteBuffer tmpBuf;
        if (internal) {
            tmpBuf = internalNioBuffer();
        } else {
            tmpBuf = this.buffer.duplicate();
        }
        tmpBuf.clear().position(index).limit(index + length);
        return out.write(tmpBuf);
    }

    public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
        return getBytes(index, out, position, length, false);
    }

    private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
        ensureAccessible();
        if (length == 0) {
            return 0;
        }
        ByteBuffer tmpBuf = internal ? internalNioBuffer() : this.buffer.duplicate();
        tmpBuf.clear().position(index).limit(index + length);
        return out.write(tmpBuf, position);
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

    public int setBytes(int index, InputStream in, int length) throws IOException {
        ensureAccessible();
        if (this.buffer.hasArray()) {
            return in.read(this.buffer.array(), this.buffer.arrayOffset() + index, length);
        }
        byte[] tmp = new byte[length];
        int readBytes = in.read(tmp);
        if (readBytes <= 0) {
            return readBytes;
        }
        ByteBuffer tmpBuf = internalNioBuffer();
        tmpBuf.clear().position(index);
        tmpBuf.put(tmp, 0, readBytes);
        return readBytes;
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
        ensureAccessible();
        internalNioBuffer().clear().position(index).limit(index + length);
        try {
            return in.read(this.tmpNioBuf);
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
        ensureAccessible();
        internalNioBuffer().clear().position(index).limit(index + length);
        try {
            return in.read(this.tmpNioBuf, position);
        } catch (ClosedChannelException e) {
            return -1;
        }
    }

    public int nioBufferCount() {
        return 1;
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return new ByteBuffer[]{nioBuffer(index, length)};
    }

    public ByteBuf copy(int index, int length) {
        ensureAccessible();
        try {
            return alloc().directBuffer(length, maxCapacity()).writeBytes((ByteBuffer) this.buffer.duplicate().clear().position(index).limit(index + length));
        } catch (IllegalArgumentException e) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
        }
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        checkIndex(index, length);
        return (ByteBuffer) internalNioBuffer().clear().position(index).limit(index + length);
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf != null) {
            return tmpNioBuf;
        }
        tmpNioBuf = this.buffer.duplicate();
        this.tmpNioBuf = tmpNioBuf;
        return tmpNioBuf;
    }

    public ByteBuffer nioBuffer(int index, int length) {
        checkIndex(index, length);
        return ((ByteBuffer) this.buffer.duplicate().position(index).limit(index + length)).slice();
    }

    protected void deallocate() {
        ByteBuffer buffer = this.buffer;
        if (buffer != null) {
            this.buffer = null;
            if (!this.doNotFree) {
                freeDirect(buffer);
            }
        }
    }

    public ByteBuf unwrap() {
        return null;
    }
}
