package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.codec.language.bm.Rule;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.tomcat.jni.Buffer;
import org.apache.tomcat.jni.Library;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLContext;
import org.apache.tools.ant.taskdefs.condition.Os;
import org.apache.tools.zip.UnixStat;

public final class OpenSsl {
    static final /* synthetic */ boolean $assertionsDisabled = (!OpenSsl.class.desiredAssertionStatus());
    static final Set<String> AVAILABLE_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
    private static final String LINUX = "linux";
    static final String PROTOCOL_SSL_V2 = "SSLv2";
    static final String PROTOCOL_SSL_V2_HELLO = "SSLv2Hello";
    static final String PROTOCOL_SSL_V3 = "SSLv3";
    static final String PROTOCOL_TLS_V1 = "TLSv1";
    static final String PROTOCOL_TLS_V1_1 = "TLSv1.1";
    static final String PROTOCOL_TLS_V1_2 = "TLSv1.2";
    private static final String[] SUPPORTED_PROTOCOLS = new String[]{PROTOCOL_SSL_V2_HELLO, "SSLv2", PROTOCOL_SSL_V3, PROTOCOL_TLS_V1, PROTOCOL_TLS_V1_1, PROTOCOL_TLS_V1_2};
    static final Set<String> SUPPORTED_PROTOCOLS_SET = Collections.unmodifiableSet(new HashSet(Arrays.asList(SUPPORTED_PROTOCOLS)));
    private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
    private static final Throwable UNAVAILABILITY_CAUSE;
    private static final String UNKNOWN = "unknown";
    private static final boolean USE_KEYMANAGER_FACTORY;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSsl.class);

    static {
        Throwable cause = null;
        try {
            Class.forName("org.apache.tomcat.jni.SSL", false, OpenSsl.class.getClassLoader());
        } catch (Throwable t) {
            cause = t;
            logger.debug("netty-tcnative not in the classpath; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.");
        }
        if (cause == null) {
            try {
                loadTcNative();
            } catch (Throwable t2) {
                cause = t2;
                logger.debug("Failed to load netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable, unless the " + "application has already loaded the symbols by some other means. " + "See http://netty.io/wiki/forked-tomcat-native.html for more information.", t2);
            }
            try {
                initializeTcNative();
                cause = null;
            } catch (Throwable t22) {
                if (cause == null) {
                    cause = t22;
                }
                logger.debug("Failed to initialize netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable. " + "See http://netty.io/wiki/forked-tomcat-native.html for more information.", t22);
            }
        }
        if (cause == null && !isNettyTcnative()) {
            logger.debug("incompatible tcnative in the classpath; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.");
            cause = new ClassNotFoundException("incompatible tcnative in the classpath");
        }
        UNAVAILABILITY_CAUSE = cause;
        if (cause == null) {
            Set<String> availableOpenSslCipherSuites = new LinkedHashSet(128);
            boolean supportsKeyManagerFactory = false;
            boolean useKeyManagerFactory = false;
            long aprPool = Pool.create(0);
            try {
                long sslCtx = SSLContext.make(aprPool, 31, 1);
                long j = 0;
                long ssl;
                try {
                    SSLContext.setOptions(sslCtx, UnixStat.PERM_MASK);
                    SSLContext.setCipherSuite(sslCtx, Rule.ALL);
                    ssl = SSL.newSSL(sslCtx, true);
                    for (String c : SSL.getCiphers(ssl)) {
                        if (!(c == null || c.length() == 0 || availableOpenSslCipherSuites.contains(c))) {
                            availableOpenSslCipherSuites.add(c);
                        }
                    }
                    try {
                        j = OpenSslContext.toBIO(new X509Certificate[]{new SelfSignedCertificate().cert()});
                        SSL.setCertificateChainBio(ssl, j, false);
                        supportsKeyManagerFactory = true;
                        useKeyManagerFactory = ((Boolean) AccessController.doPrivileged(new 1())).booleanValue();
                    } catch (Throwable th) {
                        logger.debug("KeyManagerFactory not supported.");
                    }
                    SSL.freeSSL(ssl);
                    if (0 != 0) {
                        SSL.freeBIO(0);
                    }
                    if (j != 0) {
                        SSL.freeBIO(j);
                    }
                    SSLContext.free(sslCtx);
                    Pool.destroy(aprPool);
                    AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.unmodifiableSet(availableOpenSslCipherSuites);
                    Set<String> availableJavaCipherSuites = new LinkedHashSet(AVAILABLE_OPENSSL_CIPHER_SUITES.size() * 2);
                    for (String cipher : AVAILABLE_OPENSSL_CIPHER_SUITES) {
                        availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, SSLSocketFactory.TLS));
                        availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, SSLSocketFactory.SSL));
                    }
                    AVAILABLE_JAVA_CIPHER_SUITES = Collections.unmodifiableSet(availableJavaCipherSuites);
                    Set<String> availableCipherSuites = new LinkedHashSet(AVAILABLE_OPENSSL_CIPHER_SUITES.size() + AVAILABLE_JAVA_CIPHER_SUITES.size());
                    for (String cipher2 : AVAILABLE_OPENSSL_CIPHER_SUITES) {
                        availableCipherSuites.add(cipher2);
                    }
                    for (String cipher22 : AVAILABLE_JAVA_CIPHER_SUITES) {
                        availableCipherSuites.add(cipher22);
                    }
                    AVAILABLE_CIPHER_SUITES = availableCipherSuites;
                    SUPPORTS_KEYMANAGER_FACTORY = supportsKeyManagerFactory;
                    USE_KEYMANAGER_FACTORY = useKeyManagerFactory;
                } catch (Throwable th2) {
                    SSLContext.free(sslCtx);
                }
            } catch (Throwable e) {
                try {
                    logger.warn("Failed to get the list of available OpenSSL cipher suites.", e);
                } finally {
                    Pool.destroy(aprPool);
                }
            }
        } else {
            AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.emptySet();
            AVAILABLE_JAVA_CIPHER_SUITES = Collections.emptySet();
            AVAILABLE_CIPHER_SUITES = Collections.emptySet();
            SUPPORTS_KEYMANAGER_FACTORY = false;
            USE_KEYMANAGER_FACTORY = false;
        }
    }

    private static boolean isNettyTcnative() {
        return ((Boolean) AccessController.doPrivileged(new 2())).booleanValue();
    }

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    public static boolean isAlpnSupported() {
        return ((long) version()) >= 268443648;
    }

    public static int version() {
        if (isAvailable()) {
            return SSL.version();
        }
        return -1;
    }

    public static String versionString() {
        if (isAvailable()) {
            return SSL.versionString();
        }
        return null;
    }

    public static void ensureAvailability() {
        if (UNAVAILABILITY_CAUSE != null) {
            throw ((Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE));
        }
    }

    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }

    @Deprecated
    public static Set<String> availableCipherSuites() {
        return availableOpenSslCipherSuites();
    }

    public static Set<String> availableOpenSslCipherSuites() {
        return AVAILABLE_OPENSSL_CIPHER_SUITES;
    }

    public static Set<String> availableJavaCipherSuites() {
        return AVAILABLE_JAVA_CIPHER_SUITES;
    }

    public static boolean isCipherSuiteAvailable(String cipherSuite) {
        String converted = CipherSuiteConverter.toOpenSsl(cipherSuite);
        if (converted != null) {
            cipherSuite = converted;
        }
        return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(cipherSuite);
    }

    public static boolean supportsKeyManagerFactory() {
        return SUPPORTS_KEYMANAGER_FACTORY;
    }

    static boolean useKeyManagerFactory() {
        return USE_KEYMANAGER_FACTORY;
    }

    static boolean isError(long errorCode) {
        return errorCode != 0;
    }

    static long memoryAddress(ByteBuf buf) {
        if ($assertionsDisabled || buf.isDirect()) {
            return buf.hasMemoryAddress() ? buf.memoryAddress() : Buffer.address(buf.nioBuffer());
        } else {
            throw new AssertionError();
        }
    }

    private OpenSsl() {
    }

    private static void loadTcNative() throws Exception {
        String os = normalizeOs(SystemPropertyUtil.get("os.name", ""));
        String arch = normalizeArch(SystemPropertyUtil.get("os.arch", ""));
        Set<String> libNames = new LinkedHashSet(3);
        libNames.add("netty-tcnative-" + os + '-' + arch);
        if (LINUX.equalsIgnoreCase(os)) {
            libNames.add("netty-tcnative-" + os + '-' + arch + "-fedora");
        }
        libNames.add("netty-tcnative");
        NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), (String[]) libNames.toArray(new String[libNames.size()]));
    }

    private static void initializeTcNative() throws Exception {
        Library.initialize("provided");
        SSL.initialize(null);
    }

    private static String normalizeOs(String value) {
        value = normalize(value);
        if (value.startsWith("aix")) {
            return "aix";
        }
        if (value.startsWith("hpux")) {
            return "hpux";
        }
        if (value.startsWith("os400") && (value.length() <= 5 || !Character.isDigit(value.charAt(5)))) {
            return "os400";
        }
        if (value.startsWith(LINUX)) {
            return LINUX;
        }
        if (value.startsWith("macosx") || value.startsWith("osx")) {
            return "osx";
        }
        if (value.startsWith("freebsd")) {
            return "freebsd";
        }
        if (value.startsWith("openbsd")) {
            return "openbsd";
        }
        if (value.startsWith("netbsd")) {
            return "netbsd";
        }
        if (value.startsWith("solaris") || value.startsWith("sunos")) {
            return "sunos";
        }
        if (value.startsWith(Os.FAMILY_WINDOWS)) {
            return Os.FAMILY_WINDOWS;
        }
        return "unknown";
    }

    private static String normalizeArch(String value) {
        value = normalize(value);
        if (value.matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return "x86_64";
        }
        if (value.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return "x86_32";
        }
        if (value.matches("^(ia64|itanium64)$")) {
            return "itanium_64";
        }
        if (value.matches("^(sparc|sparc32)$")) {
            return "sparc_32";
        }
        if (value.matches("^(sparcv9|sparc64)$")) {
            return "sparc_64";
        }
        if (value.matches("^(arm|arm32)$")) {
            return "arm_32";
        }
        if ("aarch64".equals(value)) {
            return "aarch_64";
        }
        if (value.matches("^(ppc|ppc32)$")) {
            return "ppc_32";
        }
        if ("ppc64".equals(value)) {
            return "ppc_64";
        }
        if ("ppc64le".equals(value)) {
            return "ppcle_64";
        }
        if ("s390".equals(value)) {
            return "s390_32";
        }
        if ("s390x".equals(value)) {
            return "s390_64";
        }
        return "unknown";
    }

    private static String normalize(String value) {
        return value.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    static void releaseIfNeeded(ReferenceCounted counted) {
        if (counted.refCnt() > 0) {
            ReferenceCountUtil.safeRelease(counted);
        }
    }
}
