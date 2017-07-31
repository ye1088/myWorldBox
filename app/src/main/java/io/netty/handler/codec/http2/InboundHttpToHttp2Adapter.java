package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http2.HttpConversionUtil.ExtensionHeaderNames;

public class InboundHttpToHttp2Adapter extends ChannelInboundHandlerAdapter {
    private final Http2Connection connection;
    private final Http2FrameListener listener;

    public InboundHttpToHttp2Adapter(Http2Connection connection, Http2FrameListener listener) {
        this.connection = connection;
        this.listener = listener;
    }

    private int getStreamId(HttpHeaders httpHeaders) {
        return httpHeaders.getInt(ExtensionHeaderNames.STREAM_ID.text(), this.connection.remote().incrementAndGetNextStreamId());
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpMessage) {
            HttpMessage message = (FullHttpMessage) msg;
            try {
                int streamId = getStreamId(message.headers());
                Http2Stream stream = this.connection.stream(streamId);
                if (stream == null) {
                    stream = this.connection.remote().createStream(streamId, false);
                }
                message.headers().set(ExtensionHeaderNames.SCHEME.text(), HttpScheme.HTTP.name());
                Http2Headers messageHeaders = HttpConversionUtil.toHttp2Headers(message, true);
                boolean hasContent = message.content().isReadable();
                boolean hasTrailers = !message.trailingHeaders().isEmpty();
                Http2FrameListener http2FrameListener = this.listener;
                boolean z = (hasContent || hasTrailers) ? false : true;
                http2FrameListener.onHeadersRead(ctx, streamId, messageHeaders, 0, z);
                if (hasContent) {
                    this.listener.onDataRead(ctx, streamId, message.content(), 0, !hasTrailers);
                }
                if (hasTrailers) {
                    this.listener.onHeadersRead(ctx, streamId, HttpConversionUtil.toHttp2Headers(message.trailingHeaders(), true), 0, true);
                }
                stream.closeRemoteSide();
            } finally {
                message.release();
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }
}
