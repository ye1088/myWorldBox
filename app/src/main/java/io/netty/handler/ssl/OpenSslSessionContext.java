package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.util.Enumeration;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import org.apache.tomcat.jni.SSLContext;
import org.apache.tomcat.jni.SessionTicketKey;

public abstract class OpenSslSessionContext implements SSLSessionContext {
    private static final Enumeration<byte[]> EMPTY = new EmptyEnumeration(null);
    final ReferenceCountedOpenSslContext context;
    private final OpenSslSessionStats stats;

    public abstract boolean isSessionCacheEnabled();

    public abstract void setSessionCacheEnabled(boolean z);

    OpenSslSessionContext(ReferenceCountedOpenSslContext context) {
        this.context = context;
        this.stats = new OpenSslSessionStats(context);
    }

    public SSLSession getSession(byte[] bytes) {
        if (bytes != null) {
            return null;
        }
        throw new NullPointerException("bytes");
    }

    public Enumeration<byte[]> getIds() {
        return EMPTY;
    }

    @Deprecated
    public void setTicketKeys(byte[] keys) {
        ObjectUtil.checkNotNull(keys, "keys");
        SSLContext.clearOptions(this.context.ctx, 16384);
        SSLContext.setSessionTicketKeys(this.context.ctx, keys);
    }

    public void setTicketKeys(OpenSslSessionTicketKey... keys) {
        ObjectUtil.checkNotNull(keys, "keys");
        SSLContext.clearOptions(this.context.ctx, 16384);
        SessionTicketKey[] ticketKeys = new SessionTicketKey[keys.length];
        for (int i = 0; i < ticketKeys.length; i++) {
            ticketKeys[i] = keys[i].key;
        }
        SSLContext.setSessionTicketKeys(this.context.ctx, ticketKeys);
    }

    public OpenSslSessionStats stats() {
        return this.stats;
    }
}
