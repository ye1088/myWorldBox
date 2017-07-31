package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public final class DefaultSocks5CommandRequest extends AbstractSocks5Message implements Socks5CommandRequest {
    private final String dstAddr;
    private final Socks5AddressType dstAddrType;
    private final int dstPort;
    private final Socks5CommandType type;

    public DefaultSocks5CommandRequest(Socks5CommandType type, Socks5AddressType dstAddrType, String dstAddr, int dstPort) {
        if (type == null) {
            throw new NullPointerException("type");
        } else if (dstAddrType == null) {
            throw new NullPointerException("dstAddrType");
        } else if (dstAddr == null) {
            throw new NullPointerException("dstAddr");
        } else {
            if (dstAddrType == Socks5AddressType.IPv4) {
                if (!NetUtil.isValidIpV4Address(dstAddr)) {
                    throw new IllegalArgumentException("dstAddr: " + dstAddr + " (expected: a valid IPv4 address)");
                }
            } else if (dstAddrType == Socks5AddressType.DOMAIN) {
                dstAddr = IDN.toASCII(dstAddr);
                if (dstAddr.length() > 255) {
                    throw new IllegalArgumentException("dstAddr: " + dstAddr + " (expected: less than 256 chars)");
                }
            } else if (dstAddrType == Socks5AddressType.IPv6 && !NetUtil.isValidIpV6Address(dstAddr)) {
                throw new IllegalArgumentException("dstAddr: " + dstAddr + " (expected: a valid IPv6 address");
            }
            if (dstPort <= 0 || dstPort >= 65536) {
                throw new IllegalArgumentException("dstPort: " + dstPort + " (expected: 1~65535)");
            }
            this.type = type;
            this.dstAddrType = dstAddrType;
            this.dstAddr = dstAddr;
            this.dstPort = dstPort;
        }
    }

    public Socks5CommandType type() {
        return this.type;
    }

    public Socks5AddressType dstAddrType() {
        return this.dstAddrType;
    }

    public String dstAddr() {
        return this.dstAddr;
    }

    public int dstPort() {
        return this.dstPort;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append(StringUtil.simpleClassName((Object) this));
        DecoderResult decoderResult = decoderResult();
        if (decoderResult.isSuccess()) {
            buf.append("(type: ");
        } else {
            buf.append("(decoderResult: ");
            buf.append(decoderResult);
            buf.append(", type: ");
        }
        buf.append(type());
        buf.append(", dstAddrType: ");
        buf.append(dstAddrType());
        buf.append(", dstAddr: ");
        buf.append(dstAddr());
        buf.append(", dstPort: ");
        buf.append(dstPort());
        buf.append(')');
        return buf.toString();
    }
}
