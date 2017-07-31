package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface SpdyDataFrame extends ByteBufHolder, SpdyStreamFrame {
    ByteBuf content();

    SpdyDataFrame copy();

    SpdyDataFrame duplicate();

    SpdyDataFrame replace(ByteBuf byteBuf);

    SpdyDataFrame retain();

    SpdyDataFrame retain(int i);

    SpdyDataFrame retainedDuplicate();

    SpdyDataFrame setLast(boolean z);

    SpdyDataFrame setStreamId(int i);

    SpdyDataFrame touch();

    SpdyDataFrame touch(Object obj);
}
