package io.netty.handler.codec.memcache.binary;

public interface BinaryMemcacheResponse extends BinaryMemcacheMessage {
    BinaryMemcacheResponse retain();

    BinaryMemcacheResponse retain(int i);

    BinaryMemcacheResponse setStatus(short s);

    short status();

    BinaryMemcacheResponse touch();

    BinaryMemcacheResponse touch(Object obj);
}
