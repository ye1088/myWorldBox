package io.netty.handler.ssl;

interface OpenSslEngineMap {
    void add(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine);

    ReferenceCountedOpenSslEngine get(long j);

    ReferenceCountedOpenSslEngine remove(long j);
}
