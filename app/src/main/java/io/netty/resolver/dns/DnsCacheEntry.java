package io.netty.resolver.dns;

import com.huluxia.data.profile.a;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public final class DnsCacheEntry {
    static final /* synthetic */ boolean $assertionsDisabled = (!DnsCacheEntry.class.desiredAssertionStatus());
    private final InetAddress address;
    private final Throwable cause;
    private volatile ScheduledFuture<?> expirationFuture;
    private final String hostname;

    public DnsCacheEntry(String hostname, InetAddress address) {
        this.hostname = (String) ObjectUtil.checkNotNull(hostname, "hostname");
        this.address = (InetAddress) ObjectUtil.checkNotNull(address, a.qf);
        this.cause = null;
    }

    public DnsCacheEntry(String hostname, Throwable cause) {
        this.hostname = (String) ObjectUtil.checkNotNull(hostname, "hostname");
        this.cause = (Throwable) ObjectUtil.checkNotNull(cause, "cause");
        this.address = null;
    }

    public String hostname() {
        return this.hostname;
    }

    public InetAddress address() {
        return this.address;
    }

    public Throwable cause() {
        return this.cause;
    }

    void scheduleExpiration(EventLoop loop, Runnable task, long delay, TimeUnit unit) {
        if ($assertionsDisabled || this.expirationFuture == null) {
            this.expirationFuture = loop.schedule(task, delay, unit);
            return;
        }
        throw new AssertionError("expiration task scheduled already");
    }

    void cancelExpiration() {
        ScheduledFuture<?> expirationFuture = this.expirationFuture;
        if (expirationFuture != null) {
            expirationFuture.cancel(false);
        }
    }

    public String toString() {
        if (this.cause != null) {
            return this.hostname + '/' + this.cause;
        }
        return this.address.toString();
    }
}
