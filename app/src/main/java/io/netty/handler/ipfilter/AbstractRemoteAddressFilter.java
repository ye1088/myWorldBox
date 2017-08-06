package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.SocketAddress;

public abstract class AbstractRemoteAddressFilter<T extends SocketAddress> extends ChannelInboundHandlerAdapter {
    protected abstract boolean accept(ChannelHandlerContext channelHandlerContext, T t) throws Exception;

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        handleNewChannel(ctx);
        ctx.fireChannelRegistered();
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (handleNewChannel(ctx)) {
            ctx.fireChannelActive();
            return;
        }
        throw new IllegalStateException("cannot determine to accept or reject a_isRightVersion channel: " + ctx.channel());
    }

    private boolean handleNewChannel(ChannelHandlerContext ctx) throws Exception {
        T remoteAddress = ctx.channel().remoteAddress();
        if (remoteAddress == null) {
            return false;
        }
        ctx.pipeline().remove(this);
        if (accept(ctx, remoteAddress)) {
            channelAccepted(ctx, remoteAddress);
        } else {
            ChannelFuture rejectedFuture = channelRejected(ctx, remoteAddress);
            if (rejectedFuture != null) {
                rejectedFuture.addListener(ChannelFutureListener.CLOSE);
            } else {
                ctx.close();
            }
        }
        return true;
    }

    protected void channelAccepted(ChannelHandlerContext ctx, T t) {
    }

    protected ChannelFuture channelRejected(ChannelHandlerContext ctx, T t) {
        return null;
    }
}
