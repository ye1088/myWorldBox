package io.netty.handler.codec.socksx.v5;

public interface Socks5CommandRequest extends Socks5Message {
    String dstAddr();

    Socks5AddressType dstAddrType();

    int dstPort();

    Socks5CommandType type();
}
