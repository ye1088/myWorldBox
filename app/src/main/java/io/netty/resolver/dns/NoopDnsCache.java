package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

public final class NoopDnsCache implements DnsCache {
    public static final NoopDnsCache INSTANCE = new NoopDnsCache();

    private NoopDnsCache() {
    }

    public void clear() {
    }

    public boolean clear(String hostname) {
        return false;
    }

    public List<DnsCacheEntry> get(String hostname) {
        return Collections.emptyList();
    }

    public void cache(String hostname, InetAddress address, long originalTtl, EventLoop loop) {
    }

    public void cache(String hostname, Throwable cause, EventLoop loop) {
    }

    public String toString() {
        return NoopDnsCache.class.getSimpleName();
    }
}
