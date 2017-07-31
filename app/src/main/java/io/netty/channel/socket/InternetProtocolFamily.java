package io.netty.channel.socket;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

public enum InternetProtocolFamily {
    IPv4(Inet4Address.class),
    IPv6(Inet6Address.class);
    
    private final Class<? extends InetAddress> addressType;

    private InternetProtocolFamily(Class<? extends InetAddress> addressType) {
        this.addressType = addressType;
    }

    public Class<? extends InetAddress> addressType() {
        return this.addressType;
    }
}
