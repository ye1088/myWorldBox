package io.netty.handler.codec.dns;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.StringUtil;

public class DefaultDnsQuestion extends AbstractDnsRecord implements DnsQuestion {
    public DefaultDnsQuestion(String name, DnsRecordType type) {
        super(name, type, 0);
    }

    public DefaultDnsQuestion(String name, DnsRecordType type, int dnsClass) {
        super(name, type, dnsClass, 0);
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(64);
        buf.append(StringUtil.simpleClassName(this)).append('(').append(name()).append(HttpConstants.SP_CHAR);
        DnsMessageUtil.appendRecordClass(buf, dnsClass()).append(HttpConstants.SP_CHAR).append(type().name()).append(')');
        return buf.toString();
    }
}
