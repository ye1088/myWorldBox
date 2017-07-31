package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.StringUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel> implements Cloneable {
    private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap();
    private volatile ChannelFactory<? extends C> channelFactory;
    volatile EventLoopGroup group;
    private volatile ChannelHandler handler;
    private volatile SocketAddress localAddress;
    private final Map<ChannelOption<?>, Object> options = new LinkedHashMap();

    public abstract B clone();

    public abstract AbstractBootstrapConfig<B, C> config();

    abstract void init(Channel channel) throws Exception;

    AbstractBootstrap() {
    }

    AbstractBootstrap(AbstractBootstrap<B, C> bootstrap) {
        this.group = bootstrap.group;
        this.channelFactory = bootstrap.channelFactory;
        this.handler = bootstrap.handler;
        this.localAddress = bootstrap.localAddress;
        synchronized (bootstrap.options) {
            this.options.putAll(bootstrap.options);
        }
        synchronized (bootstrap.attrs) {
            this.attrs.putAll(bootstrap.attrs);
        }
    }

    public B group(EventLoopGroup group) {
        if (group == null) {
            throw new NullPointerException("group");
        } else if (this.group != null) {
            throw new IllegalStateException("group set already");
        } else {
            this.group = group;
            return this;
        }
    }

    public B channel(Class<? extends C> channelClass) {
        if (channelClass != null) {
            return channelFactory(new ReflectiveChannelFactory(channelClass));
        }
        throw new NullPointerException("channelClass");
    }

    @Deprecated
    public B channelFactory(ChannelFactory<? extends C> channelFactory) {
        if (channelFactory == null) {
            throw new NullPointerException("channelFactory");
        } else if (this.channelFactory != null) {
            throw new IllegalStateException("channelFactory set already");
        } else {
            this.channelFactory = channelFactory;
            return this;
        }
    }

    public B channelFactory(ChannelFactory<? extends C> channelFactory) {
        return channelFactory((ChannelFactory) channelFactory);
    }

    public B localAddress(SocketAddress localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public B localAddress(int inetPort) {
        return localAddress(new InetSocketAddress(inetPort));
    }

    public B localAddress(String inetHost, int inetPort) {
        return localAddress(new InetSocketAddress(inetHost, inetPort));
    }

    public B localAddress(InetAddress inetHost, int inetPort) {
        return localAddress(new InetSocketAddress(inetHost, inetPort));
    }

    public <T> B option(ChannelOption<T> option, T value) {
        if (option == null) {
            throw new NullPointerException("option");
        }
        if (value == null) {
            synchronized (this.options) {
                this.options.remove(option);
            }
        } else {
            synchronized (this.options) {
                this.options.put(option, value);
            }
        }
        return this;
    }

    public <T> B attr(AttributeKey<T> key, T value) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        if (value == null) {
            synchronized (this.attrs) {
                this.attrs.remove(key);
            }
        } else {
            synchronized (this.attrs) {
                this.attrs.put(key, value);
            }
        }
        return this;
    }

    public B validate() {
        if (this.group == null) {
            throw new IllegalStateException("group not set");
        } else if (this.channelFactory != null) {
            return this;
        } else {
            throw new IllegalStateException("channel or channelFactory not set");
        }
    }

    public ChannelFuture register() {
        validate();
        return initAndRegister();
    }

    public ChannelFuture bind() {
        validate();
        SocketAddress localAddress = this.localAddress;
        if (localAddress != null) {
            return doBind(localAddress);
        }
        throw new IllegalStateException("localAddress not set");
    }

    public ChannelFuture bind(int inetPort) {
        return bind(new InetSocketAddress(inetPort));
    }

    public ChannelFuture bind(String inetHost, int inetPort) {
        return bind(new InetSocketAddress(inetHost, inetPort));
    }

    public ChannelFuture bind(InetAddress inetHost, int inetPort) {
        return bind(new InetSocketAddress(inetHost, inetPort));
    }

    public ChannelFuture bind(SocketAddress localAddress) {
        validate();
        if (localAddress != null) {
            return doBind(localAddress);
        }
        throw new NullPointerException("localAddress");
    }

    private ChannelFuture doBind(SocketAddress localAddress) {
        ChannelFuture regFuture = initAndRegister();
        Channel channel = regFuture.channel();
        if (regFuture.cause() != null) {
            return regFuture;
        }
        if (regFuture.isDone()) {
            ChannelFuture promise = channel.newPromise();
            doBind0(regFuture, channel, localAddress, promise);
            return promise;
        }
        promise = new PendingRegistrationPromise(channel);
        regFuture.addListener(new 1(this, promise, regFuture, channel, localAddress));
        return promise;
    }

    final ChannelFuture initAndRegister() {
        Channel channel = null;
        try {
            channel = this.channelFactory.newChannel();
            init(channel);
            ChannelFuture regFuture = config().group().register(channel);
            if (regFuture.cause() == null) {
                return regFuture;
            }
            if (channel.isRegistered()) {
                channel.close();
                return regFuture;
            }
            channel.unsafe().closeForcibly();
            return regFuture;
        } catch (Throwable t) {
            if (channel != null) {
                channel.unsafe().closeForcibly();
            }
            return new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE).setFailure(t);
        }
    }

    private static void doBind0(ChannelFuture regFuture, Channel channel, SocketAddress localAddress, ChannelPromise promise) {
        channel.eventLoop().execute(new 2(regFuture, channel, localAddress, promise));
    }

    public B handler(ChannelHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        this.handler = handler;
        return this;
    }

    @Deprecated
    public final EventLoopGroup group() {
        return this.group;
    }

    static <K, V> Map<K, V> copiedMap(Map<K, V> map) {
        synchronized (map) {
            if (map.isEmpty()) {
                Map<K, V> emptyMap = Collections.emptyMap();
                return emptyMap;
            }
            Map<K, V> copied = new LinkedHashMap(map);
            return Collections.unmodifiableMap(copied);
        }
    }

    final Map<ChannelOption<?>, Object> options0() {
        return this.options;
    }

    final Map<AttributeKey<?>, Object> attrs0() {
        return this.attrs;
    }

    final SocketAddress localAddress() {
        return this.localAddress;
    }

    final ChannelFactory<? extends C> channelFactory() {
        return this.channelFactory;
    }

    final ChannelHandler handler() {
        return this.handler;
    }

    final Map<ChannelOption<?>, Object> options() {
        return copiedMap(this.options);
    }

    final Map<AttributeKey<?>, Object> attrs() {
        return copiedMap(this.attrs);
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + config() + ')';
    }
}
