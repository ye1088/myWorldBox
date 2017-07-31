package io.netty.buffer;

import io.netty.util.Recycler.Handle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class PooledByteBuf<T> extends AbstractReferenceCountedByteBuf {
    static final /* synthetic */ boolean $assertionsDisabled = (!PooledByteBuf.class.desiredAssertionStatus());
    PoolThreadCache cache;
    protected PoolChunk<T> chunk;
    protected long handle;
    protected int length;
    int maxLength;
    protected T memory;
    protected int offset;
    private final Handle<PooledByteBuf<T>> recyclerHandle;
    private ByteBuffer tmpNioBuf;

    protected abstract ByteBuffer newInternalNioBuffer(T t);

    protected PooledByteBuf(Handle<? extends PooledByteBuf<T>> recyclerHandle, int maxCapacity) {
        super(maxCapacity);
        this.recyclerHandle = recyclerHandle;
    }

    void init(PoolChunk<T> chunk, long handle, int offset, int length, int maxLength, PoolThreadCache cache) {
        if (!$assertionsDisabled && handle < 0) {
            throw new AssertionError();
        } else if ($assertionsDisabled || chunk != null) {
            this.chunk = chunk;
            this.handle = handle;
            this.memory = chunk.memory;
            this.offset = offset;
            this.length = length;
            this.maxLength = maxLength;
            this.tmpNioBuf = null;
            this.cache = cache;
        } else {
            throw new AssertionError();
        }
    }

    void initUnpooled(PoolChunk<T> chunk, int length) {
        if ($assertionsDisabled || chunk != null) {
            this.chunk = chunk;
            this.handle = 0;
            this.memory = chunk.memory;
            this.offset = 0;
            this.maxLength = length;
            this.length = length;
            this.tmpNioBuf = null;
            this.cache = null;
            return;
        }
        throw new AssertionError();
    }

    final void reuse(int maxCapacity) {
        maxCapacity(maxCapacity);
        setRefCnt(1);
        setIndex0(0, 0);
        discardMarks();
    }

    public final int capacity() {
        return this.length;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final io.netty.buffer.ByteBuf capacity(int r3) {
        /*
        r2 = this;
        r2.ensureAccessible();
        r0 = r2.chunk;
        r0 = r0.unpooled;
        if (r0 == 0) goto L_0x000e;
    L_0x0009:
        r0 = r2.length;
        if (r3 != r0) goto L_0x005b;
    L_0x000d:
        return r2;
    L_0x000e:
        r0 = r2.length;
        if (r3 <= r0) goto L_0x0019;
    L_0x0012:
        r0 = r2.maxLength;
        if (r3 > r0) goto L_0x005b;
    L_0x0016:
        r2.length = r3;
        goto L_0x000d;
    L_0x0019:
        r0 = r2.length;
        if (r3 >= r0) goto L_0x000d;
    L_0x001d:
        r0 = r2.maxLength;
        r0 = r0 >>> 1;
        if (r3 <= r0) goto L_0x005b;
    L_0x0023:
        r0 = r2.maxLength;
        r1 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        if (r0 > r1) goto L_0x0045;
    L_0x0029:
        r0 = r2.maxLength;
        r0 = r0 + -16;
        if (r3 <= r0) goto L_0x005b;
    L_0x002f:
        r2.length = r3;
        r0 = r2.readerIndex();
        r0 = java.lang.Math.min(r0, r3);
        r1 = r2.writerIndex();
        r1 = java.lang.Math.min(r1, r3);
        r2.setIndex(r0, r1);
        goto L_0x000d;
    L_0x0045:
        r2.length = r3;
        r0 = r2.readerIndex();
        r0 = java.lang.Math.min(r0, r3);
        r1 = r2.writerIndex();
        r1 = java.lang.Math.min(r1, r3);
        r2.setIndex(r0, r1);
        goto L_0x000d;
    L_0x005b:
        r0 = r2.chunk;
        r0 = r0.arena;
        r1 = 1;
        r0.reallocate(r2, r3, r1);
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.PooledByteBuf.capacity(int):io.netty.buffer.ByteBuf");
    }

    public final ByteBufAllocator alloc() {
        return this.chunk.arena.parent;
    }

    public final ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public final ByteBuf unwrap() {
        return null;
    }

    public final ByteBuf retainedDuplicate() {
        return PooledDuplicatedByteBuf.newInstance(this, this, readerIndex(), writerIndex());
    }

    public final ByteBuf retainedSlice() {
        int index = readerIndex();
        return retainedSlice(index, writerIndex() - index);
    }

    public final ByteBuf retainedSlice(int index, int length) {
        return PooledSlicedByteBuf.newInstance(this, this, index, length);
    }

    protected final ByteBuffer internalNioBuffer() {
        ByteBuffer tmpNioBuf = this.tmpNioBuf;
        if (tmpNioBuf != null) {
            return tmpNioBuf;
        }
        tmpNioBuf = newInternalNioBuffer(this.memory);
        this.tmpNioBuf = tmpNioBuf;
        return tmpNioBuf;
    }

    protected final void deallocate() {
        if (this.handle >= 0) {
            long handle = this.handle;
            this.handle = -1;
            this.memory = null;
            this.chunk.arena.free(this.chunk, handle, this.maxLength, this.cache);
            recycle();
        }
    }

    private void recycle() {
        this.recyclerHandle.recycle(this);
    }

    protected final int idx(int index) {
        return this.offset + index;
    }
}
