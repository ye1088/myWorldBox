package io.netty.handler.codec.dns;

public interface DnsResponse extends DnsMessage {
    DnsResponse addRecord(DnsSection dnsSection, int i, DnsRecord dnsRecord);

    DnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    DnsResponse clear();

    DnsResponse clear(DnsSection dnsSection);

    DnsResponseCode code();

    boolean isAuthoritativeAnswer();

    boolean isRecursionAvailable();

    boolean isTruncated();

    DnsResponse retain();

    DnsResponse retain(int i);

    DnsResponse setAuthoritativeAnswer(boolean z);

    DnsResponse setCode(DnsResponseCode dnsResponseCode);

    DnsResponse setId(int i);

    DnsResponse setOpCode(DnsOpCode dnsOpCode);

    DnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord);

    DnsResponse setRecursionAvailable(boolean z);

    DnsResponse setRecursionDesired(boolean z);

    DnsResponse setTruncated(boolean z);

    DnsResponse setZ(int i);

    DnsResponse touch();

    DnsResponse touch(Object obj);
}
