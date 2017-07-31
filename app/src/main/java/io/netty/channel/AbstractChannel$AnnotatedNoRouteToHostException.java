package io.netty.channel;

import java.net.NoRouteToHostException;
import java.net.SocketAddress;

final class AbstractChannel$AnnotatedNoRouteToHostException extends NoRouteToHostException {
    private static final long serialVersionUID = -6801433937592080623L;

    AbstractChannel$AnnotatedNoRouteToHostException(NoRouteToHostException exception, SocketAddress remoteAddress) {
        super(exception.getMessage() + ": " + remoteAddress);
        setStackTrace(exception.getStackTrace());
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}
