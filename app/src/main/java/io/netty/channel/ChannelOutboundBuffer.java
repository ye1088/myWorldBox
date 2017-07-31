package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public final class ChannelOutboundBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = (!ChannelOutboundBuffer.class.desiredAssertionStatus());
    private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS = new 1();
    private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER;
    private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> UNWRITABLE_UPDATER;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
    private final Channel channel;
    private volatile Runnable fireChannelWritabilityChangedTask;
    private int flushed;
    private Entry flushedEntry;
    private boolean inFail;
    private int nioBufferCount;
    private long nioBufferSize;
    private Entry tailEntry;
    private volatile long totalPendingSize;
    private Entry unflushedEntry;
    private volatile int unwritable;

    static {
        AtomicIntegerFieldUpdater<ChannelOutboundBuffer> unwritableUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(ChannelOutboundBuffer.class, "unwritable");
        if (unwritableUpdater == null) {
            unwritableUpdater = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "unwritable");
        }
        UNWRITABLE_UPDATER = unwritableUpdater;
        AtomicLongFieldUpdater<ChannelOutboundBuffer> pendingSizeUpdater = PlatformDependent.newAtomicLongFieldUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
        if (pendingSizeUpdater == null) {
            pendingSizeUpdater = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
        }
        TOTAL_PENDING_SIZE_UPDATER = pendingSizeUpdater;
    }

    ChannelOutboundBuffer(AbstractChannel channel) {
        this.channel = channel;
    }

    public void addMessage(Object msg, int size, ChannelPromise promise) {
        Entry entry = Entry.newInstance(msg, size, total(msg), promise);
        if (this.tailEntry == null) {
            this.flushedEntry = null;
            this.tailEntry = entry;
        } else {
            this.tailEntry.next = entry;
            this.tailEntry = entry;
        }
        if (this.unflushedEntry == null) {
            this.unflushedEntry = entry;
        }
        incrementPendingOutboundBytes((long) size, false);
    }

    public void addFlush() {
        Entry entry = this.unflushedEntry;
        if (entry != null) {
            if (this.flushedEntry == null) {
                this.flushedEntry = entry;
            }
            do {
                this.flushed++;
                if (!entry.promise.setUncancellable()) {
                    decrementPendingOutboundBytes((long) entry.cancel(), false, true);
                }
                entry = entry.next;
            } while (entry != null);
            this.unflushedEntry = null;
        }
    }

    void incrementPendingOutboundBytes(long size) {
        incrementPendingOutboundBytes(size, true);
    }

    private void incrementPendingOutboundBytes(long size, boolean invokeLater) {
        if (size != 0 && TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, size) > ((long) this.channel.config().getWriteBufferHighWaterMark())) {
            setUnwritable(invokeLater);
        }
    }

    void decrementPendingOutboundBytes(long size) {
        decrementPendingOutboundBytes(size, true, true);
    }

    private void decrementPendingOutboundBytes(long size, boolean invokeLater, boolean notifyWritability) {
        if (size != 0) {
            long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);
            if (notifyWritability && newWriteBufferSize < ((long) this.channel.config().getWriteBufferLowWaterMark())) {
                setWritable(invokeLater);
            }
        }
    }

    private static long total(Object msg) {
        if (msg instanceof ByteBuf) {
            return (long) ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof FileRegion) {
            return ((FileRegion) msg).count();
        }
        if (msg instanceof ByteBufHolder) {
            return (long) ((ByteBufHolder) msg).content().readableBytes();
        }
        return -1;
    }

    public Object current() {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            return null;
        }
        return entry.msg;
    }

    public void progress(long amount) {
        Entry e = this.flushedEntry;
        if ($assertionsDisabled || e != null) {
            ChannelPromise p = e.promise;
            if (p instanceof ChannelProgressivePromise) {
                long progress = e.progress + amount;
                e.progress = progress;
                ((ChannelProgressivePromise) p).tryProgress(progress, e.total);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    public boolean remove() {
        Entry e = this.flushedEntry;
        if (e == null) {
            clearNioBuffers();
            return false;
        }
        Object msg = e.msg;
        ChannelPromise promise = e.promise;
        int size = e.pendingSize;
        removeEntry(e);
        if (!e.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeSuccess(promise);
            decrementPendingOutboundBytes((long) size, false, true);
        }
        e.recycle();
        return true;
    }

    public boolean remove(Throwable cause) {
        return remove0(cause, true);
    }

    private boolean remove0(Throwable cause, boolean notifyWritability) {
        Entry e = this.flushedEntry;
        if (e == null) {
            clearNioBuffers();
            return false;
        }
        Object msg = e.msg;
        ChannelPromise promise = e.promise;
        int size = e.pendingSize;
        removeEntry(e);
        if (!e.cancelled) {
            ReferenceCountUtil.safeRelease(msg);
            safeFail(promise, cause);
            decrementPendingOutboundBytes((long) size, false, notifyWritability);
        }
        e.recycle();
        return true;
    }

    private void removeEntry(Entry e) {
        int i = this.flushed - 1;
        this.flushed = i;
        if (i == 0) {
            this.flushedEntry = null;
            if (e == this.tailEntry) {
                this.tailEntry = null;
                this.unflushedEntry = null;
                return;
            }
            return;
        }
        this.flushedEntry = e.next;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeBytes(long r10) {
        /*
        r9 = this;
        r6 = 0;
    L_0x0002:
        r1 = r9.current();
        r4 = r1 instanceof io.netty.buffer.ByteBuf;
        if (r4 != 0) goto L_0x0018;
    L_0x000a:
        r4 = $assertionsDisabled;
        if (r4 != 0) goto L_0x0044;
    L_0x000e:
        r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0044;
    L_0x0012:
        r4 = new java.lang.AssertionError;
        r4.<init>();
        throw r4;
    L_0x0018:
        r0 = r1;
        r0 = (io.netty.buffer.ByteBuf) r0;
        r3 = r0.readerIndex();
        r4 = r0.writerIndex();
        r2 = r4 - r3;
        r4 = (long) r2;
        r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
        if (r4 > 0) goto L_0x0038;
    L_0x002a:
        r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0034;
    L_0x002e:
        r4 = (long) r2;
        r9.progress(r4);
        r4 = (long) r2;
        r10 = r10 - r4;
    L_0x0034:
        r9.remove();
        goto L_0x0002;
    L_0x0038:
        r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0044;
    L_0x003c:
        r4 = (int) r10;
        r4 = r4 + r3;
        r0.readerIndex(r4);
        r9.progress(r10);
    L_0x0044:
        r9.clearNioBuffers();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.ChannelOutboundBuffer.removeBytes(long):void");
    }

    private void clearNioBuffers() {
        int count = this.nioBufferCount;
        if (count > 0) {
            this.nioBufferCount = 0;
            Arrays.fill((Object[]) NIO_BUFFERS.get(), 0, count, null);
        }
    }

    public ByteBuffer[] nioBuffers() {
        long nioBufferSize = 0;
        int nioBufferCount = 0;
        InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
        ByteBuffer[] nioBuffers = (ByteBuffer[]) NIO_BUFFERS.get(threadLocalMap);
        Entry entry = this.flushedEntry;
        while (isFlushedEntry(entry) && (entry.msg instanceof ByteBuf)) {
            if (!entry.cancelled) {
                ByteBuf buf = entry.msg;
                int readerIndex = buf.readerIndex();
                int readableBytes = buf.writerIndex() - readerIndex;
                if (readableBytes <= 0) {
                    continue;
                } else if (((long) (Integer.MAX_VALUE - readableBytes)) < nioBufferSize) {
                    break;
                } else {
                    nioBufferSize += (long) readableBytes;
                    int count = entry.count;
                    if (count == -1) {
                        count = buf.nioBufferCount();
                        entry.count = count;
                    }
                    int neededSpace = nioBufferCount + count;
                    if (neededSpace > nioBuffers.length) {
                        nioBuffers = expandNioBufferArray(nioBuffers, neededSpace, nioBufferCount);
                        NIO_BUFFERS.set(threadLocalMap, nioBuffers);
                    }
                    if (count == 1) {
                        ByteBuffer nioBuf = entry.buf;
                        if (nioBuf == null) {
                            nioBuf = buf.internalNioBuffer(readerIndex, readableBytes);
                            entry.buf = nioBuf;
                        }
                        int nioBufferCount2 = nioBufferCount + 1;
                        nioBuffers[nioBufferCount] = nioBuf;
                        nioBufferCount = nioBufferCount2;
                    } else {
                        ByteBuffer[] nioBufs = entry.bufs;
                        if (nioBufs == null) {
                            nioBufs = buf.nioBuffers();
                            entry.bufs = nioBufs;
                        }
                        nioBufferCount = fillBufferArray(nioBufs, nioBuffers, nioBufferCount);
                    }
                }
            }
            entry = entry.next;
        }
        this.nioBufferCount = nioBufferCount;
        this.nioBufferSize = nioBufferSize;
        return nioBuffers;
    }

    private static int fillBufferArray(ByteBuffer[] nioBufs, ByteBuffer[] nioBuffers, int nioBufferCount) {
        ByteBuffer[] arr$ = nioBufs;
        int len$ = arr$.length;
        int i$ = 0;
        int nioBufferCount2 = nioBufferCount;
        while (i$ < len$) {
            ByteBuffer nioBuf = arr$[i$];
            if (nioBuf == null) {
                break;
            }
            nioBufferCount = nioBufferCount2 + 1;
            nioBuffers[nioBufferCount2] = nioBuf;
            i$++;
            nioBufferCount2 = nioBufferCount;
        }
        return nioBufferCount2;
    }

    private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] array, int neededSpace, int size) {
        int newCapacity = array.length;
        do {
            newCapacity <<= 1;
            if (newCapacity < 0) {
                throw new IllegalStateException();
            }
        } while (neededSpace > newCapacity);
        ByteBuffer[] newArray = new ByteBuffer[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    public int nioBufferCount() {
        return this.nioBufferCount;
    }

    public long nioBufferSize() {
        return this.nioBufferSize;
    }

    public boolean isWritable() {
        return this.unwritable == 0;
    }

    public boolean getUserDefinedWritability(int index) {
        return (this.unwritable & writabilityMask(index)) == 0;
    }

    public void setUserDefinedWritability(int index, boolean writable) {
        if (writable) {
            setUserDefinedWritability(index);
        } else {
            clearUserDefinedWritability(index);
        }
    }

    private void setUserDefinedWritability(int index) {
        int mask = writabilityMask(index) ^ -1;
        int oldValue;
        int newValue;
        do {
            oldValue = this.unwritable;
            newValue = oldValue & mask;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue != 0 && newValue == 0) {
            fireChannelWritabilityChanged(true);
        }
    }

    private void clearUserDefinedWritability(int index) {
        int oldValue;
        int mask = writabilityMask(index);
        int newValue;
        do {
            oldValue = this.unwritable;
            newValue = oldValue | mask;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue == 0 && newValue != 0) {
            fireChannelWritabilityChanged(true);
        }
    }

    private static int writabilityMask(int index) {
        if (index >= 1 && index <= 31) {
            return 1 << index;
        }
        throw new IllegalArgumentException("index: " + index + " (expected: 1~31)");
    }

    private void setWritable(boolean invokeLater) {
        int oldValue;
        int newValue;
        do {
            oldValue = this.unwritable;
            newValue = oldValue & -2;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue != 0 && newValue == 0) {
            fireChannelWritabilityChanged(invokeLater);
        }
    }

    private void setUnwritable(boolean invokeLater) {
        int oldValue;
        int newValue;
        do {
            oldValue = this.unwritable;
            newValue = oldValue | 1;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue));
        if (oldValue == 0 && newValue != 0) {
            fireChannelWritabilityChanged(invokeLater);
        }
    }

    private void fireChannelWritabilityChanged(boolean invokeLater) {
        ChannelPipeline pipeline = this.channel.pipeline();
        if (invokeLater) {
            Runnable task = this.fireChannelWritabilityChangedTask;
            if (task == null) {
                task = new 2(this, pipeline);
                this.fireChannelWritabilityChangedTask = task;
            }
            this.channel.eventLoop().execute(task);
            return;
        }
        pipeline.fireChannelWritabilityChanged();
    }

    public int size() {
        return this.flushed;
    }

    public boolean isEmpty() {
        return this.flushed == 0;
    }

    void failFlushed(Throwable cause, boolean notify) {
        if (!this.inFail) {
            try {
                this.inFail = true;
                while (true) {
                    if (!remove0(cause, notify)) {
                        break;
                    }
                }
            } finally {
                this.inFail = false;
            }
        }
    }

    void close(ClosedChannelException cause) {
        if (this.inFail) {
            this.channel.eventLoop().execute(new 3(this, cause));
            return;
        }
        this.inFail = true;
        if (this.channel.isOpen()) {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
        } else if (isEmpty()) {
            try {
                for (Entry e = this.unflushedEntry; e != null; e = e.recycleAndGetNext()) {
                    TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, (long) (-e.pendingSize));
                    if (!e.cancelled) {
                        ReferenceCountUtil.safeRelease(e.msg);
                        safeFail(e.promise, cause);
                    }
                }
                clearNioBuffers();
            } finally {
                this.inFail = false;
            }
        } else {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
        }
    }

    private static void safeSuccess(ChannelPromise promise) {
        if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
            Throwable err = promise.cause();
            if (err == null) {
                logger.warn("Failed to mark a promise as success because it has succeeded already: {}", promise);
            } else {
                logger.warn("Failed to mark a promise as success because it has failed already: {}, unnotified cause {}", promise, ThrowableUtil.stackTraceToString(err));
            }
        }
    }

    private static void safeFail(ChannelPromise promise, Throwable cause) {
        if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
            if (promise.cause() == null) {
                logger.warn("Failed to mark a promise as failure because it has succeeded already: {}", promise, cause);
                return;
            }
            logger.warn("Failed to mark a promise as failure because it has failed already: {}, unnotified cause {}", new Object[]{promise, ThrowableUtil.stackTraceToString(promise.cause()), cause});
        }
    }

    @Deprecated
    public void recycle() {
    }

    public long totalPendingWriteBytes() {
        return this.totalPendingSize;
    }

    public long bytesBeforeUnwritable() {
        long bytes = ((long) this.channel.config().getWriteBufferHighWaterMark()) - this.totalPendingSize;
        if (bytes > 0) {
            return isWritable() ? bytes : 0;
        } else {
            return 0;
        }
    }

    public long bytesBeforeWritable() {
        long bytes = this.totalPendingSize - ((long) this.channel.config().getWriteBufferLowWaterMark());
        if (bytes <= 0) {
            return 0;
        }
        if (isWritable()) {
            return 0;
        }
        return bytes;
    }

    public void forEachFlushedMessage(MessageProcessor processor) throws Exception {
        if (processor == null) {
            throw new NullPointerException("processor");
        }
        Entry entry = this.flushedEntry;
        if (entry != null) {
            do {
                if (entry.cancelled || processor.processMessage(entry.msg)) {
                    entry = entry.next;
                } else {
                    return;
                }
            } while (isFlushedEntry(entry));
        }
    }

    private boolean isFlushedEntry(Entry e) {
        return (e == null || e == this.unflushedEntry) ? false : true;
    }
}
