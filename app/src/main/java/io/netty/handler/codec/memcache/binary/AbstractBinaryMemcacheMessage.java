package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.AbstractMemcacheObject;

public abstract class AbstractBinaryMemcacheMessage extends AbstractMemcacheObject implements BinaryMemcacheMessage {
    private long cas;
    private byte dataType;
    private ByteBuf extras;
    private byte extrasLength;
    private ByteBuf key;
    private short keyLength;
    private byte magic;
    private int opaque;
    private byte opcode;
    private int totalBodyLength;

    protected AbstractBinaryMemcacheMessage(ByteBuf key, ByteBuf extras) {
        byte b = (byte) 0;
        this.key = key;
        this.keyLength = key == null ? (short) 0 : (short) key.readableBytes();
        this.extras = extras;
        if (extras != null) {
            b = (byte) extras.readableBytes();
        }
        this.extrasLength = b;
        this.totalBodyLength = this.keyLength + this.extrasLength;
    }

    public ByteBuf key() {
        return this.key;
    }

    public ByteBuf extras() {
        return this.extras;
    }

    public BinaryMemcacheMessage setKey(ByteBuf key) {
        if (this.key != null) {
            this.key.release();
        }
        this.key = key;
        short oldKeyLength = this.keyLength;
        this.keyLength = key == null ? (short) 0 : (short) key.readableBytes();
        this.totalBodyLength = (this.totalBodyLength + this.keyLength) - oldKeyLength;
        return this;
    }

    public BinaryMemcacheMessage setExtras(ByteBuf extras) {
        if (this.extras != null) {
            this.extras.release();
        }
        this.extras = extras;
        short oldExtrasLength = (short) this.extrasLength;
        this.extrasLength = extras == null ? (byte) 0 : (byte) extras.readableBytes();
        this.totalBodyLength = (this.totalBodyLength + this.extrasLength) - oldExtrasLength;
        return this;
    }

    public byte magic() {
        return this.magic;
    }

    public BinaryMemcacheMessage setMagic(byte magic) {
        this.magic = magic;
        return this;
    }

    public long cas() {
        return this.cas;
    }

    public BinaryMemcacheMessage setCas(long cas) {
        this.cas = cas;
        return this;
    }

    public int opaque() {
        return this.opaque;
    }

    public BinaryMemcacheMessage setOpaque(int opaque) {
        this.opaque = opaque;
        return this;
    }

    public int totalBodyLength() {
        return this.totalBodyLength;
    }

    public BinaryMemcacheMessage setTotalBodyLength(int totalBodyLength) {
        this.totalBodyLength = totalBodyLength;
        return this;
    }

    public byte dataType() {
        return this.dataType;
    }

    public BinaryMemcacheMessage setDataType(byte dataType) {
        this.dataType = dataType;
        return this;
    }

    public byte extrasLength() {
        return this.extrasLength;
    }

    BinaryMemcacheMessage setExtrasLength(byte extrasLength) {
        this.extrasLength = extrasLength;
        return this;
    }

    public short keyLength() {
        return this.keyLength;
    }

    BinaryMemcacheMessage setKeyLength(short keyLength) {
        this.keyLength = keyLength;
        return this;
    }

    public byte opcode() {
        return this.opcode;
    }

    public BinaryMemcacheMessage setOpcode(byte opcode) {
        this.opcode = opcode;
        return this;
    }

    public BinaryMemcacheMessage retain() {
        super.retain();
        return this;
    }

    public BinaryMemcacheMessage retain(int increment) {
        super.retain(increment);
        return this;
    }

    protected void deallocate() {
        if (this.key != null) {
            this.key.release();
        }
        if (this.extras != null) {
            this.extras.release();
        }
    }

    public BinaryMemcacheMessage touch() {
        super.touch();
        return this;
    }

    public BinaryMemcacheMessage touch(Object hint) {
        if (this.key != null) {
            this.key.touch(hint);
        }
        if (this.extras != null) {
            this.extras.touch(hint);
        }
        return this;
    }
}
