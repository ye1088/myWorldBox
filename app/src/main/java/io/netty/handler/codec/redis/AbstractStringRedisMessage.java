package io.netty.handler.codec.redis;

import io.netty.util.internal.ObjectUtil;

public abstract class AbstractStringRedisMessage implements RedisMessage {
    private final String content;

    AbstractStringRedisMessage(String content) {
        this.content = (String) ObjectUtil.checkNotNull(content, "content");
    }

    public final String content() {
        return this.content;
    }
}
