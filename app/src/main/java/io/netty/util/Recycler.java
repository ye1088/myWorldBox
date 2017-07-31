package io.netty.util;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Recycler<T> {
    private static final int DEFAULT_INITIAL_MAX_CAPACITY_PER_THREAD = 32768;
    private static final int DEFAULT_MAX_CAPACITY_PER_THREAD;
    private static final FastThreadLocal<Map<Stack<?>, WeakOrderQueue>> DELAYED_RECYCLED = new 3();
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
    private static final int INITIAL_CAPACITY = Math.min(DEFAULT_MAX_CAPACITY_PER_THREAD, 256);
    private static final int LINK_CAPACITY = MathUtil.safeFindNextPositivePowerOfTwo(Math.max(SystemPropertyUtil.getInt("io.netty.recycler.linkCapacity", 16), 16));
    private static final int MAX_DELAYED_QUEUES_PER_THREAD = Math.max(0, SystemPropertyUtil.getInt("io.netty.recycler.maxDelayedQueuesPerThread", Runtime.getRuntime().availableProcessors() * 2));
    private static final int MAX_SHARED_CAPACITY_FACTOR = Math.max(2, SystemPropertyUtil.getInt("io.netty.recycler.maxSharedCapacityFactor", 2));
    private static final Handle NOOP_HANDLE = new 1();
    private static final int OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();
    private static final int RATIO = MathUtil.safeFindNextPositivePowerOfTwo(SystemPropertyUtil.getInt("io.netty.recycler.ratio", 8));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
    private final int maxCapacityPerThread;
    private final int maxDelayedQueuesPerThread;
    private final int maxSharedCapacityFactor;
    private final int ratioMask;
    private final FastThreadLocal<Stack<T>> threadLocal;

    protected abstract T newObject(Handle<T> handle);

    static {
        int maxCapacityPerThread = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacityPerThread", SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity", 32768));
        if (maxCapacityPerThread < 0) {
            maxCapacityPerThread = 32768;
        }
        DEFAULT_MAX_CAPACITY_PER_THREAD = maxCapacityPerThread;
        if (logger.isDebugEnabled()) {
            if (DEFAULT_MAX_CAPACITY_PER_THREAD == 0) {
                logger.debug("-Dio.netty.recycler.maxCapacityPerThread: disabled");
                logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: disabled");
                logger.debug("-Dio.netty.recycler.linkCapacity: disabled");
                logger.debug("-Dio.netty.recycler.ratio: disabled");
            } else {
                logger.debug("-Dio.netty.recycler.maxCapacityPerThread: {}", Integer.valueOf(DEFAULT_MAX_CAPACITY_PER_THREAD));
                logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: {}", Integer.valueOf(MAX_SHARED_CAPACITY_FACTOR));
                logger.debug("-Dio.netty.recycler.linkCapacity: {}", Integer.valueOf(LINK_CAPACITY));
                logger.debug("-Dio.netty.recycler.ratio: {}", Integer.valueOf(RATIO));
            }
        }
    }

    protected Recycler() {
        this(DEFAULT_MAX_CAPACITY_PER_THREAD);
    }

    protected Recycler(int maxCapacityPerThread) {
        this(maxCapacityPerThread, MAX_SHARED_CAPACITY_FACTOR);
    }

    protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor) {
        this(maxCapacityPerThread, maxSharedCapacityFactor, RATIO, MAX_DELAYED_QUEUES_PER_THREAD);
    }

    protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor, int ratio, int maxDelayedQueuesPerThread) {
        this.threadLocal = new 2(this);
        this.ratioMask = MathUtil.safeFindNextPositivePowerOfTwo(ratio) - 1;
        if (maxCapacityPerThread <= 0) {
            this.maxCapacityPerThread = 0;
            this.maxSharedCapacityFactor = 1;
            this.maxDelayedQueuesPerThread = 0;
            return;
        }
        this.maxCapacityPerThread = maxCapacityPerThread;
        this.maxSharedCapacityFactor = Math.max(1, maxSharedCapacityFactor);
        this.maxDelayedQueuesPerThread = Math.max(0, maxDelayedQueuesPerThread);
    }

    public final T get() {
        if (this.maxCapacityPerThread == 0) {
            return newObject(NOOP_HANDLE);
        }
        Stack<T> stack = (Stack) this.threadLocal.get();
        DefaultHandle<T> handle = stack.pop();
        if (handle == null) {
            handle = stack.newHandle();
            DefaultHandle.access$402(handle, newObject(handle));
        }
        return DefaultHandle.access$400(handle);
    }

    @Deprecated
    public final boolean recycle(T o, Handle<T> handle) {
        if (handle == NOOP_HANDLE) {
            return false;
        }
        DefaultHandle<T> h = (DefaultHandle) handle;
        if (DefaultHandle.access$500(h).parent != this) {
            return false;
        }
        h.recycle(o);
        return true;
    }

    final int threadLocalCapacity() {
        return Stack.access$600((Stack) this.threadLocal.get()).length;
    }

    final int threadLocalSize() {
        return Stack.access$700((Stack) this.threadLocal.get());
    }
}
