package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

public class SocksAuthResponseDecoder extends ReplayingDecoder<State> {
    private SocksAuthStatus authStatus;
    private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
    private SocksSubnegotiationVersion version;

    enum State {
        CHECK_PROTOCOL_VERSION,
        READ_AUTH_RESPONSE
    }

    public SocksAuthResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch ((State) state()) {
            case CHECK_PROTOCOL_VERSION:
                this.version = SocksSubnegotiationVersion.valueOf(byteBuf.readByte());
                if (this.version == SocksSubnegotiationVersion.AUTH_PASSWORD) {
                    checkpoint(State.READ_AUTH_RESPONSE);
                }
                break;
            case READ_AUTH_RESPONSE:
                this.authStatus = SocksAuthStatus.valueOf(byteBuf.readByte());
                this.msg = new SocksAuthResponse(this.authStatus);
                break;
        }
        channelHandlerContext.pipeline().remove(this);
        out.add(this.msg);
    }
}
