package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;

public interface StompFrame extends LastStompContentSubframe, StompHeadersSubframe {
    StompFrame copy();

    StompFrame duplicate();

    StompFrame replace(ByteBuf byteBuf);

    StompFrame retain();

    StompFrame retain(int i);

    StompFrame retainedDuplicate();

    StompFrame touch();

    StompFrame touch(Object obj);
}
