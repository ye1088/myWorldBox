package io.netty.handler.ssl;

import com.huluxia.framework.base.utils.UtilsRSA;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.util.internal.EmptyArrays;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

public abstract class SslContext {
    static final CertificateFactory X509_CERT_FACTORY;

    private static java.security.cert.X509Certificate[] getCertificatesFromBuffers(io.netty.buffer.ByteBuf[] r9) throws java.security.cert.CertificateException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Incorrect nodes count for selectOther: B:15:0x003b in [B:9:0x0033, B:15:0x003b, B:14:0x003c]
	at jadx.core.utils.BlockUtils.selectOther(BlockUtils.java:53)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:64)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r7 = "X.509";
        r2 = java.security.cert.CertificateFactory.getInstance(r7);
        r7 = r9.length;
        r6 = new java.security.cert.X509Certificate[r7];
        r3 = 0;
    L_0x000b:
        r7 = r9.length;	 Catch:{ all -> 0x002d }
        if (r3 >= r7) goto L_0x0020;	 Catch:{ all -> 0x002d }
    L_0x000e:
        r7 = new io.netty.buffer.ByteBufInputStream;	 Catch:{ all -> 0x002d }
        r8 = r9[r3];	 Catch:{ all -> 0x002d }
        r7.<init>(r8);	 Catch:{ all -> 0x002d }
        r7 = r2.generateCertificate(r7);	 Catch:{ all -> 0x002d }
        r7 = (java.security.cert.X509Certificate) r7;	 Catch:{ all -> 0x002d }
        r6[r3] = r7;	 Catch:{ all -> 0x002d }
        r3 = r3 + 1;
        goto L_0x000b;
    L_0x0020:
        r0 = r9;
        r5 = r0.length;
        r4 = 0;
    L_0x0023:
        if (r4 >= r5) goto L_0x003c;
    L_0x0025:
        r1 = r0[r4];
        r1.release();
        r4 = r4 + 1;
        goto L_0x0023;
    L_0x002d:
        r7 = move-exception;
        r0 = r9;
        r5 = r0.length;
        r4 = 0;
    L_0x0031:
        if (r4 >= r5) goto L_0x003b;
    L_0x0033:
        r1 = r0[r4];
        r1.release();
        r4 = r4 + 1;
        goto L_0x0031;
    L_0x003b:
        throw r7;
    L_0x003c:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.SslContext.getCertificatesFromBuffers(io.netty.buffer.ByteBuf[]):java.security.cert.X509Certificate[]");
    }

    public abstract ApplicationProtocolNegotiator applicationProtocolNegotiator();

    public abstract List<String> cipherSuites();

    public abstract boolean isClient();

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator);

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i);

    public abstract long sessionCacheSize();

    public abstract SSLSessionContext sessionContext();

    public abstract long sessionTimeout();

    static {
        try {
            X509_CERT_FACTORY = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new IllegalStateException("unable to instance X.509 CertificateFactory", e);
        }
    }

    public static SslProvider defaultServerProvider() {
        return defaultProvider();
    }

    public static SslProvider defaultClientProvider() {
        return defaultProvider();
    }

    private static SslProvider defaultProvider() {
        if (OpenSsl.isAvailable()) {
            return SslProvider.OPENSSL;
        }
        return SslProvider.JDK;
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile) throws SSLException {
        return newServerContext(certChainFile, keyFile, null);
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
        return newServerContext(null, certChainFile, keyFile, keyPassword);
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newServerContext(null, certChainFile, keyFile, keyPassword, (Iterable) ciphers, (Iterable) nextProtocols, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newServerContext(null, certChainFile, keyFile, keyPassword, (Iterable) ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile) throws SSLException {
        return newServerContext(provider, certChainFile, keyFile, null);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword) throws SSLException {
        return newServerContext(provider, certChainFile, keyFile, keyPassword, null, IdentityCipherSuiteFilter.INSTANCE, null, 0, 0);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newServerContext(provider, certChainFile, keyFile, keyPassword, (Iterable) ciphers, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newServerContext(provider, null, trustManagerFactory, certChainFile, keyFile, keyPassword, null, ciphers, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newServerContext(provider, null, null, certChainFile, keyFile, keyPassword, null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        try {
            return newServerContextInternal(provider, toX509Certificates(trustCertCollectionFile), trustManagerFactory, toX509Certificates(keyCertChainFile), toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, ClientAuth.NONE);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the server-side SSL context", e);
        }
    }

    static SslContext newServerContextInternal(SslProvider provider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth) throws SSLException {
        if (provider == null) {
            provider = defaultServerProvider();
        }
        switch (1.$SwitchMap$io$netty$handler$ssl$SslProvider[provider.ordinal()]) {
            case 1:
                return new JdkSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth);
            case 2:
                return new OpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, (Iterable) ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth);
            case 3:
                return new ReferenceCountedOpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth);
            default:
                throw new Error(provider.toString());
        }
    }

    @Deprecated
    public static SslContext newClientContext() throws SSLException {
        return newClientContext(null, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile) throws SSLException {
        return newClientContext(null, certChainFile);
    }

    @Deprecated
    public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, certChainFile, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newClientContext(null, certChainFile, trustManagerFactory, (Iterable) ciphers, (Iterable) nextProtocols, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newClientContext(null, certChainFile, trustManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider) throws SSLException {
        return newClientContext(provider, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile) throws SSLException {
        return newClientContext(provider, certChainFile, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(provider, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(provider, certChainFile, trustManagerFactory, null, IdentityCipherSuiteFilter.INSTANCE, null, 0, 0);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newClientContext(provider, certChainFile, trustManagerFactory, null, null, null, null, ciphers, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        return newClientContext(provider, certChainFile, trustManagerFactory, null, null, null, null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        try {
            return newClientContextInternal(provider, toX509Certificates(trustCertCollectionFile), trustManagerFactory, toX509Certificates(keyCertChainFile), toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the client-side SSL context", e);
        }
    }

    static SslContext newClientContextInternal(SslProvider provider, X509Certificate[] trustCert, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
        if (provider == null) {
            provider = defaultClientProvider();
        }
        switch (1.$SwitchMap$io$netty$handler$ssl$SslProvider[provider.ordinal()]) {
            case 1:
                return new JdkSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, (Iterable) ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
            case 2:
                return new OpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, (Iterable) ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
            case 3:
                return new ReferenceCountedOpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
            default:
                throw new Error(provider.toString());
        }
    }

    static ApplicationProtocolConfig toApplicationProtocolConfig(Iterable<String> nextProtocols) {
        if (nextProtocols == null) {
            return ApplicationProtocolConfig.DISABLED;
        }
        return new ApplicationProtocolConfig(Protocol.NPN_AND_ALPN, SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL, SelectedListenerFailureBehavior.ACCEPT, nextProtocols);
    }

    protected SslContext() {
    }

    public final boolean isServer() {
        return !isClient();
    }

    @Deprecated
    public final List<String> nextProtocols() {
        return applicationProtocolNegotiator().protocols();
    }

    public final SslHandler newHandler(ByteBufAllocator alloc) {
        return newHandler(newEngine(alloc));
    }

    public final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return newHandler(newEngine(alloc, peerHost, peerPort));
    }

    static SslHandler newHandler(SSLEngine engine) {
        return new SslHandler(engine);
    }

    protected static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (password == null) {
            return new PKCS8EncodedKeySpec(key);
        }
        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
        SecretKey pbeKey = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName()).generateSecret(new PBEKeySpec(password));
        Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
        cipher.init(2, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());
        return encryptedPrivateKeyInfo.getKeySpec(cipher);
    }

    static KeyStore buildKeyStore(X509Certificate[] certChain, PrivateKey key, char[] keyPasswordChars) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        ks.setKeyEntry("key", key, keyPasswordChars, certChain);
        return ks;
    }

    static PrivateKey toPrivateKey(File keyFile, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (keyFile == null) {
            return null;
        }
        return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyFile), keyPassword);
    }

    static PrivateKey toPrivateKey(InputStream keyInputStream, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (keyInputStream == null) {
            return null;
        }
        return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyInputStream), keyPassword);
    }

    private static PrivateKey getPrivateKeyFromByteBuffer(ByteBuf encodedKeyBuf, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        PrivateKey generatePrivate;
        byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
        encodedKeyBuf.readBytes(encodedKey).release();
        PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(keyPassword == null ? null : keyPassword.toCharArray(), encodedKey);
        try {
            generatePrivate = KeyFactory.getInstance(UtilsRSA.KEY_ALGORITHM).generatePrivate(encodedKeySpec);
        } catch (InvalidKeySpecException e) {
            try {
                generatePrivate = KeyFactory.getInstance("DSA").generatePrivate(encodedKeySpec);
            } catch (InvalidKeySpecException e2) {
                try {
                    generatePrivate = KeyFactory.getInstance("EC").generatePrivate(encodedKeySpec);
                } catch (InvalidKeySpecException e3) {
                    throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", e3);
                }
            }
        }
        return generatePrivate;
    }

    @Deprecated
    protected static TrustManagerFactory buildTrustManagerFactory(File certChainFile, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        return buildTrustManagerFactory(toX509Certificates(certChainFile), trustManagerFactory);
    }

    static X509Certificate[] toX509Certificates(File file) throws CertificateException {
        if (file == null) {
            return null;
        }
        return getCertificatesFromBuffers(PemReader.readCertificates(file));
    }

    static X509Certificate[] toX509Certificates(InputStream in) throws CertificateException {
        if (in == null) {
            return null;
        }
        return getCertificatesFromBuffers(PemReader.readCertificates(in));
    }

    static TrustManagerFactory buildTrustManagerFactory(X509Certificate[] certCollection, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        int i = 1;
        for (X509Certificate cert : certCollection) {
            ks.setCertificateEntry(Integer.toString(i), cert);
            i++;
        }
        if (trustManagerFactory == null) {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        }
        trustManagerFactory.init(ks);
        return trustManagerFactory;
    }

    static PrivateKey toPrivateKeyInternal(File keyFile, String keyPassword) throws SSLException {
        try {
            return toPrivateKey(keyFile, keyPassword);
        } catch (Exception e) {
            throw new SSLException(e);
        }
    }

    static X509Certificate[] toX509CertificatesInternal(File file) throws SSLException {
        try {
            return toX509Certificates(file);
        } catch (CertificateException e) {
            throw new SSLException(e);
        }
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChain, PrivateKey key, String keyPassword, KeyManagerFactory kmf) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        return buildKeyManagerFactory(certChain, algorithm, key, keyPassword, kmf);
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChainFile, String keyAlgorithm, PrivateKey key, String keyPassword, KeyManagerFactory kmf) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
        char[] keyPasswordChars = keyPassword == null ? EmptyArrays.EMPTY_CHARS : keyPassword.toCharArray();
        KeyStore ks = buildKeyStore(certChainFile, key, keyPasswordChars);
        if (kmf == null) {
            kmf = KeyManagerFactory.getInstance(keyAlgorithm);
        }
        kmf.init(ks, keyPasswordChars);
        return kmf;
    }

    static KeyManagerFactory buildDefaultKeyManagerFactory() throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(null, null);
        return keyManagerFactory;
    }
}
