package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.http.HttpServerUpgradeHandler.SourceCodec;

public final class HttpServerCodec extends CombinedChannelDuplexHandler<HttpRequestDecoder, HttpResponseEncoder> implements SourceCodec {
    public HttpServerCodec() {
        this(4096, 8192, 8192);
    }

    public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
        super(new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize), new HttpResponseEncoder());
    }

    public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
        super(new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders), new HttpResponseEncoder());
    }

    public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize) {
        super(new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders, initialBufferSize), new HttpResponseEncoder());
    }

    public void upgradeFrom(ChannelHandlerContext ctx) {
        ctx.pipeline().remove((ChannelHandler) this);
    }
}
