package io.netty.buffer;

final class PoolSubpage<T> implements PoolSubpageMetric {
    static final /* synthetic */ boolean $assertionsDisabled = (!PoolSubpage.class.desiredAssertionStatus());
    private final long[] bitmap;
    private int bitmapLength;
    final PoolChunk<T> chunk;
    boolean doNotDestroy;
    int elemSize;
    private int maxNumElems;
    private final int memoryMapIdx;
    PoolSubpage<T> next;
    private int nextAvail;
    private int numAvail;
    private final int pageSize;
    PoolSubpage<T> prev;
    private final int runOffset;

    PoolSubpage(int pageSize) {
        this.chunk = null;
        this.memoryMapIdx = -1;
        this.runOffset = -1;
        this.elemSize = -1;
        this.pageSize = pageSize;
        this.bitmap = null;
    }

    PoolSubpage(PoolSubpage<T> head, PoolChunk<T> chunk, int memoryMapIdx, int runOffset, int pageSize, int elemSize) {
        this.chunk = chunk;
        this.memoryMapIdx = memoryMapIdx;
        this.runOffset = runOffset;
        this.pageSize = pageSize;
        this.bitmap = new long[(pageSize >>> 10)];
        init(head, elemSize);
    }

    void init(PoolSubpage<T> head, int elemSize) {
        this.doNotDestroy = true;
        this.elemSize = elemSize;
        if (elemSize != 0) {
            int i = this.pageSize / elemSize;
            this.numAvail = i;
            this.maxNumElems = i;
            this.nextAvail = 0;
            this.bitmapLength = this.maxNumElems >>> 6;
            if ((this.maxNumElems & 63) != 0) {
                this.bitmapLength++;
            }
            for (int i2 = 0; i2 < this.bitmapLength; i2++) {
                this.bitmap[i2] = 0;
            }
        }
        addToPool(head);
    }

    long allocate() {
        if (this.elemSize == 0) {
            return toHandle(0);
        }
        if (this.numAvail == 0 || !this.doNotDestroy) {
            return -1;
        }
        int bitmapIdx = getNextAvail();
        int q = bitmapIdx >>> 6;
        int r = bitmapIdx & 63;
        if ($assertionsDisabled || ((this.bitmap[q] >>> r) & 1) == 0) {
            long[] jArr = this.bitmap;
            jArr[q] = jArr[q] | (1 << r);
            int i = this.numAvail - 1;
            this.numAvail = i;
            if (i == 0) {
                removeFromPool();
            }
            return toHandle(bitmapIdx);
        }
        throw new AssertionError();
    }

    boolean free(PoolSubpage<T> head, int bitmapIdx) {
        if (this.elemSize == 0) {
            return true;
        }
        int q = bitmapIdx >>> 6;
        int r = bitmapIdx & 63;
        if ($assertionsDisabled || ((this.bitmap[q] >>> r) & 1) != 0) {
            long[] jArr = this.bitmap;
            jArr[q] = jArr[q] ^ (1 << r);
            setNextAvail(bitmapIdx);
            int i = this.numAvail;
            this.numAvail = i + 1;
            if (i == 0) {
                addToPool(head);
                return true;
            } else if (this.numAvail != this.maxNumElems || this.prev == this.next) {
                return true;
            } else {
                this.doNotDestroy = false;
                removeFromPool();
                return false;
            }
        }
        throw new AssertionError();
    }

    private void addToPool(PoolSubpage<T> head) {
        if ($assertionsDisabled || (this.prev == null && this.next == null)) {
            this.prev = head;
            this.next = head.next;
            this.next.prev = this;
            head.next = this;
            return;
        }
        throw new AssertionError();
    }

    private void removeFromPool() {
        if ($assertionsDisabled || !(this.prev == null || this.next == null)) {
            this.prev.next = this.next;
            this.next.prev = this.prev;
            this.next = null;
            this.prev = null;
            return;
        }
        throw new AssertionError();
    }

    private void setNextAvail(int bitmapIdx) {
        this.nextAvail = bitmapIdx;
    }

    private int getNextAvail() {
        int nextAvail = this.nextAvail;
        if (nextAvail < 0) {
            return findNextAvail();
        }
        this.nextAvail = -1;
        return nextAvail;
    }

    private int findNextAvail() {
        long[] bitmap = this.bitmap;
        int bitmapLength = this.bitmapLength;
        for (int i = 0; i < bitmapLength; i++) {
            long bits = bitmap[i];
            if ((-1 ^ bits) != 0) {
                return findNextAvail0(i, bits);
            }
        }
        return -1;
    }

    private int findNextAvail0(int i, long bits) {
        int maxNumElems = this.maxNumElems;
        int baseVal = i << 6;
        for (int j = 0; j < 64; j++) {
            if ((1 & bits) == 0) {
                int val = baseVal | j;
                if (val < maxNumElems) {
                    return val;
                }
                return -1;
            }
            bits >>>= 1;
        }
        return -1;
    }

    private long toHandle(int bitmapIdx) {
        return (4611686018427387904L | (((long) bitmapIdx) << 32)) | ((long) this.memoryMapIdx);
    }

    public String toString() {
        if (this.doNotDestroy) {
            return String.valueOf('(') + this.memoryMapIdx + ": " + (this.maxNumElems - this.numAvail) + '/' + this.maxNumElems + ", offset: " + this.runOffset + ", length: " + this.pageSize + ", elemSize: " + this.elemSize + ')';
        }
        return "(" + this.memoryMapIdx + ": not in use)";
    }

    public int maxNumElements() {
        return this.maxNumElems;
    }

    public int numAvailable() {
        return this.numAvail;
    }

    public int elementSize() {
        return this.elemSize;
    }

    public int pageSize() {
        return this.pageSize;
    }
}
