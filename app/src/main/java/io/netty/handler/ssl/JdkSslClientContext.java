package io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

@Deprecated
public final class JdkSslClientContext extends JdkSslContext {
    @Deprecated
    public JdkSslClientContext() throws SSLException {
        this(null, null);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile) throws SSLException {
        this(certChainFile, null);
    }

    @Deprecated
    public JdkSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        this(null, trustManagerFactory);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
        this(certChainFile, trustManagerFactory, null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0, 0);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(certChainFile, trustManagerFactory, (Iterable) ciphers, IdentityCipherSuiteFilter.INSTANCE, JdkSslContext.toNegotiator(SslContext.toApplicationProtocolConfig(nextProtocols), false), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(certChainFile, trustManagerFactory, (Iterable) ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(certChainFile, trustManagerFactory, null, null, null, null, (Iterable) ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, (Iterable) ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        Iterable iterable = ciphers;
        CipherSuiteFilter cipherSuiteFilter = cipherFilter;
        JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator = apn;
        super(newSSLContext(SslContext.toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, SslContext.toX509CertificatesInternal(keyCertChainFile), SslContext.toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), true, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, ClientAuth.NONE);
    }

    JdkSslClientContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        super(newSSLContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), true, (Iterable) ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, false), ClientAuth.NONE);
    }

    private static SSLContext newSSLContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, long sessionCacheSize, long sessionTimeout) throws SSLException {
        TrustManager[] trustManagerArr;
        if (trustCertCollection != null) {
            try {
                trustManagerFactory = SslContext.buildTrustManagerFactory(trustCertCollection, trustManagerFactory);
            } catch (Exception e) {
                if (e instanceof SSLException) {
                    throw ((SSLException) e);
                }
                throw new SSLException("failed to initialize the client-side SSL context", e);
            }
        }
        if (keyCertChain != null) {
            keyManagerFactory = SslContext.buildKeyManagerFactory(keyCertChain, key, keyPassword, keyManagerFactory);
        }
        SSLContext ctx = SSLContext.getInstance(SSLSocketFactory.TLS);
        KeyManager[] keyManagers = keyManagerFactory == null ? null : keyManagerFactory.getKeyManagers();
        if (trustManagerFactory == null) {
            trustManagerArr = null;
        } else {
            trustManagerArr = trustManagerFactory.getTrustManagers();
        }
        ctx.init(keyManagers, trustManagerArr, null);
        SSLSessionContext sessCtx = ctx.getClientSessionContext();
        if (sessionCacheSize > 0) {
            sessCtx.setSessionCacheSize((int) Math.min(sessionCacheSize, 2147483647L));
        }
        if (sessionTimeout > 0) {
            sessCtx.setSessionTimeout((int) Math.min(sessionTimeout, 2147483647L));
        }
        return ctx;
    }
}
