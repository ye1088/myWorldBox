package io.netty.handler.traffic;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TrafficCounter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
    final AtomicLong checkInterval = new AtomicLong(1000);
    private final AtomicLong cumulativeReadBytes = new AtomicLong();
    private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
    private final AtomicLong currentReadBytes = new AtomicLong();
    private final AtomicLong currentWrittenBytes = new AtomicLong();
    final ScheduledExecutorService executor;
    private long lastCumulativeTime;
    private volatile long lastReadBytes;
    private long lastReadThroughput;
    private volatile long lastReadingTime;
    final AtomicLong lastTime = new AtomicLong();
    private long lastWriteThroughput;
    private volatile long lastWritingTime;
    private volatile long lastWrittenBytes;
    Runnable monitor;
    volatile boolean monitorActive;
    final String name;
    private long readingTime;
    private long realWriteThroughput;
    private final AtomicLong realWrittenBytes = new AtomicLong();
    volatile ScheduledFuture<?> scheduledFuture;
    final AbstractTrafficShapingHandler trafficShapingHandler;
    private long writingTime;

    public static long milliSecondFromNano() {
        return System.nanoTime() / 1000000;
    }

    public synchronized void start() {
        if (!this.monitorActive) {
            this.lastTime.set(milliSecondFromNano());
            long localCheckInterval = this.checkInterval.get();
            if (localCheckInterval > 0 && this.executor != null) {
                this.monitorActive = true;
                this.monitor = new TrafficMonitoringTask(this, null);
                this.scheduledFuture = this.executor.schedule(this.monitor, localCheckInterval, TimeUnit.MILLISECONDS);
            }
        }
    }

    public synchronized void stop() {
        if (this.monitorActive) {
            this.monitorActive = false;
            resetAccounting(milliSecondFromNano());
            if (this.trafficShapingHandler != null) {
                this.trafficShapingHandler.doAccounting(this);
            }
            if (this.scheduledFuture != null) {
                this.scheduledFuture.cancel(true);
            }
        }
    }

    synchronized void resetAccounting(long newLastTime) {
        long interval = newLastTime - this.lastTime.getAndSet(newLastTime);
        if (interval != 0) {
            if (logger.isDebugEnabled() && interval > (checkInterval() << 1)) {
                logger.debug("Acct schedule not ok: " + interval + " > 2*" + checkInterval() + " from " + this.name);
            }
            this.lastReadBytes = this.currentReadBytes.getAndSet(0);
            this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0);
            this.lastReadThroughput = (this.lastReadBytes * 1000) / interval;
            this.lastWriteThroughput = (this.lastWrittenBytes * 1000) / interval;
            this.realWriteThroughput = (this.realWrittenBytes.getAndSet(0) * 1000) / interval;
            this.lastWritingTime = Math.max(this.lastWritingTime, this.writingTime);
            this.lastReadingTime = Math.max(this.lastReadingTime, this.readingTime);
        }
    }

    public TrafficCounter(ScheduledExecutorService executor, String name, long checkInterval) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.trafficShapingHandler = null;
        this.executor = executor;
        this.name = name;
        init(checkInterval);
    }

    public TrafficCounter(AbstractTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
        if (trafficShapingHandler == null) {
            throw new IllegalArgumentException("trafficShapingHandler");
        } else if (name == null) {
            throw new NullPointerException("name");
        } else {
            this.trafficShapingHandler = trafficShapingHandler;
            this.executor = executor;
            this.name = name;
            init(checkInterval);
        }
    }

    private void init(long checkInterval) {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.writingTime = milliSecondFromNano();
        this.readingTime = this.writingTime;
        this.lastWritingTime = this.writingTime;
        this.lastReadingTime = this.writingTime;
        configure(checkInterval);
    }

    public void configure(long newcheckInterval) {
        long newInterval = (newcheckInterval / 10) * 10;
        if (this.checkInterval.getAndSet(newInterval) == newInterval) {
            return;
        }
        if (newInterval <= 0) {
            stop();
            this.lastTime.set(milliSecondFromNano());
            return;
        }
        start();
    }

    void bytesRecvFlowControl(long recv) {
        this.currentReadBytes.addAndGet(recv);
        this.cumulativeReadBytes.addAndGet(recv);
    }

    void bytesWriteFlowControl(long write) {
        this.currentWrittenBytes.addAndGet(write);
        this.cumulativeWrittenBytes.addAndGet(write);
    }

    void bytesRealWriteFlowControl(long write) {
        this.realWrittenBytes.addAndGet(write);
    }

    public long checkInterval() {
        return this.checkInterval.get();
    }

    public long lastReadThroughput() {
        return this.lastReadThroughput;
    }

    public long lastWriteThroughput() {
        return this.lastWriteThroughput;
    }

    public long lastReadBytes() {
        return this.lastReadBytes;
    }

    public long lastWrittenBytes() {
        return this.lastWrittenBytes;
    }

    public long currentReadBytes() {
        return this.currentReadBytes.get();
    }

    public long currentWrittenBytes() {
        return this.currentWrittenBytes.get();
    }

    public long lastTime() {
        return this.lastTime.get();
    }

    public long cumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }

    public long cumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }

    public long lastCumulativeTime() {
        return this.lastCumulativeTime;
    }

    public AtomicLong getRealWrittenBytes() {
        return this.realWrittenBytes;
    }

    public long getRealWriteThroughput() {
        return this.realWriteThroughput;
    }

    public void resetCumulativeTime() {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.cumulativeReadBytes.set(0);
        this.cumulativeWrittenBytes.set(0);
    }

    public String name() {
        return this.name;
    }

    @Deprecated
    public long readTimeToWait(long size, long limitTraffic, long maxTime) {
        return readTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
    }

    public long readTimeToWait(long size, long limitTraffic, long maxTime, long now) {
        bytesRecvFlowControl(size);
        if (size == 0 || limitTraffic == 0) {
            return 0;
        }
        long lastTimeCheck = this.lastTime.get();
        long sum = this.currentReadBytes.get();
        long localReadingTime = this.readingTime;
        long lastRB = this.lastReadBytes;
        long interval = now - lastTimeCheck;
        long pastDelay = Math.max(this.lastReadingTime - lastTimeCheck, 0);
        long time;
        if (interval > 10) {
            time = (((1000 * sum) / limitTraffic) - interval) + pastDelay;
            if (time > 10) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Time: " + time + ':' + sum + ':' + interval + ':' + pastDelay);
                }
                if (time > maxTime && (now + time) - localReadingTime > maxTime) {
                    time = maxTime;
                }
                this.readingTime = Math.max(localReadingTime, now + time);
                return time;
            }
            this.readingTime = Math.max(localReadingTime, now);
            return 0;
        }
        long lastsum = sum + lastRB;
        long lastinterval = interval + this.checkInterval.get();
        time = (((1000 * lastsum) / limitTraffic) - lastinterval) + pastDelay;
        if (time > 10) {
            if (logger.isDebugEnabled()) {
                logger.debug("Time: " + time + ':' + lastsum + ':' + lastinterval + ':' + pastDelay);
            }
            if (time > maxTime && (now + time) - localReadingTime > maxTime) {
                time = maxTime;
            }
            this.readingTime = Math.max(localReadingTime, now + time);
            return time;
        }
        this.readingTime = Math.max(localReadingTime, now);
        return 0;
    }

    @Deprecated
    public long writeTimeToWait(long size, long limitTraffic, long maxTime) {
        return writeTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
    }

    public long writeTimeToWait(long size, long limitTraffic, long maxTime, long now) {
        bytesWriteFlowControl(size);
        if (size == 0 || limitTraffic == 0) {
            return 0;
        }
        long lastTimeCheck = this.lastTime.get();
        long sum = this.currentWrittenBytes.get();
        long lastWB = this.lastWrittenBytes;
        long localWritingTime = this.writingTime;
        long pastDelay = Math.max(this.lastWritingTime - lastTimeCheck, 0);
        long interval = now - lastTimeCheck;
        long time;
        if (interval > 10) {
            time = (((1000 * sum) / limitTraffic) - interval) + pastDelay;
            if (time > 10) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Time: " + time + ':' + sum + ':' + interval + ':' + pastDelay);
                }
                if (time > maxTime && (now + time) - localWritingTime > maxTime) {
                    time = maxTime;
                }
                this.writingTime = Math.max(localWritingTime, now + time);
                return time;
            }
            this.writingTime = Math.max(localWritingTime, now);
            return 0;
        }
        long lastsum = sum + lastWB;
        long lastinterval = interval + this.checkInterval.get();
        time = (((1000 * lastsum) / limitTraffic) - lastinterval) + pastDelay;
        if (time > 10) {
            if (logger.isDebugEnabled()) {
                logger.debug("Time: " + time + ':' + lastsum + ':' + lastinterval + ':' + pastDelay);
            }
            if (time > maxTime && (now + time) - localWritingTime > maxTime) {
                time = maxTime;
            }
            this.writingTime = Math.max(localWritingTime, now + time);
            return time;
        }
        this.writingTime = Math.max(localWritingTime, now);
        return 0;
    }

    public String toString() {
        return "Monitor " + this.name + " Current Speed Read: " + (this.lastReadThroughput >> 10) + " KB/s, " + "Asked Write: " + (this.lastWriteThroughput >> 10) + " KB/s, " + "Real Write: " + (this.realWriteThroughput >> 10) + " KB/s, " + "Current Read: " + (this.currentReadBytes.get() >> 10) + " KB, " + "Current asked Write: " + (this.currentWrittenBytes.get() >> 10) + " KB, " + "Current real Write: " + (this.realWrittenBytes.get() >> 10) + " KB";
    }
}
