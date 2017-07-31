package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;

public interface RedisMessagePool {
    byte[] getByteBufOfInteger(long j);

    ErrorRedisMessage getError(ByteBuf byteBuf);

    ErrorRedisMessage getError(String str);

    IntegerRedisMessage getInteger(long j);

    IntegerRedisMessage getInteger(ByteBuf byteBuf);

    SimpleStringRedisMessage getSimpleString(ByteBuf byteBuf);

    SimpleStringRedisMessage getSimpleString(String str);
}
