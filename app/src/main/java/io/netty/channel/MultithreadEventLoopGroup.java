package io.netty.channel;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup {
    private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);

    protected abstract EventLoop newChild(Executor executor, Object... objArr) throws Exception;

    static {
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.eventLoopThreads: {}", Integer.valueOf(DEFAULT_EVENT_LOOP_THREADS));
        }
    }

    protected MultithreadEventLoopGroup(int nThreads, Executor executor, Object... args) {
        if (nThreads == 0) {
            nThreads = DEFAULT_EVENT_LOOP_THREADS;
        }
        super(nThreads, executor, args);
    }

    protected MultithreadEventLoopGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
        if (nThreads == 0) {
            nThreads = DEFAULT_EVENT_LOOP_THREADS;
        }
        super(nThreads, threadFactory, args);
    }

    protected MultithreadEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, Object... args) {
        if (nThreads == 0) {
            nThreads = DEFAULT_EVENT_LOOP_THREADS;
        }
        super(nThreads, executor, chooserFactory, args);
    }

    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(getClass(), 10);
    }

    public EventLoop next() {
        return (EventLoop) super.next();
    }

    public ChannelFuture register(Channel channel) {
        return next().register(channel);
    }

    public ChannelFuture register(ChannelPromise promise) {
        return next().register(promise);
    }

    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        return next().register(channel, promise);
    }
}
