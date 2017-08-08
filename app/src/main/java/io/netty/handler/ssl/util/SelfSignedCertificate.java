package io.netty.handler.ssl.util;

import com.MCWorld.framework.base.utils.UtilsRSA;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

public final class SelfSignedCertificate {
    private static final Date DEFAULT_NOT_AFTER = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotAfter", 253402300799000L));
    private static final Date DEFAULT_NOT_BEFORE = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotBefore", System.currentTimeMillis() - 31536000000L));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
    private final X509Certificate cert;
    private final File certificate;
    private final PrivateKey key;
    private final File privateKey;

    public SelfSignedCertificate() throws CertificateException {
        this(DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(Date notBefore, Date notAfter) throws CertificateException {
        this("example.com", notBefore, notAfter);
    }

    public SelfSignedCertificate(String fqdn) throws CertificateException {
        this(fqdn, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(String fqdn, Date notBefore, Date notAfter) throws CertificateException {
        this(fqdn, ThreadLocalInsecureRandom.current(), 1024, notBefore, notAfter);
    }

    public SelfSignedCertificate(String fqdn, SecureRandom random, int bits) throws CertificateException {
        this(fqdn, random, bits, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(String fqdn, SecureRandom random, int bits, Date notBefore, Date notAfter) throws CertificateException {
        Exception e;
        Throwable th;
        try {
            String[] paths;
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(UtilsRSA.KEY_ALGORITHM);
            keyGen.initialize(bits, random);
            KeyPair keypair = keyGen.generateKeyPair();
            try {
                paths = OpenJdkSelfSignedCertGenerator.generate(fqdn, keypair, random, notBefore, notAfter);
            } catch (Throwable t2) {
                logger.debug("Failed to generate a_isRightVersion self-signed X.509 certificate using Bouncy Castle:", t2);
                CertificateException certificateException = new CertificateException("No provider succeeded to generate a_isRightVersion self-signed certificate. See debug log for the root cause.");
            }
            this.certificate = new File(paths[0]);
            this.privateKey = new File(paths[1]);
            this.key = keypair.getPrivate();
            FileInputStream certificateInput = null;
            try {
                FileInputStream certificateInput2 = new FileInputStream(this.certificate);
                try {
                    this.cert = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(certificateInput2);
                    if (certificateInput2 != null) {
                        try {
                            certificateInput2.close();
                        } catch (Throwable e2) {
                            logger.warn("Failed to close a_isRightVersion file: " + this.certificate, e2);
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    certificateInput = certificateInput2;
                    try {
                        throw new CertificateEncodingException(e);
                    } catch (Throwable th2) {
                        th = th2;
                        if (certificateInput != null) {
                            try {
                                certificateInput.close();
                            } catch (Throwable e22) {
                                logger.warn("Failed to close a_isRightVersion file: " + this.certificate, e22);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    certificateInput = certificateInput2;
                    if (certificateInput != null) {
                        certificateInput.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                throw new CertificateEncodingException(e);
            }
        } catch (NoSuchAlgorithmException e5) {
            throw new Error(e5);
        }
    }

    public File certificate() {
        return this.certificate;
    }

    public File privateKey() {
        return this.privateKey;
    }

    public X509Certificate cert() {
        return this.cert;
    }

    public PrivateKey key() {
        return this.key;
    }

    public void delete() {
        safeDelete(this.certificate);
        safeDelete(this.privateKey);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String[] newSelfSignedCertificate(java.lang.String r12, java.security.PrivateKey r13, java.security.cert.X509Certificate r14) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        /*
        r10 = 95;
        r11 = 1;
        r8 = r13.getEncoded();
        r7 = io.netty.buffer.Unpooled.wrappedBuffer(r8);
        r8 = 1;
        r3 = io.netty.handler.codec.base64.Base64.encode(r7, r8);	 Catch:{ all -> 0x00fc }
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f7 }
        r8.<init>();	 Catch:{ all -> 0x00f7 }
        r9 = "-----BEGIN PRIVATE KEY-----\n";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00f7 }
        r9 = io.netty.util.CharsetUtil.US_ASCII;	 Catch:{ all -> 0x00f7 }
        r9 = r3.toString(r9);	 Catch:{ all -> 0x00f7 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x00f7 }
        r9 = "\n-----END PRIVATE KEY-----\n";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00f7 }
        r6 = r8.toString();	 Catch:{ all -> 0x00f7 }
        r3.release();	 Catch:{ all -> 0x00fc }
        r7.release();
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "keyutil_";
        r8 = r8.append(r9);
        r8 = r8.append(r12);
        r8 = r8.append(r10);
        r8 = r8.toString();
        r9 = ".key";
        r4 = java.io.File.createTempFile(r8, r9);
        r4.deleteOnExit();
        r5 = new java.io.FileOutputStream;
        r5.<init>(r4);
        r8 = io.netty.util.CharsetUtil.US_ASCII;	 Catch:{ all -> 0x0101 }
        r8 = r6.getBytes(r8);	 Catch:{ all -> 0x0101 }
        r5.write(r8);	 Catch:{ all -> 0x0101 }
        r5.close();	 Catch:{ all -> 0x0101 }
        r5 = 0;
        if (r5 == 0) goto L_0x0073;
    L_0x006d:
        safeClose(r4, r5);
        safeDelete(r4);
    L_0x0073:
        r8 = r14.getEncoded();
        r7 = io.netty.buffer.Unpooled.wrappedBuffer(r8);
        r8 = 1;
        r3 = io.netty.handler.codec.base64.Base64.encode(r7, r8);	 Catch:{ all -> 0x0110 }
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x010b }
        r8.<init>();	 Catch:{ all -> 0x010b }
        r9 = "-----BEGIN CERTIFICATE-----\n";
        r8 = r8.append(r9);	 Catch:{ all -> 0x010b }
        r9 = io.netty.util.CharsetUtil.US_ASCII;	 Catch:{ all -> 0x010b }
        r9 = r3.toString(r9);	 Catch:{ all -> 0x010b }
        r8 = r8.append(r9);	 Catch:{ all -> 0x010b }
        r9 = "\n-----END CERTIFICATE-----\n";
        r8 = r8.append(r9);	 Catch:{ all -> 0x010b }
        r2 = r8.toString();	 Catch:{ all -> 0x010b }
        r3.release();	 Catch:{ all -> 0x0110 }
        r7.release();
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "keyutil_";
        r8 = r8.append(r9);
        r8 = r8.append(r12);
        r8 = r8.append(r10);
        r8 = r8.toString();
        r9 = ".crt";
        r0 = java.io.File.createTempFile(r8, r9);
        r0.deleteOnExit();
        r1 = new java.io.FileOutputStream;
        r1.<init>(r0);
        r8 = io.netty.util.CharsetUtil.US_ASCII;	 Catch:{ all -> 0x0115 }
        r8 = r2.getBytes(r8);	 Catch:{ all -> 0x0115 }
        r1.write(r8);	 Catch:{ all -> 0x0115 }
        r1.close();	 Catch:{ all -> 0x0115 }
        r1 = 0;
        if (r1 == 0) goto L_0x00e6;
    L_0x00dd:
        safeClose(r0, r1);
        safeDelete(r0);
        safeDelete(r4);
    L_0x00e6:
        r8 = 2;
        r8 = new java.lang.String[r8];
        r9 = 0;
        r10 = r0.getPath();
        r8[r9] = r10;
        r9 = r4.getPath();
        r8[r11] = r9;
        return r8;
    L_0x00f7:
        r8 = move-exception;
        r3.release();	 Catch:{ all -> 0x00fc }
        throw r8;	 Catch:{ all -> 0x00fc }
    L_0x00fc:
        r8 = move-exception;
        r7.release();
        throw r8;
    L_0x0101:
        r8 = move-exception;
        if (r5 == 0) goto L_0x010a;
    L_0x0104:
        safeClose(r4, r5);
        safeDelete(r4);
    L_0x010a:
        throw r8;
    L_0x010b:
        r8 = move-exception;
        r3.release();	 Catch:{ all -> 0x0110 }
        throw r8;	 Catch:{ all -> 0x0110 }
    L_0x0110:
        r8 = move-exception;
        r7.release();
        throw r8;
    L_0x0115:
        r8 = move-exception;
        if (r1 == 0) goto L_0x0121;
    L_0x0118:
        safeClose(r0, r1);
        safeDelete(r0);
        safeDelete(r4);
    L_0x0121:
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.util.SelfSignedCertificate.newSelfSignedCertificate(java.lang.String, java.security.PrivateKey, java.security.cert.X509Certificate):java.lang.String[]");
    }

    private static void safeDelete(File certFile) {
        if (!certFile.delete()) {
            logger.warn("Failed to delete a_isRightVersion file: " + certFile);
        }
    }

    private static void safeClose(File keyFile, OutputStream keyOut) {
        try {
            keyOut.close();
        } catch (Throwable e) {
            logger.warn("Failed to close a_isRightVersion file: " + keyFile, e);
        }
    }
}
