package io.netty.handler.codec.http2;

import io.netty.util.internal.ObjectUtil;

public abstract class AbstractInboundHttp2ToHttpAdapterBuilder<T extends InboundHttp2ToHttpAdapter, B extends AbstractInboundHttp2ToHttpAdapterBuilder<T, B>> {
    private final Http2Connection connection;
    private int maxContentLength;
    private boolean propagateSettings;
    private boolean validateHttpHeaders;

    protected abstract T build(Http2Connection http2Connection, int i, boolean z, boolean z2) throws Exception;

    protected AbstractInboundHttp2ToHttpAdapterBuilder(Http2Connection connection) {
        this.connection = (Http2Connection) ObjectUtil.checkNotNull(connection, "connection");
    }

    protected final B self() {
        return this;
    }

    protected Http2Connection connection() {
        return this.connection;
    }

    protected int maxContentLength() {
        return this.maxContentLength;
    }

    protected B maxContentLength(int maxContentLength) {
        this.maxContentLength = maxContentLength;
        return self();
    }

    protected boolean isValidateHttpHeaders() {
        return this.validateHttpHeaders;
    }

    protected B validateHttpHeaders(boolean validate) {
        this.validateHttpHeaders = validate;
        return self();
    }

    protected boolean isPropagateSettings() {
        return this.propagateSettings;
    }

    protected B propagateSettings(boolean propagate) {
        this.propagateSettings = propagate;
        return self();
    }

    protected T build() {
        try {
            T instance = build(connection(), maxContentLength(), isValidateHttpHeaders(), isPropagateSettings());
            this.connection.addListener(instance);
            return instance;
        } catch (Throwable t) {
            IllegalStateException illegalStateException = new IllegalStateException("failed to create a new InboundHttp2ToHttpAdapter", t);
        }
    }
}
