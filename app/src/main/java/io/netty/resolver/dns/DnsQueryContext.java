package io.netty.resolver.dns;

import io.netty.buffer.Unpooled;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.dns.DatagramDnsQuery;
import io.netty.handler.codec.dns.DefaultDnsRawRecord;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

final class DnsQueryContext {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DnsQueryContext.class);
    private final Iterable<DnsRecord> additional;
    private final int id;
    private final InetSocketAddress nameServerAddr;
    private final DnsRecord optResource;
    private final DnsNameResolver parent;
    private final Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise;
    private final DnsQuestion question;
    private final boolean recursionDesired;
    private volatile ScheduledFuture<?> timeoutFuture;

    DnsQueryContext(DnsNameResolver parent, InetSocketAddress nameServerAddr, DnsQuestion question, Iterable<DnsRecord> additional, Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise) {
        this.parent = (DnsNameResolver) ObjectUtil.checkNotNull(parent, "parent");
        this.nameServerAddr = (InetSocketAddress) ObjectUtil.checkNotNull(nameServerAddr, "nameServerAddr");
        this.question = (DnsQuestion) ObjectUtil.checkNotNull(question, "question");
        this.additional = (Iterable) ObjectUtil.checkNotNull(additional, "additional");
        this.promise = (Promise) ObjectUtil.checkNotNull(promise, "promise");
        this.recursionDesired = parent.isRecursionDesired();
        this.id = parent.queryContextManager.add(this);
        if (parent.isOptResourceEnabled()) {
            this.optResource = new DefaultDnsRawRecord("", DnsRecordType.OPT, parent.maxPayloadSize(), 0, Unpooled.EMPTY_BUFFER);
        } else {
            this.optResource = null;
        }
    }

    InetSocketAddress nameServerAddr() {
        return this.nameServerAddr;
    }

    DnsQuestion question() {
        return this.question;
    }

    void query() {
        DnsQuestion question = question();
        DatagramDnsQuery query = new DatagramDnsQuery(null, nameServerAddr(), this.id);
        query.setRecursionDesired(this.recursionDesired);
        query.addRecord(DnsSection.QUESTION, question);
        for (DnsRecord record : this.additional) {
            query.addRecord(DnsSection.ADDITIONAL, record);
        }
        if (this.optResource != null) {
            query.addRecord(DnsSection.ADDITIONAL, this.optResource);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("{} WRITE: [{}: {}], {}", this.parent.ch, Integer.valueOf(this.id), nameServerAddr, question);
        }
        sendQuery(query);
    }

    private void sendQuery(final DnsQuery query) {
        if (this.parent.channelFuture.isDone()) {
            writeQuery(query);
        } else {
            this.parent.channelFuture.addListener(new GenericFutureListener<Future<? super Channel>>() {
                public void operationComplete(Future<? super Channel> future) throws Exception {
                    if (future.isSuccess()) {
                        DnsQueryContext.this.writeQuery(query);
                    } else {
                        DnsQueryContext.this.promise.tryFailure(future.cause());
                    }
                }
            });
        }
    }

    private void writeQuery(DnsQuery query) {
        final ChannelFuture writeFuture = this.parent.ch.writeAndFlush(query);
        if (writeFuture.isDone()) {
            onQueryWriteCompletion(writeFuture);
        } else {
            writeFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    DnsQueryContext.this.onQueryWriteCompletion(writeFuture);
                }
            });
        }
    }

    private void onQueryWriteCompletion(ChannelFuture writeFuture) {
        if (writeFuture.isSuccess()) {
            final long queryTimeoutMillis = this.parent.queryTimeoutMillis();
            if (queryTimeoutMillis > 0) {
                this.timeoutFuture = this.parent.ch.eventLoop().schedule(new Runnable() {
                    public void run() {
                        if (!DnsQueryContext.this.promise.isDone()) {
                            DnsQueryContext.this.setFailure("query timed out after " + queryTimeoutMillis + " milliseconds", null);
                        }
                    }
                }, queryTimeoutMillis, TimeUnit.MILLISECONDS);
                return;
            }
            return;
        }
        setFailure("failed to send a query", writeFuture.cause());
    }

    void finish(AddressedEnvelope<? extends DnsResponse, InetSocketAddress> envelope) {
        DnsResponse res = (DnsResponse) envelope.content();
        if (res.count(DnsSection.QUESTION) != 1) {
            logger.warn("Received a DNS response with invalid number of questions: {}", (Object) envelope);
        } else if (question().equals(res.recordAt(DnsSection.QUESTION))) {
            setSuccess(envelope);
        } else {
            logger.warn("Received a mismatching DNS response: {}", (Object) envelope);
        }
    }

    private void setSuccess(AddressedEnvelope<? extends DnsResponse, InetSocketAddress> envelope) {
        this.parent.queryContextManager.remove(nameServerAddr(), this.id);
        ScheduledFuture<?> timeoutFuture = this.timeoutFuture;
        if (timeoutFuture != null) {
            timeoutFuture.cancel(false);
        }
        Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise = this.promise;
        if (promise.setUncancellable()) {
            promise.setSuccess(envelope.retain());
        }
    }

    private void setFailure(String message, Throwable cause) {
        DnsNameResolverException e;
        InetSocketAddress nameServerAddr = nameServerAddr();
        this.parent.queryContextManager.remove(nameServerAddr, this.id);
        StringBuilder buf = new StringBuilder(message.length() + 64);
        buf.append('[').append(nameServerAddr).append("] ").append(message).append(" (no stack trace available)");
        if (cause != null) {
            e = new DnsNameResolverException(nameServerAddr, question(), buf.toString(), cause);
        } else {
            e = new DnsNameResolverException(nameServerAddr, question(), buf.toString());
        }
        this.promise.tryFailure(e);
    }
}
