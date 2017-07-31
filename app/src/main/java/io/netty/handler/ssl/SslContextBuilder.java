package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.io.File;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

public final class SslContextBuilder {
    private ApplicationProtocolConfig apn;
    private CipherSuiteFilter cipherFilter = IdentityCipherSuiteFilter.INSTANCE;
    private Iterable<String> ciphers;
    private ClientAuth clientAuth = ClientAuth.NONE;
    private final boolean forServer;
    private PrivateKey key;
    private X509Certificate[] keyCertChain;
    private KeyManagerFactory keyManagerFactory;
    private String keyPassword;
    private SslProvider provider;
    private long sessionCacheSize;
    private long sessionTimeout;
    private X509Certificate[] trustCertCollection;
    private TrustManagerFactory trustManagerFactory;

    public static SslContextBuilder forClient() {
        return new SslContextBuilder(false);
    }

    public static SslContextBuilder forServer(File keyCertChainFile, File keyFile) {
        return new SslContextBuilder(true).keyManager(keyCertChainFile, keyFile);
    }

    public static SslContextBuilder forServer(InputStream keyCertChainInputStream, InputStream keyInputStream) {
        return new SslContextBuilder(true).keyManager(keyCertChainInputStream, keyInputStream);
    }

    public static SslContextBuilder forServer(PrivateKey key, X509Certificate... keyCertChain) {
        return new SslContextBuilder(true).keyManager(key, keyCertChain);
    }

    public static SslContextBuilder forServer(File keyCertChainFile, File keyFile, String keyPassword) {
        return new SslContextBuilder(true).keyManager(keyCertChainFile, keyFile, keyPassword);
    }

    public static SslContextBuilder forServer(InputStream keyCertChainInputStream, InputStream keyInputStream, String keyPassword) {
        return new SslContextBuilder(true).keyManager(keyCertChainInputStream, keyInputStream, keyPassword);
    }

    public static SslContextBuilder forServer(PrivateKey key, String keyPassword, X509Certificate... keyCertChain) {
        return new SslContextBuilder(true).keyManager(key, keyPassword, keyCertChain);
    }

    public static SslContextBuilder forServer(KeyManagerFactory keyManagerFactory) {
        return new SslContextBuilder(true).keyManager(keyManagerFactory);
    }

    private SslContextBuilder(boolean forServer) {
        this.forServer = forServer;
    }

    public SslContextBuilder sslProvider(SslProvider provider) {
        this.provider = provider;
        return this;
    }

    public SslContextBuilder trustManager(File trustCertCollectionFile) {
        try {
            return trustManager(SslContext.toX509Certificates(trustCertCollectionFile));
        } catch (Exception e) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + trustCertCollectionFile, e);
        }
    }

    public SslContextBuilder trustManager(InputStream trustCertCollectionInputStream) {
        try {
            return trustManager(SslContext.toX509Certificates(trustCertCollectionInputStream));
        } catch (Exception e) {
            throw new IllegalArgumentException("Input stream does not contain valid certificates.", e);
        }
    }

    public SslContextBuilder trustManager(X509Certificate... trustCertCollection) {
        X509Certificate[] x509CertificateArr;
        if (trustCertCollection != null) {
            x509CertificateArr = (X509Certificate[]) trustCertCollection.clone();
        } else {
            x509CertificateArr = null;
        }
        this.trustCertCollection = x509CertificateArr;
        this.trustManagerFactory = null;
        return this;
    }

    public SslContextBuilder trustManager(TrustManagerFactory trustManagerFactory) {
        this.trustCertCollection = null;
        this.trustManagerFactory = trustManagerFactory;
        return this;
    }

    public SslContextBuilder keyManager(File keyCertChainFile, File keyFile) {
        return keyManager(keyCertChainFile, keyFile, null);
    }

    public SslContextBuilder keyManager(InputStream keyCertChainInputStream, InputStream keyInputStream) {
        return keyManager(keyCertChainInputStream, keyInputStream, null);
    }

    public SslContextBuilder keyManager(PrivateKey key, X509Certificate... keyCertChain) {
        return keyManager(key, null, keyCertChain);
    }

    public SslContextBuilder keyManager(File keyCertChainFile, File keyFile, String keyPassword) {
        try {
            try {
                return keyManager(SslContext.toPrivateKey(keyFile, keyPassword), keyPassword, SslContext.toX509Certificates(keyCertChainFile));
            } catch (Exception e) {
                throw new IllegalArgumentException("File does not contain valid private key: " + keyFile, e);
            }
        } catch (Exception e2) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + keyCertChainFile, e2);
        }
    }

    public SslContextBuilder keyManager(InputStream keyCertChainInputStream, InputStream keyInputStream, String keyPassword) {
        try {
            try {
                return keyManager(SslContext.toPrivateKey(keyInputStream, keyPassword), keyPassword, SslContext.toX509Certificates(keyCertChainInputStream));
            } catch (Exception e) {
                throw new IllegalArgumentException("Input stream does not contain valid private key.", e);
            }
        } catch (Exception e2) {
            throw new IllegalArgumentException("Input stream not contain valid certificates.", e2);
        }
    }

    public SslContextBuilder keyManager(PrivateKey key, String keyPassword, X509Certificate... keyCertChain) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(keyCertChain, "keyCertChain required for servers");
            if (keyCertChain.length == 0) {
                throw new IllegalArgumentException("keyCertChain must be non-empty");
            }
            ObjectUtil.checkNotNull(key, "key required for servers");
        }
        if (keyCertChain == null || keyCertChain.length == 0) {
            this.keyCertChain = null;
        } else {
            for (X509Certificate cert : keyCertChain) {
                if (cert == null) {
                    throw new IllegalArgumentException("keyCertChain contains null entry");
                }
            }
            this.keyCertChain = (X509Certificate[]) keyCertChain.clone();
        }
        this.key = key;
        this.keyPassword = keyPassword;
        this.keyManagerFactory = null;
        return this;
    }

    public SslContextBuilder keyManager(KeyManagerFactory keyManagerFactory) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(keyManagerFactory, "keyManagerFactory required for servers");
        }
        this.keyCertChain = null;
        this.key = null;
        this.keyPassword = null;
        this.keyManagerFactory = keyManagerFactory;
        return this;
    }

    public SslContextBuilder ciphers(Iterable<String> ciphers) {
        return ciphers(ciphers, IdentityCipherSuiteFilter.INSTANCE);
    }

    public SslContextBuilder ciphers(Iterable<String> ciphers, CipherSuiteFilter cipherFilter) {
        ObjectUtil.checkNotNull(cipherFilter, "cipherFilter");
        this.ciphers = ciphers;
        this.cipherFilter = cipherFilter;
        return this;
    }

    public SslContextBuilder applicationProtocolConfig(ApplicationProtocolConfig apn) {
        this.apn = apn;
        return this;
    }

    public SslContextBuilder sessionCacheSize(long sessionCacheSize) {
        this.sessionCacheSize = sessionCacheSize;
        return this;
    }

    public SslContextBuilder sessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public SslContextBuilder clientAuth(ClientAuth clientAuth) {
        this.clientAuth = (ClientAuth) ObjectUtil.checkNotNull(clientAuth, "clientAuth");
        return this;
    }

    public SslContext build() throws SSLException {
        if (this.forServer) {
            return SslContext.newServerContextInternal(this.provider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.sessionCacheSize, this.sessionTimeout, this.clientAuth);
        }
        return SslContext.newClientContextInternal(this.provider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.sessionCacheSize, this.sessionTimeout);
    }
}
