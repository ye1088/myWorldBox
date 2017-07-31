package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

class PerFrameDeflateDecoder extends DeflateDecoder {
    public PerFrameDeflateDecoder(boolean noContext) {
        super(noContext);
    }

    public boolean acceptInboundMessage(Object msg) throws Exception {
        return ((msg instanceof TextWebSocketFrame) || (msg instanceof BinaryWebSocketFrame) || (msg instanceof ContinuationWebSocketFrame)) && (((WebSocketFrame) msg).rsv() & 4) > 0;
    }

    protected int newRsv(WebSocketFrame msg) {
        return msg.rsv() ^ 4;
    }

    protected boolean appendFrameTail(WebSocketFrame msg) {
        return true;
    }
}
