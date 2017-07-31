package io.netty.channel;

import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import io.netty.util.internal.ThrowableUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPerChannelEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
    final Set<EventLoop> activeChildren;
    private final Object[] childArgs;
    private final FutureListener<Object> childTerminationListener;
    final Executor executor;
    final Queue<EventLoop> idleChildren;
    private final int maxChannels;
    private volatile boolean shuttingDown;
    private final Promise<?> terminationFuture;
    private final ChannelException tooManyChannels;

    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels) {
        this(maxChannels, Executors.defaultThreadFactory(), new Object[0]);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels, ThreadFactory threadFactory, Object... args) {
        this(maxChannels, new ThreadPerTaskExecutor(threadFactory), args);
    }

    protected ThreadPerChannelEventLoopGroup(int maxChannels, Executor executor, Object... args) {
        this.activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
        this.idleChildren = new ConcurrentLinkedQueue();
        this.terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        this.childTerminationListener = new 1(this);
        if (maxChannels < 0) {
            throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", new Object[]{Integer.valueOf(maxChannels)}));
        } else if (executor == null) {
            throw new NullPointerException("executor");
        } else {
            if (args == null) {
                this.childArgs = EmptyArrays.EMPTY_OBJECTS;
            } else {
                this.childArgs = (Object[]) args.clone();
            }
            this.maxChannels = maxChannels;
            this.executor = executor;
            this.tooManyChannels = (ChannelException) ThrowableUtil.unknownStackTrace(new ChannelException("too many channels (max: " + maxChannels + ')'), ThreadPerChannelEventLoopGroup.class, "nextChild()");
        }
    }

    protected EventLoop newChild(Object... args) throws Exception {
        return new ThreadPerChannelEventLoop(this);
    }

    public Iterator<EventExecutor> iterator() {
        return new ReadOnlyIterator(this.activeChildren.iterator());
    }

    public EventLoop next() {
        throw new UnsupportedOperationException();
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        this.shuttingDown = true;
        for (EventLoop l : this.activeChildren) {
            l.shutdownGracefully(quietPeriod, timeout, unit);
        }
        for (EventLoop l2 : this.idleChildren) {
            l2.shutdownGracefully(quietPeriod, timeout, unit);
        }
        if (isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
        this.shuttingDown = true;
        for (EventLoop l : this.activeChildren) {
            l.shutdown();
        }
        for (EventLoop l2 : this.idleChildren) {
            l2.shutdown();
        }
        if (isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
    }

    public boolean isShuttingDown() {
        for (EventLoop l : this.activeChildren) {
            if (!l.isShuttingDown()) {
                return false;
            }
        }
        for (EventLoop l2 : this.idleChildren) {
            if (!l2.isShuttingDown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isShutdown() {
        for (EventLoop l : this.activeChildren) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        for (EventLoop l2 : this.idleChildren) {
            if (!l2.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTerminated() {
        for (EventLoop l : this.activeChildren) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        for (EventLoop l2 : this.idleChildren) {
            if (!l2.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long timeLeft;
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        for (EventLoop l : this.activeChildren) {
            do {
                timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0) {
                    return isTerminated();
                }
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        for (EventLoop l2 : this.idleChildren) {
            do {
                timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0) {
                    return isTerminated();
                }
            } while (!l2.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        return isTerminated();
    }

    public ChannelFuture register(Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        try {
            EventLoop l = nextChild();
            return l.register(new DefaultChannelPromise(channel, l));
        } catch (Throwable t) {
            return new FailedChannelFuture(channel, GlobalEventExecutor.INSTANCE, t);
        }
    }

    public ChannelFuture register(ChannelPromise promise) {
        try {
            promise = nextChild().register(promise);
        } catch (Throwable t) {
            promise.setFailure(t);
        }
        return promise;
    }

    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        try {
            promise = nextChild().register(channel, promise);
        } catch (Throwable t) {
            promise.setFailure(t);
        }
        return promise;
    }

    private EventLoop nextChild() throws Exception {
        if (this.shuttingDown) {
            throw new RejectedExecutionException("shutting down");
        }
        EventLoop loop = (EventLoop) this.idleChildren.poll();
        if (loop == null) {
            if (this.maxChannels <= 0 || this.activeChildren.size() < this.maxChannels) {
                loop = newChild(this.childArgs);
                loop.terminationFuture().addListener(this.childTerminationListener);
            } else {
                throw this.tooManyChannels;
            }
        }
        this.activeChildren.add(loop);
        return loop;
    }
}
