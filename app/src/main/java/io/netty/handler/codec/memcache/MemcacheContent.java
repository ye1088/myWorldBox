package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface MemcacheContent extends ByteBufHolder, MemcacheObject {
    MemcacheContent copy();

    MemcacheContent duplicate();

    MemcacheContent replace(ByteBuf byteBuf);

    MemcacheContent retain();

    MemcacheContent retain(int i);

    MemcacheContent retainedDuplicate();

    MemcacheContent touch();

    MemcacheContent touch(Object obj);
}
