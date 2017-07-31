package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelOutboundBuffer$MessageProcessor;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

final class IovArray implements ChannelOutboundBuffer$MessageProcessor {
    static final /* synthetic */ boolean $assertionsDisabled = (!IovArray.class.desiredAssertionStatus());
    private static final int ADDRESS_SIZE = PlatformDependent.addressSize();
    private static final int CAPACITY = (Native.IOV_MAX * IOV_SIZE);
    private static final int IOV_SIZE = (ADDRESS_SIZE * 2);
    private int count;
    private final long memoryAddress = PlatformDependent.allocateMemory((long) CAPACITY);
    private long size;

    IovArray() {
    }

    void clear() {
        this.count = 0;
        this.size = 0;
    }

    boolean add(ByteBuf buf) {
        if (this.count == Native.IOV_MAX) {
            return false;
        }
        int len = buf.readableBytes();
        if (len == 0) {
            return true;
        }
        return add(buf.memoryAddress(), buf.readerIndex(), len);
    }

    private boolean add(long addr, int offset, int len) {
        if (len == 0) {
            return true;
        }
        int i = this.count;
        this.count = i + 1;
        long baseOffset = memoryAddress(i);
        long lengthOffset = baseOffset + ((long) ADDRESS_SIZE);
        if (Native.SSIZE_MAX - ((long) len) < this.size) {
            return false;
        }
        this.size += (long) len;
        if (ADDRESS_SIZE == 8) {
            PlatformDependent.putLong(baseOffset, ((long) offset) + addr);
            PlatformDependent.putLong(lengthOffset, (long) len);
            return true;
        } else if ($assertionsDisabled || ADDRESS_SIZE == 4) {
            PlatformDependent.putInt(baseOffset, ((int) addr) + offset);
            PlatformDependent.putInt(lengthOffset, len);
            return true;
        } else {
            throw new AssertionError();
        }
    }

    boolean add(CompositeByteBuf buf) {
        ByteBuffer[] buffers = buf.nioBuffers();
        if (this.count + buffers.length >= Native.IOV_MAX) {
            return false;
        }
        for (ByteBuffer nioBuffer : buffers) {
            int offset = nioBuffer.position();
            int len = nioBuffer.limit() - nioBuffer.position();
            if (len != 0 && !add(PlatformDependent.directBufferAddress(nioBuffer), offset, len)) {
                return false;
            }
        }
        return true;
    }

    long processWritten(int index, long written) {
        long baseOffset = memoryAddress(index);
        long lengthOffset = baseOffset + ((long) ADDRESS_SIZE);
        long len;
        if (ADDRESS_SIZE == 8) {
            len = PlatformDependent.getLong(lengthOffset);
            if (len <= written) {
                return len;
            }
            PlatformDependent.putLong(baseOffset, PlatformDependent.getLong(baseOffset) + written);
            PlatformDependent.putLong(lengthOffset, len - written);
            return -1;
        } else if ($assertionsDisabled || ADDRESS_SIZE == 4) {
            len = (long) PlatformDependent.getInt(lengthOffset);
            if (len <= written) {
                return len;
            }
            PlatformDependent.putInt(baseOffset, (int) (((long) PlatformDependent.getInt(baseOffset)) + written));
            PlatformDependent.putInt(lengthOffset, (int) (len - written));
            return -1;
        } else {
            throw new AssertionError();
        }
    }

    int count() {
        return this.count;
    }

    long size() {
        return this.size;
    }

    long memoryAddress(int offset) {
        return this.memoryAddress + ((long) (IOV_SIZE * offset));
    }

    void release() {
        PlatformDependent.freeMemory(this.memoryAddress);
    }

    public boolean processMessage(Object msg) throws Exception {
        if (!(msg instanceof ByteBuf)) {
            return false;
        }
        if (msg instanceof CompositeByteBuf) {
            return add((CompositeByteBuf) msg);
        }
        return add((ByteBuf) msg);
    }
}
