package io.netty.handler.codec.haproxy;

final class HAProxyConstants {
    static final byte AF_IPV4_BYTE = (byte) 16;
    static final byte AF_IPV6_BYTE = (byte) 32;
    static final byte AF_UNIX_BYTE = (byte) 48;
    static final byte AF_UNSPEC_BYTE = (byte) 0;
    static final byte COMMAND_LOCAL_BYTE = (byte) 0;
    static final byte COMMAND_PROXY_BYTE = (byte) 1;
    static final byte TPAF_TCP4_BYTE = (byte) 17;
    static final byte TPAF_TCP6_BYTE = (byte) 33;
    static final byte TPAF_UDP4_BYTE = (byte) 18;
    static final byte TPAF_UDP6_BYTE = (byte) 34;
    static final byte TPAF_UNIX_DGRAM_BYTE = (byte) 50;
    static final byte TPAF_UNIX_STREAM_BYTE = (byte) 49;
    static final byte TPAF_UNKNOWN_BYTE = (byte) 0;
    static final byte TRANSPORT_DGRAM_BYTE = (byte) 2;
    static final byte TRANSPORT_STREAM_BYTE = (byte) 1;
    static final byte TRANSPORT_UNSPEC_BYTE = (byte) 0;
    static final byte VERSION_ONE_BYTE = (byte) 16;
    static final byte VERSION_TWO_BYTE = (byte) 32;

    private HAProxyConstants() {
    }
}
