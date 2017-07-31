package io.netty.handler.codec.socksx.v4;

public interface Socks4CommandRequest extends Socks4Message {
    String dstAddr();

    int dstPort();

    Socks4CommandType type();

    String userId();
}
