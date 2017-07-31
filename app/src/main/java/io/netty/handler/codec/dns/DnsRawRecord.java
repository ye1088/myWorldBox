package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

public interface DnsRawRecord extends ByteBufHolder, DnsRecord {
    DnsRawRecord copy();

    DnsRawRecord duplicate();

    DnsRawRecord replace(ByteBuf byteBuf);

    DnsRawRecord retain();

    DnsRawRecord retain(int i);

    DnsRawRecord retainedDuplicate();

    DnsRawRecord touch();

    DnsRawRecord touch(Object obj);
}
