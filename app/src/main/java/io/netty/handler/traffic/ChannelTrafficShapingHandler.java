package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ArrayDeque<ToSend> messagesQueue = new ArrayDeque();
    private long queueSize;

    private static final class ToSend {
        final ChannelPromise promise;
        final long relativeTimeAction;
        final Object toSend;

        private ToSend(long delay, Object toSend, ChannelPromise promise) {
            this.relativeTimeAction = delay;
            this.toSend = toSend;
            this.promise = promise;
        }
    }

    public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime) {
        super(writeLimit, readLimit, checkInterval, maxTime);
    }

    public ChannelTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
        super(writeLimit, readLimit, checkInterval);
    }

    public ChannelTrafficShapingHandler(long writeLimit, long readLimit) {
        super(writeLimit, readLimit);
    }

    public ChannelTrafficShapingHandler(long checkInterval) {
        super(checkInterval);
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        TrafficCounter trafficCounter = new TrafficCounter(this, ctx.executor(), "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
        setTrafficCounter(trafficCounter);
        trafficCounter.start();
        super.handlerAdded(ctx);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.trafficCounter.stop();
        synchronized (this) {
            Iterator i$;
            ToSend toSend;
            if (ctx.channel().isActive()) {
                i$ = this.messagesQueue.iterator();
                while (i$.hasNext()) {
                    toSend = (ToSend) i$.next();
                    long size = calculateSize(toSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(size);
                    this.queueSize -= size;
                    ctx.write(toSend.toSend, toSend.promise);
                }
            } else {
                i$ = this.messagesQueue.iterator();
                while (i$.hasNext()) {
                    toSend = (ToSend) i$.next();
                    if (toSend.toSend instanceof ByteBuf) {
                        ((ByteBuf) toSend.toSend).release();
                    }
                }
            }
            this.messagesQueue.clear();
        }
        releaseWriteSuspended(ctx);
        releaseReadSuspended(ctx);
        super.handlerRemoved(ctx);
    }

    void submitWrite(final ChannelHandlerContext ctx, Object msg, long size, long delay, long now, ChannelPromise promise) {
        synchronized (this) {
            if (delay == 0) {
                if (this.messagesQueue.isEmpty()) {
                    this.trafficCounter.bytesRealWriteFlowControl(size);
                    ctx.write(msg, promise);
                    return;
                }
            }
            ToSend newToSend = new ToSend(delay + now, msg, promise);
            this.messagesQueue.addLast(newToSend);
            this.queueSize += size;
            checkWriteSuspend(ctx, delay, this.queueSize);
            final long futureNow = newToSend.relativeTimeAction;
            ctx.executor().schedule(new Runnable() {
                public void run() {
                    ChannelTrafficShapingHandler.this.sendAllValid(ctx, futureNow);
                }
            }, delay, TimeUnit.MILLISECONDS);
        }
    }

    private void sendAllValid(ChannelHandlerContext ctx, long now) {
        synchronized (this) {
            ToSend newToSend = (ToSend) this.messagesQueue.pollFirst();
            while (newToSend != null) {
                if (newToSend.relativeTimeAction > now) {
                    this.messagesQueue.addFirst(newToSend);
                    break;
                }
                long size = calculateSize(newToSend.toSend);
                this.trafficCounter.bytesRealWriteFlowControl(size);
                this.queueSize -= size;
                ctx.write(newToSend.toSend, newToSend.promise);
                newToSend = (ToSend) this.messagesQueue.pollFirst();
            }
            if (this.messagesQueue.isEmpty()) {
                releaseWriteSuspended(ctx);
            }
        }
        ctx.flush();
    }

    public long queueSize() {
        return this.queueSize;
    }
}
