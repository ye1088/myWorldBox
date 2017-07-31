package io.netty.handler.codec.dns;

public interface DnsQuery extends DnsMessage {
    DnsQuery addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord);

    DnsQuery addRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    DnsQuery clear();

    DnsQuery clear(DnsSection dnsSection);

    DnsQuery retain();

    DnsQuery retain(int i);

    DnsQuery setId(int i);

    DnsQuery setOpCode(DnsOpCode dnsOpCode);

    DnsQuery setRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    DnsQuery setRecursionDesired(boolean z);

    DnsQuery setZ(int i);

    DnsQuery touch();

    DnsQuery touch(Object obj);
}
