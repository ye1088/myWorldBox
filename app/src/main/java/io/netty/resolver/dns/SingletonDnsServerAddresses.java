package io.netty.resolver.dns;

import java.net.InetSocketAddress;

final class SingletonDnsServerAddresses extends DnsServerAddresses {
    private final InetSocketAddress address;
    private final String strVal;
    private final DnsServerAddressStream stream = new DnsServerAddressStream() {
        public InetSocketAddress next() {
            return SingletonDnsServerAddresses.this.address;
        }

        public String toString() {
            return SingletonDnsServerAddresses.this.toString();
        }
    };

    SingletonDnsServerAddresses(InetSocketAddress address) {
        this.address = address;
        this.strVal = "singleton(" + address + ')';
    }

    public DnsServerAddressStream stream() {
        return this.stream;
    }

    public String toString() {
        return this.strVal;
    }
}
