package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

public interface FullHttpRequest extends FullHttpMessage, HttpRequest {
    FullHttpRequest copy();

    FullHttpRequest duplicate();

    FullHttpRequest replace(ByteBuf byteBuf);

    FullHttpRequest retain();

    FullHttpRequest retain(int i);

    FullHttpRequest retainedDuplicate();

    FullHttpRequest setMethod(HttpMethod httpMethod);

    FullHttpRequest setProtocolVersion(HttpVersion httpVersion);

    FullHttpRequest setUri(String str);

    FullHttpRequest touch();

    FullHttpRequest touch(Object obj);
}
