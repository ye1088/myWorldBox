package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;

public final class OpenSslEngine extends ReferenceCountedOpenSslEngine {
    OpenSslEngine(OpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort) {
        super(context, alloc, peerHost, peerPort, false);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        OpenSsl.releaseIfNeeded(this);
    }
}
