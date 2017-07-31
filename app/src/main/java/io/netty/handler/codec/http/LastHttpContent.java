package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

public interface LastHttpContent extends HttpContent {
    public static final LastHttpContent EMPTY_LAST_CONTENT = new 1();

    LastHttpContent copy();

    LastHttpContent duplicate();

    LastHttpContent replace(ByteBuf byteBuf);

    LastHttpContent retain();

    LastHttpContent retain(int i);

    LastHttpContent retainedDuplicate();

    LastHttpContent touch();

    LastHttpContent touch(Object obj);

    HttpHeaders trailingHeaders();
}
