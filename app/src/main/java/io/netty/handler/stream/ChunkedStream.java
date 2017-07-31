package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class ChunkedStream implements ChunkedInput<ByteBuf> {
    static final int DEFAULT_CHUNK_SIZE = 8192;
    private final int chunkSize;
    private boolean closed;
    private final PushbackInputStream in;
    private long offset;

    public io.netty.buffer.ByteBuf readChunk(io.netty.buffer.ByteBufAllocator r9) throws java.lang.Exception {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r4 = r8.isEndOfInput();
        if (r4 == 0) goto L_0x0008;
    L_0x0006:
        r1 = 0;
    L_0x0007:
        return r1;
    L_0x0008:
        r4 = r8.in;
        r0 = r4.available();
        if (r0 > 0) goto L_0x002a;
    L_0x0010:
        r2 = r8.chunkSize;
    L_0x0012:
        r3 = 1;
        r1 = r9.buffer(r2);
        r4 = r8.offset;	 Catch:{ all -> 0x0037 }
        r6 = r8.in;	 Catch:{ all -> 0x0037 }
        r6 = r1.writeBytes(r6, r2);	 Catch:{ all -> 0x0037 }
        r6 = (long) r6;	 Catch:{ all -> 0x0037 }
        r4 = r4 + r6;	 Catch:{ all -> 0x0037 }
        r8.offset = r4;	 Catch:{ all -> 0x0037 }
        r3 = 0;
        if (r3 == 0) goto L_0x0007;
    L_0x0026:
        r1.release();
        goto L_0x0007;
    L_0x002a:
        r4 = r8.chunkSize;
        r5 = r8.in;
        r5 = r5.available();
        r2 = java.lang.Math.min(r4, r5);
        goto L_0x0012;
    L_0x0037:
        r4 = move-exception;
        if (r3 == 0) goto L_0x003d;
    L_0x003a:
        r1.release();
    L_0x003d:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.stream.ChunkedStream.readChunk(io.netty.buffer.ByteBufAllocator):io.netty.buffer.ByteBuf");
    }

    public ChunkedStream(InputStream in) {
        this(in, 8192);
    }

    public ChunkedStream(InputStream in, int chunkSize) {
        if (in == null) {
            throw new NullPointerException("in");
        } else if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        } else {
            if (in instanceof PushbackInputStream) {
                this.in = (PushbackInputStream) in;
            } else {
                this.in = new PushbackInputStream(in);
            }
            this.chunkSize = chunkSize;
        }
    }

    public long transferredBytes() {
        return this.offset;
    }

    public boolean isEndOfInput() throws Exception {
        if (this.closed) {
            return true;
        }
        int b = this.in.read();
        if (b < 0) {
            return true;
        }
        this.in.unread(b);
        return false;
    }

    public void close() throws Exception {
        this.closed = true;
        this.in.close();
    }

    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
        return readChunk(ctx.alloc());
    }

    public long length() {
        return -1;
    }

    public long progress() {
        return this.offset;
    }
}
