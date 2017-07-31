package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.AbstractTrafficShapingHandler.ReopenReadTimerTask;
import io.netty.util.Attribute;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Sharable
public class GlobalChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private static final float DEFAULT_ACCELERATION = -0.1f;
    private static final float DEFAULT_DEVIATION = 0.1f;
    private static final float DEFAULT_SLOWDOWN = 0.4f;
    private static final float MAX_DEVIATION = 0.4f;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalChannelTrafficShapingHandler.class);
    private volatile float accelerationFactor;
    final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
    private final AtomicLong cumulativeReadBytes = new AtomicLong();
    private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
    private volatile float maxDeviation;
    volatile long maxGlobalWriteSize = 419430400;
    private final AtomicLong queuesSize = new AtomicLong();
    private volatile long readChannelLimit;
    private volatile boolean readDeviationActive;
    private volatile float slowDownFactor;
    private volatile long writeChannelLimit;
    private volatile boolean writeDeviationActive;

    void createGlobalTrafficCounter(ScheduledExecutorService executor) {
        setMaxDeviation(DEFAULT_DEVIATION, 0.4f, DEFAULT_ACCELERATION);
        if (executor == null) {
            throw new IllegalArgumentException("Executor must not be null");
        }
        TrafficCounter tc = new GlobalChannelTrafficCounter(this, executor, "GlobalChannelTC", this.checkInterval);
        setTrafficCounter(tc);
        tc.start();
    }

    int userDefinedWritabilityIndex() {
        return 3;
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit, long checkInterval, long maxTime) {
        super(writeGlobalLimit, readGlobalLimit, checkInterval, maxTime);
        createGlobalTrafficCounter(executor);
        this.writeChannelLimit = writeChannelLimit;
        this.readChannelLimit = readChannelLimit;
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit, long checkInterval) {
        super(writeGlobalLimit, readGlobalLimit, checkInterval);
        this.writeChannelLimit = writeChannelLimit;
        this.readChannelLimit = readChannelLimit;
        createGlobalTrafficCounter(executor);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit) {
        super(writeGlobalLimit, readGlobalLimit);
        this.writeChannelLimit = writeChannelLimit;
        this.readChannelLimit = readChannelLimit;
        createGlobalTrafficCounter(executor);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval) {
        super(checkInterval);
        createGlobalTrafficCounter(executor);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor) {
        createGlobalTrafficCounter(executor);
    }

    public float maxDeviation() {
        return this.maxDeviation;
    }

    public float accelerationFactor() {
        return this.accelerationFactor;
    }

    public float slowDownFactor() {
        return this.slowDownFactor;
    }

    public void setMaxDeviation(float maxDeviation, float slowDownFactor, float accelerationFactor) {
        if (maxDeviation > 0.4f) {
            throw new IllegalArgumentException("maxDeviation must be <= 0.4");
        } else if (slowDownFactor < 0.0f) {
            throw new IllegalArgumentException("slowDownFactor must be >= 0");
        } else if (accelerationFactor > 0.0f) {
            throw new IllegalArgumentException("accelerationFactor must be <= 0");
        } else {
            this.maxDeviation = maxDeviation;
            this.accelerationFactor = 1.0f + accelerationFactor;
            this.slowDownFactor = 1.0f + slowDownFactor;
        }
    }

    private void computeDeviationCumulativeBytes() {
        long maxWrittenBytes = 0;
        long maxReadBytes = 0;
        long minWrittenBytes = Long.MAX_VALUE;
        long minReadBytes = Long.MAX_VALUE;
        for (PerChannel perChannel : this.channelQueues.values()) {
            long value = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
            if (maxWrittenBytes < value) {
                maxWrittenBytes = value;
            }
            if (minWrittenBytes > value) {
                minWrittenBytes = value;
            }
            value = perChannel.channelTrafficCounter.cumulativeReadBytes();
            if (maxReadBytes < value) {
                maxReadBytes = value;
            }
            if (minReadBytes > value) {
                minReadBytes = value;
            }
        }
        boolean multiple = this.channelQueues.size() > 1;
        boolean z = multiple && minReadBytes < maxReadBytes / 2;
        this.readDeviationActive = z;
        z = multiple && minWrittenBytes < maxWrittenBytes / 2;
        this.writeDeviationActive = z;
        this.cumulativeWrittenBytes.set(maxWrittenBytes);
        this.cumulativeReadBytes.set(maxReadBytes);
    }

    protected void doAccounting(TrafficCounter counter) {
        computeDeviationCumulativeBytes();
        super.doAccounting(counter);
    }

    private long computeBalancedWait(float maxLocal, float maxGlobal, long wait) {
        if (maxGlobal == 0.0f) {
            return wait;
        }
        float ratio = maxLocal / maxGlobal;
        if (ratio <= this.maxDeviation) {
            ratio = this.accelerationFactor;
        } else if (ratio < 1.0f - this.maxDeviation) {
            return wait;
        } else {
            ratio = this.slowDownFactor;
            if (wait < 10) {
                wait = 10;
            }
        }
        return (long) (((float) wait) * ratio);
    }

    public long getMaxGlobalWriteSize() {
        return this.maxGlobalWriteSize;
    }

    public void setMaxGlobalWriteSize(long maxGlobalWriteSize) {
        if (maxGlobalWriteSize <= 0) {
            throw new IllegalArgumentException("maxGlobalWriteSize must be positive");
        }
        this.maxGlobalWriteSize = maxGlobalWriteSize;
    }

    public long queuesSize() {
        return this.queuesSize.get();
    }

    public void configureChannel(long newWriteLimit, long newReadLimit) {
        this.writeChannelLimit = newWriteLimit;
        this.readChannelLimit = newReadLimit;
        long now = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(now);
        }
    }

    public long getWriteChannelLimit() {
        return this.writeChannelLimit;
    }

    public void setWriteChannelLimit(long writeLimit) {
        this.writeChannelLimit = writeLimit;
        long now = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(now);
        }
    }

    public long getReadChannelLimit() {
        return this.readChannelLimit;
    }

    public void setReadChannelLimit(long readLimit) {
        this.readChannelLimit = readLimit;
        long now = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(now);
        }
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
        perChannel = new PerChannel();
        perChannel.messagesQueue = new ArrayDeque();
        perChannel.channelTrafficCounter = new TrafficCounter(this, null, "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
        perChannel.queueSize = 0;
        perChannel.lastReadTimestamp = TrafficCounter.milliSecondFromNano();
        perChannel.lastWriteTimestamp = perChannel.lastReadTimestamp;
        this.channelQueues.put(key, perChannel);
        return perChannel;
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        getOrSetPerChannel(ctx);
        this.trafficCounter.resetCumulativeTime();
        super.handlerAdded(ctx);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.trafficCounter.resetCumulativeTime();
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
                        perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
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

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        long size = calculateSize(msg);
        long now = TrafficCounter.milliSecondFromNano();
        if (size > 0) {
            long waitGlobal = this.trafficCounter.readTimeToWait(size, getReadLimit(), this.maxTime, now);
            PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
            long wait = 0;
            if (perChannel != null) {
                wait = perChannel.channelTrafficCounter.readTimeToWait(size, this.readChannelLimit, this.maxTime, now);
                if (this.readDeviationActive) {
                    long maxLocalRead = perChannel.channelTrafficCounter.cumulativeReadBytes();
                    long maxGlobalRead = this.cumulativeReadBytes.get();
                    if (maxLocalRead <= 0) {
                        maxLocalRead = 0;
                    }
                    if (maxGlobalRead < maxLocalRead) {
                        maxGlobalRead = maxLocalRead;
                    }
                    wait = computeBalancedWait((float) maxLocalRead, (float) maxGlobalRead, wait);
                }
            }
            if (wait < waitGlobal) {
                wait = waitGlobal;
            }
            wait = checkWaitReadTime(ctx, wait, now);
            if (wait >= 10) {
                ChannelConfig config = ctx.channel().config();
                if (logger.isDebugEnabled()) {
                    logger.debug("Read Suspend: " + wait + ':' + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(ctx));
                }
                if (config.isAutoRead() && AbstractTrafficShapingHandler.isHandlerActive(ctx)) {
                    config.setAutoRead(false);
                    ctx.attr(READ_SUSPENDED).set(Boolean.valueOf(true));
                    Attribute<Runnable> attr = ctx.attr(REOPEN_TASK);
                    Runnable reopenTask = (Runnable) attr.get();
                    if (reopenTask == null) {
                        Runnable reopenReadTimerTask = new ReopenReadTimerTask(ctx);
                        attr.set(reopenReadTimerTask);
                    }
                    ctx.executor().schedule(reopenTask, wait, TimeUnit.MILLISECONDS);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Suspend final status => " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(ctx) + " will reopened at: " + wait);
                    }
                }
            }
        }
        informReadOperation(ctx, now);
        ctx.fireChannelRead(msg);
    }

    protected long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
        if (perChannel == null || wait <= this.maxTime || (now + wait) - perChannel.lastReadTimestamp <= this.maxTime) {
            return wait;
        }
        return this.maxTime;
    }

    protected void informReadOperation(ChannelHandlerContext ctx, long now) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
        if (perChannel != null) {
            perChannel.lastReadTimestamp = now;
        }
    }

    protected long maximumCumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }

    protected long maximumCumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }

    public Collection<TrafficCounter> channelTrafficCounters() {
        return new 1(this);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        long size = calculateSize(msg);
        long now = TrafficCounter.milliSecondFromNano();
        if (size > 0) {
            long waitGlobal = this.trafficCounter.writeTimeToWait(size, getWriteLimit(), this.maxTime, now);
            PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(ctx.channel().hashCode()));
            long wait = 0;
            if (perChannel != null) {
                wait = perChannel.channelTrafficCounter.writeTimeToWait(size, this.writeChannelLimit, this.maxTime, now);
                if (this.writeDeviationActive) {
                    long maxLocalWrite = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
                    long maxGlobalWrite = this.cumulativeWrittenBytes.get();
                    if (maxLocalWrite <= 0) {
                        maxLocalWrite = 0;
                    }
                    if (maxGlobalWrite < maxLocalWrite) {
                        maxGlobalWrite = maxLocalWrite;
                    }
                    wait = computeBalancedWait((float) maxLocalWrite, (float) maxGlobalWrite, wait);
                }
            }
            if (wait < waitGlobal) {
                wait = waitGlobal;
            }
            if (wait >= 10) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Write suspend: " + wait + ':' + ctx.channel().config().isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(ctx));
                }
                submitWrite(ctx, msg, size, wait, now, promise);
                return;
            }
        }
        submitWrite(ctx, msg, size, 0, now, promise);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void submitWrite(io.netty.channel.ChannelHandlerContext r22, java.lang.Object r23, long r24, long r26, long r28, io.netty.channel.ChannelPromise r30) {
        /*
        r21 = this;
        r12 = r22.channel();
        r5 = r12.hashCode();
        r16 = java.lang.Integer.valueOf(r5);
        r0 = r21;
        r5 = r0.channelQueues;
        r0 = r16;
        r17 = r5.get(r0);
        r17 = (io.netty.handler.traffic.GlobalChannelTrafficShapingHandler.PerChannel) r17;
        if (r17 != 0) goto L_0x001e;
    L_0x001a:
        r17 = r21.getOrSetPerChannel(r22);
    L_0x001e:
        r14 = r26;
        r13 = 0;
        monitor-enter(r17);
        r6 = 0;
        r5 = (r26 > r6 ? 1 : (r26 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0055;
    L_0x0028:
        r0 = r17;
        r5 = r0.messagesQueue;	 Catch:{ all -> 0x00df }
        r5 = r5.isEmpty();	 Catch:{ all -> 0x00df }
        if (r5 == 0) goto L_0x0055;
    L_0x0032:
        r0 = r21;
        r5 = r0.trafficCounter;	 Catch:{ all -> 0x00df }
        r0 = r24;
        r5.bytesRealWriteFlowControl(r0);	 Catch:{ all -> 0x00df }
        r0 = r17;
        r5 = r0.channelTrafficCounter;	 Catch:{ all -> 0x00df }
        r0 = r24;
        r5.bytesRealWriteFlowControl(r0);	 Catch:{ all -> 0x00df }
        r0 = r22;
        r1 = r23;
        r2 = r30;
        r0.write(r1, r2);	 Catch:{ all -> 0x00df }
        r0 = r28;
        r2 = r17;
        r2.lastWriteTimestamp = r0;	 Catch:{ all -> 0x00df }
        monitor-exit(r17);	 Catch:{ all -> 0x00df }
    L_0x0054:
        return;
    L_0x0055:
        r0 = r21;
        r6 = r0.maxTime;	 Catch:{ all -> 0x00df }
        r5 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1));
        if (r5 <= 0) goto L_0x0075;
    L_0x005d:
        r6 = r28 + r14;
        r0 = r17;
        r0 = r0.lastWriteTimestamp;	 Catch:{ all -> 0x00df }
        r18 = r0;
        r6 = r6 - r18;
        r0 = r21;
        r0 = r0.maxTime;	 Catch:{ all -> 0x00df }
        r18 = r0;
        r5 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1));
        if (r5 <= 0) goto L_0x0075;
    L_0x0071:
        r0 = r21;
        r14 = r0.maxTime;	 Catch:{ all -> 0x00df }
    L_0x0075:
        r4 = new io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$ToSend;	 Catch:{ all -> 0x00df }
        r5 = r14 + r28;
        r11 = 0;
        r7 = r23;
        r8 = r24;
        r10 = r30;
        r4.<init>(r5, r7, r8, r10, r11);	 Catch:{ all -> 0x00df }
        r0 = r17;
        r5 = r0.messagesQueue;	 Catch:{ all -> 0x00df }
        r5.addLast(r4);	 Catch:{ all -> 0x00df }
        r0 = r17;
        r6 = r0.queueSize;	 Catch:{ all -> 0x00df }
        r6 = r6 + r24;
        r0 = r17;
        r0.queueSize = r6;	 Catch:{ all -> 0x00df }
        r0 = r21;
        r5 = r0.queuesSize;	 Catch:{ all -> 0x00df }
        r0 = r24;
        r5.addAndGet(r0);	 Catch:{ all -> 0x00df }
        r0 = r17;
        r10 = r0.queueSize;	 Catch:{ all -> 0x00df }
        r6 = r21;
        r7 = r22;
        r8 = r14;
        r6.checkWriteSuspend(r7, r8, r10);	 Catch:{ all -> 0x00df }
        r0 = r21;
        r5 = r0.queuesSize;	 Catch:{ all -> 0x00df }
        r6 = r5.get();	 Catch:{ all -> 0x00df }
        r0 = r21;
        r0 = r0.maxGlobalWriteSize;	 Catch:{ all -> 0x00df }
        r18 = r0;
        r5 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1));
        if (r5 <= 0) goto L_0x00bc;
    L_0x00bb:
        r13 = 1;
    L_0x00bc:
        monitor-exit(r17);	 Catch:{ all -> 0x00df }
        if (r13 == 0) goto L_0x00c7;
    L_0x00bf:
        r5 = 0;
        r0 = r21;
        r1 = r22;
        r0.setUserDefinedWritability(r1, r5);
    L_0x00c7:
        r10 = r4.relativeTimeAction;
        r9 = r17;
        r5 = r22.executor();
        r6 = new io.netty.handler.traffic.GlobalChannelTrafficShapingHandler$2;
        r7 = r21;
        r8 = r22;
        r6.<init>(r7, r8, r9, r10);
        r7 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r5.schedule(r6, r14, r7);
        goto L_0x0054;
    L_0x00df:
        r5 = move-exception;
        monitor-exit(r17);	 Catch:{ all -> 0x00df }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.traffic.GlobalChannelTrafficShapingHandler.submitWrite(io.netty.channel.ChannelHandlerContext, java.lang.Object, long, long, long, io.netty.channel.ChannelPromise):void");
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
                perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
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

    public String toString() {
        return super.toString() + " Write Channel Limit: " + this.writeChannelLimit + " Read Channel Limit: " + this.readChannelLimit;
    }
}
