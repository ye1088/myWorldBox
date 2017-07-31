package io.netty.handler.codec.http2;

import io.netty.channel.AbstractChannel;
import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

abstract class AbstractHttp2StreamChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractHttp2StreamChannel.class.desiredAssertionStatus());
    private static final int ARBITRARY_MESSAGE_SIZE = 9;
    private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractHttp2StreamChannel.class, "doWrite(...)"));
    protected static final Object CLOSE_MESSAGE = new Object();
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private boolean closed;
    private final ChannelConfig config = new DefaultChannelConfig(this);
    private final Runnable fireChildReadCompleteTask = new Runnable() {
        public void run() {
            if (AbstractHttp2StreamChannel.this.readInProgress) {
                AbstractHttp2StreamChannel.this.readInProgress = false;
                AbstractHttp2StreamChannel.this.unsafe().recvBufAllocHandle().readComplete();
                AbstractHttp2StreamChannel.this.pipeline().fireChannelReadComplete();
            }
        }
    };
    private final Queue<Object> inboundBuffer = new ArrayDeque(4);
    private boolean readInProgress;

    private final class Unsafe extends AbstractUnsafe {
        private Unsafe() {
            super(AbstractHttp2StreamChannel.this);
        }

        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            promise.setFailure(new UnsupportedOperationException());
        }
    }

    protected abstract void bytesConsumed(int i);

    protected abstract void doWrite(Object obj) throws Exception;

    protected abstract void doWriteComplete();

    protected abstract EventExecutor preferredEventExecutor();

    public AbstractHttp2StreamChannel(Channel parent) {
        super(parent);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public ChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return !this.closed;
    }

    public boolean isActive() {
        return !this.closed;
    }

    protected AbstractUnsafe newUnsafe() {
        return new Unsafe();
    }

    protected boolean isCompatible(EventLoop loop) {
        return true;
    }

    protected SocketAddress localAddress0() {
        return parent().localAddress();
    }

    protected SocketAddress remoteAddress0() {
        return parent().remoteAddress();
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    protected void doClose() throws Exception {
        this.closed = true;
        while (!this.inboundBuffer.isEmpty()) {
            ReferenceCountUtil.release(this.inboundBuffer.poll());
        }
    }

    protected void doBeginRead() {
        if (!this.readInProgress) {
            Handle allocHandle = unsafe().recvBufAllocHandle();
            allocHandle.reset(config());
            if (this.inboundBuffer.isEmpty()) {
                this.readInProgress = true;
                return;
            }
            do {
                Object m = this.inboundBuffer.poll();
                if (m == null) {
                    break;
                } else if (!doRead0(m, allocHandle)) {
                    return;
                }
            } while (allocHandle.continueReading());
            allocHandle.readComplete();
            pipeline().fireChannelReadComplete();
        }
    }

    protected final void doWrite(ChannelOutboundBuffer in) throws Exception {
        if (this.closed) {
            throw CLOSED_CHANNEL_EXCEPTION;
        }
        EventExecutor preferredExecutor = preferredEventExecutor();
        if (preferredExecutor.inEventLoop()) {
            while (true) {
                Object msg = in.current();
                if (msg == null) {
                    doWriteComplete();
                    return;
                }
                try {
                    doWrite(ReferenceCountUtil.retain(msg));
                } catch (Throwable t) {
                    pipeline().fireExceptionCaught(t);
                }
                in.remove();
            }
        } else {
            final Object[] msgsCopy = new Object[in.size()];
            for (int i = 0; i < msgsCopy.length; i++) {
                msgsCopy[i] = ReferenceCountUtil.retain(in.current());
                in.remove();
            }
            preferredExecutor.execute(new Runnable() {
                public void run() {
                    for (Object msg : msgsCopy) {
                        try {
                            AbstractHttp2StreamChannel.this.doWrite(msg);
                        } catch (Throwable t) {
                            AbstractHttp2StreamChannel.this.pipeline().fireExceptionCaught(t);
                        }
                    }
                    AbstractHttp2StreamChannel.this.doWriteComplete();
                }
            });
        }
    }

    protected void fireChildRead(final Object msg) {
        if (eventLoop().inEventLoop()) {
            fireChildRead0(msg);
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    AbstractHttp2StreamChannel.this.fireChildRead0(msg);
                }
            });
        }
    }

    private void fireChildRead0(Object msg) {
        if (this.closed) {
            ReferenceCountUtil.release(msg);
        } else if (!this.readInProgress) {
            this.inboundBuffer.add(msg);
        } else if ($assertionsDisabled || this.inboundBuffer.isEmpty()) {
            Handle allocHandle = unsafe().recvBufAllocHandle();
            this.readInProgress = doRead0(ObjectUtil.checkNotNull(msg, "msg"), allocHandle);
            if (!allocHandle.continueReading()) {
                this.fireChildReadCompleteTask.run();
            }
        } else {
            throw new AssertionError();
        }
    }

    protected void fireChildReadComplete() {
        if (eventLoop().inEventLoop()) {
            this.fireChildReadCompleteTask.run();
        } else {
            eventLoop().execute(this.fireChildReadCompleteTask);
        }
    }

    private boolean doRead0(Object msg, Handle allocHandle) {
        if (msg == CLOSE_MESSAGE) {
            allocHandle.readComplete();
            pipeline().fireChannelReadComplete();
            unsafe().close(voidPromise());
            return false;
        }
        int numBytesToBeConsumed = 0;
        if (msg instanceof Http2DataFrame) {
            Http2DataFrame data = (Http2DataFrame) msg;
            numBytesToBeConsumed = data.content().readableBytes() + data.padding();
            allocHandle.lastBytesRead(numBytesToBeConsumed);
        } else {
            allocHandle.lastBytesRead(9);
        }
        allocHandle.incMessagesRead(1);
        pipeline().fireChannelRead(msg);
        if (numBytesToBeConsumed == 0) {
            return true;
        }
        bytesConsumed(numBytesToBeConsumed);
        return true;
    }
}
