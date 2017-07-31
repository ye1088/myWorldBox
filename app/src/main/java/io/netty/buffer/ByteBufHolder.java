package io.netty.buffer;

import io.netty.util.ReferenceCounted;

public interface ByteBufHolder extends ReferenceCounted {
    ByteBuf content();

    ByteBufHolder copy();

    ByteBufHolder duplicate();

    ByteBufHolder replace(ByteBuf byteBuf);

    ByteBufHolder retain();

    ByteBufHolder retain(int i);

    ByteBufHolder retainedDuplicate();

    ByteBufHolder touch();

    ByteBufHolder touch(Object obj);
}
