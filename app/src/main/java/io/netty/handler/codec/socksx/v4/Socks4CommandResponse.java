package io.netty.handler.codec.socksx.v4;

public interface Socks4CommandResponse extends Socks4Message {
    String dstAddr();

    int dstPort();

    Socks4CommandStatus status();
}
