package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;

public class DefaultBinaryMemcacheRequest extends AbstractBinaryMemcacheMessage implements BinaryMemcacheRequest {
    public static final byte REQUEST_MAGIC_BYTE = Byte.MIN_VALUE;
    private short reserved;

    public DefaultBinaryMemcacheRequest() {
        this(null, null);
    }

    public DefaultBinaryMemcacheRequest(ByteBuf key) {
        this(key, null);
    }

    public DefaultBinaryMemcacheRequest(ByteBuf key, ByteBuf extras) {
        super(key, extras);
        setMagic(REQUEST_MAGIC_BYTE);
    }

    public short reserved() {
        return this.reserved;
    }

    public BinaryMemcacheRequest setReserved(short reserved) {
        this.reserved = reserved;
        return this;
    }

    public BinaryMemcacheRequest retain() {
        super.retain();
        return this;
    }

    public BinaryMemcacheRequest retain(int increment) {
        super.retain(increment);
        return this;
    }

    public BinaryMemcacheRequest touch() {
        super.touch();
        return this;
    }

    public BinaryMemcacheRequest touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
