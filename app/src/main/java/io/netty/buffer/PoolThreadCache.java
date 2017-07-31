package io.netty.buffer;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.ThreadDeathWatcher;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.Queue;

final class PoolThreadCache {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
    private int allocations;
    final PoolArena<ByteBuffer> directArena;
    private final int freeSweepAllocationThreshold;
    private final Runnable freeTask = new Runnable() {
        public void run() {
            PoolThreadCache.this.free0();
        }
    };
    final PoolArena<byte[]> heapArena;
    private final MemoryRegionCache<ByteBuffer>[] normalDirectCaches;
    private final MemoryRegionCache<byte[]>[] normalHeapCaches;
    private final int numShiftsNormalDirect;
    private final int numShiftsNormalHeap;
    private final MemoryRegionCache<ByteBuffer>[] smallSubPageDirectCaches;
    private final MemoryRegionCache<byte[]>[] smallSubPageHeapCaches;
    private final Thread thread = Thread.currentThread();
    private final MemoryRegionCache<ByteBuffer>[] tinySubPageDirectCaches;
    private final MemoryRegionCache<byte[]>[] tinySubPageHeapCaches;

    private static abstract class MemoryRegionCache<T> {
        private static final Recycler<Entry> RECYCLER = new Recycler<Entry>() {
            protected Entry newObject(Handle<Entry> handle) {
                return new Entry(handle);
            }
        };
        private int allocations;
        private final Queue<Entry<T>> queue = PlatformDependent.newFixedMpscQueue(this.size);
        private final int size;
        private final SizeClass sizeClass;

        static final class Entry<T> {
            PoolChunk<T> chunk;
            long handle = -1;
            final Handle<Entry<?>> recyclerHandle;

            Entry(Handle<Entry<?>> recyclerHandle) {
                this.recyclerHandle = recyclerHandle;
            }

            void recycle() {
                this.chunk = null;
                this.handle = -1;
                this.recyclerHandle.recycle(this);
            }
        }

        protected abstract void initBuf(PoolChunk<T> poolChunk, long j, PooledByteBuf<T> pooledByteBuf, int i);

        MemoryRegionCache(int size, SizeClass sizeClass) {
            this.size = MathUtil.safeFindNextPositivePowerOfTwo(size);
            this.sizeClass = sizeClass;
        }

        public final boolean add(PoolChunk<T> chunk, long handle) {
            Entry<T> entry = newEntry(chunk, handle);
            boolean queued = this.queue.offer(entry);
            if (!queued) {
                entry.recycle();
            }
            return queued;
        }

        public final boolean allocate(PooledByteBuf<T> buf, int reqCapacity) {
            Entry<T> entry = (Entry) this.queue.poll();
            if (entry == null) {
                return false;
            }
            initBuf(entry.chunk, entry.handle, buf, reqCapacity);
            entry.recycle();
            this.allocations++;
            return true;
        }

        public final int free() {
            return free(Integer.MAX_VALUE);
        }

        private int free(int max) {
            int numFreed = 0;
            while (numFreed < max) {
                Entry<T> entry = (Entry) this.queue.poll();
                if (entry == null) {
                    break;
                }
                freeEntry(entry);
                numFreed++;
            }
            return numFreed;
        }

        public final void trim() {
            int free = this.size - this.allocations;
            this.allocations = 0;
            if (free > 0) {
                free(free);
            }
        }

        private void freeEntry(Entry entry) {
            PoolChunk chunk = entry.chunk;
            long handle = entry.handle;
            entry.recycle();
            chunk.arena.freeChunk(chunk, handle, this.sizeClass);
        }

        private static Entry newEntry(PoolChunk<?> chunk, long handle) {
            Entry entry = (Entry) RECYCLER.get();
            entry.chunk = chunk;
            entry.handle = handle;
            return entry;
        }
    }

    private static final class NormalMemoryRegionCache<T> extends MemoryRegionCache<T> {
        NormalMemoryRegionCache(int size) {
            super(size, SizeClass.Normal);
        }

        protected void initBuf(PoolChunk<T> chunk, long handle, PooledByteBuf<T> buf, int reqCapacity) {
            chunk.initBuf(buf, handle, reqCapacity);
        }
    }

