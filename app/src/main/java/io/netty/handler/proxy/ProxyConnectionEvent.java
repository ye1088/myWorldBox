package io.netty.handler.proxy;

import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;

public final class ProxyConnectionEvent {
    private final String authScheme;
    private final SocketAddress destinationAddress;
    private final String protocol;
    private final SocketAddress proxyAddress;
    private String strVal;

    public ProxyConnectionEvent(String protocol, String authScheme, SocketAddress proxyAddress, SocketAddress destinationAddress) {
        if (protocol == null) {
            throw new NullPointerException("protocol");
        } else if (authScheme == null) {
            throw new NullPointerException("authScheme");
        } else if (proxyAddress == null) {
            throw new NullPointerException("proxyAddress");
        } else if (destinationAddress == null) {
            throw new NullPointerException("destinationAddress");
        } else {
            this.protocol = protocol;
            this.authScheme = authScheme;
            this.proxyAddress = proxyAddress;
            this.destinationAddress = destinationAddress;
        }
    }

    public String protocol() {
        return this.protocol;
    }

    public String authScheme() {
        return this.authScheme;
    }

    public <T extends SocketAddress> T proxyAddress() {
        return this.proxyAddress;
    }

    public <T extends SocketAddress> T destinationAddress() {
        return this.destinationAddress;
    }

    public String toString() {
        if (this.strVal != null) {
            return this.strVal;
        }
        String str = StringUtil.simpleClassName((Object) this) + '(' + this.protocol + ", " + this.authScheme + ", " + this.proxyAddress + " => " + this.destinationAddress + ')';
        this.strVal = str;
        return str;
    }
}
