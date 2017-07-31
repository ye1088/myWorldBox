package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Exception.ShutdownHint;

public class Http2NoMoreStreamIdsException extends Http2Exception {
    private static final String ERROR_MESSAGE = "No more streams can be created on this connection";
    private static final long serialVersionUID = -7756236161274851110L;

    public Http2NoMoreStreamIdsException() {
        super(Http2Error.PROTOCOL_ERROR, ERROR_MESSAGE, ShutdownHint.GRACEFUL_SHUTDOWN);
    }

    public Http2NoMoreStreamIdsException(Throwable cause) {
        super(Http2Error.PROTOCOL_ERROR, ERROR_MESSAGE, cause, ShutdownHint.GRACEFUL_SHUTDOWN);
    }
}
