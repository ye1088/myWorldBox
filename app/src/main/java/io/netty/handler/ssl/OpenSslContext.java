package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;

public abstract class OpenSslContext extends ReferenceCountedOpenSslContext {
    OpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth) throws SSLException {
        super(ciphers, cipherFilter, apnCfg, sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, false);
    }

    OpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth) throws SSLException {
        super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, false);
    }

    final SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return new OpenSslEngine(this, alloc, peerHost, peerPort);
    }

    protected final void finalize() throws Throwable {
        super.finalize();
        OpenSsl.releaseIfNeeded(this);
    }
}
