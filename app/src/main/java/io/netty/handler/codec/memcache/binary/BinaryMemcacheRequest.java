package io.netty.handler.codec.memcache.binary;

public interface BinaryMemcacheRequest extends BinaryMemcacheMessage {
    short reserved();

    BinaryMemcacheRequest retain();

    BinaryMemcacheRequest retain(int i);

    BinaryMemcacheRequest setReserved(short s);

    BinaryMemcacheRequest touch();

    BinaryMemcacheRequest touch(Object obj);
}
