package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface StompContentSubframe extends ByteBufHolder, StompSubframe {
    StompContentSubframe copy();

    StompContentSubframe duplicate();

    StompContentSubframe replace(ByteBuf byteBuf);

    StompContentSubframe retain();

    StompContentSubframe retain(int i);

    StompContentSubframe retainedDuplicate();

    StompContentSubframe touch();

    StompContentSubframe touch(Object obj);
}
