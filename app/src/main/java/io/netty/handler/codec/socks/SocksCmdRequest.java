package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.IDN;

public final class SocksCmdRequest extends SocksRequest {
    private final SocksAddressType addressType;
    private final SocksCmdType cmdType;
    private final String host;
    private final int port;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$socks$SocksAddressType = new int[SocksAddressType.values().length];

        static {
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.IPv4.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.DOMAIN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.IPv6.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$socks$SocksAddressType[SocksAddressType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public SocksCmdRequest(SocksCmdType cmdType, SocksAddressType addressType, String host, int port) {
        super(SocksRequestType.CMD);
        if (cmdType == null) {
            throw new NullPointerException("cmdType");
        } else if (addressType == null) {
            throw new NullPointerException("addressType");
        } else if (host == null) {
            throw new NullPointerException("host");
        } else {
            switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$socks$SocksAddressType[addressType.ordinal()]) {
                case 1:
                    if (!NetUtil.isValidIpV4Address(host)) {
                        throw new IllegalArgumentException(host + " is not a_isRightVersion valid IPv4 address");
                    }
                    break;
                case 2:
                    if (IDN.toASCII(host).length() > 255) {
                        throw new IllegalArgumentException(host + " IDN: " + IDN.toASCII(host) + " exceeds 255 char limit");
                    }
                    break;
                case 3:
                    if (!NetUtil.isValidIpV6Address(host)) {
                        throw new IllegalArgumentException(host + " is not a_isRightVersion valid IPv6 address");
                    }
                    break;
            }
            if (port <= 0 || port >= 65536) {
                throw new IllegalArgumentException(port + " is not in bounds 0 < x < 65536");
            }
            this.cmdType = cmdType;
            this.addressType = addressType;
            this.host = IDN.toASCII(host);
            this.port = port;
        }
    }

    public SocksCmdType cmdType() {
        return this.cmdType;
    }

    public SocksAddressType addressType() {
        return this.addressType;
    }

    public String host() {
        return IDN.toUnicode(this.host);
    }

    public int port() {
        return this.port;
    }

    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdType.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$socks$SocksAddressType[this.addressType.ordinal()]) {
            case 1:
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                return;
            case 2:
                byteBuf.writeByte(this.host.length());
                byteBuf.writeBytes(this.host.getBytes(CharsetUtil.US_ASCII));
                byteBuf.writeShort(this.port);
                return;
            case 3:
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                return;
            default:
                return;
        }
    }
}
