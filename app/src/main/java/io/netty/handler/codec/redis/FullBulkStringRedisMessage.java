package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

public class FullBulkStringRedisMessage extends DefaultByteBufHolder implements LastBulkStringRedisContent {
    public static final FullBulkStringRedisMessage EMPTY_INSTANCE = new FullBulkStringRedisMessage() {
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        public FullBulkStringRedisMessage copy() {
            return this;
        }

        public FullBulkStringRedisMessage duplicate() {
            return this;
        }

        public FullBulkStringRedisMessage retainedDuplicate() {
            return this;
        }

        public int refCnt() {
            return 1;
        }

        public FullBulkStringRedisMessage retain() {
            return this;
        }

        public FullBulkStringRedisMessage retain(int increment) {
            return this;
        }

        public FullBulkStringRedisMessage touch() {
            return this;
        }

        public FullBulkStringRedisMessage touch(Object hint) {
            return this;
        }

        public boolean release() {
            return false;
        }

        public boolean release(int decrement) {
            return false;
        }
    };
    public static final FullBulkStringRedisMessage NULL_INSTANCE = new FullBulkStringRedisMessage() {
        public boolean isNull() {
            return true;
        }

        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        public FullBulkStringRedisMessage copy() {
            return this;
        }

        public FullBulkStringRedisMessage duplicate() {
            return this;
        }

        public FullBulkStringRedisMessage retainedDuplicate() {
            return this;
        }

        public int refCnt() {
            return 1;
        }

        public FullBulkStringRedisMessage retain() {
            return this;
        }

        public FullBulkStringRedisMessage retain(int increment) {
            return this;
        }

        public FullBulkStringRedisMessage touch() {
            return this;
        }

        public FullBulkStringRedisMessage touch(Object hint) {
            return this;
        }

        public boolean release() {
            return false;
        }

        public boolean release(int decrement) {
            return false;
        }
    };

    private FullBulkStringRedisMessage() {
        this(Unpooled.EMPTY_BUFFER);
    }

    public FullBulkStringRedisMessage(ByteBuf content) {
        super(content);
    }

    public boolean isNull() {
        return false;
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("content=").append(content()).append(']').toString();
    }

    public FullBulkStringRedisMessage copy() {
        return (FullBulkStringRedisMessage) super.copy();
    }

    public FullBulkStringRedisMessage duplicate() {
        return (FullBulkStringRedisMessage) super.duplicate();
    }

    public FullBulkStringRedisMessage retainedDuplicate() {
        return (FullBulkStringRedisMessage) super.retainedDuplicate();
    }

    public FullBulkStringRedisMessage replace(ByteBuf content) {
        return new FullBulkStringRedisMessage(content);
    }

    public FullBulkStringRedisMessage retain() {
        super.retain();
        return this;
    }

    public FullBulkStringRedisMessage retain(int increment) {
        super.retain(increment);
        return this;
    }

    public FullBulkStringRedisMessage touch() {
        super.touch();
        return this;
    }

    public FullBulkStringRedisMessage touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
