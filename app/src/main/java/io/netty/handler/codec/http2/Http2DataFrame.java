package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface Http2DataFrame extends ByteBufHolder, Http2StreamFrame {
    ByteBuf content();

    Http2DataFrame copy();

    Http2DataFrame duplicate();

    boolean isEndStream();

    int padding();

    Http2DataFrame replace(ByteBuf byteBuf);

    Http2DataFrame retain();

    Http2DataFrame retain(int i);

    Http2DataFrame retainedDuplicate();

    Http2DataFrame touch();

    Http2DataFrame touch(Object obj);
}
