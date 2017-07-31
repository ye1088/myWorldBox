package io.netty.util.concurrent;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public final class ImmediateEventExecutor extends AbstractEventExecutor {
    private static final FastThreadLocal<Queue<Runnable>> DELAYED_RUNNABLES = new 1();
    public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
    private static final FastThreadLocal<Boolean> RUNNING = new 2();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ImmediateEventExecutor.class);
    private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());

    private ImmediateEventExecutor() {
    }

    public boolean inEventLoop() {
        return true;
    }

    public boolean inEventLoop(Thread thread) {
        return true;
    }

    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Deprecated
    public void shutdown() {
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return false;
    }

    public void execute(Runnable command) {
        Runnable runnable;
        if (command == null) {
            throw new NullPointerException("command");
        } else if (((Boolean) RUNNING.get()).booleanValue()) {
            ((Queue) DELAYED_RUNNABLES.get()).add(command);
        } else {
            RUNNING.set(Boolean.valueOf(true));
            Queue<Runnable> delayedRunnables;
            try {
                command.run();
                delayedRunnables = (Queue) DELAYED_RUNNABLES.get();
                while (true) {
                    runnable = (Runnable) delayedRunnables.poll();
                    if (runnable != null) {
                        try {
                            runnable.run();
                        } catch (Throwable cause) {
                            logger.info("Throwable caught while executing Runnable {}", runnable, cause);
                        }
                    } else {
                        RUNNING.set(Boolean.valueOf(false));
                        return;
                    }
                }
            } catch (Throwable cause2) {
                logger.info("Throwable caught while executing Runnable {}", runnable, cause2);
            }
        }
    }

    public <V> Promise<V> newPromise() {
        return new ImmediatePromise(this);
    }

    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new ImmediateProgressivePromise(this);
    }
}
