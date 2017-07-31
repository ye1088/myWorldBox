package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

public interface FullHttpResponse extends FullHttpMessage, HttpResponse {
    FullHttpResponse copy();

    FullHttpResponse duplicate();

    FullHttpResponse replace(ByteBuf byteBuf);

    FullHttpResponse retain();

    FullHttpResponse retain(int i);

    FullHttpResponse retainedDuplicate();

    FullHttpResponse setProtocolVersion(HttpVersion httpVersion);

    FullHttpResponse setStatus(HttpResponseStatus httpResponseStatus);

    FullHttpResponse touch();

    FullHttpResponse touch(Object obj);
}
