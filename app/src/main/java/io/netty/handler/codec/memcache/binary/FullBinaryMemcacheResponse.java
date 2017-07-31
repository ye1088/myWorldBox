package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;

public interface FullBinaryMemcacheResponse extends FullMemcacheMessage, BinaryMemcacheResponse {
    FullBinaryMemcacheResponse copy();

    FullBinaryMemcacheResponse duplicate();

    FullBinaryMemcacheResponse replace(ByteBuf byteBuf);

    FullBinaryMemcacheResponse retain();

    FullBinaryMemcacheResponse retain(int i);

    FullBinaryMemcacheResponse retainedDuplicate();

    FullBinaryMemcacheResponse touch();

    FullBinaryMemcacheResponse touch(Object obj);
}
