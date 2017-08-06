package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ScheduledFuture;

public abstract class AbstractNioChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractNioChannel.class.desiredAssertionStatus());
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractNioChannel.class, "doClose()"));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
    private final SelectableChannel ch;
    private final Runnable clearReadPendingRunnable = new 1(this);
    private ChannelPromise connectPromise;
    private ScheduledFuture<?> connectTimeoutFuture;
    protected final int readInterestOp;
    boolean readPending;
    private SocketAddress requestedRemoteAddress;
    volatile SelectionKey selectionKey;

    protected abstract boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception;

    protected abstract void doFinishConnect() throws Exception;

    protected AbstractNioChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
        super(parent);
        this.ch = ch;
        this.readInterestOp = readInterestOp;
        try {
            ch.configureBlocking(false);
        } catch (IOException e) {
            try {
                ch.close();
            } catch (IOException e2) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close a_isRightVersion partially initialized socket.", e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e);
        }
    }

    public boolean isOpen() {
        return this.ch.isOpen();
    }

    public NioUnsafe unsafe() {
        return (NioUnsafe) super.unsafe();
    }

    protected SelectableChannel javaChannel() {
        return this.ch;
    }

    public NioEventLoop eventLoop() {
        return (NioEventLoop) super.eventLoop();
    }

    protected SelectionKey selectionKey() {
        if ($assertionsDisabled || this.selectionKey != null) {
            return this.selectionKey;
        }
        throw new AssertionError();
    }

    @Deprecated
    protected boolean isReadPending() {
        return this.readPending;
    }

    @Deprecated
    protected void setReadPending(boolean readPending) {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                setReadPending0(readPending);
                return;
            } else {
                eventLoop.execute(new 2(this, readPending));
                return;
            }
        }
        this.readPending = readPending;
    }

    protected final void clearReadPending() {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                clearReadPending0();
                return;
            } else {
                eventLoop.execute(this.clearReadPendingRunnable);
                return;
            }
        }
        this.readPending = false;
    }

    private void setReadPending0(boolean readPending) {
        this.readPending = readPending;
        if (!readPending) {
            ((AbstractNioUnsafe) unsafe()).removeReadOp();
        }
    }

    private void clearReadPending0() {
        this.readPending = false;
        ((AbstractNioUnsafe) unsafe()).removeReadOp();
    }

    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof NioEventLoop;
    }

    protected void doRegister() throws Exception {
        boolean selected = false;
        while (true) {
            try {
                this.selectionKey = javaChannel().register(eventLoop().selector, 0, this);
                break;
            } catch (CancelledKeyException e) {
                if (selected) {
                    throw e;
                }
                eventLoop().selectNow();
                selected = true;
            }
        }
    }

    protected void doDeregister() throws Exception {
        eventLoop().cancel(selectionKey());
    }

    protected void doBeginRead() throws Exception {
        SelectionKey selectionKey = this.selectionKey;
        if (selectionKey.isValid()) {
            this.readPending = true;
            int interestOps = selectionKey.interestOps();
            if ((this.readInterestOp & interestOps) == 0) {
                selectionKey.interestOps(this.readInterestOp | interestOps);
            }
        }
    }

    protected final ByteBuf newDirectBuffer(ByteBuf buf) {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(buf);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuf = alloc.directBuffer(readableBytes);
            directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(buf);
            return directBuf;
        }
        directBuf = ByteBufUtil.threadLocalDirectBuffer();
        if (directBuf == null) {
            return buf;
        }
        directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(buf);
        return directBuf;
    }

    protected final ByteBuf newDirectBuffer(ReferenceCounted holder, ByteBuf buf) {
        int readableBytes = buf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(holder);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuf = alloc.directBuffer(readableBytes);
            directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(holder);
            return directBuf;
        }
        directBuf = ByteBufUtil.threadLocalDirectBuffer();
        if (directBuf != null) {
            directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(holder);
            return directBuf;
        }
        if (holder != buf) {
            buf.retain();
            ReferenceCountUtil.safeRelease(holder);
        }
        return buf;
    }

    protected void doClose() throws Exception {
        ChannelPromise promise = this.connectPromise;
        if (promise != null) {
            promise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
            this.connectPromise = null;
        }
        ScheduledFuture<?> future = this.connectTimeoutFuture;
        if (future != null) {
            future.cancel(false);
            this.connectTimeoutFuture = null;
        }
    }
}