    private static final class SubPageMemoryRegionCache<T> extends MemoryRegionCache<T> {
        SubPageMemoryRegionCache(int size, SizeClass sizeClass) {
            super(size, sizeClass);
        }

        protected void initBuf(PoolChunk<T> chunk, long handle, PooledByteBuf<T> buf, int reqCapacity) {
            chunk.initBufWithSubpage(buf, handle, reqCapacity);
        }
    }

    PoolThreadCache(PoolArena<byte[]> heapArena, PoolArena<ByteBuffer> directArena, int tinyCacheSize, int smallCacheSize, int normalCacheSize, int maxCachedBufferCapacity, int freeSweepAllocationThreshold) {
        if (maxCachedBufferCapacity < 0) {
            throw new IllegalArgumentException("maxCachedBufferCapacity: " + maxCachedBufferCapacity + " (expected: >= 0)");
        } else if (freeSweepAllocationThreshold < 1) {
            throw new IllegalArgumentException("freeSweepAllocationThreshold: " + freeSweepAllocationThreshold + " (expected: > 0)");
        } else {
            this.freeSweepAllocationThreshold = freeSweepAllocationThreshold;
            this.heapArena = heapArena;
            this.directArena = directArena;
            if (directArena != null) {
                this.tinySubPageDirectCaches = createSubPageCaches(tinyCacheSize, 32, SizeClass.Tiny);
                this.smallSubPageDirectCaches = createSubPageCaches(smallCacheSize, directArena.numSmallSubpagePools, SizeClass.Small);
                this.numShiftsNormalDirect = log2(directArena.pageSize);
                this.normalDirectCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, directArena);
                directArena.numThreadCaches.getAndIncrement();
            } else {
                this.tinySubPageDirectCaches = null;
                this.smallSubPageDirectCaches = null;
                this.normalDirectCaches = null;
                this.numShiftsNormalDirect = -1;
            }
            if (heapArena != null) {
                this.tinySubPageHeapCaches = createSubPageCaches(tinyCacheSize, 32, SizeClass.Tiny);
                this.smallSubPageHeapCaches = createSubPageCaches(smallCacheSize, heapArena.numSmallSubpagePools, SizeClass.Small);
                this.numShiftsNormalHeap = log2(heapArena.pageSize);
                this.normalHeapCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, heapArena);
                heapArena.numThreadCaches.getAndIncrement();
            } else {
                this.tinySubPageHeapCaches = null;
                this.smallSubPageHeapCaches = null;
                this.normalHeapCaches = null;
                this.numShiftsNormalHeap = -1;
            }
            ThreadDeathWatcher.watch(this.thread, this.freeTask);
        }
    }

    private static <T> MemoryRegionCache<T>[] createSubPageCaches(int cacheSize, int numCaches, SizeClass sizeClass) {
        if (cacheSize <= 0) {
            return null;
        }
        MemoryRegionCache<T>[] memoryRegionCacheArr = new MemoryRegionCache[numCaches];
        for (int i = 0; i < memoryRegionCacheArr.length; i++) {
            memoryRegionCacheArr[i] = new SubPageMemoryRegionCache(cacheSize, sizeClass);
        }
        return memoryRegionCacheArr;
    }

    private static <T> MemoryRegionCache<T>[] createNormalCaches(int cacheSize, int maxCachedBufferCapacity, PoolArena<T> area) {
        if (cacheSize <= 0) {
            return null;
        }
        MemoryRegionCache<T>[] memoryRegionCacheArr = new MemoryRegionCache[Math.max(1, log2(Math.min(area.chunkSize, maxCachedBufferCapacity) / area.pageSize) + 1)];
        for (int i = 0; i < memoryRegionCacheArr.length; i++) {
            memoryRegionCacheArr[i] = new NormalMemoryRegionCache(cacheSize);
        }
        return memoryRegionCacheArr;
    }

    private static int log2(int val) {
        int res = 0;
        while (val > 1) {
            val >>= 1;
            res++;
        }
        return res;
    }

    boolean allocateTiny(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
        return allocate(cacheForTiny(area, normCapacity), buf, reqCapacity);
    }

    boolean allocateSmall(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
        return allocate(cacheForSmall(area, normCapacity), buf, reqCapacity);
    }

    boolean allocateNormal(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
        return allocate(cacheForNormal(area, normCapacity), buf, reqCapacity);
    }

    private boolean allocate(MemoryRegionCache<?> cache, PooledByteBuf buf, int reqCapacity) {
        if (cache == null) {
            return false;
        }
        boolean allocated = cache.allocate(buf, reqCapacity);
        int i = this.allocations + 1;
        this.allocations = i;
        if (i < this.freeSweepAllocationThreshold) {
            return allocated;
        }
        this.allocations = 0;
        trim();
        return allocated;
    }

    boolean add(PoolArena<?> area, PoolChunk chunk, long handle, int normCapacity, SizeClass sizeClass) {
        MemoryRegionCache<?> cache = cache(area, normCapacity, sizeClass);
        if (cache == null) {
            return false;
        }
        return cache.add(chunk, handle);
    }

    private MemoryRegionCache<?> cache(PoolArena<?> area, int normCapacity, SizeClass sizeClass) {
        switch (sizeClass) {
            case Normal:
                return cacheForNormal(area, normCapacity);
            case Small:
                return cacheForSmall(area, normCapacity);
            case Tiny:
                return cacheForTiny(area, normCapacity);
            default:
                throw new Error();
        }
    }

    void free() {
        ThreadDeathWatcher.unwatch(this.thread, this.freeTask);
        free0();
    }

    private void free0() {
        int numFreed = ((((free(this.tinySubPageDirectCaches) + free(this.smallSubPageDirectCaches)) + free(this.normalDirectCaches)) + free(this.tinySubPageHeapCaches)) + free(this.smallSubPageHeapCaches)) + free(this.normalHeapCaches);
        if (numFreed > 0 && logger.isDebugEnabled()) {
            logger.debug("Freed {} thread-local buffer(s) from thread: {}", Integer.valueOf(numFreed), this.thread.getName());
        }
        if (this.directArena != null) {
            this.directArena.numThreadCaches.getAndDecrement();
        }
        if (this.heapArena != null) {
            this.heapArena.numThreadCaches.getAndDecrement();
        }
    }

    private static int free(MemoryRegionCache<?>[] caches) {
        if (caches == null) {
            return 0;
        }
        int numFreed = 0;
        for (MemoryRegionCache c : caches) {
            numFreed += free(c);
        }
        return numFreed;
    }

    private static int free(MemoryRegionCache<?> cache) {
        if (cache == null) {
            return 0;
        }
        return cache.free();
    }

    void trim() {
        trim(this.tinySubPageDirectCaches);
        trim(this.smallSubPageDirectCaches);
        trim(this.normalDirectCaches);
        trim(this.tinySubPageHeapCaches);
        trim(this.smallSubPageHeapCaches);
        trim(this.normalHeapCaches);
    }

    private static void trim(MemoryRegionCache<?>[] caches) {
        if (caches != null) {
            for (MemoryRegionCache c : caches) {
                trim(c);
            }
        }
    }

    private static void trim(MemoryRegionCache<?> cache) {
        if (cache != null) {
            cache.trim();
        }
    }

    private MemoryRegionCache<?> cacheForTiny(PoolArena<?> area, int normCapacity) {
        int idx = PoolArena.tinyIdx(normCapacity);
        if (area.isDirect()) {
            return cache(this.tinySubPageDirectCaches, idx);
        }
        return cache(this.tinySubPageHeapCaches, idx);
    }

    private MemoryRegionCache<?> cacheForSmall(PoolArena<?> area, int normCapacity) {
        int idx = PoolArena.smallIdx(normCapacity);
        if (area.isDirect()) {
            return cache(this.smallSubPageDirectCaches, idx);
        }
        return cache(this.smallSubPageHeapCaches, idx);
    }

    private MemoryRegionCache<?> cacheForNormal(PoolArena<?> area, int normCapacity) {
        if (area.isDirect()) {
            return cache(this.normalDirectCaches, log2(normCapacity >> this.numShiftsNormalDirect));
        }
        return cache(this.normalHeapCaches, log2(normCapacity >> this.numShiftsNormalHeap));
    }

    private static <T> MemoryRegionCache<T> cache(MemoryRegionCache<T>[] cache, int idx) {
        if (cache == null || idx > cache.length - 1) {
            return null;
        }
        return cache[idx];
    }
}
