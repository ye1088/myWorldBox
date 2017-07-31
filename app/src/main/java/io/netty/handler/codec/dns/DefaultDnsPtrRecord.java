package io.netty.handler.codec.dns;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

public class DefaultDnsPtrRecord extends AbstractDnsRecord implements DnsPtrRecord {
    private final String hostname;

    public DefaultDnsPtrRecord(String name, int dnsClass, long timeToLive, String hostname) {
        super(name, DnsRecordType.PTR, dnsClass, timeToLive);
        this.hostname = (String) ObjectUtil.checkNotNull(hostname, "hostname");
    }

    public String hostname() {
        return this.hostname;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(64).append(StringUtil.simpleClassName(this)).append('(');
        DnsRecordType type = type();
        buf.append(name().isEmpty() ? "<root>" : name()).append(HttpConstants.SP_CHAR).append(timeToLive()).append(HttpConstants.SP_CHAR);
        DnsMessageUtil.appendRecordClass(buf, dnsClass()).append(HttpConstants.SP_CHAR).append(type.name());
        buf.append(HttpConstants.SP_CHAR).append(this.hostname);
        return buf.toString();
    }
}
