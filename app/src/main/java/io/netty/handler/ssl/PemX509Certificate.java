package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

public final class PemX509Certificate extends X509Certificate implements PemEncoded {
    private static final byte[] BEGIN_CERT = "-----BEGIN CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] END_CERT = "\n-----END CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private final ByteBuf content;

    static io.netty.handler.ssl.PemEncoded toPEM(io.netty.buffer.ByteBufAllocator r11, boolean r12, java.security.cert.X509Certificate... r13) throws java.security.cert.CertificateEncodingException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = 0;
        if (r13 == 0) goto L_0x0006;
    L_0x0003:
        r8 = r13.length;
        if (r8 != 0) goto L_0x000f;
    L_0x0006:
        r8 = new java.lang.IllegalArgumentException;
        r9 = "X.509 certificate chain can't be null or empty";
        r8.<init>(r9);
        throw r8;
    L_0x000f:
        r8 = r13.length;
        r9 = 1;
        if (r8 != r9) goto L_0x0020;
    L_0x0013:
        r2 = r13[r10];
        r8 = r2 instanceof io.netty.handler.ssl.PemEncoded;
        if (r8 == 0) goto L_0x0020;
    L_0x0019:
        r2 = (io.netty.handler.ssl.PemEncoded) r2;
        r7 = r2.retain();
    L_0x001f:
        return r7;
    L_0x0020:
        r6 = 0;
        r5 = 0;
        r0 = r13;
        r4 = r0.length;	 Catch:{ all -> 0x0049 }
        r3 = 0;	 Catch:{ all -> 0x0049 }
    L_0x0025:
        if (r3 >= r4) goto L_0x0066;	 Catch:{ all -> 0x0049 }
    L_0x0027:
        r1 = r0[r3];	 Catch:{ all -> 0x0049 }
        if (r1 != 0) goto L_0x0052;	 Catch:{ all -> 0x0049 }
    L_0x002b:
        r8 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0049 }
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0049 }
        r9.<init>();	 Catch:{ all -> 0x0049 }
        r10 = "Null element in chain: ";	 Catch:{ all -> 0x0049 }
        r9 = r9.append(r10);	 Catch:{ all -> 0x0049 }
        r10 = java.util.Arrays.toString(r13);	 Catch:{ all -> 0x0049 }
        r9 = r9.append(r10);	 Catch:{ all -> 0x0049 }
        r9 = r9.toString();	 Catch:{ all -> 0x0049 }
        r8.<init>(r9);	 Catch:{ all -> 0x0049 }
        throw r8;	 Catch:{ all -> 0x0049 }
    L_0x0049:
        r8 = move-exception;
        if (r6 != 0) goto L_0x0051;
    L_0x004c:
        if (r5 == 0) goto L_0x0051;
    L_0x004e:
        r5.release();
    L_0x0051:
        throw r8;
    L_0x0052:
        r8 = r1 instanceof io.netty.handler.ssl.PemEncoded;	 Catch:{ all -> 0x0049 }
        if (r8 == 0) goto L_0x0060;	 Catch:{ all -> 0x0049 }
    L_0x0056:
        r1 = (io.netty.handler.ssl.PemEncoded) r1;	 Catch:{ all -> 0x0049 }
        r8 = r13.length;	 Catch:{ all -> 0x0049 }
        r5 = append(r11, r12, r1, r8, r5);	 Catch:{ all -> 0x0049 }
    L_0x005d:
        r3 = r3 + 1;	 Catch:{ all -> 0x0049 }
        goto L_0x0025;	 Catch:{ all -> 0x0049 }
    L_0x0060:
        r8 = r13.length;	 Catch:{ all -> 0x0049 }
        r5 = append(r11, r12, r1, r8, r5);	 Catch:{ all -> 0x0049 }
        goto L_0x005d;	 Catch:{ all -> 0x0049 }
    L_0x0066:
        r7 = new io.netty.handler.ssl.PemValue;	 Catch:{ all -> 0x0049 }
        r8 = 0;	 Catch:{ all -> 0x0049 }
        r7.<init>(r5, r8);	 Catch:{ all -> 0x0049 }
        r6 = 1;
        if (r6 != 0) goto L_0x001f;
    L_0x006f:
        if (r5 == 0) goto L_0x001f;
    L_0x0071:
        r5.release();
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.PemX509Certificate.toPEM(io.netty.buffer.ByteBufAllocator, boolean, java.security.cert.X509Certificate[]):io.netty.handler.ssl.PemEncoded");
    }

    private static ByteBuf append(ByteBufAllocator allocator, boolean useDirect, PemEncoded encoded, int count, ByteBuf pem) {
        ByteBuf content = encoded.content();
        if (pem == null) {
            pem = newBuffer(allocator, useDirect, content.readableBytes() * count);
        }
        pem.writeBytes(content.slice());
        return pem;
    }

    private static ByteBuf append(ByteBufAllocator allocator, boolean useDirect, X509Certificate cert, int count, ByteBuf pem) throws CertificateEncodingException {
        ByteBuf encoded = Unpooled.wrappedBuffer(cert.getEncoded());
        ByteBuf base64;
        try {
            base64 = SslUtils.toBase64(allocator, encoded);
            if (pem == null) {
                pem = newBuffer(allocator, useDirect, ((BEGIN_CERT.length + base64.readableBytes()) + END_CERT.length) * count);
            }
            pem.writeBytes(BEGIN_CERT);
            pem.writeBytes(base64);
            pem.writeBytes(END_CERT);
        } catch (Throwable th) {
        } finally {
            
/*
Method generation error in method: io.netty.handler.ssl.PemX509Certificate.append(io.netty.buffer.ByteBufAllocator, boolean, java.security.cert.X509Certificate, int, io.netty.buffer.ByteBuf):io.netty.buffer.ByteBuf
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0034: INVOKE  (wrap: io.netty.buffer.ByteBuf
  ?: MERGE  (r0_2 'base64' io.netty.buffer.ByteBuf) = (r0_1 'base64' io.netty.buffer.ByteBuf), (r1_0 'encoded' io.netty.buffer.ByteBuf)) io.netty.buffer.ByteBuf.release():boolean type: VIRTUAL in method: io.netty.handler.ssl.PemX509Certificate.append(io.netty.buffer.ByteBufAllocator, boolean, java.security.cert.X509Certificate, int, io.netty.buffer.ByteBuf):io.netty.buffer.ByteBuf
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r0_2 'base64' io.netty.buffer.ByteBuf) = (r0_1 'base64' io.netty.buffer.ByteBuf), (r1_0 'encoded' io.netty.buffer.ByteBuf) in method: io.netty.handler.ssl.PemX509Certificate.append(io.netty.buffer.ByteBufAllocator, boolean, java.security.cert.X509Certificate, int, io.netty.buffer.ByteBuf):io.netty.buffer.ByteBuf
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:84)
	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:625)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:343)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 21 more
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 26 more

*/

            private static ByteBuf newBuffer(ByteBufAllocator allocator, boolean useDirect, int initialCapacity) {
                return useDirect ? allocator.directBuffer(initialCapacity) : allocator.buffer(initialCapacity);
            }

            public static PemX509Certificate valueOf(byte[] key) {
                return valueOf(Unpooled.wrappedBuffer(key));
            }

            public static PemX509Certificate valueOf(ByteBuf key) {
                return new PemX509Certificate(key);
            }

            private PemX509Certificate(ByteBuf content) {
                this.content = (ByteBuf) ObjectUtil.checkNotNull(content, "content");
            }

            public boolean isSensitive() {
                return false;
            }

            public int refCnt() {
                return this.content.refCnt();
            }

            public ByteBuf content() {
                int count = refCnt();
                if (count > 0) {
                    return this.content;
                }
                throw new IllegalReferenceCountException(count);
            }

            public PemX509Certificate copy() {
                return replace(this.content.copy());
            }

            public PemX509Certificate duplicate() {
                return replace(this.content.duplicate());
            }

            public PemX509Certificate retainedDuplicate() {
                return replace(this.content.retainedDuplicate());
            }

            public PemX509Certificate replace(ByteBuf content) {
                return new PemX509Certificate(content);
            }

            public PemX509Certificate retain() {
                this.content.retain();
                return this;
            }

            public PemX509Certificate retain(int increment) {
                this.content.retain(increment);
                return this;
            }

            public PemX509Certificate touch() {
                this.content.touch();
                return this;
            }

            public PemX509Certificate touch(Object hint) {
                this.content.touch(hint);
                return this;
            }

            public boolean release() {
                return this.content.release();
            }

            public boolean release(int decrement) {
                return this.content.release(decrement);
            }

            public byte[] getEncoded() {
                throw new UnsupportedOperationException();
            }

            public boolean hasUnsupportedCriticalExtension() {
                throw new UnsupportedOperationException();
            }

            public Set<String> getCriticalExtensionOIDs() {
                throw new UnsupportedOperationException();
            }

            public Set<String> getNonCriticalExtensionOIDs() {
                throw new UnsupportedOperationException();
            }

            public byte[] getExtensionValue(String oid) {
                throw new UnsupportedOperationException();
            }

            public void checkValidity() {
                throw new UnsupportedOperationException();
            }

            public void checkValidity(Date date) {
                throw new UnsupportedOperationException();
            }

            public int getVersion() {
                throw new UnsupportedOperationException();
            }

            public BigInteger getSerialNumber() {
                throw new UnsupportedOperationException();
            }

            public Principal getIssuerDN() {
                throw new UnsupportedOperationException();
            }

            public Principal getSubjectDN() {
                throw new UnsupportedOperationException();
            }

            public Date getNotBefore() {
                throw new UnsupportedOperationException();
            }

            public Date getNotAfter() {
                throw new UnsupportedOperationException();
            }

            public byte[] getTBSCertificate() {
                throw new UnsupportedOperationException();
            }

            public byte[] getSignature() {
                throw new UnsupportedOperationException();
            }

            public String getSigAlgName() {
                throw new UnsupportedOperationException();
            }

            public String getSigAlgOID() {
                throw new UnsupportedOperationException();
            }

            public byte[] getSigAlgParams() {
                throw new UnsupportedOperationException();
            }

            public boolean[] getIssuerUniqueID() {
                throw new UnsupportedOperationException();
            }

            public boolean[] getSubjectUniqueID() {
                throw new UnsupportedOperationException();
            }

            public boolean[] getKeyUsage() {
                throw new UnsupportedOperationException();
            }

            public int getBasicConstraints() {
                throw new UnsupportedOperationException();
            }

            public void verify(PublicKey key) {
                throw new UnsupportedOperationException();
            }

            public void verify(PublicKey key, String sigProvider) {
                throw new UnsupportedOperationException();
            }

            public PublicKey getPublicKey() {
                throw new UnsupportedOperationException();
            }

            public boolean equals(Object o) {
                if (o == this) {
                    return true;
                }
                if (!(o instanceof PemX509Certificate)) {
                    return false;
                }
                return this.content.equals(((PemX509Certificate) o).content);
            }

            public int hashCode() {
                return this.content.hashCode();
            }

            public String toString() {
                return this.content.toString(CharsetUtil.UTF_8);
            }
        }
