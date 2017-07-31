package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.ServerHandshakeStateEvent;
import io.netty.handler.ssl.SslHandler;

class WebSocketServerProtocolHandshakeHandler extends ChannelInboundHandlerAdapter {
    private final boolean allowExtensions;
    private final boolean allowMaskMismatch;
    private final int maxFramePayloadSize;
    private final String subprotocols;
    private final String websocketPath;

    WebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadSize = maxFrameSize;
        this.allowMaskMismatch = allowMaskMismatch;
    }

    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest req = (FullHttpRequest) msg;
        if (this.websocketPath.equals(req.uri())) {
            try {
                if (req.method() != HttpMethod.GET) {
                    sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
                    return;
                }
                WebSocketServerHandshaker handshaker = new WebSocketServerHandshakerFactory(getWebSocketLocation(ctx.pipeline(), req, this.websocketPath), this.subprotocols, this.allowExtensions, this.maxFramePayloadSize, this.allowMaskMismatch).newHandshaker(req);
                if (handshaker == null) {
                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
                } else {
                    handshaker.handshake(ctx.channel(), req).addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                ctx.fireUserEventTriggered(ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                            } else {
                                ctx.fireExceptionCaught(future.cause());
                            }
                        }
                    });
                    WebSocketServerProtocolHandler.setHandshaker(ctx.channel(), handshaker);
                    ctx.pipeline().replace(this, "WS403Responder", WebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
                }
                req.release();
            } finally {
                req.release();
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            protocol = "wss";
        }
        return protocol + "://" + req.headers().get(HttpHeaderNames.HOST) + path;
    }
}
