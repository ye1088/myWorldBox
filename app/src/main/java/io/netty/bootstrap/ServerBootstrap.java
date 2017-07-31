package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
    private final Map<AttributeKey<?>, Object> childAttrs = new LinkedHashMap();
    private volatile EventLoopGroup childGroup;
    private volatile ChannelHandler childHandler;
    private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap();
    private final ServerBootstrapConfig config = new ServerBootstrapConfig(this);

    private ServerBootstrap(ServerBootstrap bootstrap) {
        super(bootstrap);
        this.childGroup = bootstrap.childGroup;
        this.childHandler = bootstrap.childHandler;
        synchronized (bootstrap.childOptions) {
            this.childOptions.putAll(bootstrap.childOptions);
        }
        synchronized (bootstrap.childAttrs) {
            this.childAttrs.putAll(bootstrap.childAttrs);
        }
    }

    public ServerBootstrap group(EventLoopGroup group) {
        return group(group, group);
    }

    public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
        super.group(parentGroup);
        if (childGroup == null) {
            throw new NullPointerException("childGroup");
        } else if (this.childGroup != null) {
            throw new IllegalStateException("childGroup set already");
        } else {
            this.childGroup = childGroup;
            return this;
        }
    }

    public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value) {
        if (childOption == null) {
            throw new NullPointerException("childOption");
        }
        if (value == null) {
            synchronized (this.childOptions) {
                this.childOptions.remove(childOption);
            }
        } else {
            synchronized (this.childOptions) {
                this.childOptions.put(childOption, value);
            }
        }
        return this;
    }

    public <T> ServerBootstrap childAttr(AttributeKey<T> childKey, T value) {
        if (childKey == null) {
            throw new NullPointerException("childKey");
        }
        if (value == null) {
            this.childAttrs.remove(childKey);
        } else {
            this.childAttrs.put(childKey, value);
        }
        return this;
    }

    public ServerBootstrap childHandler(ChannelHandler childHandler) {
        if (childHandler == null) {
            throw new NullPointerException("childHandler");
        }
        this.childHandler = childHandler;
        return this;
    }

    void init(Channel channel) throws Exception {
        Entry[] currentChildOptions;
        Entry[] currentChildAttrs;
        Map<ChannelOption<?>, Object> options = options0();
        synchronized (options) {
            channel.config().setOptions(options);
        }
        Map<AttributeKey<?>, Object> attrs = attrs0();
        synchronized (attrs) {
            for (Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
                channel.attr((AttributeKey) e.getKey()).set(e.getValue());
            }
        }
        ChannelPipeline p = channel.pipeline();
        EventLoopGroup currentChildGroup = this.childGroup;
        ChannelHandler currentChildHandler = this.childHandler;
        synchronized (this.childOptions) {
            currentChildOptions = (Entry[]) this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size()));
        }
        synchronized (this.childAttrs) {
            currentChildAttrs = (Entry[]) this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
        }
        p.addLast(new ChannelHandler[]{new 1(this, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs)});
    }

    public ServerBootstrap validate() {
        super.validate();
        if (this.childHandler == null) {
            throw new IllegalStateException("childHandler not set");
        }
        if (this.childGroup == null) {
            logger.warn("childGroup is not set. Using parentGroup instead.");
            this.childGroup = this.config.group();
        }
        return this;
    }

    private static Entry<ChannelOption<?>, Object>[] newOptionArray(int size) {
        return new Entry[size];
    }

    private static Entry<AttributeKey<?>, Object>[] newAttrArray(int size) {
        return new Entry[size];
    }

    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }

    @Deprecated
    public EventLoopGroup childGroup() {
        return this.childGroup;
    }

    final ChannelHandler childHandler() {
        return this.childHandler;
    }

    final Map<ChannelOption<?>, Object> childOptions() {
        return AbstractBootstrap.copiedMap(this.childOptions);
    }

    final Map<AttributeKey<?>, Object> childAttrs() {
        return AbstractBootstrap.copiedMap(this.childAttrs);
    }

    public final ServerBootstrapConfig config() {
        return this.config;
    }
}
