package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface Http2GoAwayFrame extends ByteBufHolder, Http2Frame {
    ByteBuf content();

    Http2GoAwayFrame copy();

    Http2GoAwayFrame duplicate();

    long errorCode();

    int extraStreamIds();

    int lastStreamId();

    Http2GoAwayFrame replace(ByteBuf byteBuf);

    Http2GoAwayFrame retain();

    Http2GoAwayFrame retain(int i);

    Http2GoAwayFrame retainedDuplicate();

    Http2GoAwayFrame setExtraStreamIds(int i);

    Http2GoAwayFrame touch();

    Http2GoAwayFrame touch(Object obj);
}
