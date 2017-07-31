package io.netty.channel.epoll;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public final class EpollEventLoopGroup extends MultithreadEventLoopGroup {
    public EpollEventLoopGroup() {
        this(0);
    }

    public EpollEventLoopGroup(int nThreads) {
        this(nThreads, (ThreadFactory) null);
    }

    public EpollEventLoopGroup(int nThreads, SelectStrategyFactory selectStrategyFactory) {
        this(nThreads, (ThreadFactory) null, selectStrategyFactory);
    }

    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, 0);
    }

    public EpollEventLoopGroup(int nThreads, Executor executor) {
        this(nThreads, executor, DefaultSelectStrategyFactory.INSTANCE);
    }

    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectStrategyFactory selectStrategyFactory) {
        this(nThreads, threadFactory, 0, selectStrategyFactory);
    }

    @Deprecated
    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce) {
        this(nThreads, threadFactory, maxEventsAtOnce, DefaultSelectStrategyFactory.INSTANCE);
    }

    @Deprecated
    public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, Integer.valueOf(maxEventsAtOnce), selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public EpollEventLoopGroup(int nThreads, Executor executor, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, Integer.valueOf(0), selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public EpollEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, chooserFactory, Integer.valueOf(0), selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public EpollEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor, chooserFactory, Integer.valueOf(0), selectStrategyFactory, rejectedExecutionHandler);
    }

    public void setIoRatio(int ioRatio) {
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            ((EpollEventLoop) ((EventExecutor) i$.next())).setIoRatio(ioRatio);
        }
    }

    protected EventLoop newChild(Executor executor, Object... args) throws Exception {
        return new EpollEventLoop(this, executor, ((Integer) args[0]).intValue(), ((SelectStrategyFactory) args[1]).newSelectStrategy(), (RejectedExecutionHandler) args[2]);
    }
}
