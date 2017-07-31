package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

public class SocksInitResponseDecoder extends ReplayingDecoder<State> {
    private SocksAuthScheme authScheme;
    private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
    private SocksProtocolVersion version;

    enum State {
        CHECK_PROTOCOL_VERSION,
        READ_PREFFERED_AUTH_TYPE
    }

    public SocksInitResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch ((State) state()) {
            case CHECK_PROTOCOL_VERSION:
                this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
                if (this.version == SocksProtocolVersion.SOCKS5) {
                    checkpoint(State.READ_PREFFERED_AUTH_TYPE);
                }
                break;
            case READ_PREFFERED_AUTH_TYPE:
                this.authScheme = SocksAuthScheme.valueOf(byteBuf.readByte());
                this.msg = new SocksInitResponse(this.authScheme);
                break;
        }
        ctx.pipeline().remove(this);
        out.add(this.msg);
    }
}
