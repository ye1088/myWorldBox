package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.security.PrivateKey;

public final class PemPrivateKey extends AbstractReferenceCounted implements PemEncoded, PrivateKey {
    private static final byte[] BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] END_PRIVATE_KEY = "\n-----END PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final String PKCS8_FORMAT = "PKCS#8";
    private final ByteBuf content;

    static io.netty.handler.ssl.PemEncoded toPEM(io.netty.buffer.ByteBufAllocator r8, boolean r9, java.security.PrivateKey r10) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Assign predecessor not found for B:12:0x002b from B:30:0x005d
	at jadx.core.dex.visitors.ssa.EliminatePhiNodes.replaceMerge(EliminatePhiNodes.java:102)
	at jadx.core.dex.visitors.ssa.EliminatePhiNodes.replaceMergeInstructions(EliminatePhiNodes.java:68)
	at jadx.core.dex.visitors.ssa.EliminatePhiNodes.visit(EliminatePhiNodes.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r6 = r10 instanceof io.netty.handler.ssl.PemEncoded;
        if (r6 == 0) goto L_0x000b;
    L_0x0004:
        r10 = (io.netty.handler.ssl.PemEncoded) r10;
        r5 = r10.retain();
    L_0x000a:
        return r5;
    L_0x000b:
        r6 = r10.getEncoded();
        r1 = io.netty.buffer.Unpooled.wrappedBuffer(r6);
        r0 = io.netty.handler.ssl.SslUtils.toBase64(r8, r1);	 Catch:{ all -> 0x005c }
        r6 = BEGIN_PRIVATE_KEY;	 Catch:{ all -> 0x0057 }
        r6 = r6.length;	 Catch:{ all -> 0x0057 }
        r7 = r0.readableBytes();	 Catch:{ all -> 0x0057 }
        r6 = r6 + r7;	 Catch:{ all -> 0x0057 }
        r7 = END_PRIVATE_KEY;	 Catch:{ all -> 0x0057 }
        r7 = r7.length;	 Catch:{ all -> 0x0057 }
        r3 = r6 + r7;	 Catch:{ all -> 0x0057 }
        r4 = 0;	 Catch:{ all -> 0x0057 }
        if (r9 == 0) goto L_0x004b;	 Catch:{ all -> 0x0057 }
    L_0x0027:
        r2 = r8.directBuffer(r3);	 Catch:{ all -> 0x0057 }
    L_0x002b:
        r6 = BEGIN_PRIVATE_KEY;	 Catch:{ all -> 0x0050 }
        r2.writeBytes(r6);	 Catch:{ all -> 0x0050 }
        r2.writeBytes(r0);	 Catch:{ all -> 0x0050 }
        r6 = END_PRIVATE_KEY;	 Catch:{ all -> 0x0050 }
        r2.writeBytes(r6);	 Catch:{ all -> 0x0050 }
        r5 = new io.netty.handler.ssl.PemValue;	 Catch:{ all -> 0x0050 }
        r6 = 1;	 Catch:{ all -> 0x0050 }
        r5.<init>(r2, r6);	 Catch:{ all -> 0x0050 }
        r4 = 1;
        if (r4 != 0) goto L_0x0044;
    L_0x0041:
        io.netty.handler.ssl.SslUtils.zerooutAndRelease(r2);	 Catch:{ all -> 0x0057 }
    L_0x0044:
        io.netty.handler.ssl.SslUtils.zerooutAndRelease(r0);	 Catch:{ all -> 0x005c }
        io.netty.handler.ssl.SslUtils.zerooutAndRelease(r1);
        goto L_0x000a;
    L_0x004b:
        r2 = r8.buffer(r3);	 Catch:{ all -> 0x0057 }
        goto L_0x002b;	 Catch:{ all -> 0x0057 }
    L_0x0050:
        r6 = move-exception;	 Catch:{ all -> 0x0057 }
        if (r4 != 0) goto L_0x0056;	 Catch:{ all -> 0x0057 }
    L_0x0053:
        io.netty.handler.ssl.SslUtils.zerooutAndRelease(r2);	 Catch:{ all -> 0x0057 }
    L_0x0056:
        throw r6;	 Catch:{ all -> 0x0057 }
    L_0x0057:
        r6 = move-exception;
        io.netty.handler.ssl.SslUtils.zerooutAndRelease(r0);	 Catch:{ all -> 0x005c }
        throw r6;	 Catch:{ all -> 0x005c }
    L_0x005c:
        r6 = move-exception;
        io.netty.handler.ssl.SslUtils.zerooutAndRelease(r1);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.PemPrivateKey.toPEM(io.netty.buffer.ByteBufAllocator, boolean, java.security.PrivateKey):io.netty.handler.ssl.PemEncoded");
    }

    public static PemPrivateKey valueOf(byte[] key) {
        return valueOf(Unpooled.wrappedBuffer(key));
    }

    public static PemPrivateKey valueOf(ByteBuf key) {
        return new PemPrivateKey(key);
    }

    private PemPrivateKey(ByteBuf content) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content, "content");
    }

    public boolean isSensitive() {
        return true;
    }

    public ByteBuf content() {
        int count = refCnt();
        if (count > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(count);
    }

    public PemPrivateKey copy() {
        return replace(this.content.copy());
    }

    public PemPrivateKey duplicate() {
        return replace(this.content.duplicate());
    }

    public PemPrivateKey retainedDuplicate() {
        return replace(this.content.retainedDuplicate());
    }

    public PemPrivateKey replace(ByteBuf content) {
        return new PemPrivateKey(content);
    }

    public PemPrivateKey touch() {
        this.content.touch();
        return this;
    }

    public PemPrivateKey touch(Object hint) {
        this.content.touch(hint);
        return this;
    }

    public PemPrivateKey retain() {
        return (PemPrivateKey) super.retain();
    }

    public PemPrivateKey retain(int increment) {
        return (PemPrivateKey) super.retain(increment);
    }

    protected void deallocate() {
        SslUtils.zerooutAndRelease(this.content);
    }

    public byte[] getEncoded() {
        throw new UnsupportedOperationException();
    }

    public String getAlgorithm() {
        throw new UnsupportedOperationException();
    }

    public String getFormat() {
        return PKCS8_FORMAT;
    }

    public void destroy() {
        release(refCnt());
    }

    public boolean isDestroyed() {
        return refCnt() == 0;
    }
}
