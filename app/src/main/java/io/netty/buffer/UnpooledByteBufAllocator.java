package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

public final class UnpooledByteBufAllocator extends AbstractByteBufAllocator {
    public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    private final boolean disableLeakDetector;

    public UnpooledByteBufAllocator(boolean preferDirect) {
        this(preferDirect, false);
    }

    public UnpooledByteBufAllocator(boolean preferDirect, boolean disableLeakDetector) {
        super(preferDirect);
        this.disableLeakDetector = disableLeakDetector;
    }

    protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
        return PlatformDependent.hasUnsafe() ? new UnpooledUnsafeHeapByteBuf(this, initialCapacity, maxCapacity) : new UnpooledHeapByteBuf((ByteBufAllocator) this, initialCapacity, maxCapacity);
    }

    protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
        ByteBuf buf = PlatformDependent.hasUnsafe() ? UnsafeByteBufUtil.newUnsafeDirectByteBuf(this, initialCapacity, maxCapacity) : new UnpooledDirectByteBuf((ByteBufAllocator) this, initialCapacity, maxCapacity);
        return this.disableLeakDetector ? buf : AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
        CompositeByteBuf buf = new CompositeByteBuf(this, false, maxNumComponents);
        return this.disableLeakDetector ? buf : AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
        CompositeByteBuf buf = new CompositeByteBuf(this, true, maxNumComponents);
        return this.disableLeakDetector ? buf : AbstractByteBufAllocator.toLeakAwareBuffer(buf);
    }

    public boolean isDirectBufferPooled() {
        return false;
    }
}
