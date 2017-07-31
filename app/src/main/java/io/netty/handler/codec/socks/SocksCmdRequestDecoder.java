package io.netty.handler.codec.socks;

import io.netty.handler.codec.ReplayingDecoder;

public class SocksCmdRequestDecoder extends ReplayingDecoder<State> {
    private SocksAddressType addressType;
    private SocksCmdType cmdType;
    private int fieldLength;
    private String host;
    private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
    private int port;
    private byte reserved;
    private SocksProtocolVersion version;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$socks$SocksAddressType = new int[SocksAddressType.values().length];

        static {
            $SwitchMap$io$netty$handler$codec$socks$SocksCmdRequestDecoder$State = new int[State.values().length];
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksCmdRequestDecoder$State[State.CHECK_PROTOCOL_VERSION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksCmdRequestDecoder$State[State.READ_CMD_HEADER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksCmdRequestDecoder$State[State.READ_CMD_ADDRESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.IPv4.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.DOMAIN.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.IPv6.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    enum State {
        CHECK_PROTOCOL_VERSION,
        READ_CMD_HEADER,
        READ_CMD_ADDRESS
    }

    public SocksCmdRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void decode(io.netty.channel.ChannelHandlerContext r7, io.netty.buffer.ByteBuf r8, java.util.List<java.lang.Object> r9) throws java.lang.Exception {
        /*
        r6 = this;
        r2 = io.netty.handler.codec.socks.SocksCmdRequestDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$socks$SocksCmdRequestDecoder$State;
        r1 = r6.state();
        r1 = (io.netty.handler.codec.socks.SocksCmdRequestDecoder.State) r1;
        r1 = r1.ordinal();
        r1 = r2[r1];
        switch(r1) {
            case 1: goto L_0x001e;
            case 2: goto L_0x0033;
            case 3: goto L_0x0052;
            default: goto L_0x0011;
        };
    L_0x0011:
        r1 = r7.pipeline();
        r1.remove(r6);
        r1 = r6.msg;
        r9.add(r1);
        return;
    L_0x001e:
        r1 = r8.readByte();
        r1 = io.netty.handler.codec.socks.SocksProtocolVersion.valueOf(r1);
        r6.version = r1;
        r1 = r6.version;
        r2 = io.netty.handler.codec.socks.SocksProtocolVersion.SOCKS5;
        if (r1 != r2) goto L_0x0011;
    L_0x002e:
        r1 = io.netty.handler.codec.socks.SocksCmdRequestDecoder.State.READ_CMD_HEADER;
        r6.checkpoint(r1);
    L_0x0033:
        r1 = r8.readByte();
        r1 = io.netty.handler.codec.socks.SocksCmdType.valueOf(r1);
        r6.cmdType = r1;
        r1 = r8.readByte();
        r6.reserved = r1;
        r1 = r8.readByte();
        r1 = io.netty.handler.codec.socks.SocksAddressType.valueOf(r1);
        r6.addressType = r1;
        r1 = io.netty.handler.codec.socks.SocksCmdRequestDecoder.State.READ_CMD_ADDRESS;
        r6.checkpoint(r1);
    L_0x0052:
        r1 = io.netty.handler.codec.socks.SocksCmdRequestDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$socks$SocksAddressType;
        r2 = r6.addressType;
        r2 = r2.ordinal();
        r1 = r1[r2];
        switch(r1) {
            case 1: goto L_0x0060;
            case 2: goto L_0x0080;
            case 3: goto L_0x00a5;
            default: goto L_0x005f;
        };
    L_0x005f:
        goto L_0x0011;
    L_0x0060:
        r1 = r8.readInt();
        r1 = io.netty.handler.codec.socks.SocksCommonUtils.intToIp(r1);
        r6.host = r1;
        r1 = r8.readUnsignedShort();
        r6.port = r1;
        r1 = new io.netty.handler.codec.socks.SocksCmdRequest;
        r2 = r6.cmdType;
        r3 = r6.addressType;
        r4 = r6.host;
        r5 = r6.port;
        r1.<init>(r2, r3, r4, r5);
        r6.msg = r1;
        goto L_0x0011;
    L_0x0080:
        r1 = r8.readByte();
        r6.fieldLength = r1;
        r1 = r6.fieldLength;
        r1 = io.netty.handler.codec.socks.SocksCommonUtils.readUsAscii(r8, r1);
        r6.host = r1;
        r1 = r8.readUnsignedShort();
        r6.port = r1;
        r1 = new io.netty.handler.codec.socks.SocksCmdRequest;
        r2 = r6.cmdType;
        r3 = r6.addressType;
        r4 = r6.host;
        r5 = r6.port;
        r1.<init>(r2, r3, r4, r5);
        r6.msg = r1;
        goto L_0x0011;
    L_0x00a5:
        r1 = 16;
        r0 = new byte[r1];
        r8.readBytes(r0);
        r1 = io.netty.handler.codec.socks.SocksCommonUtils.ipv6toStr(r0);
        r6.host = r1;
        r1 = r8.readUnsignedShort();
        r6.port = r1;
        r1 = new io.netty.handler.codec.socks.SocksCmdRequest;
        r2 = r6.cmdType;
        r3 = r6.addressType;
        r4 = r6.host;
        r5 = r6.port;
        r1.<init>(r2, r3, r4, r5);
        r6.msg = r1;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.socks.SocksCmdRequestDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }
}
