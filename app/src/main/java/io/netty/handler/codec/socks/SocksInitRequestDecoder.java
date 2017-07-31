package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.ArrayList;
import java.util.List;

public class SocksInitRequestDecoder extends ReplayingDecoder<State> {
    private byte authSchemeNum;
    private final List<SocksAuthScheme> authSchemes = new ArrayList();
    private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
    private SocksProtocolVersion version;

    enum State {
        CHECK_PROTOCOL_VERSION,
        READ_AUTH_SCHEMES
    }

    public SocksInitRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch ((State) state()) {
            case CHECK_PROTOCOL_VERSION:
                this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
                if (this.version == SocksProtocolVersion.SOCKS5) {
                    checkpoint(State.READ_AUTH_SCHEMES);
                }
                break;
            case READ_AUTH_SCHEMES:
                this.authSchemes.clear();
                this.authSchemeNum = byteBuf.readByte();
                for (byte i = (byte) 0; i < this.authSchemeNum; i++) {
                    this.authSchemes.add(SocksAuthScheme.valueOf(byteBuf.readByte()));
                }
                this.msg = new SocksInitRequest(this.authSchemes);
                break;
        }
        ctx.pipeline().remove(this);
        out.add(this.msg);
    }
}
