package io.netty.channel;

import java.net.SocketAddress;
import java.net.SocketException;

final class AbstractChannel$AnnotatedSocketException extends SocketException {
    private static final long serialVersionUID = 3896743275010454039L;

    AbstractChannel$AnnotatedSocketException(SocketException exception, SocketAddress remoteAddress) {
        super(exception.getMessage() + ": " + remoteAddress);
        setStackTrace(exception.getStackTrace());
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}
