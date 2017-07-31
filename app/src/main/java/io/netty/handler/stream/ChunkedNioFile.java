package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ChunkedNioFile implements ChunkedInput<ByteBuf> {
    private final int chunkSize;
    private final long endOffset;
    private final FileChannel in;
    private long offset;
    private final long startOffset;

    public io.netty.buffer.ByteBuf readChunk(io.netty.buffer.ByteBufAllocator r13) throws java.lang.Exception {
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
        r12 = this;
        r4 = r12.offset;
        r8 = r12.endOffset;
        r7 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r7 < 0) goto L_0x000a;
    L_0x0008:
        r0 = 0;
    L_0x0009:
        return r0;
    L_0x000a:
        r7 = r12.chunkSize;
        r8 = (long) r7;
        r10 = r12.endOffset;
        r10 = r10 - r4;
        r8 = java.lang.Math.min(r8, r10);
        r1 = (int) r8;
        r0 = r13.buffer(r1);
        r6 = 1;
        r3 = 0;
    L_0x001b:
        r7 = r12.in;	 Catch:{ all -> 0x0036 }
        r8 = r1 - r3;	 Catch:{ all -> 0x0036 }
        r2 = r0.writeBytes(r7, r8);	 Catch:{ all -> 0x0036 }
        if (r2 >= 0) goto L_0x0032;	 Catch:{ all -> 0x0036 }
    L_0x0025:
        r8 = r12.offset;	 Catch:{ all -> 0x0036 }
        r10 = (long) r3;	 Catch:{ all -> 0x0036 }
        r8 = r8 + r10;	 Catch:{ all -> 0x0036 }
        r12.offset = r8;	 Catch:{ all -> 0x0036 }
        r6 = 0;
        if (r6 == 0) goto L_0x0009;
    L_0x002e:
        r0.release();
        goto L_0x0009;
    L_0x0032:
        r3 = r3 + r2;
        if (r3 != r1) goto L_0x001b;
    L_0x0035:
        goto L_0x0025;
    L_0x0036:
        r7 = move-exception;
        if (r6 == 0) goto L_0x003c;
    L_0x0039:
        r0.release();
    L_0x003c:
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.stream.ChunkedNioFile.readChunk(io.netty.buffer.ByteBufAllocator):io.netty.buffer.ByteBuf");
    }

    public ChunkedNioFile(File in) throws IOException {
        this(new FileInputStream(in).getChannel());
    }

    public ChunkedNioFile(File in, int chunkSize) throws IOException {
        this(new FileInputStream(in).getChannel(), chunkSize);
    }

    public ChunkedNioFile(FileChannel in) throws IOException {
        this(in, 8192);
    }

    public ChunkedNioFile(FileChannel in, int chunkSize) throws IOException {
        this(in, 0, in.size(), chunkSize);
    }

    public ChunkedNioFile(FileChannel in, long offset, long length, int chunkSize) throws IOException {
        if (in == null) {
            throw new NullPointerException("in");
        } else if (offset < 0) {
            throw new IllegalArgumentException("offset: " + offset + " (expected: 0 or greater)");
        } else if (length < 0) {
            throw new IllegalArgumentException("length: " + length + " (expected: 0 or greater)");
        } else if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        } else {
            if (offset != 0) {
                in.position(offset);
            }
            this.in = in;
            this.chunkSize = chunkSize;
            this.startOffset = offset;
            this.offset = offset;
            this.endOffset = offset + length;
        }
    }

    public long startOffset() {
        return this.startOffset;
    }

    public long endOffset() {
        return this.endOffset;
    }

    public long currentOffset() {
        return this.offset;
    }

    public boolean isEndOfInput() throws Exception {
        return this.offset >= this.endOffset || !this.in.isOpen();
    }

    public void close() throws Exception {
        this.in.close();
    }

    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
        return readChunk(ctx.alloc());
    }

    public long length() {
        return this.endOffset - this.startOffset;
    }

    public long progress() {
        return this.offset - this.startOffset;
    }
}
