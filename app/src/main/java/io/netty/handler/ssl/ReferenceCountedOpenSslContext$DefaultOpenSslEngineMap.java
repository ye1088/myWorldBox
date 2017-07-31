package io.netty.handler.ssl;

import io.netty.util.internal.PlatformDependent;
import java.util.Map;

final class ReferenceCountedOpenSslContext$DefaultOpenSslEngineMap implements OpenSslEngineMap {
    private final Map<Long, ReferenceCountedOpenSslEngine> engines;

    private ReferenceCountedOpenSslContext$DefaultOpenSslEngineMap() {
        this.engines = PlatformDependent.newConcurrentHashMap();
    }

    public ReferenceCountedOpenSslEngine remove(long ssl) {
        return (ReferenceCountedOpenSslEngine) this.engines.remove(Long.valueOf(ssl));
    }

    public void add(ReferenceCountedOpenSslEngine engine) {
        this.engines.put(Long.valueOf(engine.sslPointer()), engine);
    }

    public ReferenceCountedOpenSslEngine get(long ssl) {
        return (ReferenceCountedOpenSslEngine) this.engines.get(Long.valueOf(ssl));
    }
}
