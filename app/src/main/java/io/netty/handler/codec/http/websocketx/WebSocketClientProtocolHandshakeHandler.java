package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler.ClientHandshakeStateEvent;

class WebSocketClientProtocolHandshakeHandler extends ChannelInboundHandlerAdapter {
    private final WebSocketClientHandshaker handshaker;

    WebSocketClientProtocolHandshakeHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.handshaker.handshake(ctx.channel()).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ctx.fireUserEventTriggered(ClientHandshakeStateEvent.HANDSHAKE_ISSUED);
                } else {
                    ctx.fireExceptionCaught(future.cause());
                }
            }
        });
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            try {
                if (this.handshaker.isHandshakeComplete()) {
                    throw new IllegalStateException("WebSocketClientHandshaker should have been non finished yet");
                }
                this.handshaker.finishHandshake(ctx.channel(), response);
                ctx.fireUserEventTriggered(ClientHandshakeStateEvent.HANDSHAKE_COMPLETE);
                ctx.pipeline().remove(this);
            } finally {
                response.release();
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
