package io.netty.util;

import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.ReferenceQueue;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResourceLeakDetector<T> {
    private static final Level DEFAULT_LEVEL = Level.SIMPLE;
    private static final int DEFAULT_MAX_RECORDS = 4;
    static final int DEFAULT_SAMPLING_INTERVAL = 128;
    private static final int MAX_RECORDS = SystemPropertyUtil.getInt(PROP_MAX_RECORDS, 4);
    private static final String PROP_LEVEL = "io.netty.leakDetection.level";
    private static final String PROP_LEVEL_OLD = "io.netty.leakDetectionLevel";
    private static final String PROP_MAX_RECORDS = "io.netty.leakDetection.maxRecords";
    private static final String[] STACK_TRACE_ELEMENT_EXCLUSIONS = new String[]{"io.netty.util.ReferenceCountUtil.touch(", "io.netty.buffer.AdvancedLeakAwareByteBuf.touch(", "io.netty.buffer.AbstractByteBufAllocator.toLeakAwareBuffer(", "io.netty.buffer.AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation("};
    private static Level level;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
    private long active;
    private final DefaultResourceLeak head;
    private long leakCheckCnt;
    private final AtomicBoolean loggedTooManyActive;
    private final int mask;
    private final long maxActive;
    private final ReferenceQueue<Object> refQueue;
    private final ConcurrentMap<String, Boolean> reportedLeaks;
    private final String resourceType;
    private final int samplingInterval;
    private final DefaultResourceLeak tail;

    static {
        boolean disabled;
        if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
            disabled = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
            logger.debug("-Dio.netty.noResourceLeakDetection: {}", Boolean.valueOf(disabled));
            logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", PROP_LEVEL, DEFAULT_LEVEL.name().toLowerCase());
        } else {
            disabled = false;
        }
        String levelStr = SystemPropertyUtil.get(PROP_LEVEL, SystemPropertyUtil.get(PROP_LEVEL_OLD, (disabled ? Level.DISABLED : DEFAULT_LEVEL).name()).trim().toUpperCase()).trim().toUpperCase();
        Level level = DEFAULT_LEVEL;
        Iterator i$ = EnumSet.allOf(Level.class).iterator();
        while (i$.hasNext()) {
            Level l = (Level) i$.next();
            if (levelStr.equals(l.name()) || levelStr.equals(String.valueOf(l.ordinal()))) {
                level = l;
            }
        }
        level = level;
        if (logger.isDebugEnabled()) {
            logger.debug("-D{}: {}", PROP_LEVEL, level.name().toLowerCase());
            logger.debug("-D{}: {}", PROP_MAX_RECORDS, Integer.valueOf(MAX_RECORDS));
        }
    }

    @Deprecated
    public static void setEnabled(boolean enabled) {
        setLevel(enabled ? Level.SIMPLE : Level.DISABLED);
    }

    public static boolean isEnabled() {
        return getLevel().ordinal() > Level.DISABLED.ordinal();
    }

    public static void setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level");
        }
        level = level;
    }

    public static Level getLevel() {
        return level;
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> resourceType) {
        this(StringUtil.simpleClassName(resourceType));
    }

    @Deprecated
    public ResourceLeakDetector(String resourceType) {
        this(resourceType, 128, Long.MAX_VALUE);
    }

    public ResourceLeakDetector(Class<?> resourceType, int samplingInterval, long maxActive) {
        this(StringUtil.simpleClassName(resourceType), samplingInterval, maxActive);
    }

    @Deprecated
    public ResourceLeakDetector(String resourceType, int samplingInterval, long maxActive) {
        this.head = new DefaultResourceLeak(this, null);
        this.tail = new DefaultResourceLeak(this, null);
        this.refQueue = new ReferenceQueue();
        this.reportedLeaks = PlatformDependent.newConcurrentHashMap();
        this.loggedTooManyActive = new AtomicBoolean();
        if (resourceType == null) {
            throw new NullPointerException("resourceType");
        } else if (maxActive <= 0) {
            throw new IllegalArgumentException("maxActive: " + maxActive + " (expected: 1+)");
        } else {
            this.resourceType = resourceType;
            this.samplingInterval = MathUtil.safeFindNextPositivePowerOfTwo(samplingInterval);
            this.mask = this.samplingInterval - 1;
            this.maxActive = maxActive;
            DefaultResourceLeak.access$002(this.head, this.tail);
            DefaultResourceLeak.access$102(this.tail, this.head);
        }
    }

    public final ResourceLeak open(T obj) {
        Level level = level;
        if (level == Level.DISABLED) {
            return null;
        }
        if (level.ordinal() < Level.PARANOID.ordinal()) {
            long j = this.leakCheckCnt;
            this.leakCheckCnt = 1 + j;
            if ((j & ((long) this.mask)) != 0) {
                return null;
            }
            reportLeak(level);
            return new DefaultResourceLeak(this, obj);
        }
        reportLeak(level);
        return new DefaultResourceLeak(this, obj);
    }

    private void reportLeak(Level level) {
        DefaultResourceLeak ref;
        if (logger.isErrorEnabled()) {
            if (this.active * ((long) (level == Level.PARANOID ? 1 : this.samplingInterval)) > this.maxActive && this.loggedTooManyActive.compareAndSet(false, true)) {
                reportInstancesLeak(this.resourceType);
            }
            while (true) {
                ref = (DefaultResourceLeak) this.refQueue.poll();
                if (ref != null) {
                    ref.clear();
                    if (ref.close()) {
                        String records = ref.toString();
                        if (this.reportedLeaks.putIfAbsent(records, Boolean.TRUE) == null) {
                            if (records.isEmpty()) {
                                reportUntracedLeak(this.resourceType);
                            } else {
                                reportTracedLeak(this.resourceType, records);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
        while (true) {
            ref = (DefaultResourceLeak) this.refQueue.poll();
            if (ref != null) {
                ref.close();
            } else {
                return;
            }
        }
    }

    protected void reportTracedLeak(String resourceType, String records) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}", resourceType, records);
    }

    protected void reportUntracedLeak(String resourceType) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.", new Object[]{resourceType, PROP_LEVEL, Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName(this)});
    }

    protected void reportInstancesLeak(String resourceType) {
        logger.error("LEAK: You are creating too many " + resourceType + " instances.  " + resourceType + " is a shared resource that must be reused across the JVM," + "so that only a few instances are created.");
    }

    static String newRecord(Object hint, int recordsToSkip) {
        StringBuilder buf = new StringBuilder(4096);
        if (hint != null) {
            buf.append("\tHint: ");
            if (hint instanceof ResourceLeakHint) {
                buf.append(((ResourceLeakHint) hint).toHintString());
            } else {
                buf.append(hint);
            }
            buf.append(StringUtil.NEWLINE);
        }
        for (StackTraceElement e : new Throwable().getStackTrace()) {
            if (recordsToSkip > 0) {
                recordsToSkip--;
            } else {
                String estr = e.toString();
                boolean excluded = false;
                for (String exclusion : STACK_TRACE_ELEMENT_EXCLUSIONS) {
                    if (estr.startsWith(exclusion)) {
                        excluded = true;
                        break;
                    }
                }
                if (!excluded) {
                    buf.append('\t');
                    buf.append(estr);
                    buf.append(StringUtil.NEWLINE);
                }
            }
        }
        return buf.toString();
    }
}
