package io.netty.resolver.dns;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.dns.DatagramDnsQueryEncoder;
import io.netty.handler.codec.dns.DatagramDnsResponse;
import io.netty.handler.codec.dns.DatagramDnsResponseDecoder;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.resolver.InetNameResolver;
import io.netty.util.NetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.IDN;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DnsNameResolver extends InetNameResolver {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final DatagramDnsResponseDecoder DECODER = new DatagramDnsResponseDecoder();
    static final InternetProtocolFamily[] DEFAULT_RESOLVE_ADDRESS_TYPES;
    static final String[] DEFAULT_SEACH_DOMAINS;
    private static final DatagramDnsQueryEncoder ENCODER = new DatagramDnsQueryEncoder();
    private static final String LOCALHOST = "localhost";
    private static final InetAddress LOCALHOST_ADDRESS;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DnsNameResolver.class);
    final DatagramChannel ch;
    final Future<Channel> channelFuture;
    private final HostsFileEntriesResolver hostsFileEntriesResolver;
    private final int maxPayloadSize;
    private final int maxQueriesPerResolve;
    private final FastThreadLocal<DnsServerAddressStream> nameServerAddrStream = new FastThreadLocal<DnsServerAddressStream>() {
        protected DnsServerAddressStream initialValue() throws Exception {
            return DnsNameResolver.this.nameServerAddresses.stream();
        }
    };
    final DnsServerAddresses nameServerAddresses;
    private final int ndots;
    private final boolean optResourceEnabled;
    final DnsQueryContextManager queryContextManager = new DnsQueryContextManager();
    private final long queryTimeoutMillis;
    private final boolean recursionDesired;
    private final DnsCache resolveCache;
    private final InternetProtocolFamily[] resolvedAddressTypes;
    private final String[] searchDomains;
    private final boolean traceEnabled;

    private final class DnsResponseHandler extends ChannelInboundHandlerAdapter {
        private final Promise<Channel> channelActivePromise;

        DnsResponseHandler(Promise<Channel> channelActivePromise) {
            this.channelActivePromise = channelActivePromise;
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            try {
                DatagramDnsResponse res = (DatagramDnsResponse) msg;
                int queryId = res.id();
                if (DnsNameResolver.logger.isDebugEnabled()) {
                    DnsNameResolver.logger.debug("{} RECEIVED: [{}: {}], {}", DnsNameResolver.this.ch, Integer.valueOf(queryId), res.sender(), res);
                }
                DnsQueryContext qCtx = DnsNameResolver.this.queryContextManager.get(res.sender(), queryId);
                if (qCtx == null) {
                    DnsNameResolver.logger.warn("{} Received a_isRightVersion DNS response with an unknown ID: {}", DnsNameResolver.this.ch, Integer.valueOf(queryId));
                    return;
                }
                qCtx.finish(res);
                ReferenceCountUtil.safeRelease(msg);
            } finally {
                ReferenceCountUtil.safeRelease(msg);
            }
        }

        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            this.channelActivePromise.setSuccess(ctx.channel());
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            DnsNameResolver.logger.warn("{} Unexpected exception: ", DnsNameResolver.this.ch, cause);
        }
    }

    final class ListResolverContext extends DnsNameResolverContext<List<InetAddress>> {
        ListResolverContext(DnsNameResolver parent, String hostname, DnsCache resolveCache) {
            super(parent, hostname, resolveCache);
        }

        DnsNameResolverContext<List<InetAddress>> newResolverContext(DnsNameResolver parent, String hostname, DnsCache resolveCache) {
            return new ListResolverContext(parent, hostname, resolveCache);
        }

        boolean finishResolve(Class<? extends InetAddress> addressType, List<DnsCacheEntry> resolvedEntries, Promise<List<InetAddress>> promise) {
            List<InetAddress> result = null;
            int numEntries = resolvedEntries.size();
            for (int i = 0; i < numEntries; i++) {
                InetAddress a = ((DnsCacheEntry) resolvedEntries.get(i)).address();
                if (addressType.isInstance(a)) {
                    if (result == null) {
                        result = new ArrayList(numEntries);
                    }
                    result.add(a);
                }
            }
            if (result == null) {
                return false;
            }
            promise.trySuccess(result);
            return true;
        }
    }

    final class SingleResolverContext extends DnsNameResolverContext<InetAddress> {
        SingleResolverContext(DnsNameResolver parent, String hostname, DnsCache resolveCache) {
            super(parent, hostname, resolveCache);
        }

        DnsNameResolverContext<InetAddress> newResolverContext(DnsNameResolver parent, String hostname, DnsCache resolveCache) {
            return new SingleResolverContext(parent, hostname, resolveCache);
        }

        boolean finishResolve(Class<? extends InetAddress> addressType, List<DnsCacheEntry> resolvedEntries, Promise<InetAddress> promise) {
            int numEntries = resolvedEntries.size();
            for (int i = 0; i < numEntries; i++) {
                InetAddress a = ((DnsCacheEntry) resolvedEntries.get(i)).address();
                if (addressType.isInstance(a)) {
                    DnsNameResolver.setSuccess(promise, a);
                    return true;
                }
            }
            return false;
        }
    }

    static {
        boolean z;
        String[] searchDomains;
        if (DnsNameResolver.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        if (NetUtil.isIpV4StackPreferred()) {
            DEFAULT_RESOLVE_ADDRESS_TYPES = new InternetProtocolFamily[]{InternetProtocolFamily.IPv4};
            LOCALHOST_ADDRESS = NetUtil.LOCALHOST4;
        } else {
            DEFAULT_RESOLVE_ADDRESS_TYPES = new InternetProtocolFamily[2];
            if (NetUtil.isIpV6AddressesPreferred()) {
                DEFAULT_RESOLVE_ADDRESS_TYPES[0] = InternetProtocolFamily.IPv6;
                DEFAULT_RESOLVE_ADDRESS_TYPES[1] = InternetProtocolFamily.IPv4;
                LOCALHOST_ADDRESS = NetUtil.LOCALHOST6;
            } else {
                DEFAULT_RESOLVE_ADDRESS_TYPES[0] = InternetProtocolFamily.IPv4;
                DEFAULT_RESOLVE_ADDRESS_TYPES[1] = InternetProtocolFamily.IPv6;
                LOCALHOST_ADDRESS = NetUtil.LOCALHOST4;
            }
        }
        try {
            Class<?> configClass = Class.forName("sun.net.dns.ResolverConfiguration");
            List<String> list = (List) configClass.getMethod("searchlist", new Class[0]).invoke(configClass.getMethod("open", new Class[0]).invoke(null, new Object[0]), new Object[0]);
            searchDomains = (String[]) list.toArray(new String[list.size()]);
        } catch (Exception e) {
            searchDomains = EmptyArrays.EMPTY_STRINGS;
        }
        DEFAULT_SEACH_DOMAINS = searchDomains;
    }

    public DnsNameResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddresses nameServerAddresses, final DnsCache resolveCache, long queryTimeoutMillis, InternetProtocolFamily[] resolvedAddressTypes, boolean recursionDesired, int maxQueriesPerResolve, boolean traceEnabled, int maxPayloadSize, boolean optResourceEnabled, HostsFileEntriesResolver hostsFileEntriesResolver, String[] searchDomains, int ndots) {
        super(eventLoop);
        ObjectUtil.checkNotNull(channelFactory, "channelFactory");
        this.nameServerAddresses = (DnsServerAddresses) ObjectUtil.checkNotNull(nameServerAddresses, "nameServerAddresses");
        this.queryTimeoutMillis = ObjectUtil.checkPositive(queryTimeoutMillis, "queryTimeoutMillis");
        this.resolvedAddressTypes = (InternetProtocolFamily[]) ObjectUtil.checkNonEmpty(resolvedAddressTypes, "resolvedAddressTypes");
        this.recursionDesired = recursionDesired;
        this.maxQueriesPerResolve = ObjectUtil.checkPositive(maxQueriesPerResolve, "maxQueriesPerResolve");
        this.traceEnabled = traceEnabled;
        this.maxPayloadSize = ObjectUtil.checkPositive(maxPayloadSize, "maxPayloadSize");
        this.optResourceEnabled = optResourceEnabled;
        this.hostsFileEntriesResolver = (HostsFileEntriesResolver) ObjectUtil.checkNotNull(hostsFileEntriesResolver, "hostsFileEntriesResolver");
        this.resolveCache = resolveCache;
        this.searchDomains = (String[]) ((String[]) ObjectUtil.checkNotNull(searchDomains, "searchDomains")).clone();
        this.ndots = ObjectUtil.checkPositiveOrZero(ndots, "ndots");
        Bootstrap b = new Bootstrap();
        b.group(executor());
        b.channelFactory(channelFactory);
        b.option(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, Boolean.valueOf(true));
        final DnsResponseHandler responseHandler = new DnsResponseHandler(executor().newPromise());
        b.handler(new ChannelInitializer<DatagramChannel>() {
            protected void initChannel(DatagramChannel ch) throws Exception {
                ch.pipeline().addLast(new ChannelHandler[]{DnsNameResolver.DECODER, DnsNameResolver.ENCODER, responseHandler});
            }
        });
        this.channelFuture = responseHandler.channelActivePromise;
        this.ch = (DatagramChannel) b.register().channel();
        this.ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(maxPayloadSize));
        this.ch.closeFuture().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                resolveCache.clear();
            }
        });
    }

    public DnsCache resolveCache() {
        return this.resolveCache;
    }

    public long queryTimeoutMillis() {
        return this.queryTimeoutMillis;
    }

    public List<InternetProtocolFamily> resolvedAddressTypes() {
        return Arrays.asList(this.resolvedAddressTypes);
    }

    InternetProtocolFamily[] resolveAddressTypesUnsafe() {
        return this.resolvedAddressTypes;
    }

    final String[] searchDomains() {
        return this.searchDomains;
    }

    final int ndots() {
        return this.ndots;
    }

    public boolean isRecursionDesired() {
        return this.recursionDesired;
    }

    public int maxQueriesPerResolve() {
        return this.maxQueriesPerResolve;
    }

    public boolean isTraceEnabled() {
        return this.traceEnabled;
    }

    public int maxPayloadSize() {
        return this.maxPayloadSize;
    }

    public boolean isOptResourceEnabled() {
        return this.optResourceEnabled;
    }

    public HostsFileEntriesResolver hostsFileEntriesResolver() {
        return this.hostsFileEntriesResolver;
    }

    public void close() {
        if (this.ch.isOpen()) {
            this.ch.close();
        }
    }

    protected EventLoop executor() {
        return (EventLoop) super.executor();
    }

    private InetAddress resolveHostsFileEntry(String hostname) {
        if (this.hostsFileEntriesResolver == null) {
            return null;
        }
        InetAddress address = this.hostsFileEntriesResolver.address(hostname);
        if (address == null && PlatformDependent.isWindows() && "localhost".equalsIgnoreCase(hostname)) {
            return LOCALHOST_ADDRESS;
        }
        return address;
    }

    protected void doResolve(String inetHost, Promise<InetAddress> promise) throws Exception {
        doResolve(inetHost, promise, this.resolveCache);
    }

    protected void doResolve(String inetHost, Promise<InetAddress> promise, DnsCache resolveCache) throws Exception {
        byte[] bytes = NetUtil.createByteArrayFromIpAddressString(inetHost);
        if (bytes != null) {
            promise.setSuccess(InetAddress.getByAddress(bytes));
            return;
        }
        String hostname = hostname(inetHost);
        InetAddress hostsFileEntry = resolveHostsFileEntry(hostname);
        if (hostsFileEntry != null) {
            promise.setSuccess(hostsFileEntry);
        } else if (!doResolveCached(hostname, promise, resolveCache)) {
            doResolveUncached(hostname, promise, resolveCache);
        }
    }

    private boolean doResolveCached(String hostname, Promise<InetAddress> promise, DnsCache resolveCache) {
        List<DnsCacheEntry> cachedEntries = resolveCache.get(hostname);
        if (cachedEntries == null || cachedEntries.isEmpty()) {
            return false;
        }
        InetAddress address = null;
        Throwable cause = null;
        synchronized (cachedEntries) {
            int numEntries = cachedEntries.size();
            if ($assertionsDisabled || numEntries > 0) {
                if (((DnsCacheEntry) cachedEntries.get(0)).cause() != null) {
                    cause = ((DnsCacheEntry) cachedEntries.get(0)).cause();
                } else {
                    for (InternetProtocolFamily f : this.resolvedAddressTypes) {
                        for (int i = 0; i < numEntries; i++) {
                            DnsCacheEntry e = (DnsCacheEntry) cachedEntries.get(i);
                            if (f.addressType().isInstance(e.address())) {
                                address = e.address();
                                break;
                            }
                        }
                    }
                }
            } else {
                throw new AssertionError();
            }
        }
        if (address != null) {
            setSuccess(promise, address);
        } else if (cause == null) {
            return false;
        } else {
            if (!promise.tryFailure(cause)) {
                logger.warn("Failed to notify failure to a_isRightVersion promise: {}", promise, cause);
            }
        }
        return true;
    }

    private static void setSuccess(Promise<InetAddress> promise, InetAddress result) {
        if (!promise.trySuccess(result)) {
            logger.warn("Failed to notify success ({}) to a_isRightVersion promise: {}", result, promise);
        }
    }

    private void doResolveUncached(String hostname, Promise<InetAddress> promise, DnsCache resolveCache) {
        new SingleResolverContext(this, hostname, resolveCache).resolve(promise);
    }

    protected void doResolveAll(String inetHost, Promise<List<InetAddress>> promise) throws Exception {
        doResolveAll(inetHost, promise, this.resolveCache);
    }

    protected void doResolveAll(String inetHost, Promise<List<InetAddress>> promise, DnsCache resolveCache) throws Exception {
        byte[] bytes = NetUtil.createByteArrayFromIpAddressString(inetHost);
        if (bytes != null) {
            promise.setSuccess(Collections.singletonList(InetAddress.getByAddress(bytes)));
            return;
        }
        String hostname = hostname(inetHost);
        InetAddress hostsFileEntry = resolveHostsFileEntry(hostname);
        if (hostsFileEntry != null) {
            promise.setSuccess(Collections.singletonList(hostsFileEntry));
        } else if (!doResolveAllCached(hostname, promise, resolveCache)) {
            doResolveAllUncached(hostname, promise, resolveCache);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean doResolveAllCached(java.lang.String r15, io.netty.util.concurrent.Promise<java.util.List<java.net.InetAddress>> r16, io.netty.resolver.dns.DnsCache r17) {
        /*
        r14 = this;
        r0 = r17;
        r2 = r0.get(r15);
        if (r2 == 0) goto L_0x000e;
    L_0x0008:
        r12 = r2.isEmpty();
        if (r12 == 0) goto L_0x0010;
    L_0x000e:
        r12 = 0;
    L_0x000f:
        return r12;
    L_0x0010:
        r10 = 0;
        r3 = 0;
        monitor-enter(r2);
        r9 = r2.size();	 Catch:{ all -> 0x0023 }
        r12 = $assertionsDisabled;	 Catch:{ all -> 0x0023 }
        if (r12 != 0) goto L_0x0026;
    L_0x001b:
        if (r9 > 0) goto L_0x0026;
    L_0x001d:
        r12 = new java.lang.AssertionError;	 Catch:{ all -> 0x0023 }
        r12.<init>();	 Catch:{ all -> 0x0023 }
        throw r12;	 Catch:{ all -> 0x0023 }
    L_0x0023:
        r12 = move-exception;
    L_0x0024:
        monitor-exit(r2);	 Catch:{ all -> 0x0023 }
        throw r12;
    L_0x0026:
        r12 = 0;
        r12 = r2.get(r12);	 Catch:{ all -> 0x0023 }
        r12 = (io.netty.resolver.dns.DnsCacheEntry) r12;	 Catch:{ all -> 0x0023 }
        r12 = r12.cause();	 Catch:{ all -> 0x0023 }
        if (r12 == 0) goto L_0x0048;
    L_0x0033:
        r12 = 0;
        r12 = r2.get(r12);	 Catch:{ all -> 0x0023 }
        r12 = (io.netty.resolver.dns.DnsCacheEntry) r12;	 Catch:{ all -> 0x0023 }
        r3 = r12.cause();	 Catch:{ all -> 0x0023 }
    L_0x003e:
        monitor-exit(r2);	 Catch:{ all -> 0x0023 }
        if (r10 == 0) goto L_0x007e;
    L_0x0041:
        r0 = r16;
        r0.trySuccess(r10);
    L_0x0046:
        r12 = 1;
        goto L_0x000f;
    L_0x0048:
        r1 = r14.resolvedAddressTypes;	 Catch:{ all -> 0x0023 }
        r8 = r1.length;	 Catch:{ all -> 0x0023 }
        r7 = 0;
    L_0x004c:
        if (r7 >= r8) goto L_0x003e;
    L_0x004e:
        r5 = r1[r7];	 Catch:{ all -> 0x0023 }
        r6 = 0;
        r11 = r10;
    L_0x0052:
        if (r6 >= r9) goto L_0x007a;
    L_0x0054:
        r4 = r2.get(r6);	 Catch:{ all -> 0x0088 }
        r4 = (io.netty.resolver.dns.DnsCacheEntry) r4;	 Catch:{ all -> 0x0088 }
        r12 = r5.addressType();	 Catch:{ all -> 0x0088 }
        r13 = r4.address();	 Catch:{ all -> 0x0088 }
        r12 = r12.isInstance(r13);	 Catch:{ all -> 0x0088 }
        if (r12 == 0) goto L_0x008d;
    L_0x0068:
        if (r11 != 0) goto L_0x008b;
    L_0x006a:
        r10 = new java.util.ArrayList;	 Catch:{ all -> 0x0088 }
        r10.<init>(r9);	 Catch:{ all -> 0x0088 }
    L_0x006f:
        r12 = r4.address();	 Catch:{ all -> 0x0023 }
        r10.add(r12);	 Catch:{ all -> 0x0023 }
    L_0x0076:
        r6 = r6 + 1;
        r11 = r10;
        goto L_0x0052;
    L_0x007a:
        r7 = r7 + 1;
        r10 = r11;
        goto L_0x004c;
    L_0x007e:
        if (r3 == 0) goto L_0x0086;
    L_0x0080:
        r0 = r16;
        r0.tryFailure(r3);
        goto L_0x0046;
    L_0x0086:
        r12 = 0;
        goto L_0x000f;
    L_0x0088:
        r12 = move-exception;
        r10 = r11;
        goto L_0x0024;
    L_0x008b:
        r10 = r11;
        goto L_0x006f;
    L_0x008d:
        r10 = r11;
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.resolver.dns.DnsNameResolver.doResolveAllCached(java.lang.String, io.netty.util.concurrent.Promise, io.netty.resolver.dns.DnsCache):boolean");
    }

    private void doResolveAllUncached(String hostname, Promise<List<InetAddress>> promise, DnsCache resolveCache) {
        new ListResolverContext(this, hostname, resolveCache).resolve(promise);
    }

    private static String hostname(String inetHost) {
        String hostname = IDN.toASCII(inetHost);
        if (!StringUtil.endsWith(inetHost, '.') || StringUtil.endsWith(hostname, '.')) {
            return hostname;
        }
        return hostname + ".";
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion question) {
        return query(nextNameServerAddress(), question);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion question, Iterable<DnsRecord> additional) {
        return query(nextNameServerAddress(), question, (Iterable) additional);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion question, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return query(nextNameServerAddress(), question, Collections.emptyList(), promise);
    }

    private InetSocketAddress nextNameServerAddress() {
        return ((DnsServerAddressStream) this.nameServerAddrStream.get()).next();
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress nameServerAddr, DnsQuestion question) {
        return query0(nameServerAddr, question, Collections.emptyList(), this.ch.eventLoop().newPromise());
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress nameServerAddr, DnsQuestion question, Iterable<DnsRecord> additional) {
        return query0(nameServerAddr, question, additional, this.ch.eventLoop().newPromise());
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress nameServerAddr, DnsQuestion question, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return query0(nameServerAddr, question, Collections.emptyList(), promise);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress nameServerAddr, DnsQuestion question, Iterable<DnsRecord> additional, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return query0(nameServerAddr, question, additional, promise);
    }

    private Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query0(InetSocketAddress nameServerAddr, DnsQuestion question, Iterable<DnsRecord> additional, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> castPromise = cast((Promise) ObjectUtil.checkNotNull(promise, "promise"));
        try {
            new DnsQueryContext(this, nameServerAddr, question, additional, castPromise).query();
            return castPromise;
        } catch (Exception e) {
            return castPromise.setFailure(e);
        }
    }

    private static Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> cast(Promise<?> promise) {
        return promise;
    }
}
