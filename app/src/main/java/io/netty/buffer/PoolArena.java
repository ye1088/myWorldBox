package io.netty.buffer;

import io.netty.util.internal.LongCounter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

abstract class PoolArena<T> implements PoolArenaMetric {
    static final /* synthetic */ boolean $assertionsDisabled = (!PoolArena.class.desiredAssertionStatus());
    static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe();
    static final int numTinySubpagePools = 32;
    private final LongCounter activeBytesHuge = PlatformDependent.newLongCounter();
    private final LongCounter allocationsHuge = PlatformDependent.newLongCounter();
    private long allocationsNormal;
    private final LongCounter allocationsSmall = PlatformDependent.newLongCounter();
    private final LongCounter allocationsTiny = PlatformDependent.newLongCounter();
    private final List<PoolChunkListMetric> chunkListMetrics;
    final int chunkSize;
    private final LongCounter deallocationsHuge = PlatformDependent.newLongCounter();
    private long deallocationsNormal;
    private long deallocationsSmall;
    private long deallocationsTiny;
    private final int maxOrder;
    final int numSmallSubpagePools;
    final AtomicInteger numThreadCaches = new AtomicInteger();
    final int pageShifts;
    final int pageSize;
    final PooledByteBufAllocator parent;
    private final PoolChunkList<T> q000;
    private final PoolChunkList<T> q025;
    private final PoolChunkList<T> q050;
    private final PoolChunkList<T> q075;
    private final PoolChunkList<T> q100;
    private final PoolChunkList<T> qInit;
    private final PoolSubpage<T>[] smallSubpagePools;
    final int subpageOverflowMask;
    private final PoolSubpage<T>[] tinySubpagePools;

    static final class DirectArena extends PoolArena<ByteBuffer> {
        DirectArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
            super(parent, pageSize, maxOrder, pageShifts, chunkSize);
        }

        boolean isDirect() {
            return true;
        }

