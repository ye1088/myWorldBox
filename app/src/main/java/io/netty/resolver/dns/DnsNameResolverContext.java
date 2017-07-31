package io.netty.resolver.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.dns.DefaultDnsQuestion;
import io.netty.handler.codec.dns.DefaultDnsRecordDecoder;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.StringUtil;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

abstract class DnsNameResolverContext<T> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DnsNameResolverContext.class.desiredAssertionStatus());
    private static final int INADDRSZ4 = 4;
    private static final int INADDRSZ6 = 16;
    private static final FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>> RELEASE_RESPONSE = new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>() {
        public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
            if (future.isSuccess()) {
                ((AddressedEnvelope) future.getNow()).release();
            }
        }
    };
    private int allowedQueries;
    private final String hostname;
    private final int maxAllowedQueries;
    private final DnsServerAddressStream nameServerAddrs;
    private final DnsNameResolver parent;
    protected String pristineHostname;
    private final Set<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>> queriesInProgress = Collections.newSetFromMap(new IdentityHashMap());
    private final InternetProtocolFamily[] resolveAddressTypes;
    private final DnsCache resolveCache;
    private List<DnsCacheEntry> resolvedEntries;
    private StringBuilder trace;
    private final boolean traceEnabled;
    private boolean triedCNAME;

    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$channel$socket$InternetProtocolFamily = new int[InternetProtocolFamily.values().length];

        static {
            try {
                $SwitchMap$io$netty$channel$socket$InternetProtocolFamily[InternetProtocolFamily.IPv4.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$channel$socket$InternetProtocolFamily[InternetProtocolFamily.IPv6.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    abstract boolean finishResolve(Class<? extends InetAddress> cls, List<DnsCacheEntry> list, Promise<T> promise);

    abstract DnsNameResolverContext<T> newResolverContext(DnsNameResolver dnsNameResolver, String str, DnsCache dnsCache);

    protected DnsNameResolverContext(DnsNameResolver parent, String hostname, DnsCache resolveCache) {
        this.parent = parent;
        this.hostname = hostname;
        this.resolveCache = resolveCache;
        this.nameServerAddrs = parent.nameServerAddresses.stream();
        this.maxAllowedQueries = parent.maxQueriesPerResolve();
        this.resolveAddressTypes = parent.resolveAddressTypesUnsafe();
        this.traceEnabled = parent.isTraceEnabled();
        this.allowedQueries = this.maxAllowedQueries;
    }

    void resolve(Promise<T> promise) {
        boolean directSearch = this.parent.searchDomains().length == 0 || StringUtil.endsWith(this.hostname, '.');
        if (directSearch) {
            internalResolve(promise);
            return;
        }
        final Promise<T> original = promise;
        promise = this.parent.executor().newPromise();
        promise.addListener(new FutureListener<T>() {
            int count;

            public void operationComplete(Future<T> future) throws Exception {
                if (future.isSuccess()) {
                    original.trySuccess(future.getNow());
                } else if (this.count < DnsNameResolverContext.this.parent.searchDomains().length) {
                    String[] searchDomains = DnsNameResolverContext.this.parent.searchDomains();
                    int i = this.count;
                    this.count = i + 1;
                    String searchDomain = searchDomains[i];
                    Promise<T> nextPromise = DnsNameResolverContext.this.parent.executor().newPromise();
                    DnsNameResolverContext<T> nextContext = DnsNameResolverContext.this.newResolverContext(DnsNameResolverContext.this.parent, DnsNameResolverContext.this.hostname + "." + searchDomain, DnsNameResolverContext.this.resolveCache);
                    nextContext.pristineHostname = DnsNameResolverContext.this.hostname;
                    nextContext.internalResolve(nextPromise);
                    nextPromise.addListener(this);
                } else {
                    original.tryFailure(future.cause());
                }
            }
        });
        if (this.parent.ndots() == 0) {
            internalResolve(promise);
            return;
        }
        int dots = 0;
        for (int idx = this.hostname.length() - 1; idx >= 0; idx--) {
            if (this.hostname.charAt(idx) == '.') {
                dots++;
                if (dots >= this.parent.ndots()) {
                    internalResolve(promise);
                    return;
                }
            }
        }
        promise.tryFailure(new UnknownHostException(this.hostname));
    }

    private void internalResolve(Promise<T> promise) {
        InetSocketAddress nameServerAddrToTry = this.nameServerAddrs.next();
        for (InternetProtocolFamily f : this.resolveAddressTypes) {
            DnsRecordType type;
            switch (AnonymousClass4.$SwitchMap$io$netty$channel$socket$InternetProtocolFamily[f.ordinal()]) {
                case 1:
                    type = DnsRecordType.A;
                    break;
                case 2:
                    type = DnsRecordType.AAAA;
                    break;
                default:
                    throw new Error();
            }
            query(nameServerAddrToTry, new DefaultDnsQuestion(this.hostname, type), promise);
        }
    }

    private void query(InetSocketAddress nameServerAddr, final DnsQuestion question, final Promise<T> promise) {
        if (this.allowedQueries == 0 || promise.isCancelled()) {
            tryToFinishResolve(promise);
            return;
        }
        this.allowedQueries--;
        Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> f = this.parent.query(nameServerAddr, question);
        this.queriesInProgress.add(f);
        f.addListener(new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>() {
            public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
                DnsNameResolverContext.this.queriesInProgress.remove(future);
                if (!promise.isDone() && !future.isCancelled()) {
                    try {
                        if (future.isSuccess()) {
                            DnsNameResolverContext.this.onResponse(question, (AddressedEnvelope) future.getNow(), promise);
                        } else {
                            if (DnsNameResolverContext.this.traceEnabled) {
                                DnsNameResolverContext.this.addTrace(future.cause());
                            }
                            DnsNameResolverContext.this.query(DnsNameResolverContext.this.nameServerAddrs.next(), question, promise);
                        }
                        DnsNameResolverContext.this.tryToFinishResolve(promise);
                    } catch (Throwable th) {
                        DnsNameResolverContext.this.tryToFinishResolve(promise);
                    }
                }
            }
        });
    }

    void onResponse(DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> envelope, Promise<T> promise) {
        try {
            DnsResponse res = (DnsResponse) envelope.content();
            DnsResponseCode code = res.code();
            if (code == DnsResponseCode.NOERROR) {
                DnsRecordType type = question.type();
                if (type == DnsRecordType.A || type == DnsRecordType.AAAA) {
                    onResponseAorAAAA(type, question, envelope, promise);
                } else if (type == DnsRecordType.CNAME) {
                    onResponseCNAME(question, envelope, promise);
                }
                ReferenceCountUtil.safeRelease(envelope);
                return;
            }
            if (this.traceEnabled) {
                addTrace((InetSocketAddress) envelope.sender(), "response code: " + code + " with " + res.count(DnsSection.ANSWER) + " answer(s) and " + res.count(DnsSection.AUTHORITY) + " authority resource(s)");
            }
            if (code != DnsResponseCode.NXDOMAIN) {
                query(this.nameServerAddrs.next(), question, promise);
            }
            ReferenceCountUtil.safeRelease(envelope);
        } catch (Throwable th) {
            ReferenceCountUtil.safeRelease(envelope);
        }
    }

    private void onResponseAorAAAA(DnsRecordType qType, DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> envelope, Promise<T> promise) {
        DnsResponse response = (DnsResponse) envelope.content();
        Map<String, String> cnames = buildAliasMap(response);
        int answerCount = response.count(DnsSection.ANSWER);
        boolean found = false;
        for (int i = 0; i < answerCount; i++) {
            DnsRecord r = response.recordAt(DnsSection.ANSWER, i);
            DnsRecordType type = r.type();
            if (type == DnsRecordType.A || type == DnsRecordType.AAAA) {
                String qName = question.name().toLowerCase(Locale.US);
                String rName = r.name().toLowerCase(Locale.US);
                if (!rName.equals(qName)) {
                    String resolved = qName;
                    do {
                        resolved = (String) cnames.get(resolved);
                        if (!rName.equals(resolved)) {
                            break;
                        }
                        break;
                    } while (resolved != null);
                    if (resolved == null) {
                        continue;
                    }
                }
                if (r instanceof DnsRawRecord) {
                    ByteBuf content = ((ByteBufHolder) r).content();
                    int contentLen = content.readableBytes();
                    if (contentLen == 4 || contentLen == 16) {
                        byte[] addrBytes = new byte[contentLen];
                        content.getBytes(content.readerIndex(), addrBytes);
                        try {
                            InetAddress resolved2 = InetAddress.getByAddress(this.hostname, addrBytes);
                            if (this.resolvedEntries == null) {
                                this.resolvedEntries = new ArrayList(8);
                            }
                            DnsCacheEntry e = new DnsCacheEntry(this.hostname, resolved2);
                            this.resolveCache.cache(this.hostname, resolved2, r.timeToLive(), this.parent.ch.eventLoop());
                            this.resolvedEntries.add(e);
                            found = true;
                        } catch (UnknownHostException e2) {
                            throw new Error(e2);
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        if (!found) {
            if (this.traceEnabled) {
                addTrace((InetSocketAddress) envelope.sender(), "no matching " + qType + " record found");
            }
            if (!cnames.isEmpty()) {
                onResponseCNAME(question, envelope, cnames, false, promise);
            }
        }
    }

    private void onResponseCNAME(DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> envelope, Promise<T> promise) {
        onResponseCNAME(question, envelope, buildAliasMap((DnsResponse) envelope.content()), true, promise);
    }

    private void onResponseCNAME(DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> response, Map<String, String> cnames, boolean trace, Promise<T> promise) {
        String name = question.name().toLowerCase(Locale.US);
        String resolved = name;
        boolean found = false;
        while (!cnames.isEmpty()) {
            String next = (String) cnames.remove(resolved);
            if (next == null) {
                break;
            }
            found = true;
            resolved = next;
        }
        if (found) {
            followCname((InetSocketAddress) response.sender(), name, resolved, promise);
        } else if (trace && this.traceEnabled) {
            addTrace((InetSocketAddress) response.sender(), "no matching CNAME record found");
        }
    }

    private static Map<String, String> buildAliasMap(DnsResponse response) {
        int answerCount = response.count(DnsSection.ANSWER);
        Map<String, String> cnames = null;
        for (int i = 0; i < answerCount; i++) {
            DnsRecord r = response.recordAt(DnsSection.ANSWER, i);
            if (r.type() == DnsRecordType.CNAME && (r instanceof DnsRawRecord)) {
                String domainName = decodeDomainName(((ByteBufHolder) r).content());
                if (domainName != null) {
                    if (cnames == null) {
                        cnames = new HashMap();
                    }
                    cnames.put(r.name().toLowerCase(Locale.US), domainName.toLowerCase(Locale.US));
                }
            }
        }
        return cnames != null ? cnames : Collections.emptyMap();
    }

    void tryToFinishResolve(Promise<T> promise) {
        if (this.queriesInProgress.isEmpty()) {
            if (this.resolvedEntries != null || this.triedCNAME) {
                finishResolve(promise);
                return;
            }
            this.triedCNAME = true;
            query(this.nameServerAddrs.next(), new DefaultDnsQuestion(this.hostname, DnsRecordType.CNAME), promise);
        } else if (gotPreferredAddress()) {
            finishResolve(promise);
        }
    }

    private boolean gotPreferredAddress() {
        if (this.resolvedEntries == null) {
            return false;
        }
        int size = this.resolvedEntries.size();
        int i;
        switch (AnonymousClass4.$SwitchMap$io$netty$channel$socket$InternetProtocolFamily[this.resolveAddressTypes[0].ordinal()]) {
            case 1:
                for (i = 0; i < size; i++) {
                    if (((DnsCacheEntry) this.resolvedEntries.get(i)).address() instanceof Inet4Address) {
                        return true;
                    }
                }
                break;
            case 2:
                for (i = 0; i < size; i++) {
                    if (((DnsCacheEntry) this.resolvedEntries.get(i)).address() instanceof Inet6Address) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private void finishResolve(Promise<T> promise) {
        if (!this.queriesInProgress.isEmpty()) {
            Iterator<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>> i = this.queriesInProgress.iterator();
            while (i.hasNext()) {
                Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> f = (Future) i.next();
                i.remove();
                if (!f.cancel(false)) {
                    f.addListener(RELEASE_RESPONSE);
                }
            }
        }
        if (this.resolvedEntries != null) {
            InternetProtocolFamily[] arr$ = this.resolveAddressTypes;
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                if (!finishResolve(arr$[i$].addressType(), this.resolvedEntries, promise)) {
                    i$++;
                } else {
                    return;
                }
            }
        }
        int tries = this.maxAllowedQueries - this.allowedQueries;
        StringBuilder buf = new StringBuilder(64);
        buf.append("failed to resolve '");
        if (this.pristineHostname != null) {
            buf.append(this.pristineHostname);
        } else {
            buf.append(this.hostname);
        }
        buf.append('\'');
        if (tries > 1) {
            if (tries < this.maxAllowedQueries) {
                buf.append(" after ").append(tries).append(" queries ");
            } else {
                buf.append(". Exceeded max queries per resolve ").append(this.maxAllowedQueries).append(HttpConstants.SP_CHAR);
            }
        }
        if (this.trace != null) {
            buf.append(':').append(this.trace);
        }
        UnknownHostException cause = new UnknownHostException(buf.toString());
        this.resolveCache.cache(this.hostname, cause, this.parent.ch.eventLoop());
        promise.tryFailure(cause);
    }

    static String decodeDomainName(ByteBuf in) {
        String decodeName;
        in.markReaderIndex();
        try {
            decodeName = DefaultDnsRecordDecoder.decodeName(in);
        } catch (CorruptedFrameException e) {
            decodeName = null;
        } finally {
            in.resetReaderIndex();
        }
        return decodeName;
    }

    private void followCname(InetSocketAddress nameServerAddr, String name, String cname, Promise<T> promise) {
        if (this.traceEnabled) {
            if (this.trace == null) {
                this.trace = new StringBuilder(128);
            }
            this.trace.append(StringUtil.NEWLINE);
            this.trace.append("\tfrom ");
            this.trace.append(nameServerAddr);
            this.trace.append(": ");
            this.trace.append(name);
            this.trace.append(" CNAME ");
            this.trace.append(cname);
        }
        InetSocketAddress nextAddr = this.nameServerAddrs.next();
        query(nextAddr, new DefaultDnsQuestion(cname, DnsRecordType.A), promise);
        query(nextAddr, new DefaultDnsQuestion(cname, DnsRecordType.AAAA), promise);
    }

    private void addTrace(InetSocketAddress nameServerAddr, String msg) {
        if ($assertionsDisabled || this.traceEnabled) {
            if (this.trace == null) {
                this.trace = new StringBuilder(128);
            }
            this.trace.append(StringUtil.NEWLINE);
            this.trace.append("\tfrom ");
            this.trace.append(nameServerAddr);
            this.trace.append(": ");
            this.trace.append(msg);
            return;
        }
        throw new AssertionError();
    }

    private void addTrace(Throwable cause) {
        if ($assertionsDisabled || this.traceEnabled) {
            if (this.trace == null) {
                this.trace = new StringBuilder(128);
            }
            this.trace.append(StringUtil.NEWLINE);
            this.trace.append("Caused by: ");
            this.trace.append(cause);
            return;
        }
        throw new AssertionError();
    }
}
