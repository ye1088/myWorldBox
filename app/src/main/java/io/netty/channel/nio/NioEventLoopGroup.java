package io.netty.channel.nio;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public class NioEventLoopGroup extends MultithreadEventLoopGroup {
    public NioEventLoopGroup() {
        this(0);
    }

    public NioEventLoopGroup(int nThreads) {
        this(nThreads, (Executor) null);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
        this(nThreads, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, Executor executor) {
        this(nThreads, executor, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
        this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, threadFactory, new Object[]{selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject()});
    }

    public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider) {
        this(nThreads, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, new Object[]{selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject()});
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(nThreads, executor, chooserFactory, new Object[]{selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject()});
    }

    public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(nThreads, executor, chooserFactory, new Object[]{selectorProvider, selectStrategyFactory, rejectedExecutionHandler});
    }

    public void setIoRatio(int ioRatio) {
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            ((NioEventLoop) ((EventExecutor) i$.next())).setIoRatio(ioRatio);
        }
    }

    public void rebuildSelectors() {
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            ((NioEventLoop) ((EventExecutor) i$.next())).rebuildSelector();
        }
    }

    protected EventLoop newChild(Executor executor, Object... args) throws Exception {
        return new NioEventLoop(this, executor, (SelectorProvider) args[0], ((SelectStrategyFactory) args[1]).newSelectStrategy(), (RejectedExecutionHandler) args[2]);
    }
}
