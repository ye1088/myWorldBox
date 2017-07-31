package io.netty.handler.codec.redis;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;

public enum RedisMessageType {
    SIMPLE_STRING((byte) 43, true),
    ERROR((byte) 45, true),
    INTEGER(HttpConstants.COLON, true),
    BULK_STRING(BinaryMemcacheOpcodes.GATKQ, false),
    ARRAY_HEADER((byte) 42, false),
    ARRAY((byte) 42, false);
    
    private final boolean inline;
    private final byte value;

    private RedisMessageType(byte value, boolean inline) {
        this.value = value;
        this.inline = inline;
    }

    public byte value() {
        return this.value;
    }

    public boolean isInline() {
        return this.inline;
    }

    public static RedisMessageType valueOf(byte value) {
        switch (value) {
            case (byte) 36:
                return BULK_STRING;
            case (byte) 42:
                return ARRAY_HEADER;
            case (byte) 43:
                return SIMPLE_STRING;
            case (byte) 45:
                return ERROR;
            case (byte) 58:
                return INTEGER;
            default:
                throw new RedisCodecException("Unknown RedisMessageType: " + value);
        }
    }
}
