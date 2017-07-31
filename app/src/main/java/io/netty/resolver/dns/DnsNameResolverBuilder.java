package io.netty.resolver.dns;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public final class DnsNameResolverBuilder {
    private ChannelFactory<? extends DatagramChannel> channelFactory;
    private final EventLoop eventLoop;
    private HostsFileEntriesResolver hostsFileEntriesResolver = HostsFileEntriesResolver.DEFAULT;
    private int maxPayloadSize = 4096;
    private int maxQueriesPerResolve = 16;
    private Integer maxTtl;
    private Integer minTtl;
    private DnsServerAddresses nameServerAddresses = DnsServerAddresses.defaultAddresses();
    private int ndots = 1;
    private Integer negativeTtl;
    private boolean optResourceEnabled = true;
    private long queryTimeoutMillis = 5000;
    private boolean recursionDesired = true;
    private DnsCache resolveCache;
    private InternetProtocolFamily[] resolvedAddressTypes = DnsNameResolver.DEFAULT_RESOLVE_ADDRESS_TYPES;
    private String[] searchDomains = DnsNameResolver.DEFAULT_SEACH_DOMAINS;
    private boolean traceEnabled;

    public DnsNameResolverBuilder(EventLoop eventLoop) {
        this.eventLoop = eventLoop;
    }

    public DnsNameResolverBuilder channelFactory(ChannelFactory<? extends DatagramChannel> channelFactory) {
        this.channelFactory = channelFactory;
        return this;
    }

    public DnsNameResolverBuilder channelType(Class<? extends DatagramChannel> channelType) {
        return channelFactory(new ReflectiveChannelFactory(channelType));
    }

    public DnsNameResolverBuilder nameServerAddresses(DnsServerAddresses nameServerAddresses) {
        this.nameServerAddresses = nameServerAddresses;
        return this;
    }

    public DnsNameResolverBuilder resolveCache(DnsCache resolveCache) {
        this.resolveCache = resolveCache;
        return this;
    }

    public DnsNameResolverBuilder ttl(int minTtl, int maxTtl) {
        this.maxTtl = Integer.valueOf(maxTtl);
        this.minTtl = Integer.valueOf(minTtl);
        return this;
    }

    public DnsNameResolverBuilder negativeTtl(int negativeTtl) {
        this.negativeTtl = Integer.valueOf(negativeTtl);
        return this;
    }

    public DnsNameResolverBuilder queryTimeoutMillis(long queryTimeoutMillis) {
        this.queryTimeoutMillis = queryTimeoutMillis;
        return this;
    }

    public DnsNameResolverBuilder resolvedAddressTypes(InternetProtocolFamily... resolvedAddressTypes) {
        ObjectUtil.checkNotNull(resolvedAddressTypes, "resolvedAddressTypes");
        List<InternetProtocolFamily> list = InternalThreadLocalMap.get().arrayList(InternetProtocolFamily.values().length);
        for (InternetProtocolFamily f : resolvedAddressTypes) {
            if (f == null) {
                break;
            }
            if (!list.contains(f)) {
                list.add(f);
            }
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("no protocol family specified");
        }
        this.resolvedAddressTypes = (InternetProtocolFamily[]) list.toArray(new InternetProtocolFamily[list.size()]);
        return this;
    }

    public DnsNameResolverBuilder resolvedAddressTypes(Iterable<InternetProtocolFamily> resolvedAddressTypes) {
        ObjectUtil.checkNotNull(resolvedAddressTypes, "resolveAddressTypes");
        List<InternetProtocolFamily> list = InternalThreadLocalMap.get().arrayList(InternetProtocolFamily.values().length);
        for (InternetProtocolFamily f : resolvedAddressTypes) {
            if (f == null) {
                break;
            } else if (!list.contains(f)) {
                list.add(f);
            }
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("no protocol family specified");
        }
        this.resolvedAddressTypes = (InternetProtocolFamily[]) list.toArray(new InternetProtocolFamily[list.size()]);
        return this;
    }

    public DnsNameResolverBuilder recursionDesired(boolean recursionDesired) {
        this.recursionDesired = recursionDesired;
        return this;
    }

    public DnsNameResolverBuilder maxQueriesPerResolve(int maxQueriesPerResolve) {
        this.maxQueriesPerResolve = maxQueriesPerResolve;
        return this;
    }

    public DnsNameResolverBuilder traceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
        return this;
    }

    public DnsNameResolverBuilder maxPayloadSize(int maxPayloadSize) {
        this.maxPayloadSize = maxPayloadSize;
        return this;
    }

    public DnsNameResolverBuilder optResourceEnabled(boolean optResourceEnabled) {
        this.optResourceEnabled = optResourceEnabled;
        return this;
    }

    public DnsNameResolverBuilder hostsFileEntriesResolver(HostsFileEntriesResolver hostsFileEntriesResolver) {
        this.hostsFileEntriesResolver = hostsFileEntriesResolver;
        return this;
    }

    public DnsNameResolverBuilder searchDomains(Iterable<String> searchDomains) {
        ObjectUtil.checkNotNull(searchDomains, "searchDomains");
        List<String> list = InternalThreadLocalMap.get().arrayList(4);
        for (String f : searchDomains) {
            if (f == null) {
                break;
            } else if (!list.contains(f)) {
                list.add(f);
            }
        }
        this.searchDomains = (String[]) list.toArray(new String[list.size()]);
        return this;
    }

    public DnsNameResolverBuilder ndots(int ndots) {
        this.ndots = ndots;
        return this;
    }

    public DnsNameResolver build() {
        if (this.resolveCache == null || (this.minTtl == null && this.maxTtl == null && this.negativeTtl == null)) {
            return new DnsNameResolver(this.eventLoop, this.channelFactory, this.nameServerAddresses, this.resolveCache != null ? this.resolveCache : new DefaultDnsCache(ObjectUtil.intValue(this.minTtl, 0), ObjectUtil.intValue(this.maxTtl, Integer.MAX_VALUE), ObjectUtil.intValue(this.negativeTtl, 0)), this.queryTimeoutMillis, this.resolvedAddressTypes, this.recursionDesired, this.maxQueriesPerResolve, this.traceEnabled, this.maxPayloadSize, this.optResourceEnabled, this.hostsFileEntriesResolver, this.searchDomains, this.ndots);
        }
        throw new IllegalStateException("resolveCache and TTLs are mutually exclusive");
    }
}
