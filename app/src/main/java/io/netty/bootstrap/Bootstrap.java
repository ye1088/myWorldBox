package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.DefaultAddressResolverGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.Map.Entry;

public class Bootstrap extends AbstractBootstrap<Bootstrap, Channel> {
    private static final AddressResolverGroup<?> DEFAULT_RESOLVER = DefaultAddressResolverGroup.INSTANCE;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
    private final BootstrapConfig config = new BootstrapConfig(this);
    private volatile SocketAddress remoteAddress;
    private volatile AddressResolverGroup<SocketAddress> resolver = DEFAULT_RESOLVER;

    private Bootstrap(Bootstrap bootstrap) {
        super(bootstrap);
        this.resolver = bootstrap.resolver;
        this.remoteAddress = bootstrap.remoteAddress;
    }

    public Bootstrap resolver(AddressResolverGroup<?> resolver) {
        if (resolver == null) {
            resolver = DEFAULT_RESOLVER;
        }
        this.resolver = resolver;
        return this;
    }

    public Bootstrap remoteAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public Bootstrap remoteAddress(String inetHost, int inetPort) {
        this.remoteAddress = InetSocketAddress.createUnresolved(inetHost, inetPort);
        return this;
    }

    public Bootstrap remoteAddress(InetAddress inetHost, int inetPort) {
        this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
        return this;
    }

    public ChannelFuture connect() {
        validate();
        SocketAddress remoteAddress = this.remoteAddress;
        if (remoteAddress != null) {
            return doResolveAndConnect(remoteAddress, this.config.localAddress());
        }
        throw new IllegalStateException("remoteAddress not set");
    }

    public ChannelFuture connect(String inetHost, int inetPort) {
        return connect(InetSocketAddress.createUnresolved(inetHost, inetPort));
    }

    public ChannelFuture connect(InetAddress inetHost, int inetPort) {
        return connect(new InetSocketAddress(inetHost, inetPort));
    }

    public ChannelFuture connect(SocketAddress remoteAddress) {
        if (remoteAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        validate();
        return doResolveAndConnect(remoteAddress, this.config.localAddress());
    }

    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        if (remoteAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        validate();
        return doResolveAndConnect(remoteAddress, localAddress);
    }

    private ChannelFuture doResolveAndConnect(SocketAddress remoteAddress, SocketAddress localAddress) {
        ChannelFuture regFuture = initAndRegister();
        final Channel channel = regFuture.channel();
        if (!regFuture.isDone()) {
            final AbstractBootstrap$PendingRegistrationPromise promise = new AbstractBootstrap$PendingRegistrationPromise(channel);
            final SocketAddress socketAddress = remoteAddress;
            final SocketAddress socketAddress2 = localAddress;
            regFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    Throwable cause = future.cause();
                    if (cause != null) {
                        promise.setFailure(cause);
                        return;
                    }
                    promise.registered();
                    Bootstrap.this.doResolveAndConnect0(channel, socketAddress, socketAddress2, promise);
                }
            });
            return promise;
        } else if (regFuture.isSuccess()) {
            return doResolveAndConnect0(channel, remoteAddress, localAddress, channel.newPromise());
        } else {
            return regFuture;
        }
    }

    private ChannelFuture doResolveAndConnect0(final Channel channel, SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        try {
            AddressResolver<SocketAddress> resolver = this.resolver.getResolver(channel.eventLoop());
            if (!resolver.isSupported(remoteAddress) || resolver.isResolved(remoteAddress)) {
                doConnect(remoteAddress, localAddress, promise);
                return promise;
            }
            Future<SocketAddress> resolveFuture = resolver.resolve(remoteAddress);
            if (resolveFuture.isDone()) {
                Throwable resolveFailureCause = resolveFuture.cause();
                if (resolveFailureCause != null) {
                    channel.close();
                    promise.setFailure(resolveFailureCause);
                } else {
                    doConnect((SocketAddress) resolveFuture.getNow(), localAddress, promise);
                }
            } else {
                resolveFuture.addListener(new FutureListener<SocketAddress>() {
                    public void operationComplete(Future<SocketAddress> future) throws Exception {
                        if (future.cause() != null) {
                            channel.close();
                            promise.setFailure(future.cause());
                            return;
                        }
                        Bootstrap.doConnect((SocketAddress) future.getNow(), localAddress, promise);
                    }
                });
            }
            return promise;
        } catch (Throwable cause) {
            promise.tryFailure(cause);
        }
    }

    private static void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise connectPromise) {
        final Channel channel = connectPromise.channel();
        channel.eventLoop().execute(new Runnable() {
            public void run() {
                if (localAddress == null) {
                    channel.connect(remoteAddress, connectPromise);
                } else {
                    channel.connect(remoteAddress, localAddress, connectPromise);
                }
                connectPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        });
    }

    void init(Channel channel) throws Exception {
        channel.pipeline().addLast(this.config.handler());
        Map<ChannelOption<?>, Object> options = options0();
        synchronized (options) {
            for (Entry<ChannelOption<?>, Object> e : options.entrySet()) {
                try {
                    if (!channel.config().setOption((ChannelOption) e.getKey(), e.getValue())) {
                        logger.warn("Unknown channel option: " + e);
                    }
                } catch (Throwable t) {
                    logger.warn("Failed to set a channel option: " + channel, t);
                }
            }
        }
        Map<AttributeKey<?>, Object> attrs = attrs0();
        synchronized (attrs) {
            for (Entry<AttributeKey<?>, Object> e2 : attrs.entrySet()) {
                channel.attr((AttributeKey) e2.getKey()).set(e2.getValue());
            }
        }
    }

    public Bootstrap validate() {
        super.validate();
        if (this.config.handler() != null) {
            return this;
        }
        throw new IllegalStateException("handler not set");
    }

    public Bootstrap clone() {
        return new Bootstrap(this);
    }

    public Bootstrap clone(EventLoopGroup group) {
        Bootstrap bs = new Bootstrap(this);
        bs.group = group;
        return bs;
    }

    public final BootstrapConfig config() {
        return this.config;
    }

    final SocketAddress remoteAddress() {
        return this.remoteAddress;
    }

    final AddressResolverGroup<?> resolver() {
        return this.resolver;
    }
}
