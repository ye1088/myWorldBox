package io.netty.resolver.dns;

import io.netty.util.collection.IntObjectMap;
import io.netty.util.internal.ThreadLocalRandom;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

final class DnsQueryContextManager {
    final Map<InetSocketAddress, IntObjectMap<DnsQueryContext>> map = new HashMap();

    DnsQueryContextManager() {
    }

    int add(DnsQueryContext qCtx) {
        IntObjectMap<DnsQueryContext> contexts = getOrCreateContextMap(qCtx.nameServerAddr());
        int id = ThreadLocalRandom.current().nextInt(1, 65536);
        int tries = 0;
        synchronized (contexts) {
            do {
                if (contexts.containsKey(id)) {
                    id = (id + 1) & 65535;
                    tries++;
                } else {
                    contexts.put(id, qCtx);
                }
            } while (tries < 131070);
            throw new IllegalStateException("query ID space exhausted: " + qCtx.question());
        }
        return id;
    }

    DnsQueryContext get(InetSocketAddress nameServerAddr, int id) {
        IntObjectMap<DnsQueryContext> contexts = getContextMap(nameServerAddr);
        if (contexts == null) {
            return null;
        }
        DnsQueryContext qCtx;
        synchronized (contexts) {
            qCtx = (DnsQueryContext) contexts.get(id);
        }
        return qCtx;
    }

    DnsQueryContext remove(InetSocketAddress nameServerAddr, int id) {
        IntObjectMap<DnsQueryContext> contexts = getContextMap(nameServerAddr);
        if (contexts == null) {
            return null;
        }
        DnsQueryContext dnsQueryContext;
        synchronized (contexts) {
            dnsQueryContext = (DnsQueryContext) contexts.remove(id);
        }
        return dnsQueryContext;
    }

    private IntObjectMap<DnsQueryContext> getContextMap(InetSocketAddress nameServerAddr) {
        IntObjectMap<DnsQueryContext> intObjectMap;
        synchronized (this.map) {
            intObjectMap = (IntObjectMap) this.map.get(nameServerAddr);
        }
        return intObjectMap;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private io.netty.util.collection.IntObjectMap<io.netty.resolver.dns.DnsQueryContext> getOrCreateContextMap(java.net.InetSocketAddress r12) {
        /*
        r11 = this;
        r8 = r11.map;
        monitor-enter(r8);
        r7 = r11.map;	 Catch:{ all -> 0x004d }
        r4 = r7.get(r12);	 Catch:{ all -> 0x004d }
        r4 = (io.netty.util.collection.IntObjectMap) r4;	 Catch:{ all -> 0x004d }
        if (r4 == 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r8);	 Catch:{ all -> 0x004d }
    L_0x000e:
        return r4;
    L_0x000f:
        r5 = new io.netty.util.collection.IntObjectHashMap;	 Catch:{ all -> 0x004d }
        r5.<init>();	 Catch:{ all -> 0x004d }
        r1 = r12.getAddress();	 Catch:{ all -> 0x004d }
        r6 = r12.getPort();	 Catch:{ all -> 0x004d }
        r7 = r11.map;	 Catch:{ all -> 0x004d }
        r7.put(r12, r5);	 Catch:{ all -> 0x004d }
        r7 = r1 instanceof java.net.Inet4Address;	 Catch:{ all -> 0x004d }
        if (r7 == 0) goto L_0x0050;
    L_0x0025:
        r0 = r1;
        r0 = (java.net.Inet4Address) r0;	 Catch:{ all -> 0x004d }
        r2 = r0;
        r7 = r2.isLoopbackAddress();	 Catch:{ all -> 0x004d }
        if (r7 == 0) goto L_0x003e;
    L_0x002f:
        r7 = r11.map;	 Catch:{ all -> 0x004d }
        r9 = new java.net.InetSocketAddress;	 Catch:{ all -> 0x004d }
        r10 = io.netty.util.NetUtil.LOCALHOST6;	 Catch:{ all -> 0x004d }
        r9.<init>(r10, r6);	 Catch:{ all -> 0x004d }
        r7.put(r9, r5);	 Catch:{ all -> 0x004d }
    L_0x003b:
        monitor-exit(r8);	 Catch:{ all -> 0x004d }
        r4 = r5;
        goto L_0x000e;
    L_0x003e:
        r7 = r11.map;	 Catch:{ all -> 0x004d }
        r9 = new java.net.InetSocketAddress;	 Catch:{ all -> 0x004d }
        r10 = toCompatAddress(r2);	 Catch:{ all -> 0x004d }
        r9.<init>(r10, r6);	 Catch:{ all -> 0x004d }
        r7.put(r9, r5);	 Catch:{ all -> 0x004d }
        goto L_0x003b;
    L_0x004d:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x004d }
        throw r7;
    L_0x0050:
        r7 = r1 instanceof java.net.Inet6Address;	 Catch:{ all -> 0x004d }
        if (r7 == 0) goto L_0x003b;
    L_0x0054:
        r0 = r1;
        r0 = (java.net.Inet6Address) r0;	 Catch:{ all -> 0x004d }
        r3 = r0;
        r7 = r3.isLoopbackAddress();	 Catch:{ all -> 0x004d }
        if (r7 == 0) goto L_0x006b;
    L_0x005e:
        r7 = r11.map;	 Catch:{ all -> 0x004d }
        r9 = new java.net.InetSocketAddress;	 Catch:{ all -> 0x004d }
        r10 = io.netty.util.NetUtil.LOCALHOST4;	 Catch:{ all -> 0x004d }
        r9.<init>(r10, r6);	 Catch:{ all -> 0x004d }
        r7.put(r9, r5);	 Catch:{ all -> 0x004d }
        goto L_0x003b;
    L_0x006b:
        r7 = r3.isIPv4CompatibleAddress();	 Catch:{ all -> 0x004d }
        if (r7 == 0) goto L_0x003b;
    L_0x0071:
        r7 = r11.map;	 Catch:{ all -> 0x004d }
        r9 = new java.net.InetSocketAddress;	 Catch:{ all -> 0x004d }
        r10 = toIPv4Address(r3);	 Catch:{ all -> 0x004d }
        r9.<init>(r10, r6);	 Catch:{ all -> 0x004d }
        r7.put(r9, r5);	 Catch:{ all -> 0x004d }
        goto L_0x003b;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.resolver.dns.DnsQueryContextManager.getOrCreateContextMap(java.net.InetSocketAddress):io.netty.util.collection.IntObjectMap<io.netty.resolver.dns.DnsQueryContext>");
    }

    private static Inet6Address toCompatAddress(Inet4Address a4) {
        byte[] b4 = a4.getAddress();
        try {
            return (Inet6Address) InetAddress.getByAddress(new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, b4[0], b4[1], b4[2], b4[3]});
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
    }

    private static Inet4Address toIPv4Address(Inet6Address a6) {
        byte[] b6 = a6.getAddress();
        try {
            return (Inet4Address) InetAddress.getByAddress(new byte[]{b6[12], b6[13], b6[14], b6[15]});
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
    }
}
