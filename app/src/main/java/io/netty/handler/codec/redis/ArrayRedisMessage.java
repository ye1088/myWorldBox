package io.netty.handler.codec.redis;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.List;

public class ArrayRedisMessage extends AbstractReferenceCounted implements RedisMessage {
    public static final ArrayRedisMessage EMPTY_INSTANCE = new ArrayRedisMessage() {
        public boolean isNull() {
            return false;
        }

        public ArrayRedisMessage retain() {
            return this;
        }

        public ArrayRedisMessage retain(int increment) {
            return this;
        }

        public ArrayRedisMessage touch() {
            return this;
        }

        public ArrayRedisMessage touch(Object hint) {
            return this;
        }

        public boolean release() {
            return false;
        }

        public boolean release(int decrement) {
            return false;
        }

        public String toString() {
            return "EmptyArrayRedisMessage";
        }
    };
    public static final ArrayRedisMessage NULL_INSTANCE = new ArrayRedisMessage() {
        public boolean isNull() {
            return true;
        }

        public ArrayRedisMessage retain() {
            return this;
        }

        public ArrayRedisMessage retain(int increment) {
            return this;
        }

        public ArrayRedisMessage touch() {
            return this;
        }

        public ArrayRedisMessage touch(Object hint) {
            return this;
        }

        public boolean release() {
            return false;
        }

        public boolean release(int decrement) {
            return false;
        }

        public String toString() {
            return "NullArrayRedisMessage";
        }
    };
    private final List<RedisMessage> children;

    private ArrayRedisMessage() {
        this.children = Collections.emptyList();
    }

    public ArrayRedisMessage(List<RedisMessage> children) {
        this.children = (List) ObjectUtil.checkNotNull(children, "children");
    }

    public final List<RedisMessage> children() {
        return this.children;
    }

    public boolean isNull() {
        return false;
    }

    protected void deallocate() {
        for (RedisMessage msg : this.children) {
            ReferenceCountUtil.release(msg);
        }
    }

    public ArrayRedisMessage touch(Object hint) {
        for (RedisMessage msg : this.children) {
            ReferenceCountUtil.touch(msg);
        }
        return this;
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("children=").append(this.children.size()).append(']').toString();
    }
}
