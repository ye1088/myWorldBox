package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.IDN;

public final class SocksCmdResponse extends SocksResponse {
    private static final byte[] DOMAIN_ZEROED = new byte[]{(byte) 0};
    private static final byte[] IPv4_HOSTNAME_ZEROED = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] IPv6_HOSTNAME_ZEROED = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private final SocksAddressType addressType;
    private final SocksCmdStatus cmdStatus;
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

    public SocksCmdResponse(SocksCmdStatus cmdStatus, SocksAddressType addressType) {
        this(cmdStatus, addressType, null, 0);
    }

    public SocksCmdResponse(SocksCmdStatus cmdStatus, SocksAddressType addressType, String host, int port) {
        super(SocksResponseType.CMD);
        if (cmdStatus == null) {
            throw new NullPointerException("cmdStatus");
        } else if (addressType == null) {
            throw new NullPointerException("addressType");
        } else {
            if (host != null) {
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
                host = IDN.toASCII(host);
            }
            if (port < 0 || port > 65535) {
                throw new IllegalArgumentException(port + " is not in bounds 0 <= x <= 65535");
            }
            this.cmdStatus = cmdStatus;
            this.addressType = addressType;
            this.host = host;
            this.port = port;
        }
    }

    public SocksCmdStatus cmdStatus() {
        return this.cmdStatus;
    }

    public SocksAddressType addressType() {
        return this.addressType;
    }

    public String host() {
        if (this.host != null) {
            return IDN.toUnicode(this.host);
        }
        return null;
    }

    public int port() {
        return this.port;
    }

    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(protocolVersion().byteValue());
        byteBuf.writeByte(this.cmdStatus.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.addressType.byteValue());
        byte[] hostContent;
        switch (AnonymousClass1.$SwitchMap$io$netty$handler$codec$socks$SocksAddressType[this.addressType.ordinal()]) {
            case 1:
                byteBuf.writeBytes(this.host == null ? IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host));
                byteBuf.writeShort(this.port);
                return;
            case 2:
                hostContent = this.host == null ? DOMAIN_ZEROED : this.host.getBytes(CharsetUtil.US_ASCII);
                byteBuf.writeByte(hostContent.length);
                byteBuf.writeBytes(hostContent);
                byteBuf.writeShort(this.port);
                return;
            case 3:
                if (this.host == null) {
                    hostContent = IPv6_HOSTNAME_ZEROED;
                } else {
                    hostContent = NetUtil.createByteArrayFromIpAddressString(this.host);
                }
                byteBuf.writeBytes(hostContent);
                byteBuf.writeShort(this.port);
                return;
            default:
                return;
        }
    }
}
