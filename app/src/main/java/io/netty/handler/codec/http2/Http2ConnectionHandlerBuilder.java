package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2HeadersEncoder.SensitivityDetector;

public final class Http2ConnectionHandlerBuilder extends AbstractHttp2ConnectionHandlerBuilder<Http2ConnectionHandler, Http2ConnectionHandlerBuilder> {
    public Http2ConnectionHandlerBuilder validateHeaders(boolean validateHeaders) {
        return (Http2ConnectionHandlerBuilder) super.validateHeaders(validateHeaders);
    }

    public Http2ConnectionHandlerBuilder initialSettings(Http2Settings settings) {
        return (Http2ConnectionHandlerBuilder) super.initialSettings(settings);
    }

    public Http2ConnectionHandlerBuilder frameListener(Http2FrameListener frameListener) {
        return (Http2ConnectionHandlerBuilder) super.frameListener(frameListener);
    }

    public Http2ConnectionHandlerBuilder gracefulShutdownTimeoutMillis(long gracefulShutdownTimeoutMillis) {
        return (Http2ConnectionHandlerBuilder) super.gracefulShutdownTimeoutMillis(gracefulShutdownTimeoutMillis);
    }

    public Http2ConnectionHandlerBuilder server(boolean isServer) {
        return (Http2ConnectionHandlerBuilder) super.server(isServer);
    }

    public Http2ConnectionHandlerBuilder connection(Http2Connection connection) {
        return (Http2ConnectionHandlerBuilder) super.connection(connection);
    }

    public Http2ConnectionHandlerBuilder codec(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder) {
        return (Http2ConnectionHandlerBuilder) super.codec(decoder, encoder);
    }

    public Http2ConnectionHandlerBuilder frameLogger(Http2FrameLogger frameLogger) {
        return (Http2ConnectionHandlerBuilder) super.frameLogger(frameLogger);
    }

    public Http2ConnectionHandlerBuilder encoderEnforceMaxConcurrentStreams(boolean encoderEnforceMaxConcurrentStreams) {
        return (Http2ConnectionHandlerBuilder) super.encoderEnforceMaxConcurrentStreams(encoderEnforceMaxConcurrentStreams);
    }

    public Http2ConnectionHandlerBuilder headerSensitivityDetector(SensitivityDetector headerSensitivityDetector) {
        return (Http2ConnectionHandlerBuilder) super.headerSensitivityDetector(headerSensitivityDetector);
    }

    public Http2ConnectionHandler build() {
        return super.build();
    }

    protected Http2ConnectionHandler build(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings) {
        return new Http2ConnectionHandler(decoder, encoder, initialSettings);
    }
}
