package io.netty.handler.ssl;

import io.netty.util.internal.PlatformDependent;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateRevokedException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLHandshakeException;
import org.apache.tomcat.jni.CertificateVerifier;

abstract class ReferenceCountedOpenSslContext$AbstractCertificateVerifier implements CertificateVerifier {
    private final OpenSslEngineMap engineMap;

    abstract void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArr, String str) throws Exception;

    ReferenceCountedOpenSslContext$AbstractCertificateVerifier(OpenSslEngineMap engineMap) {
        this.engineMap = engineMap;
    }

    public final int verify(long ssl, byte[][] chain, String auth) {
        X509Certificate[] peerCerts = ReferenceCountedOpenSslContext.certificates(chain);
        ReferenceCountedOpenSslEngine engine = this.engineMap.get(ssl);
        try {
            verify(engine, peerCerts, auth);
            return 0;
        } catch (Throwable cause) {
            ReferenceCountedOpenSslContext.access$200().debug("verification of certificate failed", cause);
            SSLHandshakeException e = new SSLHandshakeException("General OpenSslEngine problem");
            e.initCause(cause);
            engine.handshakeException = e;
            if (cause instanceof OpenSslCertificateException) {
                return ((OpenSslCertificateException) cause).errorCode();
            }
            if (cause instanceof CertificateExpiredException) {
                return 10;
            }
            if (cause instanceof CertificateNotYetValidException) {
                return 9;
            }
            if (PlatformDependent.javaVersion() < 7 || !(cause instanceof CertificateRevokedException)) {
                return 1;
            }
            return 23;
        }
    }
}
