package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.util.e;

public class ChunkedFile implements ChunkedInput<ByteBuf> {
    private final int chunkSize;
    private final long endOffset;
    private final RandomAccessFile file;
    private long offset;
    private final long startOffset;

    public io.netty.buffer.ByteBuf readChunk(io.netty.buffer.ByteBufAllocator r11) throws java.lang.Exception {
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
        r10 = this;
        r2 = r10.offset;
        r6 = r10.endOffset;
        r5 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r5 < 0) goto L_0x000a;
    L_0x0008:
        r0 = 0;
    L_0x0009:
        return r0;
    L_0x000a:
        r5 = r10.chunkSize;
        r6 = (long) r5;
        r8 = r10.endOffset;
        r8 = r8 - r2;
        r6 = java.lang.Math.min(r6, r8);
        r1 = (int) r6;
        r0 = r11.heapBuffer(r1);
        r4 = 1;
        r5 = r10.file;	 Catch:{ all -> 0x0035 }
        r6 = r0.array();	 Catch:{ all -> 0x0035 }
        r7 = r0.arrayOffset();	 Catch:{ all -> 0x0035 }
        r5.readFully(r6, r7, r1);	 Catch:{ all -> 0x0035 }
        r0.writerIndex(r1);	 Catch:{ all -> 0x0035 }
        r6 = (long) r1;	 Catch:{ all -> 0x0035 }
        r6 = r6 + r2;	 Catch:{ all -> 0x0035 }
        r10.offset = r6;	 Catch:{ all -> 0x0035 }
        r4 = 0;
        if (r4 == 0) goto L_0x0009;
    L_0x0031:
        r0.release();
        goto L_0x0009;
    L_0x0035:
        r5 = move-exception;
        if (r4 == 0) goto L_0x003b;
    L_0x0038:
        r0.release();
    L_0x003b:
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.stream.ChunkedFile.readChunk(io.netty.buffer.ByteBufAllocator):io.netty.buffer.ByteBuf");
    }

    public ChunkedFile(File file) throws IOException {
        this(file, 8192);
    }

    public ChunkedFile(File file, int chunkSize) throws IOException {
        this(new RandomAccessFile(file, e.clf), chunkSize);
    }

    public ChunkedFile(RandomAccessFile file) throws IOException {
        this(file, 8192);
    }

    public ChunkedFile(RandomAccessFile file, int chunkSize) throws IOException {
        this(file, 0, file.length(), chunkSize);
    }

    public ChunkedFile(RandomAccessFile file, long offset, long length, int chunkSize) throws IOException {
        if (file == null) {
            throw new NullPointerException("file");
        } else if (offset < 0) {
            throw new IllegalArgumentException("offset: " + offset + " (expected: 0 or greater)");
        } else if (length < 0) {
            throw new IllegalArgumentException("length: " + length + " (expected: 0 or greater)");
        } else if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        } else {
            this.file = file;
            this.startOffset = offset;
            this.offset = offset;
            this.endOffset = offset + length;
            this.chunkSize = chunkSize;
            file.seek(offset);
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
        return this.offset >= this.endOffset || !this.file.getChannel().isOpen();
    }

    public void close() throws Exception {
        this.file.close();
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
