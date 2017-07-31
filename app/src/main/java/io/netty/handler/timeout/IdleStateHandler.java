package io.netty.handler.timeout;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class IdleStateHandler extends ChannelDuplexHandler {
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1);
    private final long allIdleTimeNanos;
    volatile ScheduledFuture<?> allIdleTimeout;
    private boolean firstAllIdleEvent;
    private boolean firstReaderIdleEvent;
    private boolean firstWriterIdleEvent;
    volatile long lastReadTime;
    volatile long lastWriteTime;
    private final long readerIdleTimeNanos;
    volatile ScheduledFuture<?> readerIdleTimeout;
    private volatile boolean reading;
    private volatile int state;
    private final ChannelFutureListener writeListener;
    private final long writerIdleTimeNanos;
    volatile ScheduledFuture<?> writerIdleTimeout;

    private final class AllIdleTimeoutTask implements Runnable {
        private final ChannelHandlerContext ctx;

        AllIdleTimeoutTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            if (this.ctx.channel().isOpen()) {
                long nextDelay = IdleStateHandler.this.allIdleTimeNanos;
                if (!IdleStateHandler.this.reading) {
                    nextDelay -= System.nanoTime() - Math.max(IdleStateHandler.this.lastReadTime, IdleStateHandler.this.lastWriteTime);
                }
                if (nextDelay <= 0) {
                    IdleStateHandler.this.allIdleTimeout = this.ctx.executor().schedule(this, IdleStateHandler.this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
                    try {
                        IdleStateEvent event = IdleStateHandler.this.newIdleStateEvent(IdleState.ALL_IDLE, IdleStateHandler.this.firstAllIdleEvent);
                        if (IdleStateHandler.this.firstAllIdleEvent) {
                            IdleStateHandler.this.firstAllIdleEvent = false;
                        }
                        IdleStateHandler.this.channelIdle(this.ctx, event);
                        return;
                    } catch (Throwable t) {
                        this.ctx.fireExceptionCaught(t);
                        return;
                    }
                }
                IdleStateHandler.this.allIdleTimeout = this.ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }

    private final class ReaderIdleTimeoutTask implements Runnable {
        private final ChannelHandlerContext ctx;

        ReaderIdleTimeoutTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            if (this.ctx.channel().isOpen()) {
                long nextDelay = IdleStateHandler.this.readerIdleTimeNanos;
                if (!IdleStateHandler.this.reading) {
                    nextDelay -= System.nanoTime() - IdleStateHandler.this.lastReadTime;
                }
                if (nextDelay <= 0) {
                    IdleStateHandler.this.readerIdleTimeout = this.ctx.executor().schedule(this, IdleStateHandler.this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
                    try {
                        IdleStateEvent event = IdleStateHandler.this.newIdleStateEvent(IdleState.READER_IDLE, IdleStateHandler.this.firstReaderIdleEvent);
                        if (IdleStateHandler.this.firstReaderIdleEvent) {
                            IdleStateHandler.this.firstReaderIdleEvent = false;
                        }
                        IdleStateHandler.this.channelIdle(this.ctx, event);
                        return;
                    } catch (Throwable t) {
                        this.ctx.fireExceptionCaught(t);
                        return;
                    }
                }
                IdleStateHandler.this.readerIdleTimeout = this.ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }

    private final class WriterIdleTimeoutTask implements Runnable {
        private final ChannelHandlerContext ctx;

        WriterIdleTimeoutTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            if (this.ctx.channel().isOpen()) {
                long nextDelay = IdleStateHandler.this.writerIdleTimeNanos - (System.nanoTime() - IdleStateHandler.this.lastWriteTime);
                if (nextDelay <= 0) {
                    IdleStateHandler.this.writerIdleTimeout = this.ctx.executor().schedule(this, IdleStateHandler.this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
                    try {
                        IdleStateEvent event = IdleStateHandler.this.newIdleStateEvent(IdleState.WRITER_IDLE, IdleStateHandler.this.firstWriterIdleEvent);
                        if (IdleStateHandler.this.firstWriterIdleEvent) {
                            IdleStateHandler.this.firstWriterIdleEvent = false;
                        }
                        IdleStateHandler.this.channelIdle(this.ctx, event);
                        return;
                    } catch (Throwable t) {
                        this.ctx.fireExceptionCaught(t);
                        return;
                    }
                }
                IdleStateHandler.this.writerIdleTimeout = this.ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }

    public IdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        this((long) readerIdleTimeSeconds, (long) writerIdleTimeSeconds, (long) allIdleTimeSeconds, TimeUnit.SECONDS);
    }

    public IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        this.writeListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                IdleStateHandler.this.lastWriteTime = System.nanoTime();
                IdleStateHandler.this.firstWriterIdleEvent = IdleStateHandler.this.firstAllIdleEvent = true;
            }
        };
        this.firstReaderIdleEvent = true;
        this.firstWriterIdleEvent = true;
        this.firstAllIdleEvent = true;
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (readerIdleTime <= 0) {
            this.readerIdleTimeNanos = 0;
        } else {
            this.readerIdleTimeNanos = Math.max(unit.toNanos(readerIdleTime), MIN_TIMEOUT_NANOS);
        }
        if (writerIdleTime <= 0) {
            this.writerIdleTimeNanos = 0;
        } else {
            this.writerIdleTimeNanos = Math.max(unit.toNanos(writerIdleTime), MIN_TIMEOUT_NANOS);
        }
        if (allIdleTime <= 0) {
            this.allIdleTimeNanos = 0;
        } else {
            this.allIdleTimeNanos = Math.max(unit.toNanos(allIdleTime), MIN_TIMEOUT_NANOS);
        }
    }

    public long getReaderIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.readerIdleTimeNanos);
    }

    public long getWriterIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.writerIdleTimeNanos);
    }

    public long getAllIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.allIdleTimeNanos);
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive() && ctx.channel().isRegistered()) {
            initialize(ctx);
        }
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        destroy();
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive()) {
            initialize(ctx);
        }
        super.channelRegistered(ctx);
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        initialize(ctx);
        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        destroy();
        super.channelInactive(ctx);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (this.readerIdleTimeNanos > 0 || this.allIdleTimeNanos > 0) {
            this.reading = true;
            this.firstAllIdleEvent = true;
            this.firstReaderIdleEvent = true;
        }
        ctx.fireChannelRead(msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (this.readerIdleTimeNanos > 0 || this.allIdleTimeNanos > 0) {
            this.lastReadTime = System.nanoTime();
            this.reading = false;
        }
        ctx.fireChannelReadComplete();
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (this.writerIdleTimeNanos > 0 || this.allIdleTimeNanos > 0) {
            ChannelPromise unvoid = promise.unvoid();
            unvoid.addListener(this.writeListener);
            ctx.write(msg, unvoid);
            return;
        }
        ctx.write(msg, promise);
    }

    private void initialize(ChannelHandlerContext ctx) {
        switch (this.state) {
            case 1:
            case 2:
                return;
            default:
                this.state = 1;
                EventExecutor loop = ctx.executor();
                long nanoTime = System.nanoTime();
                this.lastWriteTime = nanoTime;
                this.lastReadTime = nanoTime;
                if (this.readerIdleTimeNanos > 0) {
                    this.readerIdleTimeout = loop.schedule(new ReaderIdleTimeoutTask(ctx), this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
                }
                if (this.writerIdleTimeNanos > 0) {
                    this.writerIdleTimeout = loop.schedule(new WriterIdleTimeoutTask(ctx), this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
                }
                if (this.allIdleTimeNanos > 0) {
                    this.allIdleTimeout = loop.schedule(new AllIdleTimeoutTask(ctx), this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
                    return;
                }
                return;
        }
    }

    private void destroy() {
        this.state = 2;
        if (this.readerIdleTimeout != null) {
            this.readerIdleTimeout.cancel(false);
            this.readerIdleTimeout = null;
        }
        if (this.writerIdleTimeout != null) {
            this.writerIdleTimeout.cancel(false);
            this.writerIdleTimeout = null;
        }
        if (this.allIdleTimeout != null) {
            this.allIdleTimeout.cancel(false);
            this.allIdleTimeout = null;
        }
    }

    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }

    protected IdleStateEvent newIdleStateEvent(IdleState state, boolean first) {
        switch (state) {
            case ALL_IDLE:
                return first ? IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT : IdleStateEvent.ALL_IDLE_STATE_EVENT;
            case READER_IDLE:
                return first ? IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT : IdleStateEvent.READER_IDLE_STATE_EVENT;
            case WRITER_IDLE:
                return first ? IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT : IdleStateEvent.WRITER_IDLE_STATE_EVENT;
            default:
                throw new Error();
        }
    }
}
