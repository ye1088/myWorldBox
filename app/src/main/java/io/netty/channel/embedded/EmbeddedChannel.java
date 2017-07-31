package io.netty.channel.embedded;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

public class EmbeddedChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = (!EmbeddedChannel.class.desiredAssertionStatus());
    private static final ChannelHandler[] EMPTY_HANDLERS = new ChannelHandler[0];
    private static final SocketAddress LOCAL_ADDRESS = new EmbeddedSocketAddress();
    private static final ChannelMetadata METADATA_DISCONNECT = new ChannelMetadata(true);
    private static final ChannelMetadata METADATA_NO_DISCONNECT = new ChannelMetadata(false);
    private static final SocketAddress REMOTE_ADDRESS = new EmbeddedSocketAddress();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
    private final ChannelConfig config;
    private Queue<Object> inboundMessages;
    private Throwable lastException;
    private final EmbeddedEventLoop loop;
    private final ChannelMetadata metadata;
    private Queue<Object> outboundMessages;
    private final ChannelFutureListener recordExceptionListener;
    private State state;

    public EmbeddedChannel() {
        this(EMPTY_HANDLERS);
    }

    public EmbeddedChannel(ChannelId channelId) {
        this(channelId, EMPTY_HANDLERS);
    }

    public EmbeddedChannel(ChannelHandler... handlers) {
        this(EmbeddedChannelId.INSTANCE, handlers);
    }

    public EmbeddedChannel(boolean hasDisconnect, ChannelHandler... handlers) {
        this(EmbeddedChannelId.INSTANCE, hasDisconnect, handlers);
    }

    public EmbeddedChannel(ChannelId channelId, ChannelHandler... handlers) {
        this(channelId, false, handlers);
    }

    public EmbeddedChannel(ChannelId channelId, boolean hasDisconnect, ChannelHandler... handlers) {
        super(null, channelId);
        this.loop = new EmbeddedEventLoop();
        this.recordExceptionListener = new 1(this);
        this.metadata = metadata(hasDisconnect);
        this.config = new DefaultChannelConfig(this);
        setup(handlers);
    }

    public EmbeddedChannel(ChannelId channelId, boolean hasDisconnect, ChannelConfig config, ChannelHandler... handlers) {
        super(null, channelId);
        this.loop = new EmbeddedEventLoop();
        this.recordExceptionListener = new 1(this);
        this.metadata = metadata(hasDisconnect);
        this.config = (ChannelConfig) ObjectUtil.checkNotNull(config, "config");
        setup(handlers);
    }

    private static ChannelMetadata metadata(boolean hasDisconnect) {
        return hasDisconnect ? METADATA_DISCONNECT : METADATA_NO_DISCONNECT;
    }

    private void setup(ChannelHandler... handlers) {
        ObjectUtil.checkNotNull(handlers, "handlers");
        pipeline().addLast(new ChannelHandler[]{new 2(this, handlers)});
        ChannelFuture future = this.loop.register((Channel) this);
        if (!$assertionsDisabled && !future.isDone()) {
            throw new AssertionError();
        }
    }

    protected final DefaultChannelPipeline newChannelPipeline() {
        return new EmbeddedChannelPipeline(this, this);
    }

    public ChannelMetadata metadata() {
        return this.metadata;
    }

    public ChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return this.state != State.CLOSED;
    }

    public boolean isActive() {
        return this.state == State.ACTIVE;
    }

    public Queue<Object> inboundMessages() {
        if (this.inboundMessages == null) {
            this.inboundMessages = new ArrayDeque();
        }
        return this.inboundMessages;
    }

    @Deprecated
    public Queue<Object> lastInboundBuffer() {
        return inboundMessages();
    }

    public Queue<Object> outboundMessages() {
        if (this.outboundMessages == null) {
            this.outboundMessages = new ArrayDeque();
        }
        return this.outboundMessages;
    }

    @Deprecated
    public Queue<Object> lastOutboundBuffer() {
        return outboundMessages();
    }

    public <T> T readInbound() {
        return poll(this.inboundMessages);
    }

    public <T> T readOutbound() {
        return poll(this.outboundMessages);
    }

    public boolean writeInbound(Object... msgs) {
        ensureOpen();
        if (msgs.length == 0) {
            return isNotEmpty(this.inboundMessages);
        }
        ChannelPipeline p = pipeline();
        for (Object m : msgs) {
            p.fireChannelRead(m);
        }
        p.fireChannelReadComplete();
        runPendingTasks();
        checkException();
        return isNotEmpty(this.inboundMessages);
    }

    public boolean writeOutbound(Object... msgs) {
        ensureOpen();
        if (msgs.length == 0) {
            return isNotEmpty(this.outboundMessages);
        }
        RecyclableArrayList futures = RecyclableArrayList.newInstance(msgs.length);
        try {
            for (Object m : msgs) {
                if (m == null) {
                    break;
                }
                futures.add(write(m));
            }
            runPendingTasks();
            flush();
            int size = futures.size();
            for (int i = 0; i < size; i++) {
                ChannelFuture future = (ChannelFuture) futures.get(i);
                if (future.isDone()) {
                    recordException(future);
                } else {
                    future.addListener(this.recordExceptionListener);
                }
            }
            checkException();
            boolean isNotEmpty = isNotEmpty(this.outboundMessages);
            return isNotEmpty;
        } finally {
            futures.recycle();
        }
    }

    public boolean finish() {
        return finish(false);
    }

    public boolean finishAndReleaseAll() {
        return finish(true);
    }

    private boolean finish(boolean releaseAll) {
        close();
        try {
            checkException();
            boolean z = isNotEmpty(this.inboundMessages) || isNotEmpty(this.outboundMessages);
            if (releaseAll) {
                releaseAll(this.inboundMessages);
                releaseAll(this.outboundMessages);
            }
            return z;
        } catch (Throwable th) {
            if (releaseAll) {
                releaseAll(this.inboundMessages);
                releaseAll(this.outboundMessages);
            }
        }
    }

    public boolean releaseInbound() {
        return releaseAll(this.inboundMessages);
    }

    public boolean releaseOutbound() {
        return releaseAll(this.outboundMessages);
    }

    private static boolean releaseAll(Queue<Object> queue) {
        if (!isNotEmpty(queue)) {
            return false;
        }
        while (true) {
            Object msg = queue.poll();
            if (msg == null) {
                return true;
            }
            ReferenceCountUtil.release(msg);
        }
    }

    private void finishPendingTasks(boolean cancel) {
        runPendingTasks();
        if (cancel) {
            this.loop.cancelScheduledTasks();
        }
    }

    public final ChannelFuture close() {
        return close(newPromise());
    }

    public final ChannelFuture disconnect() {
        return disconnect(newPromise());
    }

    public final ChannelFuture close(ChannelPromise promise) {
        runPendingTasks();
        ChannelFuture future = super.close(promise);
        finishPendingTasks(true);
        return future;
    }

    public final ChannelFuture disconnect(ChannelPromise promise) {
        ChannelFuture future = super.disconnect(promise);
        finishPendingTasks(!this.metadata.hasDisconnect());
        return future;
    }

    private static boolean isNotEmpty(Queue<Object> queue) {
        return (queue == null || queue.isEmpty()) ? false : true;
    }

    private static Object poll(Queue<Object> queue) {
        return queue != null ? queue.poll() : null;
    }

    public void runPendingTasks() {
        try {
            this.loop.runTasks();
        } catch (Throwable e) {
            recordException(e);
        }
        try {
            this.loop.runScheduledTasks();
        } catch (Throwable e2) {
            recordException(e2);
        }
    }

    public long runScheduledPendingTasks() {
        try {
            return this.loop.runScheduledTasks();
        } catch (Throwable e) {
            recordException(e);
            return this.loop.nextScheduledTask();
        }
    }

    private void recordException(ChannelFuture future) {
        if (!future.isSuccess()) {
            recordException(future.cause());
        }
    }

    private void recordException(Throwable cause) {
        if (this.lastException == null) {
            this.lastException = cause;
        } else {
            logger.warn("More than one exception was raised. Will report only the first one and log others.", cause);
        }
    }

    public void checkException() {
        Throwable t = this.lastException;
        if (t != null) {
            this.lastException = null;
            PlatformDependent.throwException(t);
        }
    }

    protected final void ensureOpen() {
        if (!isOpen()) {
            recordException(new ClosedChannelException());
            checkException();
        }
    }

    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof EmbeddedEventLoop;
    }

    protected SocketAddress localAddress0() {
        return isActive() ? LOCAL_ADDRESS : null;
    }

    protected SocketAddress remoteAddress0() {
        return isActive() ? REMOTE_ADDRESS : null;
    }

    protected void doRegister() throws Exception {
        this.state = State.ACTIVE;
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
    }

    protected void doDisconnect() throws Exception {
        if (!this.metadata.hasDisconnect()) {
            doClose();
        }
    }

    protected void doClose() throws Exception {
        this.state = State.CLOSED;
    }

    protected void doBeginRead() throws Exception {
    }

    protected AbstractUnsafe newUnsafe() {
        return new DefaultUnsafe(this, null);
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        while (true) {
            Object msg = in.current();
            if (msg != null) {
                ReferenceCountUtil.retain(msg);
                outboundMessages().add(msg);
                in.remove();
            } else {
                return;
            }
        }
    }
}
