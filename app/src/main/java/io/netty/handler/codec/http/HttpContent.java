package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface HttpContent extends ByteBufHolder, HttpObject {
    HttpContent copy();

    HttpContent duplicate();

    HttpContent replace(ByteBuf byteBuf);

    HttpContent retain();

    HttpContent retain(int i);

    HttpContent retainedDuplicate();

    HttpContent touch();

    HttpContent touch(Object obj);
}
