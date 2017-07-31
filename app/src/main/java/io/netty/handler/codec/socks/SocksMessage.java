package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

public abstract class SocksMessage {
    private final SocksProtocolVersion protocolVersion = SocksProtocolVersion.SOCKS5;
    private final SocksMessageType type;

    @Deprecated
    public abstract void encodeAsByteBuf(ByteBuf byteBuf);

    protected SocksMessage(SocksMessageType type) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.type = type;
    }

    public SocksMessageType type() {
        return this.type;
    }

    public SocksProtocolVersion protocolVersion() {
        return this.protocolVersion;
    }
}
