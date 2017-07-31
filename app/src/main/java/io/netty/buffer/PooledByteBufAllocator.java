package io.netty.buffer;

import io.netty.buffer.PoolArena.DirectArena;
import io.netty.buffer.PoolArena.HeapArena;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PooledByteBufAllocator extends AbstractByteBufAllocator {
    public static final PooledByteBufAllocator DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    private static final int DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
    private static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
    private static final int DEFAULT_MAX_ORDER;
    private static final int DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
    private static final int DEFAULT_NUM_DIRECT_ARENA;
    private static final int DEFAULT_NUM_HEAP_ARENA;
    private static final int DEFAULT_PAGE_SIZE;
    private static final int DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
    private static final int DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
    private static final int MAX_CHUNK_SIZE = 1073741824;
    private static final int MIN_PAGE_SIZE = 4096;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
    private final List<PoolArenaMetric> directArenaMetrics;
    private final PoolArena<ByteBuffer>[] directArenas;
    private final List<PoolArenaMetric> heapArenaMetrics;
    private final PoolArena<byte[]>[] heapArenas;
    private final int normalCacheSize;
    private final int smallCacheSize;
    private final PoolThreadLocalCache threadCache;
    private final int tinyCacheSize;

    static {
        int defaultPageSize = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
        Throwable pageSizeFallbackCause = null;
        try {
            validateAndCalculatePageShifts(defaultPageSize);
        } catch (Throwable t) {
            pageSizeFallbackCause = t;
            defaultPageSize = 8192;
        }
        DEFAULT_PAGE_SIZE = defaultPageSize;
        int defaultMaxOrder = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
        Throwable maxOrderFallbackCause = null;
        try {
            validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, defaultMaxOrder);
        } catch (Throwable t2) {
            maxOrderFallbackCause = t2;
            defaultMaxOrder = 11;
        }
        DEFAULT_MAX_ORDER = defaultMaxOrder;
        Runtime runtime = Runtime.getRuntime();
        int defaultMinNumArena = runtime.availableProcessors() * 2;
        int defaultChunkSize = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
        DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int) Math.min((long) defaultMinNumArena, ((runtime.maxMemory() / ((long) defaultChunkSize)) / 2) / 3)));
        DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int) Math.min((long) defaultMinNumArena, ((PlatformDependent.maxDirectMemory() / ((long) defaultChunkSize)) / 2) / 3)));
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.allocator.numHeapArenas: {}", Integer.valueOf(DEFAULT_NUM_HEAP_ARENA));
            logger.debug("-Dio.netty.allocator.numDirectArenas: {}", Integer.valueOf(DEFAULT_NUM_DIRECT_ARENA));
            if (pageSizeFallbackCause == null) {
                logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE));
            } else {
                logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE), pageSizeFallbackCause);
            }
            if (maxOrderFallbackCause == null) {
                logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER));
            } else {
                logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER), maxOrderFallbackCause);
            }
            logger.debug("-Dio.netty.allocator.chunkSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
            logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", Integer.valueOf(DEFAULT_TINY_CACHE_SIZE));
            logger.debug("-Dio.netty.allocator.smallCacheSize: {}", Integer.valueOf(DEFAULT_SMALL_CACHE_SIZE));
            logger.debug("-Dio.netty.allocator.normalCacheSize: {}", Integer.valueOf(DEFAULT_NORMAL_CACHE_SIZE));
            logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", Integer.valueOf(DEFAULT_MAX_CACHED_BUFFER_CAPACITY));
            logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", Integer.valueOf(DEFAULT_CACHE_TRIM_INTERVAL));
        }
    }

    public PooledByteBufAllocator() {
        this(false);
    }

    public PooledByteBufAllocator(boolean preferDirect) {
        this(preferDirect, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
    }

    public PooledByteBufAllocator(int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
        this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
    }

    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
        this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
    }

    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize) {
        super(preferDirect);
        this.threadCache = new PoolThreadLocalCache(this);
        this.tinyCacheSize = tinyCacheSize;
        this.smallCacheSize = smallCacheSize;
        this.normalCacheSize = normalCacheSize;
        int chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
        if (nHeapArena < 0) {
            throw new IllegalArgumentException("nHeapArena: " + nHeapArena + " (expected: >= 0)");
        } else if (nDirectArena < 0) {
            throw new IllegalArgumentException("nDirectArea: " + nDirectArena + " (expected: >= 0)");
        } else {
            List<PoolArenaMetric> metrics;
            int i;
            int pageShifts = validateAndCalculatePageShifts(pageSize);
            if (nHeapArena > 0) {
                this.heapArenas = newArenaArray(nHeapArena);
                metrics = new ArrayList(this.heapArenas.length);
                for (i = 0; i < this.heapArenas.length; i++) {
                    HeapArena arena = new HeapArena(this, pageSize, maxOrder, pageShifts, chunkSize);
                    this.heapArenas[i] = arena;
                    metrics.add(arena);
                }
                this.heapArenaMetrics = Collections.unmodifiableList(metrics);
            } else {
                this.heapArenas = null;
                this.heapArenaMetrics = Collections.emptyList();
            }
            if (nDirectArena > 0) {
                this.directArenas = newArenaArray(nDirectArena);
                metrics = new ArrayList(this.directArenas.length);
                for (i = 0; i < this.directArenas.length; i++) {
                    DirectArena arena2 = new DirectArena(this, pageSize, maxOrder, pageShifts, chunkSize);
                    this.directArenas[i] = arena2;
                    metrics.add(arena2);
                }
                this.directArenaMetrics = Collections.unmodifiableList(metrics);
                return;
            }
            this.directArenas = null;
            this.directArenaMetrics = Collections.emptyList();
        }
    }

    private static <T> PoolArena<T>[] newArenaArray(int size) {
        return new PoolArena[size];
    }

    private static int validateAndCalculatePageShifts(int pageSize) {
        if (pageSize < 4096) {
            throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: " + 4096 + ")");
        } else if (((pageSize - 1) & pageSize) == 0) {
            return 31 - Integer.numberOfLeadingZeros(pageSize);
        } else {
            throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2)");
        }
    }

    private static int validateAndCalculateChunkSize(int pageSize, int maxOrder) {
        if (maxOrder > 14) {
            throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)");
        }
        int chunkSize = pageSize;
        for (int i = maxOrder; i > 0; i--) {
            if (chunkSize > 536870912) {
                throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[]{Integer.valueOf(pageSize), Integer.valueOf(maxOrder), Integer.valueOf(1073741824)}));
            }
            chunkSize <<= 1;
        }
        return chunkSize;
    }

    protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
        ByteBuf buf;
        PoolThreadCache cache = (PoolThreadCache) this.threadCache.get();
        PoolArena<byte[]> heapArena = cache.heapArena;
        if (heapArena != null) {
            buf = heapArena.allocate(cache, initialCapacity, maxCapacity);
        } else {
            buf = new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
        }
        return toLeakAwareBuffer(buf);
    }

    protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
        ByteBuf buf;
        PoolThreadCache cache = (PoolThreadCache) this.threadCache.get();
        PoolArena<ByteBuffer> directArena = cache.directArena;
        if (directArena != null) {
            buf = directArena.allocate(cache, initialCapacity, maxCapacity);
        } else if (PlatformDependent.hasUnsafe()) {
            buf = UnsafeByteBufUtil.newUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
        } else {
            buf = new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
        }
        return toLeakAwareBuffer(buf);
    }

    public static int defaultNumHeapArena() {
        return DEFAULT_NUM_HEAP_ARENA;
    }

    public static int defaultNumDirectArena() {
        return DEFAULT_NUM_DIRECT_ARENA;
    }

    public static int defaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public static int defaultMaxOrder() {
        return DEFAULT_MAX_ORDER;
    }

    public static int defaultTinyCacheSize() {
        return DEFAULT_TINY_CACHE_SIZE;
    }

    public static int defaultSmallCacheSize() {
        return DEFAULT_SMALL_CACHE_SIZE;
    }

    public static int defaultNormalCacheSize() {
        return DEFAULT_NORMAL_CACHE_SIZE;
    }

    public boolean isDirectBufferPooled() {
        return this.directArenas != null;
    }

    @Deprecated
    public boolean hasThreadLocalCache() {
        return this.threadCache.isSet();
    }

    @Deprecated
    public void freeThreadLocalCache() {
        this.threadCache.remove();
    }

    public int numHeapArenas() {
        return this.heapArenaMetrics.size();
    }

    public int numDirectArenas() {
        return this.directArenaMetrics.size();
    }

    public List<PoolArenaMetric> heapArenas() {
        return this.heapArenaMetrics;
    }

    public List<PoolArenaMetric> directArenas() {
        return this.directArenaMetrics;
    }

    public int numThreadLocalCaches() {
        PoolArena<?>[] arenas = this.heapArenas != null ? this.heapArenas : this.directArenas;
        if (arenas == null) {
            return 0;
        }
        int total = 0;
        for (PoolArena poolArena : arenas) {
            total += poolArena.numThreadCaches.get();
        }
        return total;
    }

    public int tinyCacheSize() {
        return this.tinyCacheSize;
    }

    public int smallCacheSize() {
        return this.smallCacheSize;
    }

    public int normalCacheSize() {
        return this.normalCacheSize;
    }

    final PoolThreadCache threadCache() {
        return (PoolThreadCache) this.threadCache.get();
    }

    public String dumpStats() {
        int heapArenasLen;
        int directArenasLen;
        if (this.heapArenas == null) {
            heapArenasLen = 0;
        } else {
            heapArenasLen = this.heapArenas.length;
        }
        StringBuilder buf = new StringBuilder(512).append(heapArenasLen).append(" heap arena(s):").append(StringUtil.NEWLINE);
        if (heapArenasLen > 0) {
            for (PoolArena<byte[]> a : this.heapArenas) {
                buf.append(a);
            }
        }
        if (this.directArenas == null) {
            directArenasLen = 0;
        } else {
            directArenasLen = this.directArenas.length;
        }
        buf.append(directArenasLen).append(" direct arena(s):").append(StringUtil.NEWLINE);
        if (directArenasLen > 0) {
            for (PoolArena<ByteBuffer> a2 : this.directArenas) {
                buf.append(a2);
            }
        }
        return buf.toString();
    }
}
