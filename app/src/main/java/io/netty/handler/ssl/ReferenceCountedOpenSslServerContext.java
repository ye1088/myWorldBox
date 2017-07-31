package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import org.apache.tomcat.jni.SSLContext;

public final class ReferenceCountedOpenSslServerContext extends ReferenceCountedOpenSslContext {
    private static final byte[] ID = new byte[]{(byte) 110, (byte) 101, (byte) 116, (byte) 116, (byte) 121};
    private final OpenSslKeyMaterialManager keyMaterialManager;
    private final OpenSslServerSessionContext sessionContext;

    private static final class ExtendedTrustManagerVerifyCallback extends ReferenceCountedOpenSslContext$AbstractCertificateVerifier {
        private final X509ExtendedTrustManager manager;

        ExtendedTrustManagerVerifyCallback(OpenSslEngineMap engineMap, X509ExtendedTrustManager manager) {
            super(engineMap);
            this.manager = manager;
        }

        void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
            this.manager.checkClientTrusted(peerCerts, auth, engine);
        }
    }

    static final class ServerContext {
        OpenSslKeyMaterialManager keyMaterialManager;
        OpenSslServerSessionContext sessionContext;

        ServerContext() {
        }
    }

    private static final class TrustManagerVerifyCallback extends ReferenceCountedOpenSslContext$AbstractCertificateVerifier {
        private final X509TrustManager manager;

        TrustManagerVerifyCallback(OpenSslEngineMap engineMap, X509TrustManager manager) {
            super(engineMap);
            this.manager = manager;
        }

        void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
            this.manager.checkClientTrusted(peerCerts, auth);
        }
    }

    ReferenceCountedOpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth) throws SSLException {
        this(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, (Iterable) ciphers, cipherFilter, toNegotiator(apn), sessionCacheSize, sessionTimeout, clientAuth);
    }

    private ReferenceCountedOpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth) throws SSLException {
        super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, 1, keyCertChain, clientAuth, true);
        boolean success = false;
        try {
            ServerContext context = newSessionContext(this, this.ctx, this.engineMap, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory);
            this.sessionContext = context.sessionContext;
            this.keyMaterialManager = context.keyMaterialManager;
            success = true;
        } finally {
            if (!success) {
                release();
            }
        }
    }

    public OpenSslServerSessionContext sessionContext() {
        return this.sessionContext;
    }

    OpenSslKeyMaterialManager keyMaterialManager() {
        return this.keyMaterialManager;
    }

    static ServerContext newSessionContext(ReferenceCountedOpenSslContext thiz, long ctx, OpenSslEngineMap engineMap, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory) throws SSLException {
        ServerContext result = new ServerContext();
        synchronized (ReferenceCountedOpenSslContext.class) {
            try {
                SSLContext.setVerify(ctx, 0, 10);
                if (OpenSsl.useKeyManagerFactory()) {
                    OpenSslKeyMaterialManager openSslExtendedKeyMaterialManager;
                    if (keyManagerFactory == null) {
                        keyManagerFactory = buildKeyManagerFactory(keyCertChain, key, keyPassword, keyManagerFactory);
                    }
                    X509KeyManager keyManager = chooseX509KeyManager(keyManagerFactory.getKeyManagers());
                    if (useExtendedKeyManager(keyManager)) {
                        openSslExtendedKeyMaterialManager = new OpenSslExtendedKeyMaterialManager((X509ExtendedKeyManager) keyManager, keyPassword);
                    } else {
                        openSslExtendedKeyMaterialManager = new OpenSslKeyMaterialManager(keyManager, keyPassword);
                    }
                    result.keyMaterialManager = openSslExtendedKeyMaterialManager;
                } else if (keyManagerFactory != null) {
                    throw new IllegalArgumentException("KeyManagerFactory not supported");
                } else {
                    ObjectUtil.checkNotNull(keyCertChain, "keyCertChain");
                    SSLContext.setVerify(ctx, 0, 10);
                    setKeyMaterial(ctx, keyCertChain, key, keyPassword);
                }
                if (trustCertCollection != null) {
                    trustManagerFactory = buildTrustManagerFactory(trustCertCollection, trustManagerFactory);
                } else if (trustManagerFactory == null) {
                    trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init((KeyStore) null);
                }
                X509TrustManager manager = chooseTrustManager(trustManagerFactory.getTrustManagers());
                if (useExtendedTrustManager(manager)) {
                    SSLContext.setCertVerifyCallback(ctx, new ExtendedTrustManagerVerifyCallback(engineMap, (X509ExtendedTrustManager) manager));
                } else {
                    SSLContext.setCertVerifyCallback(ctx, new TrustManagerVerifyCallback(engineMap, manager));
                }
            } catch (Exception e) {
                throw new SSLException("unable to setup trustmanager", e);
            } catch (Exception e2) {
                throw new SSLException("failed to set certificate and key", e2);
            }
        }
        result.sessionContext = new OpenSslServerSessionContext(thiz);
        result.sessionContext.setSessionIdContext(ID);
        return result;
    }
}
