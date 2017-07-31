package io.netty.handler.ssl;

import org.apache.tomcat.jni.SSLContext;

public final class OpenSslServerSessionContext extends OpenSslSessionContext {
    OpenSslServerSessionContext(ReferenceCountedOpenSslContext context) {
        super(context);
    }

    public void setSessionTimeout(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException();
        }
        SSLContext.setSessionCacheTimeout(this.context.ctx, (long) seconds);
    }

    public int getSessionTimeout() {
        return (int) SSLContext.getSessionCacheTimeout(this.context.ctx);
    }

    public void setSessionCacheSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException();
        }
        SSLContext.setSessionCacheSize(this.context.ctx, (long) size);
    }

    public int getSessionCacheSize() {
        return (int) SSLContext.getSessionCacheSize(this.context.ctx);
    }

    public void setSessionCacheEnabled(boolean enabled) {
        SSLContext.setSessionCacheMode(this.context.ctx, enabled ? 2 : 0);
    }

    public boolean isSessionCacheEnabled() {
        return SSLContext.getSessionCacheMode(this.context.ctx) == 2;
    }

    public boolean setSessionIdContext(byte[] sidCtx) {
        return SSLContext.setSessionIdContext(this.context.ctx, sidCtx);
    }
}
