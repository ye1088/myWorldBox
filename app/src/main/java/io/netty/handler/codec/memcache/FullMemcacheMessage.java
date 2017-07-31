package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;

public interface FullMemcacheMessage extends LastMemcacheContent, MemcacheMessage {
    FullMemcacheMessage copy();

    FullMemcacheMessage duplicate();

    FullMemcacheMessage replace(ByteBuf byteBuf);

    FullMemcacheMessage retain();

    FullMemcacheMessage retain(int i);

    FullMemcacheMessage retainedDuplicate();

    FullMemcacheMessage touch();

    FullMemcacheMessage touch(Object obj);
}
