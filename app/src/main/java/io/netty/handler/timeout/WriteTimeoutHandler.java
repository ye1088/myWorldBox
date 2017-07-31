package io.netty.handler.timeout;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WriteTimeoutHandler extends ChannelOutboundHandlerAdapter {
    static final /* synthetic */ boolean $assertionsDisabled = (!WriteTimeoutHandler.class.desiredAssertionStatus());
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1);
    private boolean closed;
    private WriteTimeoutTask lastTask;
    private final long timeoutNanos;

    private final class WriteTimeoutTask implements ChannelFutureListener, Runnable {
        private final ChannelHandlerContext ctx;
        WriteTimeoutTask next;
        WriteTimeoutTask prev;
        private final ChannelPromise promise;
        ScheduledFuture<?> scheduledFuture;

        WriteTimeoutTask(ChannelHandlerContext ctx, ChannelPromise promise) {
            this.ctx = ctx;
            this.promise = promise;
        }

        public void run() {
            if (!this.promise.isDone()) {
                try {
                    WriteTimeoutHandler.this.writeTimedOut(this.ctx);
                } catch (Throwable t) {
                    this.ctx.fireExceptionCaught(t);
                }
            }
            WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
        }

        public void operationComplete(ChannelFuture future) throws Exception {
            this.scheduledFuture.cancel(false);
            WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
        }
    }

    public WriteTimeoutHandler(int timeoutSeconds) {
        this((long) timeoutSeconds, TimeUnit.SECONDS);
    }

    public WriteTimeoutHandler(long timeout, TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        } else if (timeout <= 0) {
            this.timeoutNanos = 0;
        } else {
            this.timeoutNanos = Math.max(unit.toNanos(timeout), MIN_TIMEOUT_NANOS);
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (this.timeoutNanos > 0) {
            promise = promise.unvoid();
            scheduleTimeout(ctx, promise);
        }
        ctx.write(msg, promise);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        WriteTimeoutTask task = this.lastTask;
        this.lastTask = null;
        while (task != null) {
            task.scheduledFuture.cancel(false);
            WriteTimeoutTask prev = task.prev;
            task.prev = null;
            task.next = null;
            task = prev;
        }
    }

    private void scheduleTimeout(ChannelHandlerContext ctx, ChannelPromise promise) {
        WriteTimeoutTask task = new WriteTimeoutTask(ctx, promise);
        task.scheduledFuture = ctx.executor().schedule(task, this.timeoutNanos, TimeUnit.NANOSECONDS);
        if (!task.scheduledFuture.isDone()) {
            addWriteTimeoutTask(task);
            promise.addListener(task);
        }
    }

    private void addWriteTimeoutTask(WriteTimeoutTask task) {
        if (this.lastTask == null) {
            this.lastTask = task;
            return;
        }
        this.lastTask.next = task;
        task.prev = this.lastTask;
        this.lastTask = task;
    }

    private void removeWriteTimeoutTask(WriteTimeoutTask task) {
        if (task == this.lastTask) {
            if ($assertionsDisabled || task.next == null) {
                this.lastTask = this.lastTask.prev;
                if (this.lastTask != null) {
                    this.lastTask.next = null;
                }
            } else {
                throw new AssertionError();
            }
        } else if (task.prev != null || task.next != null) {
            if (task.prev == null) {
                task.next.prev = null;
            } else {
                task.prev.next = task.next;
                task.next.prev = task.prev;
            }
        } else {
            return;
        }
        task.prev = null;
        task.next = null;
    }

    protected void writeTimedOut(ChannelHandlerContext ctx) throws Exception {
        if (!this.closed) {
            ctx.fireExceptionCaught(WriteTimeoutException.INSTANCE);
            ctx.close();
            this.closed = true;
        }
    }
}
