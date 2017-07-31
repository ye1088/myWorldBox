package io.netty.handler.codec.redis;

import io.netty.util.internal.StringUtil;

public final class SimpleStringRedisMessage extends AbstractStringRedisMessage {
    public SimpleStringRedisMessage(String content) {
        super(content);
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("content=").append(content()).append(']').toString();
    }
}
