package io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

@Deprecated
public final class JdkSslServerContext extends JdkSslContext {
    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile) throws SSLException {
        this(certChainFile, keyFile, null);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
        this(certChainFile, keyFile, keyPassword, null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0, 0);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(certChainFile, keyFile, keyPassword, (Iterable) ciphers, IdentityCipherSuiteFilter.INSTANCE, JdkSslContext.toNegotiator(SslContext.toApplicationProtocolConfig(nextProtocols), true), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(certChainFile, keyFile, keyPassword, (Iterable) ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, true), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(null, null, certChainFile, keyFile, keyPassword, null, (Iterable) ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslServerContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, (Iterable) ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, true), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public JdkSslServerContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        Iterable iterable = ciphers;
        CipherSuiteFilter cipherSuiteFilter = cipherFilter;
        JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator = apn;
        super(newSSLContext(SslContext.toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, SslContext.toX509CertificatesInternal(keyCertChainFile), SslContext.toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), false, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, ClientAuth.NONE);
    }

    JdkSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth) throws SSLException {
        super(newSSLContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout), false, (Iterable) ciphers, cipherFilter, JdkSslContext.toNegotiator(apn, true), clientAuth);
    }

    private static SSLContext newSSLContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, long sessionCacheSize, long sessionTimeout) throws SSLException {
        if (key == null && keyManagerFactory == null) {
            throw new NullPointerException("key, keyManagerFactory");
        }
        if (trustCertCollection != null) {
            try {
                trustManagerFactory = SslContext.buildTrustManagerFactory(trustCertCollection, trustManagerFactory);
            } catch (Exception e) {
                if (e instanceof SSLException) {
                    throw ((SSLException) e);
                }
                throw new SSLException("failed to initialize the server-side SSL context", e);
            }
        }
        if (key != null) {
            keyManagerFactory = SslContext.buildKeyManagerFactory(keyCertChain, key, keyPassword, keyManagerFactory);
        }
        SSLContext ctx = SSLContext.getInstance(SSLSocketFactory.TLS);
        ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory == null ? null : trustManagerFactory.getTrustManagers(), null);
        SSLSessionContext sessCtx = ctx.getServerSessionContext();
        if (sessionCacheSize > 0) {
            sessCtx.setSessionCacheSize((int) Math.min(sessionCacheSize, 2147483647L));
        }
        if (sessionTimeout > 0) {
            sessCtx.setSessionTimeout((int) Math.min(sessionTimeout, 2147483647L));
        }
        return ctx;
    }
}
