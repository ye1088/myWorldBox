package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface BulkStringRedisContent extends ByteBufHolder, RedisMessage {
    BulkStringRedisContent copy();

    BulkStringRedisContent duplicate();

    BulkStringRedisContent replace(ByteBuf byteBuf);

    BulkStringRedisContent retain();

    BulkStringRedisContent retain(int i);

    BulkStringRedisContent retainedDuplicate();

    BulkStringRedisContent touch();

    BulkStringRedisContent touch(Object obj);
}
