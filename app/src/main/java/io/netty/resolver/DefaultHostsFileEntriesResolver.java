package io.netty.resolver;

import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

public final class DefaultHostsFileEntriesResolver implements HostsFileEntriesResolver {
    private final Map<String, InetAddress> entries = HostsFileParser.parseSilently();

    public InetAddress address(String inetHost) {
        return (InetAddress) this.entries.get(inetHost.toLowerCase(Locale.ENGLISH));
    }
}
