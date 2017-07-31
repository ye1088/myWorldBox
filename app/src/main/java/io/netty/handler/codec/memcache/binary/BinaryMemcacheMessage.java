package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.MemcacheMessage;

public interface BinaryMemcacheMessage extends MemcacheMessage {
    long cas();

    byte dataType();

    ByteBuf extras();

    byte extrasLength();

    ByteBuf key();

    short keyLength();

    byte magic();

    int opaque();

    byte opcode();

    BinaryMemcacheMessage retain();

    BinaryMemcacheMessage retain(int i);

    BinaryMemcacheMessage setCas(long j);

    BinaryMemcacheMessage setDataType(byte b);

    BinaryMemcacheMessage setExtras(ByteBuf byteBuf);

    BinaryMemcacheMessage setKey(ByteBuf byteBuf);

    BinaryMemcacheMessage setMagic(byte b);

    BinaryMemcacheMessage setOpaque(int i);

    BinaryMemcacheMessage setOpcode(byte b);

    BinaryMemcacheMessage setTotalBodyLength(int i);

    int totalBodyLength();

    BinaryMemcacheMessage touch();

    BinaryMemcacheMessage touch(Object obj);
}
