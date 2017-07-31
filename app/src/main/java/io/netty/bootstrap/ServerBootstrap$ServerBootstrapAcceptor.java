package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

class ServerBootstrap$ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter {
    private final Entry<AttributeKey<?>, Object>[] childAttrs;
    private final EventLoopGroup childGroup;
    private final ChannelHandler childHandler;
    private final Entry<ChannelOption<?>, Object>[] childOptions;

    ServerBootstrap$ServerBootstrapAcceptor(EventLoopGroup childGroup, ChannelHandler childHandler, Entry<ChannelOption<?>, Object>[] childOptions, Entry<AttributeKey<?>, Object>[] childAttrs) {
        this.childGroup = childGroup;
        this.childHandler = childHandler;
        this.childOptions = childOptions;
        this.childAttrs = childAttrs;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final Channel child = (Channel) msg;
        child.pipeline().addLast(this.childHandler);
        for (Entry<ChannelOption<?>, Object> e : this.childOptions) {
            try {
                if (!child.config().setOption((ChannelOption) e.getKey(), e.getValue())) {
                    ServerBootstrap.access$100().warn("Unknown channel option: " + e);
                }
            } catch (Throwable t) {
                ServerBootstrap.access$100().warn("Failed to set a channel option: " + child, t);
            }
        }
        for (Entry<AttributeKey<?>, Object> e2 : this.childAttrs) {
            child.attr((AttributeKey) e2.getKey()).set(e2.getValue());
        }
        try {
            this.childGroup.register(child).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        ServerBootstrap$ServerBootstrapAcceptor.forceClose(child, future.cause());
                    }
                }
            });
        } catch (Throwable t2) {
            forceClose(child, t2);
        }
    }

    private static void forceClose(Channel child, Throwable t) {
        child.unsafe().closeForcibly();
        ServerBootstrap.access$100().warn("Failed to register an accepted channel: " + child, t);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        final ChannelConfig config = ctx.channel().config();
        if (config.isAutoRead()) {
            config.setAutoRead(false);
            ctx.channel().eventLoop().schedule(new Runnable() {
                public void run() {
                    config.setAutoRead(true);
                }
            }, 1, TimeUnit.SECONDS);
        }
        ctx.fireExceptionCaught(cause);
    }
}
