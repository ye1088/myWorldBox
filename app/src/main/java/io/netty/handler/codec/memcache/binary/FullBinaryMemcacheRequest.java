package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.FullMemcacheMessage;

public interface FullBinaryMemcacheRequest extends FullMemcacheMessage, BinaryMemcacheRequest {
    FullBinaryMemcacheRequest copy();

    FullBinaryMemcacheRequest duplicate();

    FullBinaryMemcacheRequest replace(ByteBuf byteBuf);

    FullBinaryMemcacheRequest retain();

    FullBinaryMemcacheRequest retain(int i);

    FullBinaryMemcacheRequest retainedDuplicate();

    FullBinaryMemcacheRequest touch();

    FullBinaryMemcacheRequest touch(Object obj);
}
