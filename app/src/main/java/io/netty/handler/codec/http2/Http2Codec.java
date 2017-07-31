package io.netty.handler.codec.http2;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;

public final class Http2Codec extends ChannelDuplexHandler {
    private final Http2FrameCodec frameCodec;
    private final Http2MultiplexCodec multiplexCodec;

    public Http2Codec(boolean server, ChannelHandler streamHandler) {
        this(server, streamHandler, null);
    }

    public Http2Codec(boolean server, ChannelHandler streamHandler, EventLoopGroup streamGroup) {
        this(server, streamHandler, streamGroup, new DefaultHttp2FrameWriter());
    }

    Http2Codec(boolean server, ChannelHandler streamHandler, EventLoopGroup streamGroup, Http2FrameWriter frameWriter) {
        this.frameCodec = new Http2FrameCodec(server, frameWriter);
        this.multiplexCodec = new Http2MultiplexCodec(server, streamGroup, streamHandler);
    }

    Http2FrameCodec frameCodec() {
        return this.frameCodec;
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addBefore(ctx.executor(), ctx.name(), null, this.frameCodec);
        ctx.pipeline().addBefore(ctx.executor(), ctx.name(), null, this.multiplexCodec);
        ctx.pipeline().remove(this);
    }
}
