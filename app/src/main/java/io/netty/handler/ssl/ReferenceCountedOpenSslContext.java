package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLContext;
import org.apache.tools.zip.UnixStat;
import org.bytedeco.javacpp.swscale;

public abstract class ReferenceCountedOpenSslContext extends SslContext implements ReferenceCounted {
    private static final List<String> DEFAULT_CIPHERS;
    private static final Integer DH_KEY_LENGTH;
    private static final boolean JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION = SystemPropertyUtil.getBoolean("jdk.tls.rejectClientInitiatedRenegotiation", false);
    static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR = new 2();
    protected static final int VERIFY_DEPTH = 10;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslContext> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslContext.class);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslContext.class);
    private final OpenSslApplicationProtocolNegotiator apn;
    long aprPool;
    private volatile int aprPoolDestroyed;
    final ClientAuth clientAuth;
    protected volatile long ctx;
    final OpenSslEngineMap engineMap;
    final Certificate[] keyCertChain;
    private final ResourceLeak leak;
    private final int mode;
    private final AbstractReferenceCounted refCnt;
    volatile boolean rejectRemoteInitiatedRenegotiation;
    private final long sessionCacheSize;
    private final long sessionTimeout;
    private final List<String> unmodifiableCiphers;

    abstract OpenSslKeyMaterialManager keyMaterialManager();

    public abstract OpenSslSessionContext sessionContext();

    static {
        List<String> ciphers = new ArrayList();
        Collections.addAll(ciphers, new String[]{"ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-AES256-SHA", "AES128-GCM-SHA256", "AES128-SHA", "AES256-SHA", "DES-CBC3-SHA"});
        DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
        if (logger.isDebugEnabled()) {
            logger.debug("Default cipher suite (OpenSSL): " + ciphers);
        }
        Integer dhLen = null;
        String dhKeySize;
        try {
            dhKeySize = (String) AccessController.doPrivileged(new 3());
            if (dhKeySize != null) {
                dhLen = Integer.valueOf(dhKeySize);
            }
        } catch (NumberFormatException e) {
            logger.debug("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: " + dhKeySize);
        } catch (Throwable th) {
        }
        DH_KEY_LENGTH = dhLen;
    }

    ReferenceCountedOpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, boolean leakDetection) throws SSLException {
        this((Iterable) ciphers, cipherFilter, toNegotiator(apnCfg), sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, leakDetection);
    }

    ReferenceCountedOpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, boolean leakDetection) throws SSLException {
        this.refCnt = new 1(this);
        this.engineMap = new DefaultOpenSslEngineMap(null);
        OpenSsl.ensureAvailability();
        if (mode == 1 || mode == 0) {
            List<String> convertedCiphers;
            this.leak = leakDetection ? leakDetector.open(this) : null;
            this.mode = mode;
            this.clientAuth = isServer() ? (ClientAuth) ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
            if (mode == 1) {
                this.rejectRemoteInitiatedRenegotiation = JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION;
            }
            this.keyCertChain = keyCertChain == null ? null : (Certificate[]) keyCertChain.clone();
            if (ciphers != null) {
                convertedCiphers = new ArrayList();
                for (String c : ciphers) {
                    String c2;
                    if (c2 == null) {
                        break;
                    }
                    String converted = CipherSuiteConverter.toOpenSsl(c2);
                    if (converted != null) {
                        c2 = converted;
                    }
                    convertedCiphers.add(c2);
                }
            } else {
                convertedCiphers = null;
            }
            this.unmodifiableCiphers = Arrays.asList(((CipherSuiteFilter) ObjectUtil.checkNotNull(cipherFilter, "cipherFilter")).filterCipherSuites(convertedCiphers, DEFAULT_CIPHERS, OpenSsl.availableCipherSuites()));
            this.apn = (OpenSslApplicationProtocolNegotiator) ObjectUtil.checkNotNull(apn, "apn");
            this.aprPool = Pool.create(0);
            try {
                synchronized (ReferenceCountedOpenSslContext.class) {
                    this.ctx = SSLContext.make(this.aprPool, 31, mode);
                    SSLContext.setOptions(this.ctx, UnixStat.PERM_MASK);
                    SSLContext.setOptions(this.ctx, 16777216);
                    SSLContext.setOptions(this.ctx, swscale.SWS_CPU_CAPS_SSE2);
                    SSLContext.setOptions(this.ctx, 4194304);
                    SSLContext.setOptions(this.ctx, 524288);
                    SSLContext.setOptions(this.ctx, 1048576);
                    SSLContext.setOptions(this.ctx, 65536);
                    SSLContext.setOptions(this.ctx, 16384);
                    SSLContext.setMode(this.ctx, SSLContext.getMode(this.ctx) | 2);
                    if (DH_KEY_LENGTH != null) {
                        SSLContext.setTmpDHLength(this.ctx, DH_KEY_LENGTH.intValue());
                    }
                    SSLContext.setCipherSuite(this.ctx, CipherSuiteConverter.toOpenSsl(this.unmodifiableCiphers));
                    List<String> nextProtoList = apn.protocols();
                    if (!nextProtoList.isEmpty()) {
                        String[] protocols = (String[]) nextProtoList.toArray(new String[nextProtoList.size()]);
                        int selectorBehavior = opensslSelectorFailureBehavior(apn.selectorFailureBehavior());
                        switch (4.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[apn.protocol().ordinal()]) {
                            case 1:
                                SSLContext.setNpnProtos(this.ctx, protocols, selectorBehavior);
                                break;
                            case 2:
                                SSLContext.setAlpnProtos(this.ctx, protocols, selectorBehavior);
                                break;
                            case 3:
                                SSLContext.setNpnProtos(this.ctx, protocols, selectorBehavior);
                                SSLContext.setAlpnProtos(this.ctx, protocols, selectorBehavior);
                                break;
                            default:
                                throw new Error();
                        }
                    }
                    if (sessionCacheSize > 0) {
                        this.sessionCacheSize = sessionCacheSize;
                        SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
                    } else {
                        sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480);
                        this.sessionCacheSize = sessionCacheSize;
                        SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
                    }
                    if (sessionTimeout > 0) {
                        this.sessionTimeout = sessionTimeout;
                        SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
                    } else {
                        sessionTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300);
                        this.sessionTimeout = sessionTimeout;
                        SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
                    }
                }
                if (!true) {
                    release();
                }
            } catch (SSLException e) {
                throw e;
            } catch (Exception e2) {
                throw new SSLException("failed to set cipher suite: " + this.unmodifiableCiphers, e2);
            } catch (Exception e22) {
                throw new SSLException("failed to create an SSL_CTX", e22);
            } catch (Throwable th) {
                if (!false) {
                    release();
                }
            }
        } else {
            throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
        }
    }

    private static int opensslSelectorFailureBehavior(SelectorFailureBehavior behavior) {
        switch (4.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[behavior.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                throw new Error();
        }
    }

    public final List<String> cipherSuites() {
        return this.unmodifiableCiphers;
    }

    public final long sessionCacheSize() {
        return this.sessionCacheSize;
    }

    public final long sessionTimeout() {
        return this.sessionTimeout;
    }

    public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    public final boolean isClient() {
        return this.mode == 0;
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return newEngine0(alloc, peerHost, peerPort);
    }

    SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return new ReferenceCountedOpenSslEngine(this, alloc, peerHost, peerPort, true);
    }

    public final SSLEngine newEngine(ByteBufAllocator alloc) {
        return newEngine(alloc, null, -1);
    }

    @Deprecated
    public final long context() {
        return this.ctx;
    }

    @Deprecated
    public final OpenSslSessionStats stats() {
        return sessionContext().stats();
    }

    public void setRejectRemoteInitiatedRenegotiation(boolean rejectRemoteInitiatedRenegotiation) {
        this.rejectRemoteInitiatedRenegotiation = rejectRemoteInitiatedRenegotiation;
    }

    @Deprecated
    public final void setTicketKeys(byte[] keys) {
        sessionContext().setTicketKeys(keys);
    }

    public final long sslCtxPointer() {
        return this.ctx;
    }

    final void destroy() {
        synchronized (ReferenceCountedOpenSslContext.class) {
            if (this.ctx != 0) {
                SSLContext.free(this.ctx);
                this.ctx = 0;
            }
            if (this.aprPool != 0) {
                Pool.destroy(this.aprPool);
                this.aprPool = 0;
            }
        }
    }

    protected static X509Certificate[] certificates(byte[][] chain) {
        X509Certificate[] peerCerts = new X509Certificate[chain.length];
        for (int i = 0; i < peerCerts.length; i++) {
            peerCerts[i] = new OpenSslX509Certificate(chain[i]);
        }
        return peerCerts;
    }

    protected static X509TrustManager chooseTrustManager(TrustManager[] managers) {
        for (TrustManager m : managers) {
            if (m instanceof X509TrustManager) {
                return (X509TrustManager) m;
            }
        }
        throw new IllegalStateException("no X509TrustManager found");
    }

    protected static X509KeyManager chooseX509KeyManager(KeyManager[] kms) {
        for (KeyManager km : kms) {
            if (km instanceof X509KeyManager) {
                return (X509KeyManager) km;
            }
        }
        throw new IllegalStateException("no X509KeyManager found");
    }

    static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config) {
        if (config == null) {
            return NONE_PROTOCOL_NEGOTIATOR;
        }
        switch (4.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[config.protocol().ordinal()]) {
            case 1:
            case 2:
            case 3:
                switch (4.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[config.selectedListenerFailureBehavior().ordinal()]) {
                    case 1:
                    case 2:
                        switch (4.$SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[config.selectorFailureBehavior().ordinal()]) {
                            case 1:
                            case 2:
                                return new OpenSslDefaultApplicationProtocolNegotiator(config);
                            default:
                                throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectorFailureBehavior() + " behavior");
                        }
                    default:
                        throw new UnsupportedOperationException("OpenSSL provider does not support " + config.selectedListenerFailureBehavior() + " behavior");
                }
            case 4:
                return NONE_PROTOCOL_NEGOTIATOR;
            default:
                throw new Error();
        }
    }

    static boolean useExtendedTrustManager(X509TrustManager trustManager) {
        return PlatformDependent.javaVersion() >= 7 && (trustManager instanceof X509ExtendedTrustManager);
    }

    static boolean useExtendedKeyManager(X509KeyManager keyManager) {
        return PlatformDependent.javaVersion() >= 7 && (keyManager instanceof X509ExtendedKeyManager);
    }

    public final int refCnt() {
        return this.refCnt.refCnt();
    }

    public final ReferenceCounted retain() {
        this.refCnt.retain();
        return this;
    }

    public final ReferenceCounted retain(int increment) {
        this.refCnt.retain(increment);
        return this;
    }

    public final ReferenceCounted touch() {
        this.refCnt.touch();
        return this;
    }

    public final ReferenceCounted touch(Object hint) {
        this.refCnt.touch(hint);
        return this;
    }

    public final boolean release() {
        return this.refCnt.release();
    }

    public final boolean release(int decrement) {
        return this.refCnt.release(decrement);
    }

    static void setKeyMaterial(long ctx, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword) throws SSLException {
        long keyBio = 0;
        PemEncoded encoded = null;
        try {
            encoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, keyCertChain);
            long keyCertChainBio = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
            long keyCertChainBio2 = toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
            if (key != null) {
                keyBio = toBIO(key);
            }
            SSLContext.setCertificateBio(ctx, keyCertChainBio, keyBio, keyPassword == null ? "" : keyPassword);
            SSLContext.setCertificateChainBio(ctx, keyCertChainBio2, false);
            freeBio(keyBio);
            freeBio(keyCertChainBio);
            freeBio(keyCertChainBio2);
            if (encoded != null) {
                encoded.release();
            }
        } catch (SSLException e) {
            throw e;
        } catch (Exception e2) {
            throw new SSLException("failed to set certificate and key", e2);
        } catch (Throwable th) {
            freeBio(0);
            freeBio(0);
            freeBio(0);
            if (encoded != null) {
                encoded.release();
            }
        }
    }

    static void freeBio(long bio) {
        if (bio != 0) {
            SSL.freeBIO(bio);
        }
    }

    static long toBIO(PrivateKey key) throws Exception {
        if (key == null) {
            return 0;
        }
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        PemEncoded pem = PemPrivateKey.toPEM(allocator, true, key);
        try {
            long toBIO = toBIO(allocator, pem.retain());
            return toBIO;
        } finally {
            pem.release();
        }
    }

    static long toBIO(X509Certificate... certChain) throws Exception {
        if (certChain == null) {
            return 0;
        }
        if (certChain.length == 0) {
            throw new IllegalArgumentException("certChain can't be empty");
        }
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        PemEncoded pem = PemX509Certificate.toPEM(allocator, true, certChain);
        try {
            long toBIO = toBIO(allocator, pem.retain());
            return toBIO;
        } finally {
            pem.release();
        }
    }

    static long toBIO(ByteBufAllocator allocator, PemEncoded pem) throws Exception {
        ByteBuf buffer;
        try {
            long newBIO;
            ByteBuf content = pem.content();
            if (content.isDirect()) {
                newBIO = newBIO(content.retainedSlice());
                pem.release();
            } else {
                buffer = allocator.directBuffer(content.readableBytes());
                buffer.writeBytes(content, content.readerIndex(), content.readableBytes());
                newBIO = newBIO(buffer.retainedSlice());
                if (pem.isSensitive()) {
                    SslUtils.zeroout(buffer);
                }
                buffer.release();
                pem.release();
            }
            return newBIO;
        } catch (Throwable th) {
            pem.release();
        }
    }

    private static long newBIO(ByteBuf buffer) throws Exception {
        try {
            long bio = SSL.newMemBIO();
            int readable = buffer.readableBytes();
            if (SSL.writeToBIO(bio, OpenSsl.memoryAddress(buffer) + ((long) buffer.readerIndex()), readable) == readable) {
                return bio;
            }
            SSL.freeBIO(bio);
            throw new IllegalStateException("Could not write data to memory BIO");
        } finally {
            buffer.release();
        }
    }
}
