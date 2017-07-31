package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import java.util.Deque;

public class SimpleChannelPool implements ChannelPool {
    static final /* synthetic */ boolean $assertionsDisabled = (!SimpleChannelPool.class.desiredAssertionStatus());
    private static final IllegalStateException FULL_EXCEPTION = ((IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("ChannelPool full"), SimpleChannelPool.class, "releaseAndOffer(...)"));
    private static final AttributeKey<SimpleChannelPool> POOL_KEY = AttributeKey.newInstance("channelPool");
    private static final IllegalStateException UNHEALTHY_NON_OFFERED_TO_POOL = ((IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("Channel is unhealthy not offering it back to pool"), SimpleChannelPool.class, "releaseAndOffer(...)"));
    private final Bootstrap bootstrap;
    private final Deque<Channel> deque;
    private final ChannelPoolHandler handler;
    private final ChannelHealthChecker healthCheck;
    private final boolean releaseHealthCheck;

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler) {
        this(bootstrap, handler, ChannelHealthChecker.ACTIVE);
    }

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck) {
        this(bootstrap, handler, healthCheck, true);
    }

    public SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler handler, ChannelHealthChecker healthCheck, boolean releaseHealthCheck) {
        this.deque = PlatformDependent.newConcurrentDeque();
        this.handler = (ChannelPoolHandler) ObjectUtil.checkNotNull(handler, "handler");
        this.healthCheck = (ChannelHealthChecker) ObjectUtil.checkNotNull(healthCheck, "healthCheck");
        this.releaseHealthCheck = releaseHealthCheck;
        this.bootstrap = ((Bootstrap) ObjectUtil.checkNotNull(bootstrap, "bootstrap")).clone();
        this.bootstrap.handler(new ChannelInitializer<Channel>() {
            static final /* synthetic */ boolean $assertionsDisabled = (!SimpleChannelPool.class.desiredAssertionStatus());

            protected void initChannel(Channel ch) throws Exception {
                if ($assertionsDisabled || ch.eventLoop().inEventLoop()) {
                    handler.channelCreated(ch);
                    return;
                }
                throw new AssertionError();
            }
        });
    }

    public final Future<Channel> acquire() {
        return acquire(this.bootstrap.config().group().next().newPromise());
    }

    public Future<Channel> acquire(Promise<Channel> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        return acquireHealthyFromPoolOrNew(promise);
    }

    private Future<Channel> acquireHealthyFromPoolOrNew(final Promise<Channel> promise) {
        try {
            final Channel ch = pollChannel();
            if (ch == null) {
                Bootstrap bs = this.bootstrap.clone();
                bs.attr(POOL_KEY, this);
                ChannelFuture f = connectChannel(bs);
                if (f.isDone()) {
                    notifyConnect(f, promise);
                } else {
                    f.addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            SimpleChannelPool.this.notifyConnect(future, promise);
                        }
                    });
                }
            } else {
                EventLoop loop = ch.eventLoop();
                if (loop.inEventLoop()) {
                    doHealthCheck(ch, promise);
                } else {
                    loop.execute(new Runnable() {
                        public void run() {
                            SimpleChannelPool.this.doHealthCheck(ch, promise);
                        }
                    });
                }
            }
        } catch (Throwable cause) {
            promise.tryFailure(cause);
        }
        return promise;
    }

    private void notifyConnect(ChannelFuture future, Promise<Channel> promise) {
        if (future.isSuccess()) {
            Channel channel = future.channel();
            if (!promise.trySuccess(channel)) {
                release(channel);
                return;
            }
            return;
        }
        promise.tryFailure(future.cause());
    }

    private void doHealthCheck(final Channel ch, final Promise<Channel> promise) {
        if ($assertionsDisabled || ch.eventLoop().inEventLoop()) {
            Future<Boolean> f = this.healthCheck.isHealthy(ch);
            if (f.isDone()) {
                notifyHealthCheck(f, ch, promise);
                return;
            } else {
                f.addListener(new FutureListener<Boolean>() {
                    public void operationComplete(Future<Boolean> future) throws Exception {
                        SimpleChannelPool.this.notifyHealthCheck(future, ch, promise);
                    }
                });
                return;
            }
        }
        throw new AssertionError();
    }

    private void notifyHealthCheck(Future<Boolean> future, Channel ch, Promise<Channel> promise) {
        if (!$assertionsDisabled && !ch.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (!future.isSuccess()) {
            closeChannel(ch);
            acquireHealthyFromPoolOrNew(promise);
        } else if (((Boolean) future.getNow()).booleanValue()) {
            try {
                ch.attr(POOL_KEY).set(this);
                this.handler.channelAcquired(ch);
                promise.setSuccess(ch);
            } catch (Throwable cause) {
                closeAndFail(ch, cause, promise);
            }
        } else {
            closeChannel(ch);
            acquireHealthyFromPoolOrNew(promise);
        }
    }

    protected ChannelFuture connectChannel(Bootstrap bs) {
        return bs.connect();
    }

    public final Future<Void> release(Channel channel) {
        return release(channel, channel.eventLoop().newPromise());
    }

    public Future<Void> release(final Channel channel, final Promise<Void> promise) {
        ObjectUtil.checkNotNull(channel, "channel");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            EventLoop loop = channel.eventLoop();
            if (loop.inEventLoop()) {
                doReleaseChannel(channel, promise);
            } else {
                loop.execute(new Runnable() {
                    public void run() {
                        SimpleChannelPool.this.doReleaseChannel(channel, promise);
                    }
                });
            }
        } catch (Throwable cause) {
            closeAndFail(channel, cause, promise);
        }
        return promise;
    }

    private void doReleaseChannel(Channel channel, Promise<Void> promise) {
        if (!$assertionsDisabled && !channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (channel.attr(POOL_KEY).getAndSet(null) != this) {
            closeAndFail(channel, new IllegalArgumentException("Channel " + channel + " was not acquired from this ChannelPool"), promise);
        } else {
            try {
                if (this.releaseHealthCheck) {
                    doHealthCheckOnRelease(channel, promise);
                } else {
                    releaseAndOffer(channel, promise);
                }
            } catch (Throwable cause) {
                closeAndFail(channel, cause, promise);
            }
        }
    }

    private void doHealthCheckOnRelease(final Channel channel, final Promise<Void> promise) throws Exception {
        final Future<Boolean> f = this.healthCheck.isHealthy(channel);
        if (f.isDone()) {
            releaseAndOfferIfHealthy(channel, promise, f);
        } else {
            f.addListener(new FutureListener<Boolean>() {
                public void operationComplete(Future<Boolean> future) throws Exception {
                    SimpleChannelPool.this.releaseAndOfferIfHealthy(channel, promise, f);
                }
            });
        }
    }

    private void releaseAndOfferIfHealthy(Channel channel, Promise<Void> promise, Future<Boolean> future) throws Exception {
        if (((Boolean) future.getNow()).booleanValue()) {
            releaseAndOffer(channel, promise);
            return;
        }
        this.handler.channelReleased(channel);
        closeAndFail(channel, UNHEALTHY_NON_OFFERED_TO_POOL, promise);
    }

    private void releaseAndOffer(Channel channel, Promise<Void> promise) throws Exception {
        if (offerChannel(channel)) {
            this.handler.channelReleased(channel);
            promise.setSuccess(null);
            return;
        }
        closeAndFail(channel, FULL_EXCEPTION, promise);
    }

    private static void closeChannel(Channel channel) {
        channel.attr(POOL_KEY).getAndSet(null);
        channel.close();
    }

    private static void closeAndFail(Channel channel, Throwable cause, Promise<?> promise) {
        closeChannel(channel);
        promise.tryFailure(cause);
    }

    protected Channel pollChannel() {
        return (Channel) this.deque.pollLast();
    }

    protected boolean offerChannel(Channel channel) {
        return this.deque.offer(channel);
    }

    public void close() {
        while (true) {
            Channel channel = pollChannel();
            if (channel != null) {
                channel.close();
            } else {
                return;
            }
        }
    }
}
