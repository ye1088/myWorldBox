package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.PlatformDependent;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

@Sharable
public class GlobalTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
    long maxGlobalWriteSize = 419430400;
    private final AtomicLong queuesSize = new AtomicLong();

    void createGlobalTrafficCounter(ScheduledExecutorService executor) {
        if (executor == null) {
            throw new NullPointerException("executor");
        }
        TrafficCounter tc = new TrafficCounter(this, executor, "GlobalTC", this.checkInterval);
        setTrafficCounter(tc);
        tc.start();
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit, long checkInterval, long maxTime) {
        super(writeLimit, readLimit, checkInterval, maxTime);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit, long checkInterval) {
        super(writeLimit, readLimit, checkInterval);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long writeLimit, long readLimit) {
        super(writeLimit, readLimit);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval) {
        super(checkInterval);
        createGlobalTrafficCounter(executor);
    }

    public GlobalTrafficShapingHandler(EventExecutor executor) {
        createGlobalTrafficCounter(executor);
    }

    public long getMaxGlobalWriteSize() {
        return this.maxGlobalWriteSize;
    }

    public void setMaxGlobalWriteSize(long maxGlobalWriteSize) {
        this.maxGlobalWriteSize = maxGlobalWriteSize;
    }

    public long queuesSize() {
        return this.queuesSize.get();
    }

    public final void release() {
        this.trafficCounter.stop();
    }

    private PerChannel getOrSetPerChannel(ChannelHandlerContext ctx) {
        Integer key = Integer.valueOf(ctx.channel().hashCode());
        PerChannel perChannel = (PerChannel) this.channelQueues.get(key);
        if (perChannel != null) {
            return perChannel;
        }
        perChannel = new PerChannel(null);
        perChannel.messagesQueue = new ArrayDeque();
        perChannel.queueSize = 0;
        perChannel.lastReadTimestamp = TrafficCounter.milliSecondFromNano();
        perChannel.lastWriteTimestamp = perChannel.lastReadTimestamp;
        this.channelQueues.put(key, perChannel);
        return perChannel;
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        getOrSetPerChannel(ctx);
        super.handlerAdded(ctx);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        PerChannel perChannel = (PerChannel) this.channelQueues.remove(Integer.valueOf(channel.hashCode()));
        if (perChannel != null) {
            synchronized (perChannel) {
                Iterator i$;
                ToSend toSend;
                if (channel.isActive()) {
                    i$ = perChannel.messagesQueue.iterator();
                    while (i$.hasNext()) {
                        toSend = (ToSend) i$.next();
                        long size = calculateSize(toSend.toSend);
                        this.trafficCounter.bytesRealWriteFlowControl(size);
                        perChannel.queueSize -= size;
                        this.queuesSize.addAndGet(-size);
                        ctx.write(toSend.toSend, toSend.promise);
                    }
                } else {
                    this.queuesSize.addAndGet(-perChannel.queueSize);
                    i$ = perChannel.messagesQueue.iterator();
                    while (i$.hasNext()) {
                        toSend = (ToSend) i$.next();
                        if (toSend.toSend instanceof ByteBuf) {
                            ((ByteBuf) toSend.toSend).release();
                        }
                    }
                }
                perChannel.messagesQueue.clear();
            }
        }
        releaseWriteSuspended(ctx);
        releaseReadSuspended(ctx);
        super.handlerRemoved(ctx);
    }

    long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
        if (perChannel == null || wait <= this.maxTime || (now + wait) - perChannel.lastReadTimestamp <= this.maxTime) {
            return wait;
        }
        return this.maxTime;
    }

    void informReadOperation(ChannelHandlerContext ctx, long now) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
        if (perChannel != null) {
            perChannel.lastReadTimestamp = now;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void submitWrite(io.netty.channel.ChannelHandlerContext r22, java.lang.Object r23, long r24, long r26, long r28, io.netty.channel.ChannelPromise r30) {
        /*
        r21 = this;
        r12 = r22.channel();
        r5 = r12.hashCode();
        r16 = java.lang.Integer.valueOf(r5);
        r0 = r21;
        r5 = r0.channelQueues;
        r0 = r16;
        r17 = r5.get(r0);
        r17 = (io.netty.handler.traffic.GlobalTrafficShapingHandler.PerChannel) r17;
        if (r17 != 0) goto L_0x001e;
    L_0x001a:
        r17 = r21.getOrSetPerChannel(r22);
    L_0x001e:
        r14 = r26;
        r13 = 0;
        monitor-enter(r17);
        r6 = 0;
        r5 = (r26 > r6 ? 1 : (r26 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x004c;
    L_0x0028:
        r0 = r17;
        r5 = r0.messagesQueue;	 Catch:{ all -> 0x00d6 }
        r5 = r5.isEmpty();	 Catch:{ all -> 0x00d6 }
        if (r5 == 0) goto L_0x004c;
    L_0x0032:
        r0 = r21;
        r5 = r0.trafficCounter;	 Catch:{ all -> 0x00d6 }
        r0 = r24;
        r5.bytesRealWriteFlowControl(r0);	 Catch:{ all -> 0x00d6 }
        r0 = r22;
        r1 = r23;
        r2 = r30;
        r0.write(r1, r2);	 Catch:{ all -> 0x00d6 }
        r0 = r28;
        r2 = r17;
        r2.lastWriteTimestamp = r0;	 Catch:{ all -> 0x00d6 }
        monitor-exit(r17);	 Catch:{ all -> 0x00d6 }
    L_0x004b:
        return;
    L_0x004c:
        r0 = r21;
        r6 = r0.maxTime;	 Catch:{ all -> 0x00d6 }
        r5 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1));
        if (r5 <= 0) goto L_0x006c;
    L_0x0054:
        r6 = r28 + r14;
        r0 = r17;
        r0 = r0.lastWriteTimestamp;	 Catch:{ all -> 0x00d6 }
        r18 = r0;
        r6 = r6 - r18;
        r0 = r21;
        r0 = r0.maxTime;	 Catch:{ all -> 0x00d6 }
        r18 = r0;
        r5 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1));
        if (r5 <= 0) goto L_0x006c;
    L_0x0068:
        r0 = r21;
        r14 = r0.maxTime;	 Catch:{ all -> 0x00d6 }
    L_0x006c:
        r4 = new io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend;	 Catch:{ all -> 0x00d6 }
        r5 = r14 + r28;
        r11 = 0;
        r7 = r23;
        r8 = r24;
        r10 = r30;
        r4.<init>(r5, r7, r8, r10, r11);	 Catch:{ all -> 0x00d6 }
        r0 = r17;
        r5 = r0.messagesQueue;	 Catch:{ all -> 0x00d6 }
        r5.addLast(r4);	 Catch:{ all -> 0x00d6 }
        r0 = r17;
        r6 = r0.queueSize;	 Catch:{ all -> 0x00d6 }
        r6 = r6 + r24;
        r0 = r17;
        r0.queueSize = r6;	 Catch:{ all -> 0x00d6 }
        r0 = r21;
        r5 = r0.queuesSize;	 Catch:{ all -> 0x00d6 }
        r0 = r24;
        r5.addAndGet(r0);	 Catch:{ all -> 0x00d6 }
        r0 = r17;
        r10 = r0.queueSize;	 Catch:{ all -> 0x00d6 }
        r6 = r21;
        r7 = r22;
        r8 = r14;
        r6.checkWriteSuspend(r7, r8, r10);	 Catch:{ all -> 0x00d6 }
        r0 = r21;
        r5 = r0.queuesSize;	 Catch:{ all -> 0x00d6 }
        r6 = r5.get();	 Catch:{ all -> 0x00d6 }
        r0 = r21;
        r0 = r0.maxGlobalWriteSize;	 Catch:{ all -> 0x00d6 }
        r18 = r0;
        r5 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1));
        if (r5 <= 0) goto L_0x00b3;
    L_0x00b2:
        r13 = 1;
    L_0x00b3:
        monitor-exit(r17);	 Catch:{ all -> 0x00d6 }
        if (r13 == 0) goto L_0x00be;
    L_0x00b6:
        r5 = 0;
        r0 = r21;
        r1 = r22;
        r0.setUserDefinedWritability(r1, r5);
    L_0x00be:
        r10 = r4.relativeTimeAction;
        r9 = r17;
        r5 = r22.executor();
        r6 = new io.netty.handler.traffic.GlobalTrafficShapingHandler$1;
        r7 = r21;
        r8 = r22;
        r6.<init>(r7, r8, r9, r10);
        r7 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r5.schedule(r6, r14, r7);
        goto L_0x004b;
    L_0x00d6:
        r5 = move-exception;
        monitor-exit(r17);	 Catch:{ all -> 0x00d6 }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.GlobalTrafficShapingHandler.submitWrite(io.netty.channel.ChannelHandlerContext, java.lang.Object, long, long, long, io.netty.channel.ChannelPromise):void");
    }

    private void sendAllValid(ChannelHandlerContext ctx, PerChannel perChannel, long now) {
        synchronized (perChannel) {
            ToSend newToSend = (ToSend) perChannel.messagesQueue.pollFirst();
            while (newToSend != null) {
                if (newToSend.relativeTimeAction > now) {
                    perChannel.messagesQueue.addFirst(newToSend);
                    break;
                }
                long size = newToSend.size;
                this.trafficCounter.bytesRealWriteFlowControl(size);
                perChannel.queueSize -= size;
                this.queuesSize.addAndGet(-size);
                ctx.write(newToSend.toSend, newToSend.promise);
                perChannel.lastWriteTimestamp = now;
                newToSend = (ToSend) perChannel.messagesQueue.pollFirst();
            }
            if (perChannel.messagesQueue.isEmpty()) {
                releaseWriteSuspended(ctx);
            }
        }
        ctx.flush();
    }
}
