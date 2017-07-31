package io.netty.handler.codec.socks;

import io.netty.handler.codec.ReplayingDecoder;

public class SocksAuthRequestDecoder extends ReplayingDecoder<State> {
    private int fieldLength;
    private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
    private String password;
    private String username;
    private SocksSubnegotiationVersion version;

    enum State {
        CHECK_PROTOCOL_VERSION,
        READ_USERNAME,
        READ_PASSWORD
    }

    public SocksAuthRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void decode(io.netty.channel.ChannelHandlerContext r4, io.netty.buffer.ByteBuf r5, java.util.List<java.lang.Object> r6) throws java.lang.Exception {
        /*
        r3 = this;
        r1 = io.netty.handler.codec.socks.SocksAuthRequestDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$socks$SocksAuthRequestDecoder$State;
        r0 = r3.state();
        r0 = (io.netty.handler.codec.socks.SocksAuthRequestDecoder.State) r0;
        r0 = r0.ordinal();
        r0 = r1[r0];
        switch(r0) {
            case 1: goto L_0x001e;
            case 2: goto L_0x0033;
            case 3: goto L_0x0046;
            default: goto L_0x0011;
        };
    L_0x0011:
        r0 = r4.pipeline();
        r0.remove(r3);
        r0 = r3.msg;
        r6.add(r0);
        return;
    L_0x001e:
        r0 = r5.readByte();
        r0 = io.netty.handler.codec.socks.SocksSubnegotiationVersion.valueOf(r0);
        r3.version = r0;
        r0 = r3.version;
        r1 = io.netty.handler.codec.socks.SocksSubnegotiationVersion.AUTH_PASSWORD;
        if (r0 != r1) goto L_0x0011;
    L_0x002e:
        r0 = io.netty.handler.codec.socks.SocksAuthRequestDecoder.State.READ_USERNAME;
        r3.checkpoint(r0);
    L_0x0033:
        r0 = r5.readByte();
        r3.fieldLength = r0;
        r0 = r3.fieldLength;
        r0 = io.netty.handler.codec.socks.SocksCommonUtils.readUsAscii(r5, r0);
        r3.username = r0;
        r0 = io.netty.handler.codec.socks.SocksAuthRequestDecoder.State.READ_PASSWORD;
        r3.checkpoint(r0);
    L_0x0046:
        r0 = r5.readByte();
        r3.fieldLength = r0;
        r0 = r3.fieldLength;
        r0 = io.netty.handler.codec.socks.SocksCommonUtils.readUsAscii(r5, r0);
        r3.password = r0;
        r0 = new io.netty.handler.codec.socks.SocksAuthRequest;
        r1 = r3.username;
        r2 = r3.password;
        r0.<init>(r1, r2);
        r3.msg = r0;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.socks.SocksAuthRequestDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }
}
