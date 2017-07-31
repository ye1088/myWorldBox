package io.netty.buffer;

final class PoolChunk<T> implements PoolChunkMetric {
    static final /* synthetic */ boolean $assertionsDisabled = (!PoolChunk.class.desiredAssertionStatus());
    private static final int INTEGER_SIZE_MINUS_ONE = 31;
    final PoolArena<T> arena;
    private final int chunkSize;
    private final byte[] depthMap;
    private int freeBytes;
    private final int log2ChunkSize;
    private final int maxOrder;
    private final int maxSubpageAllocs;
    final T memory;
    private final byte[] memoryMap;
    PoolChunk<T> next;
    private final int pageShifts;
    private final int pageSize;
    PoolChunkList<T> parent;
    PoolChunk<T> prev;
    private final int subpageOverflowMask;
    private final PoolSubpage<T>[] subpages;
    final boolean unpooled = true;
    private final byte unusable;

    PoolChunk(PoolArena<T> arena, T memory, int pageSize, int maxOrder, int pageShifts, int chunkSize) {
        this.arena = arena;
        this.memory = memory;
        this.pageSize = pageSize;
        this.pageShifts = pageShifts;
        this.maxOrder = maxOrder;
        this.chunkSize = chunkSize;
        this.unusable = (byte) (maxOrder + 1);
        this.log2ChunkSize = log2(chunkSize);
        this.subpageOverflowMask = (pageSize - 1) ^ -1;
        this.freeBytes = chunkSize;
        if ($assertionsDisabled || maxOrder < 30) {
            this.maxSubpageAllocs = 1 << maxOrder;
            this.memoryMap = new byte[(this.maxSubpageAllocs << 1)];
            this.depthMap = new byte[this.memoryMap.length];
            int memoryMapIndex = 1;
            for (int d = 0; d <= maxOrder; d++) {
                int depth = 1 << d;
                for (int p = 0; p < depth; p++) {
                    this.memoryMap[memoryMapIndex] = (byte) d;
                    this.depthMap[memoryMapIndex] = (byte) d;
                    memoryMapIndex++;
                }
            }
            this.subpages = newSubpageArray(this.maxSubpageAllocs);
            return;
        }
        throw new AssertionError("maxOrder should be < 30, but is: " + maxOrder);
    }

    PoolChunk(PoolArena<T> arena, T memory, int size) {
        this.arena = arena;
        this.memory = memory;
        this.memoryMap = null;
        this.depthMap = null;
        this.subpages = null;
        this.subpageOverflowMask = 0;
        this.pageSize = 0;
        this.pageShifts = 0;
        this.maxOrder = 0;
        this.unusable = (byte) (this.maxOrder + 1);
        this.chunkSize = size;
        this.log2ChunkSize = log2(this.chunkSize);
        this.maxSubpageAllocs = 0;
    }

    private PoolSubpage<T>[] newSubpageArray(int size) {
        return new PoolSubpage[size];
    }

    public int usage() {
        int freeBytes = this.freeBytes;
        if (freeBytes == 0) {
            return 100;
        }
        int freePercentage = (int) ((((long) freeBytes) * 100) / ((long) this.chunkSize));
        if (freePercentage == 0) {
            return 99;
        }
        return 100 - freePercentage;
    }

    long allocate(int normCapacity) {
        if ((this.subpageOverflowMask & normCapacity) != 0) {
            return allocateRun(normCapacity);
        }
        return allocateSubpage(normCapacity);
    }

    private void updateParentsAlloc(int id) {
        while (id > 1) {
            byte val;
            int parentId = id >>> 1;
            byte val1 = value(id);
            byte val2 = value(id ^ 1);
            if (val1 < val2) {
                val = val1;
            } else {
                val = val2;
            }
            setValue(parentId, val);
            id = parentId;
        }
    }

    private void updateParentsFree(int id) {
        int logChild = depth(id) + 1;
        while (id > 1) {
            int parentId = id >>> 1;
            byte val1 = value(id);
            byte val2 = value(id ^ 1);
            byte logChild2 = logChild - 1;
            if (val1 == logChild2 && val2 == logChild2) {
                setValue(parentId, (byte) (logChild2 - 1));
            } else {
                byte val;
                if (val1 < val2) {
                    val = val1;
                } else {
                    val = val2;
                }
                setValue(parentId, val);
            }
            id = parentId;
        }
    }

    private int allocateNode(int d) {
        int id = 1;
        int initial = -(1 << d);
        byte val = value(1);
        if (val > d) {
            return -1;
        }
        while (true) {
            if (val >= d && (id & initial) != 0) {
                break;
            }
            id <<= 1;
            val = value(id);
            if (val > d) {
                id ^= 1;
                val = value(id);
            }
        }
        byte value = value(id);
        if ($assertionsDisabled || (value == d && (id & initial) == (1 << d))) {
            setValue(id, this.unusable);
            updateParentsAlloc(id);
            return id;
        }
        throw new AssertionError(String.format("val = %d, id & initial = %d, d = %d", new Object[]{Byte.valueOf(value), Integer.valueOf(id & initial), Integer.valueOf(d)}));
    }

