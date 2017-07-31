package io.netty.handler.codec.redis;

import io.netty.util.internal.StringUtil;

final class RedisConstants {
    static final int EOL_LENGTH = 2;
    static final short EOL_SHORT = RedisCodecUtil.makeShort(StringUtil.CARRIAGE_RETURN, '\n');
    static final int LONG_MAX_LENGTH = 20;
    static final int NULL_LENGTH = 2;
    static final short NULL_SHORT = RedisCodecUtil.makeShort('-', '1');
    static final int NULL_VALUE = -1;
    static final int POSITIVE_LONG_MAX_LENGTH = 19;
    static final int REDIS_MESSAGE_MAX_LENGTH = 536870912;
    static final int TYPE_LENGTH = 1;

    private RedisConstants() {
    }
}
