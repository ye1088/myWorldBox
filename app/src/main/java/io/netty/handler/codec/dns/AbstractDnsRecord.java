package io.netty.handler.codec.dns;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public abstract class AbstractDnsRecord implements DnsRecord {
    private final short dnsClass;
    private int hashCode;
    private final String name;
    private final long timeToLive;
    private final DnsRecordType type;

    protected AbstractDnsRecord(String name, DnsRecordType type, long timeToLive) {
        this(name, type, 1, timeToLive);
    }

    protected AbstractDnsRecord(String name, DnsRecordType type, int dnsClass, long timeToLive) {
        if (timeToLive < 0) {
            throw new IllegalArgumentException("timeToLive: " + timeToLive + " (expected: >= 0)");
        }
        this.name = appendTrailingDot(IDN.toASCII((String) ObjectUtil.checkNotNull(name, "name")));
        this.type = (DnsRecordType) ObjectUtil.checkNotNull(type, "type");
        this.dnsClass = (short) dnsClass;
        this.timeToLive = timeToLive;
    }

    private static String appendTrailingDot(String name) {
        if (name.length() <= 0 || name.charAt(name.length() - 1) == '.') {
            return name;
        }
        return name + '.';
    }

    public String name() {
        return this.name;
    }

    public DnsRecordType type() {
        return this.type;
    }

    public int dnsClass() {
        return this.dnsClass & 65535;
    }

    public long timeToLive() {
        return this.timeToLive;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DnsRecord)) {
            return false;
        }
        DnsRecord that = (DnsRecord) obj;
        int hashCode = this.hashCode;
        if (hashCode != 0 && hashCode != that.hashCode()) {
            return false;
        }
        if (type().intValue() == that.type().intValue() && dnsClass() == that.dnsClass() && name().equals(that.name())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.hashCode;
        if (hashCode != 0) {
            return hashCode;
        }
        hashCode = ((this.name.hashCode() * 31) + (type().intValue() * 31)) + dnsClass();
        this.hashCode = hashCode;
        return hashCode;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(64);
        buf.append(StringUtil.simpleClassName(this)).append('(').append(name()).append(HttpConstants.SP_CHAR).append(timeToLive()).append(HttpConstants.SP_CHAR);
        DnsMessageUtil.appendRecordClass(buf, dnsClass()).append(HttpConstants.SP_CHAR).append(type().name()).append(')');
        return buf.toString();
    }
}
