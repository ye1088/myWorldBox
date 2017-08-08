package io.netty.resolver.dns;

import com.MCWorld.data.profile.a;
import io.netty.channel.EventLoop;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class DefaultDnsCache implements DnsCache {
    static final /* synthetic */ boolean $assertionsDisabled = (!DefaultDnsCache.class.desiredAssertionStatus());
    private final int maxTtl;
    private final int minTtl;
    private final int negativeTtl;
    private final ConcurrentMap<String, List<DnsCacheEntry>> resolveCache;

    public DefaultDnsCache() {
        this(0, Integer.MAX_VALUE, 0);
    }

    public DefaultDnsCache(int minTtl, int maxTtl, int negativeTtl) {
        this.resolveCache = PlatformDependent.newConcurrentHashMap();
        this.minTtl = ObjectUtil.checkPositiveOrZero(minTtl, "minTtl");
        this.maxTtl = ObjectUtil.checkPositiveOrZero(maxTtl, "maxTtl");
        if (minTtl > maxTtl) {
            throw new IllegalArgumentException("minTtl: " + minTtl + ", maxTtl: " + maxTtl + " (expected: 0 <= minTtl <= maxTtl)");
        }
        this.negativeTtl = ObjectUtil.checkPositiveOrZero(negativeTtl, "negativeTtl");
    }

    public int minTtl() {
        return this.minTtl;
    }

    public int maxTtl() {
        return this.maxTtl;
    }

    public int negativeTtl() {
        return this.negativeTtl;
    }

    public void clear() {
        Iterator<Entry<String, List<DnsCacheEntry>>> i = this.resolveCache.entrySet().iterator();
        while (i.hasNext()) {
            Entry<String, List<DnsCacheEntry>> e = (Entry) i.next();
            i.remove();
            cancelExpiration((List) e.getValue());
        }
    }

    public boolean clear(String hostname) {
        ObjectUtil.checkNotNull(hostname, "hostname");
        boolean removed = false;
        Iterator<Entry<String, List<DnsCacheEntry>>> i = this.resolveCache.entrySet().iterator();
        while (i.hasNext()) {
            Entry<String, List<DnsCacheEntry>> e = (Entry) i.next();
            if (((String) e.getKey()).equals(hostname)) {
                i.remove();
                cancelExpiration((List) e.getValue());
                removed = true;
            }
        }
        return removed;
    }

    public List<DnsCacheEntry> get(String hostname) {
        ObjectUtil.checkNotNull(hostname, "hostname");
        return (List) this.resolveCache.get(hostname);
    }

    private List<DnsCacheEntry> cachedEntries(String hostname) {
        List<DnsCacheEntry> oldEntries = (List) this.resolveCache.get(hostname);
        if (oldEntries != null) {
            return oldEntries;
        }
        List<DnsCacheEntry> newEntries = new ArrayList(8);
        oldEntries = (List) this.resolveCache.putIfAbsent(hostname, newEntries);
        if (oldEntries != null) {
            return oldEntries;
        }
        return newEntries;
    }

    public void cache(String hostname, InetAddress address, long originalTtl, EventLoop loop) {
        if (this.maxTtl != 0) {
            ObjectUtil.checkNotNull(hostname, "hostname");
            ObjectUtil.checkNotNull(address, a.qf);
            ObjectUtil.checkNotNull(loop, "loop");
            int ttl = Math.max(this.minTtl, (int) Math.min((long) this.maxTtl, originalTtl));
            List<DnsCacheEntry> entries = cachedEntries(hostname);
            DnsCacheEntry e = new DnsCacheEntry(hostname, address);
            synchronized (entries) {
                if (!entries.isEmpty()) {
                    DnsCacheEntry firstEntry = (DnsCacheEntry) entries.get(0);
                    if (firstEntry.cause() != null) {
                        if ($assertionsDisabled || entries.size() == 1) {
                            firstEntry.cancelExpiration();
                            entries.clear();
                        } else {
                            throw new AssertionError();
                        }
                    }
                }
                entries.add(e);
            }
            scheduleCacheExpiration(entries, e, ttl, loop);
        }
    }

    public void cache(String hostname, Throwable cause, EventLoop loop) {
        if (this.negativeTtl != 0) {
            ObjectUtil.checkNotNull(hostname, "hostname");
            ObjectUtil.checkNotNull(cause, "cause");
            ObjectUtil.checkNotNull(loop, "loop");
            List<DnsCacheEntry> entries = cachedEntries(hostname);
            DnsCacheEntry e = new DnsCacheEntry(hostname, cause);
            synchronized (entries) {
                int numEntries = entries.size();
                for (int i = 0; i < numEntries; i++) {
                    ((DnsCacheEntry) entries.get(i)).cancelExpiration();
                }
                entries.clear();
                entries.add(e);
            }
            scheduleCacheExpiration(entries, e, this.negativeTtl, loop);
        }
    }

    private static void cancelExpiration(List<DnsCacheEntry> entries) {
        int numEntries = entries.size();
        for (int i = 0; i < numEntries; i++) {
            ((DnsCacheEntry) entries.get(i)).cancelExpiration();
        }
    }

    private void scheduleCacheExpiration(final List<DnsCacheEntry> entries, final DnsCacheEntry e, int ttl, EventLoop loop) {
        e.scheduleExpiration(loop, new Runnable() {
            public void run() {
                synchronized (entries) {
                    entries.remove(e);
                    if (entries.isEmpty()) {
                        DefaultDnsCache.this.resolveCache.remove(e.hostname());
                    }
                }
            }
        }, (long) ttl, TimeUnit.SECONDS);
    }

    public String toString() {
        return "DefaultDnsCache(minTtl=" + this.minTtl + ", maxTtl=" + this.maxTtl + ", negativeTtl=" + this.negativeTtl + ", cached resolved hostname=" + this.resolveCache.size() + ")";
    }
}
