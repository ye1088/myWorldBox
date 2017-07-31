package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

class PerFrameDeflateEncoder extends DeflateEncoder {
    public PerFrameDeflateEncoder(int compressionLevel, int windowSize, boolean noContext) {
        super(compressionLevel, windowSize, noContext);
    }

    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return ((msg instanceof TextWebSocketFrame) || (msg instanceof BinaryWebSocketFrame) || (msg instanceof ContinuationWebSocketFrame)) && ((WebSocketFrame) msg).content().readableBytes() > 0 && (((WebSocketFrame) msg).rsv() & 4) == 0;
    }

    protected int rsv(WebSocketFrame msg) {
        return msg.rsv() | 4;
    }

    protected boolean removeFrameTail(WebSocketFrame msg) {
        return true;
    }
}