        protected PoolChunk<ByteBuffer> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
            return new PoolChunk(this, allocateDirect(chunkSize), pageSize, maxOrder, pageShifts, chunkSize);
        }

        protected PoolChunk<ByteBuffer> newUnpooledChunk(int capacity) {
            return new PoolChunk(this, allocateDirect(capacity), capacity);
        }

        private static ByteBuffer allocateDirect(int capacity) {
            return PlatformDependent.useDirectBufferNoCleaner() ? PlatformDependent.allocateDirectNoCleaner(capacity) : ByteBuffer.allocateDirect(capacity);
        }

        protected void destroyChunk(PoolChunk<ByteBuffer> chunk) {
            if (PlatformDependent.useDirectBufferNoCleaner()) {
                PlatformDependent.freeDirectNoCleaner((ByteBuffer) chunk.memory);
            } else {
                PlatformDependent.freeDirectBuffer((ByteBuffer) chunk.memory);
            }
        }

        protected PooledByteBuf<ByteBuffer> newByteBuf(int maxCapacity) {
            if (HAS_UNSAFE) {
                return PooledUnsafeDirectByteBuf.newInstance(maxCapacity);
            }
            return PooledDirectByteBuf.newInstance(maxCapacity);
        }

        protected void memoryCopy(ByteBuffer src, int srcOffset, ByteBuffer dst, int dstOffset, int length) {
            if (length != 0) {
                if (HAS_UNSAFE) {
                    PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(src) + ((long) srcOffset), PlatformDependent.directBufferAddress(dst) + ((long) dstOffset), (long) length);
                    return;
                }
                src = src.duplicate();
                dst = dst.duplicate();
                src.position(srcOffset).limit(srcOffset + length);
                dst.position(dstOffset);
                dst.put(src);
            }
        }
    }

    static final class HeapArena extends PoolArena<byte[]> {
        HeapArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
            super(parent, pageSize, maxOrder, pageShifts, chunkSize);
        }

        boolean isDirect() {
            return false;
        }

        protected PoolChunk<byte[]> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
            return new PoolChunk(this, new byte[chunkSize], pageSize, maxOrder, pageShifts, chunkSize);
        }

        protected PoolChunk<byte[]> newUnpooledChunk(int capacity) {
            return new PoolChunk(this, new byte[capacity], capacity);
        }

        protected void destroyChunk(PoolChunk<byte[]> poolChunk) {
        }

        protected PooledByteBuf<byte[]> newByteBuf(int maxCapacity) {
            return HAS_UNSAFE ? PooledUnsafeHeapByteBuf.newUnsafeInstance(maxCapacity) : PooledHeapByteBuf.newInstance(maxCapacity);
        }

        protected void memoryCopy(byte[] src, int srcOffset, byte[] dst, int dstOffset, int length) {
            if (length != 0) {
                System.arraycopy(src, srcOffset, dst, dstOffset, length);
            }
        }
    }

    enum SizeClass {
        Tiny,
        Small,
        Normal
    }

    protected abstract void destroyChunk(PoolChunk<T> poolChunk);

    abstract boolean isDirect();

    protected abstract void memoryCopy(T t, int i, T t2, int i2, int i3);

    protected abstract PooledByteBuf<T> newByteBuf(int i);

    protected abstract PoolChunk<T> newChunk(int i, int i2, int i3, int i4);

    protected abstract PoolChunk<T> newUnpooledChunk(int i);

    protected PoolArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
        int i;
        this.parent = parent;
        this.pageSize = pageSize;
        this.maxOrder = maxOrder;
        this.pageShifts = pageShifts;
        this.chunkSize = chunkSize;
        this.subpageOverflowMask = (pageSize - 1) ^ -1;
        this.tinySubpagePools = newSubpagePoolArray(32);
        for (i = 0; i < this.tinySubpagePools.length; i++) {
            this.tinySubpagePools[i] = newSubpagePoolHead(pageSize);
        }
        this.numSmallSubpagePools = pageShifts - 9;
        this.smallSubpagePools = newSubpagePoolArray(this.numSmallSubpagePools);
        for (i = 0; i < this.smallSubpagePools.length; i++) {
            this.smallSubpagePools[i] = newSubpagePoolHead(pageSize);
        }
        this.q100 = new PoolChunkList(null, 100, Integer.MAX_VALUE, chunkSize);
        this.q075 = new PoolChunkList(this.q100, 75, 100, chunkSize);
        this.q050 = new PoolChunkList(this.q075, 50, 100, chunkSize);
        this.q025 = new PoolChunkList(this.q050, 25, 75, chunkSize);
        this.q000 = new PoolChunkList(this.q025, 1, 50, chunkSize);
        this.qInit = new PoolChunkList(this.q000, Integer.MIN_VALUE, 25, chunkSize);
        this.q100.prevList(this.q075);
        this.q075.prevList(this.q050);
        this.q050.prevList(this.q025);
        this.q025.prevList(this.q000);
        this.q000.prevList(null);
        this.qInit.prevList(this.qInit);
        List<PoolChunkListMetric> metrics = new ArrayList(6);
        metrics.add(this.qInit);
        metrics.add(this.q000);
        metrics.add(this.q025);
        metrics.add(this.q050);
        metrics.add(this.q075);
        metrics.add(this.q100);
        this.chunkListMetrics = Collections.unmodifiableList(metrics);
    }

    private PoolSubpage<T> newSubpagePoolHead(int pageSize) {
        PoolSubpage<T> head = new PoolSubpage(pageSize);
        head.prev = head;
        head.next = head;
        return head;
    }

    private PoolSubpage<T>[] newSubpagePoolArray(int size) {
        return new PoolSubpage[size];
    }

    PooledByteBuf<T> allocate(PoolThreadCache cache, int reqCapacity, int maxCapacity) {
        PooledByteBuf buf = newByteBuf(maxCapacity);
        allocate(cache, buf, reqCapacity);
        return buf;
    }

    static int tinyIdx(int normCapacity) {
        return normCapacity >>> 4;
    }

    static int smallIdx(int normCapacity) {
        int tableIdx = 0;
        int i = normCapacity >>> 10;
        while (i != 0) {
            i >>>= 1;
            tableIdx++;
        }
        return tableIdx;
    }

    boolean isTinyOrSmall(int normCapacity) {
        return (this.subpageOverflowMask & normCapacity) == 0;
    }

    static boolean isTiny(int normCapacity) {
        return (normCapacity & -512) == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void allocate(io.netty.buffer.PoolThreadCache r11, io.netty.buffer.PooledByteBuf<T> r12, int r13) {
        /*
        r10 = this;
        r3 = r10.normalizeCapacity(r13);
        r8 = r10.isTinyOrSmall(r3);
        if (r8 == 0) goto L_0x0073;
    L_0x000a:
        r7 = isTiny(r3);
        if (r7 == 0) goto L_0x0039;
    L_0x0010:
        r8 = r11.allocateTiny(r10, r12, r13, r3);
        if (r8 == 0) goto L_0x0017;
    L_0x0016:
        return;
    L_0x0017:
        r6 = tinyIdx(r3);
        r5 = r10.tinySubpagePools;
    L_0x001d:
        r2 = r5[r6];
        monitor-enter(r2);
        r4 = r2.next;	 Catch:{ all -> 0x0036 }
        if (r4 == r2) goto L_0x006e;
    L_0x0024:
        r8 = $assertionsDisabled;	 Catch:{ all -> 0x0036 }
        if (r8 != 0) goto L_0x0046;
    L_0x0028:
        r8 = r4.doNotDestroy;	 Catch:{ all -> 0x0036 }
        if (r8 == 0) goto L_0x0030;
    L_0x002c:
        r8 = r4.elemSize;	 Catch:{ all -> 0x0036 }
        if (r8 == r3) goto L_0x0046;
    L_0x0030:
        r8 = new java.lang.AssertionError;	 Catch:{ all -> 0x0036 }
        r8.<init>();	 Catch:{ all -> 0x0036 }
        throw r8;	 Catch:{ all -> 0x0036 }
    L_0x0036:
        r8 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0036 }
        throw r8;
    L_0x0039:
        r8 = r11.allocateSmall(r10, r12, r13, r3);
        if (r8 != 0) goto L_0x0016;
    L_0x003f:
        r6 = smallIdx(r3);
        r5 = r10.smallSubpagePools;
        goto L_0x001d;
    L_0x0046:
        r0 = r4.allocate();	 Catch:{ all -> 0x0036 }
        r8 = $assertionsDisabled;	 Catch:{ all -> 0x0036 }
        if (r8 != 0) goto L_0x005a;
    L_0x004e:
        r8 = 0;
        r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r8 >= 0) goto L_0x005a;
    L_0x0054:
        r8 = new java.lang.AssertionError;	 Catch:{ all -> 0x0036 }
        r8.<init>();	 Catch:{ all -> 0x0036 }
        throw r8;	 Catch:{ all -> 0x0036 }
    L_0x005a:
        r8 = r4.chunk;	 Catch:{ all -> 0x0036 }
        r8.initBufWithSubpage(r12, r0, r13);	 Catch:{ all -> 0x0036 }
        if (r7 == 0) goto L_0x0068;
    L_0x0061:
        r8 = r10.allocationsTiny;	 Catch:{ all -> 0x0036 }
        r8.increment();	 Catch:{ all -> 0x0036 }
    L_0x0066:
        monitor-exit(r2);	 Catch:{ all -> 0x0036 }
        goto L_0x0016;
    L_0x0068:
        r8 = r10.allocationsSmall;	 Catch:{ all -> 0x0036 }
        r8.increment();	 Catch:{ all -> 0x0036 }
        goto L_0x0066;
    L_0x006e:
        monitor-exit(r2);	 Catch:{ all -> 0x0036 }
        r10.allocateNormal(r12, r13, r3);
        goto L_0x0016;
    L_0x0073:
        r8 = r10.chunkSize;
        if (r3 > r8) goto L_0x0081;
    L_0x0077:
        r8 = r11.allocateNormal(r10, r12, r13, r3);
        if (r8 != 0) goto L_0x0016;
    L_0x007d:
        r10.allocateNormal(r12, r13, r3);
        goto L_0x0016;
    L_0x0081:
        r10.allocateHuge(r12, r13);
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.PoolArena.allocate(io.netty.buffer.PoolThreadCache, io.netty.buffer.PooledByteBuf, int):void");
    }

    private synchronized void allocateNormal(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
        if (this.q050.allocate(buf, reqCapacity, normCapacity) || this.q025.allocate(buf, reqCapacity, normCapacity) || this.q000.allocate(buf, reqCapacity, normCapacity) || this.qInit.allocate(buf, reqCapacity, normCapacity) || this.q075.allocate(buf, reqCapacity, normCapacity)) {
            this.allocationsNormal++;
        } else {
            PoolChunk<T> c = newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
            long handle = c.allocate(normCapacity);
            this.allocationsNormal++;
            if ($assertionsDisabled || handle > 0) {
                c.initBuf(buf, handle, reqCapacity);
                this.qInit.add(c);
            } else {
                throw new AssertionError();
            }
        }
    }

    private void allocateHuge(PooledByteBuf<T> buf, int reqCapacity) {
        PoolChunk<T> chunk = newUnpooledChunk(reqCapacity);
        this.activeBytesHuge.add((long) chunk.chunkSize());
        buf.initUnpooled(chunk, reqCapacity);
        this.allocationsHuge.increment();
    }

    void free(PoolChunk<T> chunk, long handle, int normCapacity, PoolThreadCache cache) {
        if (chunk.unpooled) {
            int size = chunk.chunkSize();
            destroyChunk(chunk);
            this.activeBytesHuge.add((long) (-size));
            this.deallocationsHuge.increment();
            return;
        }
        SizeClass sizeClass = sizeClass(normCapacity);
        if (cache == null || !cache.add(this, chunk, handle, normCapacity, sizeClass)) {
            freeChunk(chunk, handle, sizeClass);
        }
    }

    private SizeClass sizeClass(int normCapacity) {
        if (isTinyOrSmall(normCapacity)) {
            return isTiny(normCapacity) ? SizeClass.Tiny : SizeClass.Small;
        } else {
            return SizeClass.Normal;
        }
    }

    void freeChunk(PoolChunk<T> chunk, long handle, SizeClass sizeClass) {
        synchronized (this) {
            switch (sizeClass) {
                case Normal:
                    this.deallocationsNormal++;
                    break;
                case Small:
                    this.deallocationsSmall++;
                    break;
                case Tiny:
                    this.deallocationsTiny++;
                    break;
                default:
                    throw new Error();
            }
            boolean destroyChunk = !chunk.parent.free(chunk, handle);
        }
        if (destroyChunk) {
            destroyChunk(chunk);
        }
    }

    PoolSubpage<T> findSubpagePoolHead(int elemSize) {
        int tableIdx;
        PoolSubpage<T>[] table;
        if (isTiny(elemSize)) {
            tableIdx = elemSize >>> 4;
            table = this.tinySubpagePools;
        } else {
            tableIdx = 0;
            elemSize >>>= 10;
            while (elemSize != 0) {
                elemSize >>>= 1;
                tableIdx++;
            }
            table = this.smallSubpagePools;
        }
        return table[tableIdx];
    }

    int normalizeCapacity(int reqCapacity) {
        if (reqCapacity < 0) {
            throw new IllegalArgumentException("capacity: " + reqCapacity + " (expected: 0+)");
        } else if (reqCapacity >= this.chunkSize) {
            return reqCapacity;
        } else {
            if (isTiny(reqCapacity)) {
                return (reqCapacity & 15) != 0 ? (reqCapacity & -16) + 16 : reqCapacity;
            } else {
                int normalizedCapacity = reqCapacity - 1;
                normalizedCapacity |= normalizedCapacity >>> 1;
                normalizedCapacity |= normalizedCapacity >>> 2;
                normalizedCapacity |= normalizedCapacity >>> 4;
                normalizedCapacity |= normalizedCapacity >>> 8;
                normalizedCapacity = (normalizedCapacity | (normalizedCapacity >>> 16)) + 1;
                if (normalizedCapacity < 0) {
                    normalizedCapacity >>>= 1;
                }
                return normalizedCapacity;
            }
        }
    }

    void reallocate(PooledByteBuf<T> buf, int newCapacity, boolean freeOldMemory) {
        if (newCapacity < 0 || newCapacity > buf.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + newCapacity);
        }
        int oldCapacity = buf.length;
        if (oldCapacity != newCapacity) {
            PoolChunk<T> oldChunk = buf.chunk;
            long oldHandle = buf.handle;
            T oldMemory = buf.memory;
            int oldOffset = buf.offset;
            int oldMaxLength = buf.maxLength;
            int readerIndex = buf.readerIndex();
            int writerIndex = buf.writerIndex();
            allocate(this.parent.threadCache(), (PooledByteBuf) buf, newCapacity);
            if (newCapacity > oldCapacity) {
                memoryCopy(oldMemory, oldOffset, buf.memory, buf.offset, oldCapacity);
            } else if (newCapacity < oldCapacity) {
                if (readerIndex < newCapacity) {
                    if (writerIndex > newCapacity) {
                        writerIndex = newCapacity;
                    }
                    memoryCopy(oldMemory, oldOffset + readerIndex, buf.memory, buf.offset + readerIndex, writerIndex - readerIndex);
                } else {
                    writerIndex = newCapacity;
                    readerIndex = newCapacity;
                }
            }
            buf.setIndex(readerIndex, writerIndex);
            if (freeOldMemory) {
                free(oldChunk, oldHandle, oldMaxLength, buf.cache);
            }
        }
    }

    public int numThreadCaches() {
        return this.numThreadCaches.get();
    }

    public int numTinySubpages() {
        return this.tinySubpagePools.length;
    }

    public int numSmallSubpages() {
        return this.smallSubpagePools.length;
    }

    public int numChunkLists() {
        return this.chunkListMetrics.size();
    }

    public List<PoolSubpageMetric> tinySubpages() {
        return subPageMetricList(this.tinySubpagePools);
    }

    public List<PoolSubpageMetric> smallSubpages() {
        return subPageMetricList(this.smallSubpagePools);
    }

    public List<PoolChunkListMetric> chunkLists() {
        return this.chunkListMetrics;
    }

    private static List<PoolSubpageMetric> subPageMetricList(PoolSubpage<?>[] pages) {
        List<PoolSubpageMetric> metrics = new ArrayList();
        for (PoolSubpage<?> head : pages) {
            if (head.next != head) {
                PoolSubpage<?> s = head.next;
                do {
                    metrics.add(s);
                    s = s.next;
                } while (s != head);
            }
        }
        return metrics;
    }

    public long numAllocations() {
        long allocsNormal;
        synchronized (this) {
            allocsNormal = this.allocationsNormal;
        }
        return ((this.allocationsTiny.value() + this.allocationsSmall.value()) + allocsNormal) + this.allocationsHuge.value();
    }

    public long numTinyAllocations() {
        return this.allocationsTiny.value();
    }

    public long numSmallAllocations() {
        return this.allocationsSmall.value();
    }

    public synchronized long numNormalAllocations() {
        return this.allocationsNormal;
    }

    public long numDeallocations() {
        long deallocs;
        synchronized (this) {
            deallocs = (this.deallocationsTiny + this.deallocationsSmall) + this.deallocationsNormal;
        }
        return this.deallocationsHuge.value() + deallocs;
    }

    public synchronized long numTinyDeallocations() {
        return this.deallocationsTiny;
    }

    public synchronized long numSmallDeallocations() {
        return this.deallocationsSmall;
    }

    public synchronized long numNormalDeallocations() {
        return this.deallocationsNormal;
    }

    public long numHugeAllocations() {
        return this.allocationsHuge.value();
    }

    public long numHugeDeallocations() {
        return this.deallocationsHuge.value();
    }

    public long numActiveAllocations() {
        long val = ((this.allocationsTiny.value() + this.allocationsSmall.value()) + this.allocationsHuge.value()) - this.deallocationsHuge.value();
        synchronized (this) {
            val += this.allocationsNormal - ((this.deallocationsTiny + this.deallocationsSmall) + this.deallocationsNormal);
        }
        return Math.max(val, 0);
    }

    public long numActiveTinyAllocations() {
        return Math.max(numTinyAllocations() - numTinyDeallocations(), 0);
    }

    public long numActiveSmallAllocations() {
        return Math.max(numSmallAllocations() - numSmallDeallocations(), 0);
    }

    public long numActiveNormalAllocations() {
        long val;
        synchronized (this) {
            val = this.allocationsNormal - this.deallocationsNormal;
        }
        return Math.max(val, 0);
    }

    public long numActiveHugeAllocations() {
        return Math.max(numHugeAllocations() - numHugeDeallocations(), 0);
    }

    public long numActiveBytes() {
        long val = this.activeBytesHuge.value();
        synchronized (this) {
            for (int i = 0; i < this.chunkListMetrics.size(); i++) {
                for (PoolChunkMetric m : (PoolChunkListMetric) this.chunkListMetrics.get(i)) {
                    val += (long) m.chunkSize();
                }
            }
        }
        return Math.max(0, val);
    }

    public synchronized String toString() {
        StringBuilder buf;
        buf = new StringBuilder().append("Chunk(s) at 0~25%:").append(StringUtil.NEWLINE).append(this.qInit).append(StringUtil.NEWLINE).append("Chunk(s) at 0~50%:").append(StringUtil.NEWLINE).append(this.q000).append(StringUtil.NEWLINE).append("Chunk(s) at 25~75%:").append(StringUtil.NEWLINE).append(this.q025).append(StringUtil.NEWLINE).append("Chunk(s) at 50~100%:").append(StringUtil.NEWLINE).append(this.q050).append(StringUtil.NEWLINE).append("Chunk(s) at 75~100%:").append(StringUtil.NEWLINE).append(this.q075).append(StringUtil.NEWLINE).append("Chunk(s) at 100%:").append(StringUtil.NEWLINE).append(this.q100).append(StringUtil.NEWLINE).append("tiny subpages:");
        appendPoolSubPages(buf, this.tinySubpagePools);
        buf.append(StringUtil.NEWLINE).append("small subpages:");
        appendPoolSubPages(buf, this.smallSubpagePools);
        buf.append(StringUtil.NEWLINE);
        return buf.toString();
    }

    private static void appendPoolSubPages(StringBuilder buf, PoolSubpage<?>[] subpages) {
        for (int i = 0; i < subpages.length; i++) {
            PoolSubpage<?> head = subpages[i];
            if (head.next != head) {
                buf.append(StringUtil.NEWLINE).append(i).append(": ");
                PoolSubpage<?> s = head.next;
                do {
                    buf.append(s);
                    s = s.next;
                } while (s != head);
            }
        }
    }
}