    private long allocateRun(int normCapacity) {
        int id = allocateNode(this.maxOrder - (log2(normCapacity) - this.pageShifts));
        if (id < 0) {
            return (long) id;
        }
        this.freeBytes -= runLength(id);
        return (long) id;
    }

    private long allocateSubpage(int normCapacity) {
        long j;
        PoolSubpage<T> head = this.arena.findSubpagePoolHead(normCapacity);
        synchronized (head) {
            int id = allocateNode(this.maxOrder);
            if (id < 0) {
                j = (long) id;
            } else {
                PoolSubpage<T>[] subpages = this.subpages;
                int pageSize = this.pageSize;
                this.freeBytes -= pageSize;
                int subpageIdx = subpageIdx(id);
                PoolSubpage<T> subpage = subpages[subpageIdx];
                if (subpage == null) {
                    subpage = new PoolSubpage(head, this, id, runOffset(id), pageSize, normCapacity);
                    subpages[subpageIdx] = subpage;
                } else {
                    subpage.init(head, normCapacity);
                }
                j = subpage.allocate();
            }
        }
        return j;
    }

    void free(long handle) {
        int memoryMapIdx = memoryMapIdx(handle);
        int bitmapIdx = bitmapIdx(handle);
        if (bitmapIdx != 0) {
            PoolSubpage<T> subpage = this.subpages[subpageIdx(memoryMapIdx)];
            if ($assertionsDisabled || (subpage != null && subpage.doNotDestroy)) {
                PoolSubpage<T> head = this.arena.findSubpagePoolHead(subpage.elemSize);
                synchronized (head) {
                    if (subpage.free(head, 1073741823 & bitmapIdx)) {
                        return;
                    }
                }
            }
            throw new AssertionError();
        }
        this.freeBytes += runLength(memoryMapIdx);
        setValue(memoryMapIdx, depth(memoryMapIdx));
        updateParentsFree(memoryMapIdx);
    }

    void initBuf(PooledByteBuf<T> buf, long handle, int reqCapacity) {
        int memoryMapIdx = memoryMapIdx(handle);
        int bitmapIdx = bitmapIdx(handle);
        if (bitmapIdx == 0) {
            byte val = value(memoryMapIdx);
            if ($assertionsDisabled || val == this.unusable) {
                buf.init(this, handle, runOffset(memoryMapIdx), reqCapacity, runLength(memoryMapIdx), this.arena.parent.threadCache());
                return;
            }
            throw new AssertionError(String.valueOf(val));
        }
        initBufWithSubpage(buf, handle, bitmapIdx, reqCapacity);
    }

    void initBufWithSubpage(PooledByteBuf<T> buf, long handle, int reqCapacity) {
        initBufWithSubpage(buf, handle, bitmapIdx(handle), reqCapacity);
    }

    private void initBufWithSubpage(PooledByteBuf<T> buf, long handle, int bitmapIdx, int reqCapacity) {
        if ($assertionsDisabled || bitmapIdx != 0) {
            int memoryMapIdx = memoryMapIdx(handle);
            PoolSubpage<T> subpage = this.subpages[subpageIdx(memoryMapIdx)];
            if (!$assertionsDisabled && !subpage.doNotDestroy) {
                throw new AssertionError();
            } else if ($assertionsDisabled || reqCapacity <= subpage.elemSize) {
                buf.init(this, handle, runOffset(memoryMapIdx) + ((1073741823 & bitmapIdx) * subpage.elemSize), reqCapacity, subpage.elemSize, this.arena.parent.threadCache());
                return;
            } else {
                throw new AssertionError();
            }
        }
        throw new AssertionError();
    }

    private byte value(int id) {
        return this.memoryMap[id];
    }

    private void setValue(int id, byte val) {
        this.memoryMap[id] = val;
    }

    private byte depth(int id) {
        return this.depthMap[id];
    }

    private static int log2(int val) {
        return 31 - Integer.numberOfLeadingZeros(val);
    }

    private int runLength(int id) {
        return 1 << (this.log2ChunkSize - depth(id));
    }

    private int runOffset(int id) {
        return runLength(id) * (id ^ (1 << depth(id)));
    }

    private int subpageIdx(int memoryMapIdx) {
        return this.maxSubpageAllocs ^ memoryMapIdx;
    }

    private static int memoryMapIdx(long handle) {
        return (int) handle;
    }

    private static int bitmapIdx(long handle) {
        return (int) (handle >>> 32);
    }

    public int chunkSize() {
        return this.chunkSize;
    }

    public int freeBytes() {
        return this.freeBytes;
    }

    public String toString() {
        return "Chunk(" + Integer.toHexString(System.identityHashCode(this)) + ": " + usage() + "%, " + (this.chunkSize - this.freeBytes) + '/' + this.chunkSize + ')';
    }
}
