package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;

public class JdkSslContext extends SslContext {
    static final List<String> DEFAULT_CIPHERS;
    static final String PROTOCOL = "TLS";
    static final String[] PROTOCOLS;
    static final Set<String> SUPPORTED_CIPHERS;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
    private final JdkApplicationProtocolNegotiator apn;
    private final String[] cipherSuites;
    private final ClientAuth clientAuth;
    private final boolean isClient;
    private final SSLContext sslContext;
    private final List<String> unmodifiableCipherSuites;

    static {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            SSLEngine engine = context.createSSLEngine();
            String[] supportedProtocols = engine.getSupportedProtocols();
            Set<String> supportedProtocolsSet = new HashSet(supportedProtocols.length);
            for (Object add : supportedProtocols) {
                supportedProtocolsSet.add(add);
            }
            List<String> protocols = new ArrayList();
            addIfSupported(supportedProtocolsSet, protocols, "TLSv1.2", "TLSv1.1", "TLSv1");
            if (protocols.isEmpty()) {
                PROTOCOLS = engine.getEnabledProtocols();
            } else {
                PROTOCOLS = (String[]) protocols.toArray(new String[protocols.size()]);
            }
            String[] supportedCiphers = engine.getSupportedCipherSuites();
            SUPPORTED_CIPHERS = new HashSet(supportedCiphers.length);
            for (Object add2 : supportedCiphers) {
                SUPPORTED_CIPHERS.add(add2);
            }
            List<String> ciphers = new ArrayList();
            addIfSupported(SUPPORTED_CIPHERS, ciphers, "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
            if (ciphers.isEmpty()) {
                for (String cipher : engine.getEnabledCipherSuites()) {
                    if (!cipher.contains("_RC4_")) {
                        ciphers.add(cipher);
                    }
                }
            }
            DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
            if (logger.isDebugEnabled()) {
                logger.debug("Default protocols (JDK): {} ", Arrays.asList(PROTOCOLS));
                logger.debug("Default cipher suites (JDK): {}", DEFAULT_CIPHERS);
            }
        } catch (Exception e) {
            throw new Error("failed to initialize the default SSL context", e);
        }
    }

    private static void addIfSupported(Set<String> supported, List<String> enabled, String... names) {
        for (String n : names) {
            if (supported.contains(n)) {
                enabled.add(n);
            }
        }
    }

    public JdkSslContext(SSLContext sslContext, boolean isClient, ClientAuth clientAuth) {
        this(sslContext, isClient, null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, clientAuth);
    }

    public JdkSslContext(SSLContext sslContext, boolean isClient, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, ClientAuth clientAuth) {
        this(sslContext, isClient, (Iterable) ciphers, cipherFilter, toNegotiator(apn, !isClient), clientAuth);
    }

    JdkSslContext(SSLContext sslContext, boolean isClient, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, ClientAuth clientAuth) {
        this.apn = (JdkApplicationProtocolNegotiator) ObjectUtil.checkNotNull(apn, "apn");
        this.clientAuth = (ClientAuth) ObjectUtil.checkNotNull(clientAuth, "clientAuth");
        this.cipherSuites = ((CipherSuiteFilter) ObjectUtil.checkNotNull(cipherFilter, "cipherFilter")).filterCipherSuites(ciphers, DEFAULT_CIPHERS, SUPPORTED_CIPHERS);
        this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
        this.sslContext = (SSLContext) ObjectUtil.checkNotNull(sslContext, "sslContext");
        this.isClient = isClient;
    }

    public final SSLContext context() {
        return this.sslContext;
    }

    public final boolean isClient() {
        return this.isClient;
    }

    public final SSLSessionContext sessionContext() {
        if (isServer()) {
            return context().getServerSessionContext();
        }
        return context().getClientSessionContext();
    }

    public final List<String> cipherSuites() {
        return this.unmodifiableCipherSuites;
    }

    public final long sessionCacheSize() {
        return (long) sessionContext().getSessionCacheSize();
    }

    public final long sessionTimeout() {
        return (long) sessionContext().getSessionTimeout();
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc) {
        return configureAndWrapEngine(context().createSSLEngine());
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return configureAndWrapEngine(context().createSSLEngine(peerHost, peerPort));
    }

    private SSLEngine configureAndWrapEngine(SSLEngine engine) {
        engine.setEnabledCipherSuites(this.cipherSuites);
        engine.setEnabledProtocols(PROTOCOLS);
        engine.setUseClientMode(isClient());
        if (isServer()) {
            switch (1.$SwitchMap$io$netty$handler$ssl$ClientAuth[this.clientAuth.ordinal()]) {
                case 1:
                    engine.setWantClientAuth(true);
                    break;
                case 2:
                    engine.setNeedClientAuth(true);
                    break;
            }
        }
        return this.apn.wrapperFactory().wrapSslEngine(engine, this.apn, isServer());
    }

    public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    static JdkApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config, boolean isServer) {
        if (config == null) {
            return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
        }
        switch (1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[config.protocol().ordinal()]) {
            case 1:
                return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
            case 2:
                if (isServer) {
                    switch (1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[config.selectorFailureBehavior().ordinal()]) {
                        case 1:
                            return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                        case 2:
                            return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
                    }
                }
                switch (1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[config.selectedListenerFailureBehavior().ordinal()]) {
                    case 1:
                        return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                    case 2:
                        return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                    default:
                        throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
                }
            case 3:
                if (isServer) {
                    switch (1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[config.selectedListenerFailureBehavior().ordinal()]) {
                        case 1:
                            return new JdkNpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                        case 2:
                            return new JdkNpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
                    }
                }
                switch (1.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[config.selectorFailureBehavior().ordinal()]) {
                    case 1:
                        return new JdkNpnApplicationProtocolNegotiator(true, config.supportedProtocols());
                    case 2:
                        return new JdkNpnApplicationProtocolNegotiator(false, config.supportedProtocols());
                    default:
                        throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
                }
            default:
                throw new UnsupportedOperationException("JDK provider does not support " + config.protocol() + " protocol");
        }
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, File keyFile, String keyPassword, KeyManagerFactory kmf) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        return buildKeyManagerFactory(certChainFile, algorithm, keyFile, keyPassword, kmf);
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, String keyAlgorithm, File keyFile, String keyPassword, KeyManagerFactory kmf) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
        return SslContext.buildKeyManagerFactory(SslContext.toX509Certificates(certChainFile), keyAlgorithm, SslContext.toPrivateKey(keyFile, keyPassword), keyPassword, kmf);
    }
}
