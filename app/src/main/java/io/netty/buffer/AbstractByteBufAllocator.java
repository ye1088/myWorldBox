package io.netty.buffer;

import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;

public abstract class AbstractByteBufAllocator implements ByteBufAllocator {
    private static final int DEFAULT_INITIAL_CAPACITY = 256;
    static final int DEFAULT_MAX_COMPONENTS = 16;
    private final boolean directByDefault;
    private final ByteBuf emptyBuf;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$util$ResourceLeakDetector$Level = new int[Level.values().length];

        static {
            try {
                $SwitchMap$io$netty$util$ResourceLeakDetector$Level[Level.SIMPLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$util$ResourceLeakDetector$Level[Level.ADVANCED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$util$ResourceLeakDetector$Level[Level.PARANOID.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    protected abstract ByteBuf newDirectBuffer(int i, int i2);

    protected abstract ByteBuf newHeapBuffer(int i, int i2);

    protected static ByteBuf toLeakAwareBuffer(ByteBuf buf) {
        ResourceLeak leak;
        switch (AnonymousClass1.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()]) {
            case 1:
                leak = AbstractByteBuf.leakDetector.open(buf);
                if (leak != null) {
                    return new SimpleLeakAwareByteBuf(buf, leak);
                }
                return buf;
            case 2:
            case 3:
                leak = AbstractByteBuf.leakDetector.open(buf);
                if (leak != null) {
                    return new AdvancedLeakAwareByteBuf(buf, leak);
                }
                return buf;
            default:
                return buf;
        }
    }

    protected static CompositeByteBuf toLeakAwareBuffer(CompositeByteBuf buf) {
        ResourceLeak leak;
        switch (AnonymousClass1.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()]) {
            case 1:
                leak = AbstractByteBuf.leakDetector.open(buf);
                if (leak != null) {
                    return new SimpleLeakAwareCompositeByteBuf(buf, leak);
                }
                return buf;
            case 2:
            case 3:
                leak = AbstractByteBuf.leakDetector.open(buf);
                if (leak != null) {
                    return new AdvancedLeakAwareCompositeByteBuf(buf, leak);
                }
                return buf;
            default:
                return buf;
        }
    }

    protected AbstractByteBufAllocator() {
        this(false);
    }

    protected AbstractByteBufAllocator(boolean preferDirect) {
        boolean z = preferDirect && PlatformDependent.hasUnsafe();
        this.directByDefault = z;
        this.emptyBuf = new EmptyByteBuf(this);
    }

    public ByteBuf buffer() {
        if (this.directByDefault) {
            return directBuffer();
        }
        return heapBuffer();
    }

    public ByteBuf buffer(int initialCapacity) {
        if (this.directByDefault) {
            return directBuffer(initialCapacity);
        }
        return heapBuffer(initialCapacity);
    }

    public ByteBuf buffer(int initialCapacity, int maxCapacity) {
        if (this.directByDefault) {
            return directBuffer(initialCapacity, maxCapacity);
        }
        return heapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf ioBuffer() {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(256);
        }
        return heapBuffer(256);
    }

    public ByteBuf ioBuffer(int initialCapacity) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(initialCapacity);
        }
        return heapBuffer(initialCapacity);
    }

    public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(initialCapacity, maxCapacity);
        }
        return heapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf heapBuffer() {
        return heapBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int initialCapacity) {
        return heapBuffer(initialCapacity, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
        if (initialCapacity == 0 && maxCapacity == 0) {
            return this.emptyBuf;
        }
        validate(initialCapacity, maxCapacity);
        return newHeapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf directBuffer() {
        return directBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int initialCapacity) {
        return directBuffer(initialCapacity, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
        if (initialCapacity == 0 && maxCapacity == 0) {
            return this.emptyBuf;
        }
        validate(initialCapacity, maxCapacity);
        return newDirectBuffer(initialCapacity, maxCapacity);
    }

    public CompositeByteBuf compositeBuffer() {
        if (this.directByDefault) {
            return compositeDirectBuffer();
        }
        return compositeHeapBuffer();
    }

    public CompositeByteBuf compositeBuffer(int maxNumComponents) {
        if (this.directByDefault) {
            return compositeDirectBuffer(maxNumComponents);
        }
        return compositeHeapBuffer(maxNumComponents);
    }

    public CompositeByteBuf compositeHeapBuffer() {
        return compositeHeapBuffer(16);
    }

    public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, false, maxNumComponents));
    }

    public CompositeByteBuf compositeDirectBuffer() {
        return compositeDirectBuffer(16);
    }

    public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, true, maxNumComponents));
    }

    private static void validate(int initialCapacity, int maxCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity: " + initialCapacity + " (expectd: 0+)");
        } else if (initialCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
        }
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(directByDefault: " + this.directByDefault + ')';
    }

    public int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
        if (minNewCapacity < 0) {
            throw new IllegalArgumentException("minNewCapacity: " + minNewCapacity + " (expectd: 0+)");
        } else if (minNewCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format("minNewCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[]{Integer.valueOf(minNewCapacity), Integer.valueOf(maxCapacity)}));
        } else if (minNewCapacity == 4194304) {
            return 4194304;
        } else {
            int newCapacity;
            if (minNewCapacity > 4194304) {
                newCapacity = (minNewCapacity / 4194304) * 4194304;
                if (newCapacity > maxCapacity - 4194304) {
                    return maxCapacity;
                }
                return newCapacity + 4194304;
            }
            newCapacity = 64;
            while (newCapacity < minNewCapacity) {
                newCapacity <<= 1;
            }
            return Math.min(newCapacity, maxCapacity);
        }
    }
}
