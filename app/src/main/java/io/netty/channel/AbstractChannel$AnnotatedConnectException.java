package io.netty.channel;

import java.net.ConnectException;
import java.net.SocketAddress;

final class AbstractChannel$AnnotatedConnectException extends ConnectException {
    private static final long serialVersionUID = 3901958112696433556L;

    AbstractChannel$AnnotatedConnectException(ConnectException exception, SocketAddress remoteAddress) {
        super(exception.getMessage() + ": " + remoteAddress);
        setStackTrace(exception.getStackTrace());
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}
