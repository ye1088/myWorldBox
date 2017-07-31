package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpHeaders;
import java.net.URI;
import java.util.List;

public class WebSocketClientProtocolHandler extends WebSocketProtocolHandler {
    private final boolean handleCloseFrames;
    private final WebSocketClientHandshaker handshaker;

    public enum ClientHandshakeStateEvent {
        HANDSHAKE_ISSUED,
        HANDSHAKE_COMPLETE
    }

    public /* bridge */ /* synthetic */ void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        super.exceptionCaught(channelHandlerContext, th);
    }

    public WebSocketClientHandshaker handshaker() {
        return this.handshaker;
    }

    public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean handleCloseFrames, boolean performMasking, boolean allowMaskMismatch) {
        this(WebSocketClientHandshakerFactory.newHandshaker(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch), handleCloseFrames);
    }

    public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean handleCloseFrames) {
        this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, handleCloseFrames, true, false);
    }

    public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
        this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true);
    }

    public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, boolean handleCloseFrames) {
        this.handshaker = handshaker;
        this.handleCloseFrames = handleCloseFrames;
    }

    public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker) {
        this(handshaker, true);
    }

    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
        if (this.handleCloseFrames && (frame instanceof CloseWebSocketFrame)) {
            ctx.close();
        } else {
            super.decode(ctx, frame, (List) out);
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        ChannelPipeline cp = ctx.pipeline();
        if (cp.get(WebSocketClientProtocolHandshakeHandler.class) == null) {
            ctx.pipeline().addBefore(ctx.name(), WebSocketClientProtocolHandshakeHandler.class.getName(), new WebSocketClientProtocolHandshakeHandler(this.handshaker));
        }
        if (cp.get(Utf8FrameValidator.class) == null) {
            ctx.pipeline().addBefore(ctx.name(), Utf8FrameValidator.class.getName(), new Utf8FrameValidator());
        }
    }
}
