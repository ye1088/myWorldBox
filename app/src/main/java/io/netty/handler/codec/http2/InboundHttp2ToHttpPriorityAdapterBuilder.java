package io.netty.handler.codec.http2;

public final class InboundHttp2ToHttpPriorityAdapterBuilder extends AbstractInboundHttp2ToHttpAdapterBuilder<InboundHttp2ToHttpPriorityAdapter, InboundHttp2ToHttpPriorityAdapterBuilder> {
    public InboundHttp2ToHttpPriorityAdapterBuilder(Http2Connection connection) {
        super(connection);
    }

    public InboundHttp2ToHttpPriorityAdapterBuilder maxContentLength(int maxContentLength) {
        return (InboundHttp2ToHttpPriorityAdapterBuilder) super.maxContentLength(maxContentLength);
    }

    public InboundHttp2ToHttpPriorityAdapterBuilder validateHttpHeaders(boolean validate) {
        return (InboundHttp2ToHttpPriorityAdapterBuilder) super.validateHttpHeaders(validate);
    }

    public InboundHttp2ToHttpPriorityAdapterBuilder propagateSettings(boolean propagate) {
        return (InboundHttp2ToHttpPriorityAdapterBuilder) super.propagateSettings(propagate);
    }

    public InboundHttp2ToHttpPriorityAdapter build() {
        return (InboundHttp2ToHttpPriorityAdapter) super.build();
    }

    protected InboundHttp2ToHttpPriorityAdapter build(Http2Connection connection, int maxContentLength, boolean validateHttpHeaders, boolean propagateSettings) throws Exception {
        return new InboundHttp2ToHttpPriorityAdapter(connection, maxContentLength, validateHttpHeaders, propagateSettings);
    }
}
