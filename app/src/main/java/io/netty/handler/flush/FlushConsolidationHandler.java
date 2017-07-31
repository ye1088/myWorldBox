package io.netty.handler.flush;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.ObjectUtil;

public class FlushConsolidationHandler extends ChannelDuplexHandler {
    private final int explicitFlushAfterFlushes;
    private int flushPendingCount;
    private boolean readInprogess;

    public FlushConsolidationHandler() {
        this(256);
    }

    public FlushConsolidationHandler(int explicitFlushAfterFlushes) {
        this.explicitFlushAfterFlushes = ObjectUtil.checkPositive(explicitFlushAfterFlushes, "explicitFlushAfterFlushes");
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        if (this.readInprogess) {
            int i = this.flushPendingCount + 1;
            this.flushPendingCount = i;
            if (i == this.explicitFlushAfterFlushes) {
                this.flushPendingCount = 0;
                ctx.flush();
                return;
            }
            return;
        }
        ctx.flush();
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        resetReadAndFlushIfNeeded(ctx);
        ctx.fireChannelReadComplete();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.readInprogess = true;
        ctx.fireChannelRead(msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        resetReadAndFlushIfNeeded(ctx);
        ctx.fireExceptionCaught(cause);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        resetReadAndFlushIfNeeded(ctx);
        ctx.disconnect(promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        resetReadAndFlushIfNeeded(ctx);
        ctx.close(promise);
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        if (!ctx.channel().isWritable()) {
            flushIfNeeded(ctx);
        }
        ctx.fireChannelWritabilityChanged();
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        flushIfNeeded(ctx);
    }

    private void resetReadAndFlushIfNeeded(ChannelHandlerContext ctx) {
        this.readInprogess = false;
        flushIfNeeded(ctx);
    }

    private void flushIfNeeded(ChannelHandlerContext ctx) {
        if (this.flushPendingCount > 0) {
            this.flushPendingCount = 0;
            ctx.flush();
        }
    }
}
