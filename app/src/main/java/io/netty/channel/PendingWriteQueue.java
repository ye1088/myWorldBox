package io.netty.channel;

import io.netty.channel.MessageSizeEstimator.Handle;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.PromiseCombiner;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class PendingWriteQueue {
    static final /* synthetic */ boolean $assertionsDisabled = (!PendingWriteQueue.class.desiredAssertionStatus());
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
    private final ChannelOutboundBuffer buffer;
    private long bytes;
    private final ChannelHandlerContext ctx;
    private final Handle estimatorHandle;
    private PendingWrite head;
    private int size;
    private PendingWrite tail;

    static final class PendingWrite {
        private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>() {
            protected PendingWrite newObject(Recycler.Handle<PendingWrite> handle) {
                return new PendingWrite(handle);
            }
        };
        private final Recycler.Handle<PendingWrite> handle;
        private Object msg;
        private PendingWrite next;
        private ChannelPromise promise;
        private long size;

        private PendingWrite(Recycler.Handle<PendingWrite> handle) {
            this.handle = handle;
        }

        static PendingWrite newInstance(Object msg, int size, ChannelPromise promise) {
            PendingWrite write = (PendingWrite) RECYCLER.get();
            write.size = (long) size;
            write.msg = msg;
            write.promise = promise;
            return write;
        }

        private void recycle() {
            this.size = 0;
            this.next = null;
            this.msg = null;
            this.promise = null;
            this.handle.recycle(this);
        }
    }

    public PendingWriteQueue(ChannelHandlerContext ctx) {
        if (ctx == null) {
            throw new NullPointerException("ctx");
        }
        this.ctx = ctx;
        this.buffer = ctx.channel().unsafe().outboundBuffer();
        this.estimatorHandle = ctx.channel().config().getMessageSizeEstimator().newHandle();
    }

    public boolean isEmpty() {
        if ($assertionsDisabled || this.ctx.executor().inEventLoop()) {
            return this.head == null;
        } else {
            throw new AssertionError();
        }
    }

    public int size() {
        if ($assertionsDisabled || this.ctx.executor().inEventLoop()) {
            return this.size;
        }
        throw new AssertionError();
    }

    public long bytes() {
        if ($assertionsDisabled || this.ctx.executor().inEventLoop()) {
            return this.bytes;
        }
        throw new AssertionError();
    }

    public void add(Object msg, ChannelPromise promise) {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (msg == null) {
            throw new NullPointerException("msg");
        } else if (promise == null) {
            throw new NullPointerException("promise");
        } else {
            int messageSize = this.estimatorHandle.size(msg);
            if (messageSize < 0) {
                messageSize = 0;
            }
            PendingWrite write = PendingWrite.newInstance(msg, messageSize, promise);
            PendingWrite currentTail = this.tail;
            if (currentTail == null) {
                this.head = write;
                this.tail = write;
            } else {
                currentTail.next = write;
                this.tail = write;
            }
            this.size++;
            this.bytes += (long) messageSize;
            if (this.buffer != null) {
                this.buffer.incrementPendingOutboundBytes(write.size);
            }
        }
    }

    public ChannelFuture removeAndWriteAll() {
        ChannelFuture channelFuture = null;
        if ($assertionsDisabled || this.ctx.executor().inEventLoop()) {
            if (!isEmpty()) {
                channelFuture = this.ctx.newPromise();
                PromiseCombiner combiner = new PromiseCombiner();
                try {
                    PendingWrite write = this.head;
                    while (write != null) {
                        this.tail = null;
                        this.head = null;
                        this.size = 0;
                        this.bytes = 0;
                        while (write != null) {
                            PendingWrite next = write.next;
                            Object msg = write.msg;
                            ChannelPromise promise = write.promise;
                            recycle(write, false);
                            combiner.add(promise);
                            this.ctx.write(msg, promise);
                            write = next;
                        }
                        write = this.head;
                    }
                    combiner.finish(channelFuture);
                } catch (Throwable cause) {
                    channelFuture.setFailure(cause);
                }
                assertEmpty();
            }
            return channelFuture;
        }
        throw new AssertionError();
    }

    public void removeAndFailAll(Throwable cause) {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (cause == null) {
            throw new NullPointerException("cause");
        } else {
            PendingWrite write = this.head;
            while (write != null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0;
                while (write != null) {
                    PendingWrite next = write.next;
                    ReferenceCountUtil.safeRelease(write.msg);
                    ChannelPromise promise = write.promise;
                    recycle(write, false);
                    safeFail(promise, cause);
                    write = next;
                }
                write = this.head;
            }
            assertEmpty();
        }
    }

    public void removeAndFail(Throwable cause) {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        } else if (cause == null) {
            throw new NullPointerException("cause");
        } else {
            PendingWrite write = this.head;
            if (write != null) {
                ReferenceCountUtil.safeRelease(write.msg);
                safeFail(write.promise, cause);
                recycle(write, true);
            }
        }
    }

    private void assertEmpty() {
        if (!$assertionsDisabled) {
            if (this.tail != null || this.head != null || this.size != 0) {
                throw new AssertionError();
            }
        }
    }

    public ChannelFuture removeAndWrite() {
        if ($assertionsDisabled || this.ctx.executor().inEventLoop()) {
            PendingWrite write = this.head;
            if (write == null) {
                return null;
            }
            Object msg = write.msg;
            ChannelPromise promise = write.promise;
            recycle(write, true);
            return this.ctx.write(msg, promise);
        }
        throw new AssertionError();
    }

    public ChannelPromise remove() {
        if ($assertionsDisabled || this.ctx.executor().inEventLoop()) {
            PendingWrite write = this.head;
            if (write == null) {
                return null;
            }
            ChannelPromise promise = write.promise;
            ReferenceCountUtil.safeRelease(write.msg);
            recycle(write, true);
            return promise;
        }
        throw new AssertionError();
    }

    public Object current() {
        if ($assertionsDisabled || this.ctx.executor().inEventLoop()) {
            PendingWrite write = this.head;
            if (write == null) {
                return null;
            }
            return write.msg;
        }
        throw new AssertionError();
    }

    private void recycle(PendingWrite write, boolean update) {
        PendingWrite next = write.next;
        long writeSize = write.size;
        if (update) {
            if (next == null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0;
            } else {
                this.head = next;
                this.size--;
                this.bytes -= writeSize;
                if (!$assertionsDisabled && (this.size <= 0 || this.bytes < 0)) {
                    throw new AssertionError();
                }
            }
        }
        write.recycle();
        if (this.buffer != null) {
            this.buffer.decrementPendingOutboundBytes(writeSize);
        }
    }

    private static void safeFail(ChannelPromise promise, Throwable cause) {
        if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
            logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
        }
    }
}
