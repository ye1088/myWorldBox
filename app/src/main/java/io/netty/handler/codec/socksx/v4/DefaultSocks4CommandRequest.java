package io.netty.handler.codec.socksx.v4;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public class DefaultSocks4CommandRequest extends AbstractSocks4Message implements Socks4CommandRequest {
    private final String dstAddr;
    private final int dstPort;
    private final Socks4CommandType type;
    private final String userId;

    public DefaultSocks4CommandRequest(Socks4CommandType type, String dstAddr, int dstPort) {
        this(type, dstAddr, dstPort, "");
    }

    public DefaultSocks4CommandRequest(Socks4CommandType type, String dstAddr, int dstPort, String userId) {
        if (type == null) {
            throw new NullPointerException("type");
        } else if (dstAddr == null) {
            throw new NullPointerException("dstAddr");
        } else if (dstPort <= 0 || dstPort >= 65536) {
            throw new IllegalArgumentException("dstPort: " + dstPort + " (expected: 1~65535)");
        } else if (userId == null) {
            throw new NullPointerException("userId");
        } else {
            this.userId = userId;
            this.type = type;
            this.dstAddr = IDN.toASCII(dstAddr);
            this.dstPort = dstPort;
        }
    }

    public Socks4CommandType type() {
        return this.type;
    }

    public String dstAddr() {
        return this.dstAddr;
    }

    public int dstPort() {
        return this.dstPort;
    }

    public String userId() {
        return this.userId;
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
        buf.append(", dstAddr: ");
        buf.append(dstAddr());
        buf.append(", dstPort: ");
        buf.append(dstPort());
        buf.append(", userId: ");
        buf.append(userId());
        buf.append(')');
        return buf.toString();
    }
}
