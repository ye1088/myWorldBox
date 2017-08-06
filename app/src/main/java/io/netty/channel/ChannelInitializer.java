package io.netty.channel;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ConcurrentMap;

@Sharable
public abstract class ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
    private final ConcurrentMap<ChannelHandlerContext, Boolean> initMap = PlatformDependent.newConcurrentHashMap();

    protected abstract void initChannel(C c) throws Exception;

    public final void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        if (initChannel(ctx)) {
            ctx.pipeline().fireChannelRegistered();
        } else {
            ctx.fireChannelRegistered();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Failed to initialize a_isRightVersion channel. Closing: " + ctx.channel(), cause);
        ctx.close();
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isRegistered()) {
            initChannel(ctx);
        }
    }

    private boolean initChannel(ChannelHandlerContext ctx) throws Exception {
        if (this.initMap.putIfAbsent(ctx, Boolean.TRUE) != null) {
            return false;
        }
        try {
            initChannel(ctx.channel());
        } catch (Throwable cause) {
            exceptionCaught(ctx, cause);
        } finally {
            remove(ctx);
        }
        return true;
    }

    private void remove(ChannelHandlerContext ctx) {
        try {
            ChannelPipeline pipeline = ctx.pipeline();
            if (pipeline.context(this) != null) {
                pipeline.remove(this);
            }
            this.initMap.remove(ctx);
        } catch (Throwable th) {
            this.initMap.remove(ctx);
        }
    }
}
