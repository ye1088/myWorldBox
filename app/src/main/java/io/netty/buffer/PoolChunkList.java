package io.netty.buffer;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class PoolChunkList<T> implements PoolChunkListMetric {
    static final /* synthetic */ boolean $assertionsDisabled = (!PoolChunkList.class.desiredAssertionStatus());
    private static final Iterator<PoolChunkMetric> EMPTY_METRICS = Collections.emptyList().iterator();
    private PoolChunk<T> head;
    private final int maxCapacity;
    private final int maxUsage;
    private final int minUsage;
    private final PoolChunkList<T> nextList;
    private PoolChunkList<T> prevList;

    PoolChunkList(PoolChunkList<T> nextList, int minUsage, int maxUsage, int chunkSize) {
        if ($assertionsDisabled || minUsage <= maxUsage) {
            this.nextList = nextList;
            this.minUsage = minUsage;
            this.maxUsage = maxUsage;
            this.maxCapacity = calculateMaxCapacity(minUsage, chunkSize);
            return;
        }
        throw new AssertionError();
    }

    private static int calculateMaxCapacity(int minUsage, int chunkSize) {
        minUsage = minUsage0(minUsage);
        if (minUsage == 100) {
            return 0;
        }
        return (int) ((((long) chunkSize) * (100 - ((long) minUsage))) / 100);
    }

    void prevList(PoolChunkList<T> prevList) {
        if ($assertionsDisabled || this.prevList == null) {
            this.prevList = prevList;
            return;
        }
        throw new AssertionError();
    }

    boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
        if (this.head == null || normCapacity > this.maxCapacity) {
            return false;
        }
        PoolChunk<T> cur = this.head;
        do {
            long handle = cur.allocate(normCapacity);
            if (handle < 0) {
                cur = cur.next;
            } else {
                cur.initBuf(buf, handle, reqCapacity);
                if (cur.usage() >= this.maxUsage) {
                    remove(cur);
                    this.nextList.add(cur);
                }
                return true;
            }
        } while (cur != null);
        return false;
    }

    boolean free(PoolChunk<T> chunk, long handle) {
        chunk.free(handle);
        if (chunk.usage() >= this.minUsage) {
            return true;
        }
        remove(chunk);
        return move0(chunk);
    }

    private boolean move(PoolChunk<T> chunk) {
        if (!$assertionsDisabled && chunk.usage() >= this.maxUsage) {
            throw new AssertionError();
        } else if (chunk.usage() < this.minUsage) {
            return move0(chunk);
        } else {
            add0(chunk);
            return true;
        }
    }

    private boolean move0(PoolChunk<T> chunk) {
        if (this.prevList != null) {
            return this.prevList.move(chunk);
        }
        if ($assertionsDisabled || chunk.usage() == 0) {
            return false;
        }
        throw new AssertionError();
    }

    void add(PoolChunk<T> chunk) {
        if (chunk.usage() >= this.maxUsage) {
            this.nextList.add(chunk);
        } else {
            add0(chunk);
        }
    }

    void add0(PoolChunk<T> chunk) {
        chunk.parent = this;
        if (this.head == null) {
            this.head = chunk;
            chunk.prev = null;
            chunk.next = null;
            return;
        }
        chunk.prev = null;
        chunk.next = this.head;
        this.head.prev = chunk;
        this.head = chunk;
    }

    private void remove(PoolChunk<T> cur) {
        if (cur == this.head) {
            this.head = cur.next;
            if (this.head != null) {
                this.head.prev = null;
                return;
            }
            return;
        }
        PoolChunk<T> next = cur.next;
        cur.prev.next = next;
        if (next != null) {
            next.prev = cur.prev;
        }
    }

    public int minUsage() {
        return minUsage0(this.minUsage);
    }

    public int maxUsage() {
        return Math.min(this.maxUsage, 100);
    }

    private static int minUsage0(int value) {
        return Math.max(1, value);
    }

    public Iterator<PoolChunkMetric> iterator() {
        if (this.head == null) {
            return EMPTY_METRICS;
        }
        List<PoolChunkMetric> metrics = new ArrayList();
        PoolChunk<T> cur = this.head;
        do {
            metrics.add(cur);
            cur = cur.next;
        } while (cur != null);
        return metrics.iterator();
    }

    public String toString() {
        if (this.head == null) {
            return "none";
        }
        StringBuilder buf = new StringBuilder();
        PoolChunk<T> cur = this.head;
        while (true) {
            buf.append(cur);
            cur = cur.next;
            if (cur == null) {
                return buf.toString();
            }
            buf.append(StringUtil.NEWLINE);
        }
    }
}
