package io.netty.handler.codec.http.websocketx;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

public class WebSocketServerHandshakerFactory {
    private final boolean allowExtensions;
    private final boolean allowMaskMismatch;
    private final int maxFramePayloadLength;
    private final String subprotocols;
    private final String webSocketURL;

    public WebSocketServerHandshakerFactory(String webSocketURL, String subprotocols, boolean allowExtensions) {
        this(webSocketURL, subprotocols, allowExtensions, 65536);
    }

    public WebSocketServerHandshakerFactory(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
        this(webSocketURL, subprotocols, allowExtensions, maxFramePayloadLength, false);
    }

    public WebSocketServerHandshakerFactory(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
        this.webSocketURL = webSocketURL;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadLength = maxFramePayloadLength;
        this.allowMaskMismatch = allowMaskMismatch;
    }

    public WebSocketServerHandshaker newHandshaker(HttpRequest req) {
        CharSequence version = req.headers().get(HttpHeaderNames.SEC_WEBSOCKET_VERSION);
        if (version == null) {
            return new WebSocketServerHandshaker00(this.webSocketURL, this.subprotocols, this.maxFramePayloadLength);
        }
        if (version.equals(WebSocketVersion.V13.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker13(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength, this.allowMaskMismatch);
        }
        if (version.equals(WebSocketVersion.V08.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker08(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength, this.allowMaskMismatch);
        }
        if (version.equals(WebSocketVersion.V07.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker07(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength, this.allowMaskMismatch);
        }
        return null;
    }

    @Deprecated
    public static void sendUnsupportedWebSocketVersionResponse(Channel channel) {
        sendUnsupportedVersionResponse(channel);
    }

    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel) {
        return sendUnsupportedVersionResponse(channel, channel.newPromise());
    }

    public static ChannelFuture sendUnsupportedVersionResponse(Channel channel, ChannelPromise promise) {
        HttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UPGRADE_REQUIRED);
        res.headers().set(HttpHeaderNames.SEC_WEBSOCKET_VERSION, WebSocketVersion.V13.toHttpHeaderValue());
        HttpUtil.setContentLength(res, 0);
        return channel.writeAndFlush(res, promise);
    }
}
