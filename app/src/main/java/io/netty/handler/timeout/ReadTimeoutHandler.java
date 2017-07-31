package io.netty.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.TimeUnit;

public class ReadTimeoutHandler extends IdleStateHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!ReadTimeoutHandler.class.desiredAssertionStatus());
    private boolean closed;

    public ReadTimeoutHandler(int timeoutSeconds) {
        this((long) timeoutSeconds, TimeUnit.SECONDS);
    }

    public ReadTimeoutHandler(long timeout, TimeUnit unit) {
        super(timeout, 0, 0, unit);
    }

    protected final void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if ($assertionsDisabled || evt.state() == IdleState.READER_IDLE) {
            readTimedOut(ctx);
            return;
        }
        throw new AssertionError();
    }

    protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
        if (!this.closed) {
            ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
            ctx.close();
            this.closed = true;
        }
    }
}
