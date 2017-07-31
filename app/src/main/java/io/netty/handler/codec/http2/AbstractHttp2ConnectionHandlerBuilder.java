package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2HeadersEncoder.SensitivityDetector;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.TimeUnit;

public abstract class AbstractHttp2ConnectionHandlerBuilder<T extends Http2ConnectionHandler, B extends AbstractHttp2ConnectionHandlerBuilder<T, B>> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractHttp2ConnectionHandlerBuilder.class.desiredAssertionStatus());
    private static final long DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT_MILLIS = TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS);
    private static final SensitivityDetector DEFAULT_HEADER_SENSITIVITY_DETECTOR = Http2HeadersEncoder.NEVER_SENSITIVE;
    private Http2Connection connection;
    private Http2ConnectionDecoder decoder;
    private Http2ConnectionEncoder encoder;
    private Boolean encoderEnforceMaxConcurrentStreams;
    private Http2FrameListener frameListener;
    private Http2FrameLogger frameLogger;
    private long gracefulShutdownTimeoutMillis = DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT_MILLIS;
    private SensitivityDetector headerSensitivityDetector;
    private Http2Settings initialSettings = new Http2Settings();
    private Boolean isServer;
    private Boolean validateHeaders;

    protected abstract T build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) throws Exception;

    protected Http2Settings initialSettings() {
        return this.initialSettings;
    }

    protected B initialSettings(Http2Settings settings) {
        this.initialSettings = (Http2Settings) ObjectUtil.checkNotNull(settings, "settings");
        return self();
    }

    protected Http2FrameListener frameListener() {
        return this.frameListener;
    }

    protected B frameListener(Http2FrameListener frameListener) {
        this.frameListener = (Http2FrameListener) ObjectUtil.checkNotNull(frameListener, "frameListener");
        return self();
    }

    protected long gracefulShutdownTimeoutMillis() {
        return this.gracefulShutdownTimeoutMillis;
    }

    protected B gracefulShutdownTimeoutMillis(long gracefulShutdownTimeoutMillis) {
        this.gracefulShutdownTimeoutMillis = gracefulShutdownTimeoutMillis;
        return self();
    }

    protected boolean isServer() {
        return this.isServer != null ? this.isServer.booleanValue() : true;
    }

    protected B server(boolean isServer) {
        enforceConstraint("server", "connection", this.connection);
        enforceConstraint("server", "codec", this.decoder);
        enforceConstraint("server", "codec", this.encoder);
        this.isServer = Boolean.valueOf(isServer);
        return self();
    }

    protected Http2Connection connection() {
        return this.connection;
    }

    protected B connection(Http2Connection connection) {
        enforceConstraint("connection", "server", this.isServer);
        enforceConstraint("connection", "codec", this.decoder);
        enforceConstraint("connection", "codec", this.encoder);
        this.connection = (Http2Connection) ObjectUtil.checkNotNull(connection, "connection");
        return self();
    }

    protected Http2ConnectionDecoder decoder() {
        return this.decoder;
    }

    protected Http2ConnectionEncoder encoder() {
        return this.encoder;
    }

    protected B codec(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder) {
        enforceConstraint("codec", "server", this.isServer);
        enforceConstraint("codec", "connection", this.connection);
        enforceConstraint("codec", "frameLogger", this.frameLogger);
        enforceConstraint("codec", "validateHeaders", this.validateHeaders);
        enforceConstraint("codec", "headerSensitivityDetector", this.headerSensitivityDetector);
        enforceConstraint("codec", "encoderEnforceMaxConcurrentStreams", this.encoderEnforceMaxConcurrentStreams);
        ObjectUtil.checkNotNull(decoder, "decoder");
        ObjectUtil.checkNotNull(encoder, "encoder");
        if (decoder.connection() != encoder.connection()) {
            throw new IllegalArgumentException("The specified encoder and decoder have different connections.");
        }
        this.decoder = decoder;
        this.encoder = encoder;
        return self();
    }

    protected boolean isValidateHeaders() {
        return this.validateHeaders != null ? this.validateHeaders.booleanValue() : true;
    }

    protected B validateHeaders(boolean validateHeaders) {
        enforceNonCodecConstraints("validateHeaders");
        this.validateHeaders = Boolean.valueOf(validateHeaders);
        return self();
    }

    protected Http2FrameLogger frameLogger() {
        return this.frameLogger;
    }

    protected B frameLogger(Http2FrameLogger frameLogger) {
        enforceNonCodecConstraints("frameLogger");
        this.frameLogger = (Http2FrameLogger) ObjectUtil.checkNotNull(frameLogger, "frameLogger");
        return self();
    }

    protected boolean encoderEnforceMaxConcurrentStreams() {
        return this.encoderEnforceMaxConcurrentStreams != null ? this.encoderEnforceMaxConcurrentStreams.booleanValue() : false;
    }

    protected B encoderEnforceMaxConcurrentStreams(boolean encoderEnforceMaxConcurrentStreams) {
        enforceNonCodecConstraints("encoderEnforceMaxConcurrentStreams");
        this.encoderEnforceMaxConcurrentStreams = Boolean.valueOf(encoderEnforceMaxConcurrentStreams);
        return self();
    }

    protected SensitivityDetector headerSensitivityDetector() {
        return this.headerSensitivityDetector != null ? this.headerSensitivityDetector : DEFAULT_HEADER_SENSITIVITY_DETECTOR;
    }

    protected B headerSensitivityDetector(SensitivityDetector headerSensitivityDetector) {
        enforceNonCodecConstraints("headerSensitivityDetector");
        this.headerSensitivityDetector = (SensitivityDetector) ObjectUtil.checkNotNull(headerSensitivityDetector, "headerSensitivityDetector");
        return self();
    }

    protected T build() {
        if (this.encoder == null) {
            Http2Connection connection = this.connection;
            if (connection == null) {
                connection = new DefaultHttp2Connection(isServer());
            }
            return buildFromConnection(connection);
        } else if ($assertionsDisabled || this.decoder != null) {
            return buildFromCodec(this.decoder, this.encoder);
        } else {
            throw new AssertionError();
        }
    }

    private T buildFromConnection(Http2Connection connection) {
        Http2FrameReader reader = new DefaultHttp2FrameReader(isValidateHeaders());
        Http2FrameWriter writer = new DefaultHttp2FrameWriter(headerSensitivityDetector());
        if (this.frameLogger != null) {
            Http2FrameReader reader2 = new Http2InboundFrameLogger(reader, this.frameLogger);
            writer = new Http2OutboundFrameLogger(writer, this.frameLogger);
            reader = reader2;
        }
        Http2ConnectionEncoder encoder = new DefaultHttp2ConnectionEncoder(connection, writer);
        boolean encoderEnforceMaxConcurrentStreams = encoderEnforceMaxConcurrentStreams();
        if (encoderEnforceMaxConcurrentStreams) {
            if (connection.isServer()) {
                encoder.close();
                reader.close();
                throw new IllegalArgumentException("encoderEnforceMaxConcurrentStreams: " + encoderEnforceMaxConcurrentStreams + " not supported for server");
            }
            encoder = new StreamBufferingEncoder(encoder);
        }
        return buildFromCodec(new DefaultHttp2ConnectionDecoder(connection, encoder, reader), encoder);
    }

    private T buildFromCodec(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder) {
        try {
            T handler = build(decoder, encoder, this.initialSettings);
            handler.gracefulShutdownTimeoutMillis(this.gracefulShutdownTimeoutMillis);
            if (handler.decoder().frameListener() == null) {
                handler.decoder().frameListener(this.frameListener);
            }
            return handler;
        } catch (Throwable t) {
            encoder.close();
            decoder.close();
            IllegalStateException illegalStateException = new IllegalStateException("failed to build a Http2ConnectionHandler", t);
        }
    }

    protected final B self() {
        return this;
    }

    private void enforceNonCodecConstraints(String rejectee) {
        enforceConstraint(rejectee, "server/connection", this.decoder);
        enforceConstraint(rejectee, "server/connection", this.encoder);
    }

    private static void enforceConstraint(String methodName, String rejectorName, Object value) {
        if (value != null) {
            throw new IllegalStateException(methodName + "() cannot be called because " + rejectorName + "() has been called already.");
        }
    }
}
