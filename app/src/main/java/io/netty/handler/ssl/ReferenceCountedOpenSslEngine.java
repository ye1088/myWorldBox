package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.tomcat.jni.Buffer;
import org.apache.tomcat.jni.SSL;
import org.apache.tools.zip.UnixStat;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacpp.swscale;

public class ReferenceCountedOpenSslEngine extends SSLEngine implements ReferenceCounted {
    static final /* synthetic */ boolean $assertionsDisabled = (!ReferenceCountedOpenSslEngine.class.desiredAssertionStatus());
    private static final SSLException BEGIN_HANDSHAKE_ENGINE_CLOSED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "beginHandshake()"));
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    private static final AtomicIntegerFieldUpdater<ReferenceCountedOpenSslEngine> DESTROYED_UPDATER;
    private static final long EMPTY_ADDR = Buffer.address(Unpooled.EMPTY_BUFFER.nioBuffer());
    private static final Certificate[] EMPTY_CERTIFICATES = EmptyArrays.EMPTY_CERTIFICATES;
    private static final X509Certificate[] EMPTY_X509_CERTIFICATES = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
    private static final SSLException ENCRYPTED_PACKET_OVERSIZED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("encrypted packet oversized"), ReferenceCountedOpenSslEngine.class, "unwrap(...)"));
    private static final Method GET_ASCII_NAME_METHOD;
    private static final Method GET_SERVER_NAMES_METHOD;
    private static final Method GET_USE_CIPHER_SUITES_ORDER_METHOD;
    private static final SSLException HANDSHAKE_ENGINE_CLOSED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "handshake()"));
    private static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
    private static final int MAX_CIPHERTEXT_LENGTH = 18432;
    private static final int MAX_COMPRESSED_LENGTH = 17408;
    static final int MAX_ENCRYPTED_PACKET_LENGTH = 18713;
    static final int MAX_ENCRYPTION_OVERHEAD_LENGTH = 2329;
    private static final int MAX_PLAINTEXT_LENGTH = 16384;
    private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLException RENEGOTIATION_UNSUPPORTED = ((SSLException) ThrowableUtil.unknownStackTrace(new SSLException("renegotiation unsupported"), ReferenceCountedOpenSslEngine.class, "beginHandshake()"));
    private static final Method SET_SERVER_NAMES_METHOD;
    private static final Method SET_USE_CIPHER_SUITES_ORDER_METHOD;
    private static final Class<?> SNI_HOSTNAME_CLASS;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslEngine.class);
    private Object algorithmConstraints;
    private final ByteBufAllocator alloc;
    private final OpenSslApplicationProtocolNegotiator apn;
    private boolean certificateSet;
    private volatile ClientAuth clientAuth = ClientAuth.NONE;
    private final boolean clientMode;
    private volatile int destroyed;
    private String endPointIdentificationAlgorithm;
    private boolean engineClosed;
    private final OpenSslEngineMap engineMap;
    SSLHandshakeException handshakeException;
    private HandshakeState handshakeState = HandshakeState.NOT_STARTED;
    private boolean isInboundDone;
    private boolean isOutboundDone;
    private final OpenSslKeyMaterialManager keyMaterialManager;
    private volatile long lastAccessed = -1;
    private final ResourceLeak leak;
    private final Certificate[] localCerts;
    private long networkBIO;
    private boolean receivedShutdown;
    private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted() {
        public ReferenceCounted touch(Object hint) {
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
                ReferenceCountedOpenSslEngine.this.leak.record(hint);
            }
            return ReferenceCountedOpenSslEngine.this;
        }

        protected void deallocate() {
            ReferenceCountedOpenSslEngine.this.shutdown();
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
                ReferenceCountedOpenSslEngine.this.leak.close();
            }
        }
    };
    private final boolean rejectRemoteInitiatedRenegation;
    private final OpenSslSession session;
    private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
    private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
    private List<?> sniHostNames;
    private long ssl;

    private enum HandshakeState {
        NOT_STARTED,
        STARTED_IMPLICITLY,
        STARTED_EXPLICITLY,
        FINISHED
    }

    private final class OpenSslSession implements ApplicationProtocolAccessor, SSLSession {
        static final /* synthetic */ boolean $assertionsDisabled = (!ReferenceCountedOpenSslEngine.class.desiredAssertionStatus());
        private String applicationProtocol;
        private String cipher;
        private long creationTime;
        private byte[] id;
        private Certificate[] peerCerts;
        private String protocol;
        private final OpenSslSessionContext sessionContext;
        private Map<String, Object> values;
        private X509Certificate[] x509PeerCerts;

        OpenSslSession(OpenSslSessionContext sessionContext) {
            this.sessionContext = sessionContext;
        }

        public byte[] getId() {
            byte[] bArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.id == null) {
                    bArr = EmptyArrays.EMPTY_BYTES;
                } else {
                    bArr = (byte[]) this.id.clone();
                }
            }
            return bArr;
        }

        public SSLSessionContext getSessionContext() {
            return this.sessionContext;
        }

        public long getCreationTime() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.creationTime == 0 && !ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.creationTime = SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000;
                }
            }
            return this.creationTime;
        }

        public long getLastAccessedTime() {
            long lastAccessed = ReferenceCountedOpenSslEngine.this.lastAccessed;
            return lastAccessed == -1 ? getCreationTime() : lastAccessed;
        }

        public void invalidate() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    SSL.setTimeout(ReferenceCountedOpenSslEngine.this.ssl, 0);
                }
            }
        }

        public boolean isValid() {
            boolean z = false;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                } else {
                    if (System.currentTimeMillis() - (SSL.getTimeout(ReferenceCountedOpenSslEngine.this.ssl) * 1000) < SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000) {
                        z = true;
                    }
                }
            }
            return z;
        }

        public void putValue(String name, Object value) {
            if (name == null) {
                throw new NullPointerException("name");
            } else if (value == null) {
                throw new NullPointerException("value");
            } else {
                Map<String, Object> values = this.values;
                if (values == null) {
                    values = new HashMap(2);
                    this.values = values;
                }
                Object old = values.put(name, value);
                if (value instanceof SSLSessionBindingListener) {
                    ((SSLSessionBindingListener) value).valueBound(new SSLSessionBindingEvent(this, name));
                }
                notifyUnbound(old, name);
            }
        }

        public Object getValue(String name) {
            if (name == null) {
                throw new NullPointerException("name");
            } else if (this.values == null) {
                return null;
            } else {
                return this.values.get(name);
            }
        }

        public void removeValue(String name) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            Map<String, Object> values = this.values;
            if (values != null) {
                notifyUnbound(values.remove(name), name);
            }
        }

        public String[] getValueNames() {
            Map<String, Object> values = this.values;
            if (values == null || values.isEmpty()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            return (String[]) values.keySet().toArray(new String[values.size()]);
        }

        private void notifyUnbound(Object value, String name) {
            if (value instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener) value).valueUnbound(new SSLSessionBindingEvent(this, name));
            }
        }

        void handshakeFinished() throws SSLException {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    throw new SSLException("Already closed");
                }
                this.id = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
                this.cipher = ReferenceCountedOpenSslEngine.this.toJavaCipherSuite(SSL.getCipherForSSL(ReferenceCountedOpenSslEngine.this.ssl));
                this.protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                initPeerCerts();
                selectApplicationProtocol();
                ReferenceCountedOpenSslEngine.this.handshakeState = HandshakeState.FINISHED;
            }
        }

        private void initPeerCerts() {
            byte[] clientCert;
            byte[][] chain = SSL.getPeerCertChain(ReferenceCountedOpenSslEngine.this.ssl);
            if (ReferenceCountedOpenSslEngine.this.clientMode) {
                clientCert = null;
            } else {
                clientCert = SSL.getPeerCertificate(ReferenceCountedOpenSslEngine.this.ssl);
            }
            if (chain == null && clientCert == null) {
                this.peerCerts = ReferenceCountedOpenSslEngine.EMPTY_CERTIFICATES;
                this.x509PeerCerts = ReferenceCountedOpenSslEngine.EMPTY_X509_CERTIFICATES;
                return;
            }
            Certificate[] peerCerts;
            int len = chain != null ? chain.length : 0;
            int i = 0;
            if (clientCert != null) {
                peerCerts = new Certificate[(len + 1)];
                int i2 = 0 + 1;
                peerCerts[0] = new OpenSslX509Certificate(clientCert);
                i = i2;
            } else {
                peerCerts = new Certificate[len];
            }
            if (chain != null) {
                X509Certificate[] pCerts = new X509Certificate[chain.length];
                for (int a = 0; a < pCerts.length; a++) {
                    byte[] bytes = chain[a];
                    pCerts[a] = new OpenSslJavaxX509Certificate(bytes);
                    peerCerts[i] = new OpenSslX509Certificate(bytes);
                    i++;
                }
                this.x509PeerCerts = pCerts;
            } else {
                this.x509PeerCerts = ReferenceCountedOpenSslEngine.EMPTY_X509_CERTIFICATES;
            }
            this.peerCerts = peerCerts;
        }

        private void selectApplicationProtocol() throws SSLException {
            SelectedListenerFailureBehavior behavior = ReferenceCountedOpenSslEngine.this.apn.selectedListenerFailureBehavior();
            List<String> protocols = ReferenceCountedOpenSslEngine.this.apn.protocols();
            String applicationProtocol;
            switch (ReferenceCountedOpenSslEngine.this.apn.protocol()) {
                case NONE:
                    return;
                case ALPN:
                    applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol != null) {
                        this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol);
                        return;
                    }
                    return;
                case NPN:
                    applicationProtocol = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol != null) {
                        this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol);
                        return;
                    }
                    return;
                case NPN_AND_ALPN:
                    applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol == null) {
                        applicationProtocol = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    }
                    if (applicationProtocol != null) {
                        this.applicationProtocol = selectApplicationProtocol(protocols, behavior, applicationProtocol);
                        return;
                    }
                    return;
                default:
                    throw new Error();
            }
        }

        private String selectApplicationProtocol(List<String> protocols, SelectedListenerFailureBehavior behavior, String applicationProtocol) throws SSLException {
            if (behavior == SelectedListenerFailureBehavior.ACCEPT) {
                return applicationProtocol;
            }
            int size = protocols.size();
            if (!$assertionsDisabled && size <= 0) {
                throw new AssertionError();
            } else if (protocols.contains(applicationProtocol)) {
                return applicationProtocol;
            } else {
                if (behavior == SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
                    return (String) protocols.get(size - 1);
                }
                throw new SSLException("unknown protocol " + applicationProtocol);
            }
        }

        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            Certificate[] certificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.peerCerts == null || this.peerCerts.length == 0) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                certificateArr = this.peerCerts;
            }
            return certificateArr;
        }

        public Certificate[] getLocalCertificates() {
            if (ReferenceCountedOpenSslEngine.this.localCerts == null) {
                return null;
            }
            return (Certificate[]) ReferenceCountedOpenSslEngine.this.localCerts.clone();
        }

        public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
            X509Certificate[] x509CertificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.x509PeerCerts == null || this.x509PeerCerts.length == 0) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                x509CertificateArr = this.x509PeerCerts;
            }
            return x509CertificateArr;
        }

        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            return ((java.security.cert.X509Certificate) getPeerCertificates()[0]).getSubjectX500Principal();
        }

        public Principal getLocalPrincipal() {
            Certificate[] local = ReferenceCountedOpenSslEngine.this.localCerts;
            if (local == null || local.length == 0) {
                return null;
            }
            return ((java.security.cert.X509Certificate) local[0]).getIssuerX500Principal();
        }

        public String getCipherSuite() {
            String str;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.cipher == null) {
                    str = ReferenceCountedOpenSslEngine.INVALID_CIPHER;
                } else {
                    str = this.cipher;
                }
            }
            return str;
        }

        public String getProtocol() {
            String protocol = this.protocol;
            if (protocol == null) {
                synchronized (ReferenceCountedOpenSslEngine.this) {
                    if (ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                        protocol = "";
                    } else {
                        protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                    }
                }
            }
            return protocol;
        }

        public String getApplicationProtocol() {
            String str;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                str = this.applicationProtocol;
            }
            return str;
        }

        public String getPeerHost() {
            return ReferenceCountedOpenSslEngine.this.getPeerHost();
        }

        public int getPeerPort() {
            return ReferenceCountedOpenSslEngine.this.getPeerPort();
        }

        public int getPacketBufferSize() {
            return ReferenceCountedOpenSslEngine.MAX_ENCRYPTED_PACKET_LENGTH;
        }

        public int getApplicationBufferSize() {
            return 16384;
        }
    }

    static {
        AtomicIntegerFieldUpdater<ReferenceCountedOpenSslEngine> destroyedUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(ReferenceCountedOpenSslEngine.class, "destroyed");
        if (destroyedUpdater == null) {
            destroyedUpdater = AtomicIntegerFieldUpdater.newUpdater(ReferenceCountedOpenSslEngine.class, "destroyed");
        }
        DESTROYED_UPDATER = destroyedUpdater;
        Method getUseCipherSuitesOrderMethod = null;
        Method setUseCipherSuitesOrderMethod = null;
        Class<?> sniHostNameClass = null;
        Method getAsciiNameMethod = null;
        Method getServerNamesMethod = null;
        Method setServerNamesMethod = null;
        if (PlatformDependent.javaVersion() >= 8) {
            SSLParameters parameters;
            try {
                getUseCipherSuitesOrderMethod = SSLParameters.class.getDeclaredMethod("getUseCipherSuitesOrder", new Class[0]);
                parameters = new SSLParameters();
                Boolean order = (Boolean) getUseCipherSuitesOrderMethod.invoke(parameters, new Object[0]);
                setUseCipherSuitesOrderMethod = SSLParameters.class.getDeclaredMethod("setUseCipherSuitesOrder", new Class[]{Boolean.TYPE});
                setUseCipherSuitesOrderMethod.invoke(parameters, new Object[]{Boolean.valueOf(true)});
            } catch (Throwable th) {
                getUseCipherSuitesOrderMethod = null;
                setUseCipherSuitesOrderMethod = null;
            }
            try {
                sniHostNameClass = Class.forName("javax.net.ssl.SNIHostName", false, PlatformDependent.getClassLoader(ReferenceCountedOpenSslEngine.class));
                Object sniHostName = sniHostNameClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{"netty.io"});
                getAsciiNameMethod = sniHostNameClass.getDeclaredMethod("getAsciiName", new Class[0]);
                String name = (String) getAsciiNameMethod.invoke(sniHostName, new Object[0]);
                getServerNamesMethod = SSLParameters.class.getDeclaredMethod("getServerNames", new Class[0]);
                setServerNamesMethod = SSLParameters.class.getDeclaredMethod("setServerNames", new Class[]{List.class});
                parameters = new SSLParameters();
                List serverNames = (List) getServerNamesMethod.invoke(parameters, new Object[0]);
                setServerNamesMethod.invoke(parameters, new Object[]{Collections.emptyList()});
            } catch (Throwable th2) {
                sniHostNameClass = null;
                getAsciiNameMethod = null;
                getServerNamesMethod = null;
                setServerNamesMethod = null;
            }
        }
        GET_USE_CIPHER_SUITES_ORDER_METHOD = getUseCipherSuitesOrderMethod;
        SET_USE_CIPHER_SUITES_ORDER_METHOD = setUseCipherSuitesOrderMethod;
        SNI_HOSTNAME_CLASS = sniHostNameClass;
        GET_ASCII_NAME_METHOD = getAsciiNameMethod;
        GET_SERVER_NAMES_METHOD = getServerNamesMethod;
        SET_SERVER_NAMES_METHOD = setServerNamesMethod;
    }

    ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort, boolean leakDetection) {
        super(peerHost, peerPort);
        OpenSsl.ensureAvailability();
        this.leak = leakDetection ? leakDetector.open(this) : null;
        this.alloc = (ByteBufAllocator) ObjectUtil.checkNotNull(alloc, "alloc");
        this.apn = (OpenSslApplicationProtocolNegotiator) context.applicationProtocolNegotiator();
        this.ssl = SSL.newSSL(context.ctx, !context.isClient());
        this.session = new OpenSslSession(context.sessionContext());
        this.networkBIO = SSL.makeNetworkBIO(this.ssl);
        this.clientMode = context.isClient();
        this.engineMap = context.engineMap;
        this.rejectRemoteInitiatedRenegation = context.rejectRemoteInitiatedRenegotiation;
        this.localCerts = context.keyCertChain;
        setClientAuth(this.clientMode ? ClientAuth.NONE : context.clientAuth);
        if (this.clientMode && peerHost != null) {
            SSL.setTlsExtHostName(this.ssl, peerHost);
        }
        this.keyMaterialManager = context.keyMaterialManager();
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

    public final synchronized SSLSession getHandshakeSession() {
        SSLSession sSLSession;
        switch (this.handshakeState) {
            case NOT_STARTED:
            case FINISHED:
                sSLSession = null;
                break;
            default:
                sSLSession = this.session;
                break;
        }
        return sSLSession;
    }

    public final synchronized long sslPointer() {
        return this.ssl;
    }

    public final synchronized void shutdown() {
        if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
            this.engineMap.remove(this.ssl);
            SSL.freeSSL(this.ssl);
            SSL.freeBIO(this.networkBIO);
            this.networkBIO = 0;
            this.ssl = 0;
            this.engineClosed = true;
            this.isOutboundDone = true;
            this.isInboundDone = true;
        }
        SSL.clearError();
    }

    private int writePlaintextData(ByteBuffer src) {
        int sslWrote;
        int pos = src.position();
        int limit = src.limit();
        int len = Math.min(limit - pos, 16384);
        if (src.isDirect()) {
            sslWrote = SSL.writeToSSL(this.ssl, Buffer.address(src) + ((long) pos), len);
            if (sslWrote > 0) {
                src.position(pos + sslWrote);
            }
        } else {
            ByteBuf buf = this.alloc.directBuffer(len);
            try {
                long addr = OpenSsl.memoryAddress(buf);
                src.limit(pos + len);
                buf.setBytes(0, src);
                src.limit(limit);
                sslWrote = SSL.writeToSSL(this.ssl, addr, len);
                if (sslWrote > 0) {
                    src.position(pos + sslWrote);
                } else {
                    src.position(pos);
                }
                buf.release();
            } catch (Throwable th) {
                buf.release();
            }
        }
        return sslWrote;
    }

    private int writeEncryptedData(ByteBuffer src) {
        int netWrote;
        int pos = src.position();
        int len = src.remaining();
        if (src.isDirect()) {
            netWrote = SSL.writeToBIO(this.networkBIO, Buffer.address(src) + ((long) pos), len);
            if (netWrote >= 0) {
                src.position(pos + netWrote);
            }
        } else {
            ByteBuf buf = this.alloc.directBuffer(len);
            try {
                long addr = OpenSsl.memoryAddress(buf);
                buf.setBytes(0, src);
                netWrote = SSL.writeToBIO(this.networkBIO, addr, len);
                if (netWrote >= 0) {
                    src.position(pos + netWrote);
                } else {
                    src.position(pos);
                }
                buf.release();
            } catch (Throwable th) {
                buf.release();
            }
        }
        return netWrote;
    }

    private int readPlaintextData(ByteBuffer dst) {
        int sslRead;
        int pos;
        if (dst.isDirect()) {
            pos = dst.position();
            sslRead = SSL.readFromSSL(this.ssl, Buffer.address(dst) + ((long) pos), dst.limit() - pos);
            if (sslRead > 0) {
                dst.position(pos + sslRead);
            }
        } else {
            pos = dst.position();
            int limit = dst.limit();
            int len = Math.min(MAX_ENCRYPTED_PACKET_LENGTH, limit - pos);
            ByteBuf buf = this.alloc.directBuffer(len);
            try {
                sslRead = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
                if (sslRead > 0) {
                    dst.limit(pos + sslRead);
                    buf.getBytes(0, dst);
                    dst.limit(limit);
                }
                buf.release();
            } catch (Throwable th) {
                buf.release();
            }
        }
        return sslRead;
    }

    private int readEncryptedData(ByteBuffer dst, int pending) {
        int bioRead;
        if (!dst.isDirect() || dst.remaining() < pending) {
            ByteBuf buf = this.alloc.directBuffer(pending);
            try {
                bioRead = SSL.readFromBIO(this.networkBIO, OpenSsl.memoryAddress(buf), pending);
                if (bioRead > 0) {
                    int oldLimit = dst.limit();
                    dst.limit(dst.position() + bioRead);
                    buf.getBytes(0, dst);
                    dst.limit(oldLimit);
                    return bioRead;
                }
                buf.release();
            } finally {
                buf.release();
            }
        } else {
            int pos = dst.position();
            bioRead = SSL.readFromBIO(this.networkBIO, Buffer.address(dst) + ((long) pos), pending);
            if (bioRead > 0) {
                dst.position(pos + bioRead);
                return bioRead;
            }
        }
        return bioRead;
    }

    private SSLEngineResult readPendingBytesFromBIO(ByteBuffer dst, int bytesConsumed, int bytesProduced, HandshakeStatus status) throws SSLException {
        int pendingNet = SSL.pendingWrittenBytesInBIO(this.networkBIO);
        if (pendingNet <= 0) {
            return null;
        }
        if (dst.remaining() < pendingNet) {
            Status status2 = Status.BUFFER_OVERFLOW;
            if (status != HandshakeStatus.FINISHED) {
                status = getHandshakeStatus(pendingNet);
            }
            return new SSLEngineResult(status2, mayFinishHandshake(status), bytesConsumed, bytesProduced);
        }
        int produced = readEncryptedData(dst, pendingNet);
        if (produced <= 0) {
            SSL.clearError();
        } else {
            bytesProduced += produced;
            pendingNet -= produced;
        }
        if (this.isOutboundDone) {
            shutdown();
        }
        status2 = getEngineStatus();
        if (status != HandshakeStatus.FINISHED) {
            status = getHandshakeStatus(pendingNet);
        }
        return new SSLEngineResult(status2, mayFinishHandshake(status), bytesConsumed, bytesProduced);
    }

    public final SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) throws SSLException {
        if (srcs == null) {
            throw new IllegalArgumentException("srcs is null");
        } else if (dst == null) {
            throw new IllegalArgumentException("dst is null");
        } else if (offset >= srcs.length || offset + length > srcs.length) {
            throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
        } else if (dst.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else {
            SSLEngineResult sSLEngineResult;
            synchronized (this) {
                if (isDestroyed()) {
                    sSLEngineResult = CLOSED_NOT_HANDSHAKING;
                } else {
                    HandshakeStatus status = HandshakeStatus.NOT_HANDSHAKING;
                    if (this.handshakeState != HandshakeState.FINISHED) {
                        if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                            this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                        }
                        status = handshake();
                        if (status == HandshakeStatus.NEED_UNWRAP) {
                            sSLEngineResult = NEED_UNWRAP_OK;
                        } else if (this.engineClosed) {
                            sSLEngineResult = NEED_UNWRAP_CLOSED;
                        }
                    }
                    int bytesProduced = 0;
                    int bytesConsumed = 0;
                    int endOffset = offset + length;
                    loop0:
                    for (int i = offset; i < endOffset; i++) {
                        ByteBuffer src = srcs[i];
                        if (src == null) {
                            throw new IllegalArgumentException("srcs[" + i + "] is null");
                        }
                        while (src.hasRemaining()) {
                            int result = writePlaintextData(src);
                            if (result > 0) {
                                bytesConsumed += result;
                                sSLEngineResult = readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, status);
                                if (sSLEngineResult != null) {
                                    if (sSLEngineResult.getStatus() != Status.OK) {
                                        break loop0;
                                    }
                                    bytesProduced = sSLEngineResult.bytesProduced();
                                }
                            } else {
                                switch (SSL.getError(this.ssl, result)) {
                                    case 2:
                                        sSLEngineResult = readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, status);
                                        if (sSLEngineResult == null) {
                                            sSLEngineResult = new SSLEngineResult(getEngineStatus(), HandshakeStatus.NEED_UNWRAP, bytesConsumed, bytesProduced);
                                        }
                                        break;
                                    case 3:
                                        sSLEngineResult = readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, status);
                                        if (sSLEngineResult == null) {
                                            sSLEngineResult = NEED_WRAP_CLOSED;
                                        }
                                        break;
                                    case 6:
                                        if (!this.receivedShutdown) {
                                            closeAll();
                                        }
                                        sSLEngineResult = readPendingBytesFromBIO(dst, bytesConsumed, bytesProduced, status);
                                        if (sSLEngineResult == null) {
                                            sSLEngineResult = CLOSED_NOT_HANDSHAKING;
                                        }
                                        break;
                                    default:
                                        throw shutdownWithError("SSL_write");
                                }
                            }
                        }
                    }
                    if (bytesConsumed == 0) {
                        sSLEngineResult = readPendingBytesFromBIO(dst, 0, bytesProduced, status);
                        if (sSLEngineResult != null) {
                        }
                    }
                    sSLEngineResult = newResult(bytesConsumed, bytesProduced, status);
                }
            }
            return sSLEngineResult;
        }
    }

    private SSLException shutdownWithError(String operations) {
        return shutdownWithError(operations, SSL.getLastError());
    }

    private SSLException shutdownWithError(String operation, String err) {
        if (logger.isDebugEnabled()) {
            logger.debug("{} failed: OpenSSL error: {}", operation, err);
        }
        shutdown();
        if (this.handshakeState == HandshakeState.FINISHED) {
            return new SSLException(err);
        }
        return new SSLHandshakeException(err);
    }

    public final SSLEngineResult unwrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
        if (srcs == null) {
            throw new NullPointerException("srcs");
        }
        if (srcsOffset >= srcs.length || srcsOffset + srcsLength > srcs.length) {
            throw new IndexOutOfBoundsException("offset: " + srcsOffset + ", length: " + srcsLength + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
        } else if (dsts == null) {
            throw new IllegalArgumentException("dsts is null");
        } else {
            if (dstsOffset >= dsts.length || dstsOffset + dstsLength > dsts.length) {
                throw new IndexOutOfBoundsException("offset: " + dstsOffset + ", length: " + dstsLength + " (expected: offset <= offset + length <= dsts.length (" + dsts.length + "))");
            }
            ByteBuffer dst;
            ByteBuffer src;
            SSLEngineResult sSLEngineResult;
            long capacity = 0;
            int endOffset = dstsOffset + dstsLength;
            int i = dstsOffset;
            while (i < endOffset) {
                dst = dsts[i];
                if (dst == null) {
                    throw new IllegalArgumentException("dsts[" + i + "] is null");
                } else if (dst.isReadOnly()) {
                    throw new ReadOnlyBufferException();
                } else {
                    capacity += (long) dst.remaining();
                    i++;
                }
            }
            int srcsEndOffset = srcsOffset + srcsLength;
            long len = 0;
            for (i = srcsOffset; i < srcsEndOffset; i++) {
                src = srcs[i];
                if (src == null) {
                    throw new IllegalArgumentException("srcs[" + i + "] is null");
                }
                len += (long) src.remaining();
            }
            synchronized (this) {
                if (isDestroyed()) {
                    sSLEngineResult = CLOSED_NOT_HANDSHAKING;
                } else if (len > 18713) {
                    this.isInboundDone = true;
                    this.isOutboundDone = true;
                    this.engineClosed = true;
                    shutdown();
                    throw ENCRYPTED_PACKET_OVERSIZED;
                } else {
                    HandshakeStatus status = HandshakeStatus.NOT_HANDSHAKING;
                    if (this.handshakeState != HandshakeState.FINISHED) {
                        if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                            this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                        }
                        status = handshake();
                        if (status == HandshakeStatus.NEED_WRAP) {
                            sSLEngineResult = NEED_WRAP_OK;
                        } else if (this.engineClosed) {
                            sSLEngineResult = NEED_WRAP_CLOSED;
                        }
                    }
                    int bytesConsumed = 0;
                    if (srcsOffset < srcsEndOffset) {
                        do {
                            src = srcs[srcsOffset];
                            int remaining = src.remaining();
                            if (remaining != 0) {
                                int written = writeEncryptedData(src);
                                if (written <= 0) {
                                    SSL.clearError();
                                    break;
                                }
                                bytesConsumed += written;
                                if (written != remaining) {
                                    break;
                                }
                                srcsOffset++;
                            } else {
                                srcsOffset++;
                            }
                        } while (srcsOffset < srcsEndOffset);
                    }
                    int bytesProduced = 0;
                    if (capacity > 0) {
                        int idx = dstsOffset;
                        while (idx < endOffset) {
                            dst = dsts[idx];
                            if (dst.hasRemaining()) {
                                int bytesRead = readPlaintextData(dst);
                                rejectRemoteInitiatedRenegation();
                                if (bytesRead > 0) {
                                    bytesProduced += bytesRead;
                                    if (dst.hasRemaining()) {
                                        sSLEngineResult = newResult(bytesConsumed, bytesProduced, status);
                                        break;
                                    }
                                    idx++;
                                } else {
                                    switch (SSL.getError(this.ssl, bytesRead)) {
                                        case 2:
                                        case 3:
                                            break;
                                        case 6:
                                            if (!this.receivedShutdown) {
                                                closeAll();
                                                break;
                                            }
                                            break;
                                        default:
                                            sSLEngineResult = sslReadErrorResult(SSL.getLastErrorNumber(), bytesConsumed, bytesProduced);
                                            break;
                                    }
                                    sSLEngineResult = newResult(bytesConsumed, bytesProduced, status);
                                }
                            } else {
                                idx++;
                            }
                        }
                    } else if (SSL.readFromSSL(this.ssl, EMPTY_ADDR, 0) <= 0) {
                        int err = SSL.getLastErrorNumber();
                        if (OpenSsl.isError((long) err)) {
                            sSLEngineResult = sslReadErrorResult(err, bytesConsumed, 0);
                        }
                    }
                    if (pendingAppData() > 0) {
                        Status status2 = Status.BUFFER_OVERFLOW;
                        if (status != HandshakeStatus.FINISHED) {
                            status = getHandshakeStatus();
                        }
                        SSLEngineResult sSLEngineResult2 = new SSLEngineResult(status2, mayFinishHandshake(status), bytesConsumed, bytesProduced);
                    } else {
                        if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & 2) == 2) {
                            closeAll();
                        }
                        sSLEngineResult = newResult(bytesConsumed, bytesProduced, status);
                    }
                }
            }
            return sSLEngineResult;
        }
    }

    private SSLEngineResult sslReadErrorResult(int err, int bytesConsumed, int bytesProduced) throws SSLException {
        String errStr = SSL.getErrorString((long) err);
        if (SSL.pendingWrittenBytesInBIO(this.networkBIO) > 0) {
            if (this.handshakeException == null && this.handshakeState != HandshakeState.FINISHED) {
                this.handshakeException = new SSLHandshakeException(errStr);
            }
            return new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
        }
        throw shutdownWithError("SSL_read", errStr);
    }

    private int pendingAppData() {
        return this.handshakeState == HandshakeState.FINISHED ? SSL.pendingReadableBytesInSSL(this.ssl) : 0;
    }

    private SSLEngineResult newResult(int bytesConsumed, int bytesProduced, HandshakeStatus status) throws SSLException {
        Status engineStatus = getEngineStatus();
        if (status != HandshakeStatus.FINISHED) {
            status = getHandshakeStatus();
        }
        return new SSLEngineResult(engineStatus, mayFinishHandshake(status), bytesConsumed, bytesProduced);
    }

    private void closeAll() throws SSLException {
        this.receivedShutdown = true;
        closeOutbound();
        closeInbound();
    }

    private void rejectRemoteInitiatedRenegation() throws SSLHandshakeException {
        if (this.rejectRemoteInitiatedRenegation && SSL.getHandshakeCount(this.ssl) > 1) {
            shutdown();
            throw new SSLHandshakeException("remote-initiated renegotation not allowed");
        }
    }

    public final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
        return unwrap(srcs, 0, srcs.length, dsts, 0, dsts.length);
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer src) {
        this.singleSrcBuffer[0] = src;
        return this.singleSrcBuffer;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer src) {
        this.singleDstBuffer[0] = src;
        return this.singleDstBuffer;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(src), 0, 1, dsts, offset, length);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
        }
        return unwrap;
    }

    public final synchronized SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        SSLEngineResult wrap;
        try {
            wrap = wrap(singleSrcBuffer(src), dst);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
        }
        return wrap;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(src), singleDstBuffer(dst));
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
        }
        return unwrap;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(src), dsts);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
        }
        return unwrap;
    }

    public final Runnable getDelegatedTask() {
        return null;
    }

    public final synchronized void closeInbound() throws SSLException {
        if (!this.isInboundDone) {
            this.isInboundDone = true;
            this.engineClosed = true;
            shutdown();
            if (!(this.handshakeState == HandshakeState.NOT_STARTED || this.receivedShutdown)) {
                throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
            }
        }
    }

    public final synchronized boolean isInboundDone() {
        boolean z;
        z = this.isInboundDone || this.engineClosed;
        return z;
    }

    public final synchronized void closeOutbound() {
        if (!this.isOutboundDone) {
            this.isOutboundDone = true;
            this.engineClosed = true;
            if (this.handshakeState != HandshakeState.NOT_STARTED && !isDestroyed()) {
                if ((SSL.getShutdown(this.ssl) & 1) != 1) {
                    int err = SSL.shutdownSSL(this.ssl);
                    if (err < 0) {
                        switch (SSL.getError(this.ssl, err)) {
                            case 0:
                            case 2:
                            case 3:
                            case 4:
                            case 6:
                            case 7:
                            case 8:
                                break;
                            case 1:
                            case 5:
                                if (logger.isDebugEnabled()) {
                                    logger.debug("SSL_shutdown failed: OpenSSL error: {}", SSL.getLastError());
                                }
                                shutdown();
                                break;
                            default:
                                SSL.clearError();
                                break;
                        }
                    }
                }
            }
            shutdown();
        }
    }

    public final synchronized boolean isOutboundDone() {
        return this.isOutboundDone;
    }

    public final String[] getSupportedCipherSuites() {
        return (String[]) OpenSsl.AVAILABLE_CIPHER_SUITES.toArray(new String[OpenSsl.AVAILABLE_CIPHER_SUITES.size()]);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String[] getEnabledCipherSuites() {
        /*
        r6 = this;
        monitor-enter(r6);
        r3 = r6.isDestroyed();	 Catch:{ all -> 0x0017 }
        if (r3 != 0) goto L_0x0013;
    L_0x0007:
        r4 = r6.ssl;	 Catch:{ all -> 0x0017 }
        r0 = org.apache.tomcat.jni.SSL.getCiphers(r4);	 Catch:{ all -> 0x0017 }
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
        if (r0 != 0) goto L_0x001a;
    L_0x0010:
        r0 = io.netty.util.internal.EmptyArrays.EMPTY_STRINGS;
    L_0x0012:
        return r0;
    L_0x0013:
        r0 = io.netty.util.internal.EmptyArrays.EMPTY_STRINGS;	 Catch:{ all -> 0x0017 }
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
        goto L_0x0012;
    L_0x0017:
        r3 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
        throw r3;
    L_0x001a:
        monitor-enter(r6);
        r1 = 0;
    L_0x001c:
        r3 = r0.length;	 Catch:{ all -> 0x002e }
        if (r1 >= r3) goto L_0x002c;
    L_0x001f:
        r3 = r0[r1];	 Catch:{ all -> 0x002e }
        r2 = r6.toJavaCipherSuite(r3);	 Catch:{ all -> 0x002e }
        if (r2 == 0) goto L_0x0029;
    L_0x0027:
        r0[r1] = r2;	 Catch:{ all -> 0x002e }
    L_0x0029:
        r1 = r1 + 1;
        goto L_0x001c;
    L_0x002c:
        monitor-exit(r6);	 Catch:{ all -> 0x002e }
        goto L_0x0012;
    L_0x002e:
        r3 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x002e }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.getEnabledCipherSuites():java.lang.String[]");
    }

    public final void setEnabledCipherSuites(String[] cipherSuites) {
        ObjectUtil.checkNotNull(cipherSuites, "cipherSuites");
        StringBuilder buf = new StringBuilder();
        String[] arr$ = cipherSuites;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            String c = arr$[i$];
            if (c == null) {
                break;
            }
            String converted = CipherSuiteConverter.toOpenSsl(c);
            if (converted == null) {
                converted = c;
            }
            if (OpenSsl.isCipherSuiteAvailable(converted)) {
                buf.append(converted);
                buf.append(':');
                i$++;
            } else {
                throw new IllegalArgumentException("unsupported cipher suite: " + c + '(' + converted + ')');
            }
        }
        if (buf.length() == 0) {
            throw new IllegalArgumentException("empty cipher suites");
        }
        buf.setLength(buf.length() - 1);
        String cipherSuiteSpec = buf.toString();
        synchronized (this) {
            if (isDestroyed()) {
                throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec);
            }
            try {
                SSL.setCipherSuites(this.ssl, cipherSuiteSpec);
            } catch (Exception e) {
                throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec, e);
            }
        }
    }

    public final String[] getSupportedProtocols() {
        return (String[]) OpenSsl.SUPPORTED_PROTOCOLS_SET.toArray(new String[OpenSsl.SUPPORTED_PROTOCOLS_SET.size()]);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String[] getEnabledProtocols() {
        /*
        r4 = this;
        r2 = io.netty.util.internal.InternalThreadLocalMap.get();
        r0 = r2.arrayList();
        r2 = "SSLv2Hello";
        r0.add(r2);
        monitor-enter(r4);
        r2 = r4.isDestroyed();	 Catch:{ all -> 0x006b }
        if (r2 != 0) goto L_0x0060;
    L_0x0015:
        r2 = r4.ssl;	 Catch:{ all -> 0x006b }
        r1 = org.apache.tomcat.jni.SSL.getOptions(r2);	 Catch:{ all -> 0x006b }
        monitor-exit(r4);	 Catch:{ all -> 0x006b }
        r2 = 67108864; // 0x4000000 float:1.5046328E-36 double:3.31561842E-316;
        r2 = r2 & r1;
        if (r2 != 0) goto L_0x0027;
    L_0x0021:
        r2 = "TLSv1";
        r0.add(r2);
    L_0x0027:
        r2 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r2 = r2 & r1;
        if (r2 != 0) goto L_0x0032;
    L_0x002c:
        r2 = "TLSv1.1";
        r0.add(r2);
    L_0x0032:
        r2 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r2 = r2 & r1;
        if (r2 != 0) goto L_0x003d;
    L_0x0037:
        r2 = "TLSv1.2";
        r0.add(r2);
    L_0x003d:
        r2 = 16777216; // 0x1000000 float:2.3509887E-38 double:8.289046E-317;
        r2 = r2 & r1;
        if (r2 != 0) goto L_0x0048;
    L_0x0042:
        r2 = "SSLv2";
        r0.add(r2);
    L_0x0048:
        r2 = 33554432; // 0x2000000 float:9.403955E-38 double:1.6578092E-316;
        r2 = r2 & r1;
        if (r2 != 0) goto L_0x0053;
    L_0x004d:
        r2 = "SSLv3";
        r0.add(r2);
    L_0x0053:
        r2 = r0.size();
        r2 = new java.lang.String[r2];
        r2 = r0.toArray(r2);
        r2 = (java.lang.String[]) r2;
    L_0x005f:
        return r2;
    L_0x0060:
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ all -> 0x006b }
        r2 = r0.toArray(r2);	 Catch:{ all -> 0x006b }
        r2 = (java.lang.String[]) r2;	 Catch:{ all -> 0x006b }
        monitor-exit(r4);	 Catch:{ all -> 0x006b }
        goto L_0x005f;
    L_0x006b:
        r2 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x006b }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.ReferenceCountedOpenSslEngine.getEnabledProtocols():java.lang.String[]");
    }

    public final void setEnabledProtocols(String[] protocols) {
        if (protocols == null) {
            throw new IllegalArgumentException();
        }
        boolean sslv2 = false;
        boolean sslv3 = false;
        boolean tlsv1 = false;
        boolean tlsv1_1 = false;
        boolean tlsv1_2 = false;
        String[] arr$ = protocols;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            String p = arr$[i$];
            if (OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(p)) {
                if (p.equals(SSLSocketFactory.SSLV2)) {
                    sslv2 = true;
                } else if (p.equals("SSLv3")) {
                    sslv3 = true;
                } else if (p.equals("TLSv1")) {
                    tlsv1 = true;
                } else if (p.equals("TLSv1.1")) {
                    tlsv1_1 = true;
                } else if (p.equals("TLSv1.2")) {
                    tlsv1_2 = true;
                }
                i$++;
            } else {
                throw new IllegalArgumentException("Protocol " + p + " is not supported.");
            }
        }
        synchronized (this) {
            if (isDestroyed()) {
                throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(protocols));
            }
            SSL.setOptions(this.ssl, UnixStat.PERM_MASK);
            SSL.clearOptions(this.ssl, 520093696);
            int opts = 0;
            if (!sslv2) {
                opts = 0 | 16777216;
            }
            if (!sslv3) {
                opts |= swscale.SWS_CPU_CAPS_SSE2;
            }
            if (!tlsv1) {
                opts |= avformat.AVFMT_SEEK_TO_PTS;
            }
            if (!tlsv1_1) {
                opts |= 268435456;
            }
            if (!tlsv1_2) {
                opts |= avutil.AV_CPU_FLAG_AVXSLOW;
            }
            SSL.setOptions(this.ssl, opts);
        }
    }

    public final SSLSession getSession() {
        return this.session;
    }

    public final synchronized void beginHandshake() throws SSLException {
        switch (this.handshakeState) {
            case NOT_STARTED:
                break;
            case FINISHED:
                if (!this.clientMode) {
                    if (SSL.renegotiate(this.ssl) == 1 && SSL.doHandshake(this.ssl) == 1) {
                        SSL.setState(this.ssl, 8192);
                        this.lastAccessed = System.currentTimeMillis();
                        break;
                    }
                    throw shutdownWithError("renegotiation failed");
                }
                throw RENEGOTIATION_UNSUPPORTED;
                break;
            case STARTED_IMPLICITLY:
                checkEngineClosed(BEGIN_HANDSHAKE_ENGINE_CLOSED);
                this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                break;
            case STARTED_EXPLICITLY:
                break;
            default:
                throw new Error();
        }
        this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
        handshake();
    }

    private void checkEngineClosed(SSLException cause) throws SSLException {
        if (this.engineClosed || isDestroyed()) {
            throw cause;
        }
    }

    private static HandshakeStatus pendingStatus(int pendingStatus) {
        return pendingStatus > 0 ? HandshakeStatus.NEED_WRAP : HandshakeStatus.NEED_UNWRAP;
    }

    private HandshakeStatus handshake() throws SSLException {
        if (this.handshakeState == HandshakeState.FINISHED) {
            return HandshakeStatus.FINISHED;
        }
        checkEngineClosed(HANDSHAKE_ENGINE_CLOSED);
        SSLHandshakeException exception = this.handshakeException;
        if (exception == null) {
            this.engineMap.add(this);
            if (this.lastAccessed == -1) {
                this.lastAccessed = System.currentTimeMillis();
            }
            if (!(this.certificateSet || this.keyMaterialManager == null)) {
                this.certificateSet = true;
                this.keyMaterialManager.setKeyMaterial(this);
            }
            int code = SSL.doHandshake(this.ssl);
            if (code > 0) {
                this.session.handshakeFinished();
                this.engineMap.remove(this.ssl);
                return HandshakeStatus.FINISHED;
            } else if (this.handshakeException != null) {
                exception = this.handshakeException;
                this.handshakeException = null;
                shutdown();
                throw exception;
            } else {
                switch (SSL.getError(this.ssl, code)) {
                    case 2:
                    case 3:
                        return pendingStatus(SSL.pendingWrittenBytesInBIO(this.networkBIO));
                    default:
                        throw shutdownWithError("SSL_do_handshake");
                }
            }
        } else if (SSL.pendingWrittenBytesInBIO(this.networkBIO) > 0) {
            return HandshakeStatus.NEED_WRAP;
        } else {
            this.handshakeException = null;
            shutdown();
            throw exception;
        }
    }

    private Status getEngineStatus() {
        return this.engineClosed ? Status.CLOSED : Status.OK;
    }

    private HandshakeStatus mayFinishHandshake(HandshakeStatus status) throws SSLException {
        if (status != HandshakeStatus.NOT_HANDSHAKING || this.handshakeState == HandshakeState.FINISHED) {
            return status;
        }
        return handshake();
    }

    public final synchronized HandshakeStatus getHandshakeStatus() {
        return needPendingStatus() ? pendingStatus(SSL.pendingWrittenBytesInBIO(this.networkBIO)) : HandshakeStatus.NOT_HANDSHAKING;
    }

    private HandshakeStatus getHandshakeStatus(int pending) {
        return needPendingStatus() ? pendingStatus(pending) : HandshakeStatus.NOT_HANDSHAKING;
    }

    private boolean needPendingStatus() {
        return (this.handshakeState == HandshakeState.NOT_STARTED || isDestroyed() || (this.handshakeState == HandshakeState.FINISHED && !this.engineClosed)) ? false : true;
    }

    private String toJavaCipherSuite(String openSslCipherSuite) {
        if (openSslCipherSuite == null) {
            return null;
        }
        return CipherSuiteConverter.toJava(openSslCipherSuite, toJavaCipherSuitePrefix(SSL.getVersion(this.ssl)));
    }

    private static String toJavaCipherSuitePrefix(String protocolVersion) {
        char c;
        if (protocolVersion == null || protocolVersion.length() == 0) {
            c = '\u0000';
        } else {
            c = protocolVersion.charAt(0);
        }
        switch (c) {
            case 'S':
                return SSLSocketFactory.SSL;
            case 'T':
                return SSLSocketFactory.TLS;
            default:
                return "UNKNOWN";
        }
    }

    public final void setUseClientMode(boolean clientMode) {
        if (clientMode != this.clientMode) {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean getUseClientMode() {
        return this.clientMode;
    }

    public final void setNeedClientAuth(boolean b) {
        setClientAuth(b ? ClientAuth.REQUIRE : ClientAuth.NONE);
    }

    public final boolean getNeedClientAuth() {
        return this.clientAuth == ClientAuth.REQUIRE;
    }

    public final void setWantClientAuth(boolean b) {
        setClientAuth(b ? ClientAuth.OPTIONAL : ClientAuth.NONE);
    }

    public final boolean getWantClientAuth() {
        return this.clientAuth == ClientAuth.OPTIONAL;
    }

    private void setClientAuth(ClientAuth mode) {
        if (!this.clientMode) {
            synchronized (this) {
                if (this.clientAuth == mode) {
                    return;
                }
                switch (mode) {
                    case NONE:
                        SSL.setVerify(this.ssl, 0, 10);
                        break;
                    case REQUIRE:
                        SSL.setVerify(this.ssl, 2, 10);
                        break;
                    case OPTIONAL:
                        SSL.setVerify(this.ssl, 1, 10);
                        break;
                    default:
                        throw new Error(mode.toString());
                }
                this.clientAuth = mode;
            }
        }
    }

    public final void setEnableSessionCreation(boolean b) {
        if (b) {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean getEnableSessionCreation() {
        return false;
    }

    public final synchronized SSLParameters getSSLParameters() {
        SSLParameters sslParameters;
        boolean z = true;
        synchronized (this) {
            sslParameters = super.getSSLParameters();
            int version = PlatformDependent.javaVersion();
            if (version >= 7) {
                sslParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
                SslParametersUtils.setAlgorithmConstraints(sslParameters, this.algorithmConstraints);
                if (version >= 8) {
                    if (!(SET_SERVER_NAMES_METHOD == null || this.sniHostNames == null)) {
                        try {
                            SET_SERVER_NAMES_METHOD.invoke(sslParameters, new Object[]{this.sniHostNames});
                        } catch (IllegalAccessException e) {
                            throw new Error(e);
                        } catch (InvocationTargetException e2) {
                            throw new Error(e2);
                        } catch (IllegalAccessException e3) {
                            throw new Error(e3);
                        } catch (InvocationTargetException e22) {
                            throw new Error(e22);
                        }
                    }
                    if (!(SET_USE_CIPHER_SUITES_ORDER_METHOD == null || isDestroyed())) {
                        Method method = SET_USE_CIPHER_SUITES_ORDER_METHOD;
                        Object[] objArr = new Object[1];
                        if ((SSL.getOptions(this.ssl) & 4194304) == 0) {
                            z = false;
                        }
                        objArr[0] = Boolean.valueOf(z);
                        method.invoke(sslParameters, objArr);
                    }
                }
            }
        }
        return sslParameters;
    }

    public final synchronized void setSSLParameters(SSLParameters sslParameters) {
        super.setSSLParameters(sslParameters);
        int version = PlatformDependent.javaVersion();
        if (version >= 7) {
            this.endPointIdentificationAlgorithm = sslParameters.getEndpointIdentificationAlgorithm();
            this.algorithmConstraints = sslParameters.getAlgorithmConstraints();
            if (version >= 8) {
                if (!(SNI_HOSTNAME_CLASS == null || !this.clientMode || isDestroyed())) {
                    if (!$assertionsDisabled && GET_SERVER_NAMES_METHOD == null) {
                        throw new AssertionError();
                    } else if ($assertionsDisabled || GET_ASCII_NAME_METHOD != null) {
                        try {
                            List<?> servernames = (List) GET_SERVER_NAMES_METHOD.invoke(sslParameters, new Object[0]);
                            if (servernames != null) {
                                for (Object serverName : servernames) {
                                    if (SNI_HOSTNAME_CLASS.isInstance(serverName)) {
                                        SSL.setTlsExtHostName(this.ssl, (String) GET_ASCII_NAME_METHOD.invoke(serverName, new Object[0]));
                                    } else {
                                        throw new IllegalArgumentException("Only " + SNI_HOSTNAME_CLASS.getName() + " instances are supported, but found: " + serverName);
                                    }
                                }
                            }
                            this.sniHostNames = servernames;
                        } catch (IllegalAccessException e) {
                            throw new Error(e);
                        } catch (InvocationTargetException e2) {
                            throw new Error(e2);
                        } catch (IllegalAccessException e3) {
                            throw new Error(e3);
                        } catch (InvocationTargetException e22) {
                            throw new Error(e22);
                        }
                    } else {
                        throw new AssertionError();
                    }
                }
                if (!(GET_USE_CIPHER_SUITES_ORDER_METHOD == null || isDestroyed())) {
                    if (((Boolean) GET_USE_CIPHER_SUITES_ORDER_METHOD.invoke(sslParameters, new Object[0])).booleanValue()) {
                        SSL.setOptions(this.ssl, 4194304);
                    } else {
                        SSL.clearOptions(this.ssl, 4194304);
                    }
                }
            }
        }
    }

    private boolean isDestroyed() {
        return this.destroyed != 0;
    }
}
