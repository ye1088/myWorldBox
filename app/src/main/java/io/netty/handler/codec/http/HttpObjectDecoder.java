package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.AppendableCharSequence;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class HttpObjectDecoder extends ByteToMessageDecoder {
    static final /* synthetic */ boolean $assertionsDisabled = (!HttpObjectDecoder.class.desiredAssertionStatus());
    private static final String EMPTY_VALUE = "";
    private long chunkSize;
    private final boolean chunkedSupported;
    private long contentLength;
    private State currentState;
    private final HeaderParser headerParser;
    private final LineParser lineParser;
    private final int maxChunkSize;
    private HttpMessage message;
    private CharSequence name;
    private volatile boolean resetRequested;
    private LastHttpContent trailer;
    protected final boolean validateHeaders;
    private CharSequence value;

    private static class HeaderParser implements ByteProcessor {
        private final int maxLength;
        private final AppendableCharSequence seq;
        private int size;

        HeaderParser(AppendableCharSequence seq, int maxLength) {
            this.seq = seq;
            this.maxLength = maxLength;
        }

        public AppendableCharSequence parse(ByteBuf buffer) {
            int oldSize = this.size;
            this.seq.reset();
            int i = buffer.forEachByte(this);
            if (i == -1) {
                this.size = oldSize;
                return null;
            }
            buffer.readerIndex(i + 1);
            return this.seq;
        }

        public void reset() {
            this.size = 0;
        }

        public boolean process(byte value) throws Exception {
            char nextByte = (char) value;
            if (nextByte == StringUtil.CARRIAGE_RETURN) {
                return true;
            }
            if (nextByte == '\n') {
                return false;
            }
            int i = this.size + 1;
            this.size = i;
            if (i > this.maxLength) {
                throw newException(this.maxLength);
            }
            this.seq.append(nextByte);
            return true;
        }

        protected TooLongFrameException newException(int maxLength) {
            return new TooLongFrameException("HTTP header is larger than " + maxLength + " bytes.");
        }
    }

    private static final class LineParser extends HeaderParser {
        LineParser(AppendableCharSequence seq, int maxLength) {
            super(seq, maxLength);
        }

        public AppendableCharSequence parse(ByteBuf buffer) {
            reset();
            return super.parse(buffer);
        }

        protected TooLongFrameException newException(int maxLength) {
            return new TooLongFrameException("An HTTP line is larger than " + maxLength + " bytes.");
        }
    }

    private enum State {
        SKIP_CONTROL_CHARS,
        READ_INITIAL,
        READ_HEADER,
        READ_VARIABLE_LENGTH_CONTENT,
        READ_FIXED_LENGTH_CONTENT,
        READ_CHUNK_SIZE,
        READ_CHUNKED_CONTENT,
        READ_CHUNK_DELIMITER,
        READ_CHUNK_FOOTER,
        BAD_MESSAGE,
        UPGRADED
    }

    protected abstract HttpMessage createInvalidMessage();

    protected abstract HttpMessage createMessage(String[] strArr) throws Exception;

    protected abstract boolean isDecodingRequest();

    protected HttpObjectDecoder() {
        this(4096, 8192, 8192, true);
    }

    protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported) {
        this(maxInitialLineLength, maxHeaderSize, maxChunkSize, chunkedSupported, true);
    }

    protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported, boolean validateHeaders) {
        this(maxInitialLineLength, maxHeaderSize, maxChunkSize, chunkedSupported, validateHeaders, 128);
    }

    protected HttpObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported, boolean validateHeaders, int initialBufferSize) {
        this.contentLength = Long.MIN_VALUE;
        this.currentState = State.SKIP_CONTROL_CHARS;
        if (maxInitialLineLength <= 0) {
            throw new IllegalArgumentException("maxInitialLineLength must be a_isRightVersion positive integer: " + maxInitialLineLength);
        } else if (maxHeaderSize <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be a_isRightVersion positive integer: " + maxHeaderSize);
        } else if (maxChunkSize <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a_isRightVersion positive integer: " + maxChunkSize);
        } else {
            AppendableCharSequence seq = new AppendableCharSequence(initialBufferSize);
            this.lineParser = new LineParser(seq, maxInitialLineLength);
            this.headerParser = new HeaderParser(seq, maxHeaderSize);
            this.maxChunkSize = maxChunkSize;
            this.chunkedSupported = chunkedSupported;
            this.validateHeaders = validateHeaders;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void decode(io.netty.channel.ChannelHandlerContext r27, io.netty.buffer.ByteBuf r28, java.util.List<java.lang.Object> r29) throws java.lang.Exception {
        /*
        r26 = this;
        r0 = r26;
        r0 = r0.resetRequested;
        r21 = r0;
        if (r21 == 0) goto L_0x000b;
    L_0x0008:
        r26.resetNow();
    L_0x000b:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$http$HttpObjectDecoder$State;
        r0 = r26;
        r0 = r0.currentState;
        r22 = r0;
        r22 = r22.ordinal();
        r21 = r21[r22];
        switch(r21) {
            case 1: goto L_0x001d;
            case 2: goto L_0x01e6;
            case 3: goto L_0x002b;
            case 4: goto L_0x0078;
            case 5: goto L_0x013e;
            case 6: goto L_0x0166;
            case 7: goto L_0x022d;
            case 8: goto L_0x02a2;
            case 9: goto L_0x02ce;
            case 10: goto L_0x02f6;
            case 11: goto L_0x0303;
            default: goto L_0x001c;
        };
    L_0x001c:
        return;
    L_0x001d:
        r21 = skipControlCharacters(r28);
        if (r21 == 0) goto L_0x001c;
    L_0x0023:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_INITIAL;
        r0 = r21;
        r1 = r26;
        r1.currentState = r0;
    L_0x002b:
        r0 = r26;
        r0 = r0.lineParser;	 Catch:{ Exception -> 0x0053 }
        r21 = r0;
        r0 = r21;
        r1 = r28;
        r11 = r0.parse(r1);	 Catch:{ Exception -> 0x0053 }
        if (r11 == 0) goto L_0x001c;
    L_0x003b:
        r10 = splitInitialLine(r11);	 Catch:{ Exception -> 0x0053 }
        r0 = r10.length;	 Catch:{ Exception -> 0x0053 }
        r21 = r0;
        r22 = 3;
        r0 = r21;
        r1 = r22;
        if (r0 >= r1) goto L_0x0064;
    L_0x004a:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.SKIP_CONTROL_CHARS;	 Catch:{ Exception -> 0x0053 }
        r0 = r21;
        r1 = r26;
        r1.currentState = r0;	 Catch:{ Exception -> 0x0053 }
        goto L_0x001c;
    L_0x0053:
        r7 = move-exception;
        r0 = r26;
        r1 = r28;
        r21 = r0.invalidMessage(r1, r7);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        goto L_0x001c;
    L_0x0064:
        r0 = r26;
        r21 = r0.createMessage(r10);	 Catch:{ Exception -> 0x0053 }
        r0 = r21;
        r1 = r26;
        r1.message = r0;	 Catch:{ Exception -> 0x0053 }
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_HEADER;	 Catch:{ Exception -> 0x0053 }
        r0 = r21;
        r1 = r26;
        r1.currentState = r0;	 Catch:{ Exception -> 0x0053 }
    L_0x0078:
        r0 = r26;
        r1 = r28;
        r13 = r0.readHeaders(r1);	 Catch:{ Exception -> 0x00c2 }
        if (r13 == 0) goto L_0x001c;
    L_0x0082:
        r0 = r26;
        r0.currentState = r13;	 Catch:{ Exception -> 0x00c2 }
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$http$HttpObjectDecoder$State;	 Catch:{ Exception -> 0x00c2 }
        r22 = r13.ordinal();	 Catch:{ Exception -> 0x00c2 }
        r21 = r21[r22];	 Catch:{ Exception -> 0x00c2 }
        switch(r21) {
            case 1: goto L_0x00d4;
            case 2: goto L_0x00ef;
            default: goto L_0x0091;
        };	 Catch:{ Exception -> 0x00c2 }
    L_0x0091:
        r8 = r26.contentLength();	 Catch:{ Exception -> 0x00c2 }
        r22 = 0;
        r21 = (r8 > r22 ? 1 : (r8 == r22 ? 0 : -1));
        if (r21 == 0) goto L_0x00a7;
    L_0x009b:
        r22 = -1;
        r21 = (r8 > r22 ? 1 : (r8 == r22 ? 0 : -1));
        if (r21 != 0) goto L_0x010f;
    L_0x00a1:
        r21 = r26.isDecodingRequest();	 Catch:{ Exception -> 0x00c2 }
        if (r21 == 0) goto L_0x010f;
    L_0x00a7:
        r0 = r26;
        r0 = r0.message;	 Catch:{ Exception -> 0x00c2 }
        r21 = r0;
        r0 = r29;
        r1 = r21;
        r0.add(r1);	 Catch:{ Exception -> 0x00c2 }
        r21 = io.netty.handler.codec.http.LastHttpContent.EMPTY_LAST_CONTENT;	 Catch:{ Exception -> 0x00c2 }
        r0 = r29;
        r1 = r21;
        r0.add(r1);	 Catch:{ Exception -> 0x00c2 }
        r26.resetNow();	 Catch:{ Exception -> 0x00c2 }
        goto L_0x001c;
    L_0x00c2:
        r7 = move-exception;
        r0 = r26;
        r1 = r28;
        r21 = r0.invalidMessage(r1, r7);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        goto L_0x001c;
    L_0x00d4:
        r0 = r26;
        r0 = r0.message;	 Catch:{ Exception -> 0x00c2 }
        r21 = r0;
        r0 = r29;
        r1 = r21;
        r0.add(r1);	 Catch:{ Exception -> 0x00c2 }
        r21 = io.netty.handler.codec.http.LastHttpContent.EMPTY_LAST_CONTENT;	 Catch:{ Exception -> 0x00c2 }
        r0 = r29;
        r1 = r21;
        r0.add(r1);	 Catch:{ Exception -> 0x00c2 }
        r26.resetNow();	 Catch:{ Exception -> 0x00c2 }
        goto L_0x001c;
    L_0x00ef:
        r0 = r26;
        r0 = r0.chunkedSupported;	 Catch:{ Exception -> 0x00c2 }
        r21 = r0;
        if (r21 != 0) goto L_0x0100;
    L_0x00f7:
        r21 = new java.lang.IllegalArgumentException;	 Catch:{ Exception -> 0x00c2 }
        r22 = "Chunked messages not supported";
        r21.<init>(r22);	 Catch:{ Exception -> 0x00c2 }
        throw r21;	 Catch:{ Exception -> 0x00c2 }
    L_0x0100:
        r0 = r26;
        r0 = r0.message;	 Catch:{ Exception -> 0x00c2 }
        r21 = r0;
        r0 = r29;
        r1 = r21;
        r0.add(r1);	 Catch:{ Exception -> 0x00c2 }
        goto L_0x001c;
    L_0x010f:
        r21 = $assertionsDisabled;	 Catch:{ Exception -> 0x00c2 }
        if (r21 != 0) goto L_0x0125;
    L_0x0113:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_FIXED_LENGTH_CONTENT;	 Catch:{ Exception -> 0x00c2 }
        r0 = r21;
        if (r13 == r0) goto L_0x0125;
    L_0x0119:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_VARIABLE_LENGTH_CONTENT;	 Catch:{ Exception -> 0x00c2 }
        r0 = r21;
        if (r13 == r0) goto L_0x0125;
    L_0x011f:
        r21 = new java.lang.AssertionError;	 Catch:{ Exception -> 0x00c2 }
        r21.<init>();	 Catch:{ Exception -> 0x00c2 }
        throw r21;	 Catch:{ Exception -> 0x00c2 }
    L_0x0125:
        r0 = r26;
        r0 = r0.message;	 Catch:{ Exception -> 0x00c2 }
        r21 = r0;
        r0 = r29;
        r1 = r21;
        r0.add(r1);	 Catch:{ Exception -> 0x00c2 }
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_FIXED_LENGTH_CONTENT;	 Catch:{ Exception -> 0x00c2 }
        r0 = r21;
        if (r13 != r0) goto L_0x001c;
    L_0x0138:
        r0 = r26;
        r0.chunkSize = r8;	 Catch:{ Exception -> 0x00c2 }
        goto L_0x001c;
    L_0x013e:
        r21 = r28.readableBytes();
        r0 = r26;
        r0 = r0.maxChunkSize;
        r22 = r0;
        r18 = java.lang.Math.min(r21, r22);
        if (r18 <= 0) goto L_0x001c;
    L_0x014e:
        r0 = r28;
        r1 = r18;
        r6 = r0.readRetainedSlice(r1);
        r21 = new io.netty.handler.codec.http.DefaultHttpContent;
        r0 = r21;
        r0.<init>(r6);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        goto L_0x001c;
    L_0x0166:
        r16 = r28.readableBytes();
        if (r16 == 0) goto L_0x001c;
    L_0x016c:
        r0 = r26;
        r0 = r0.maxChunkSize;
        r21 = r0;
        r0 = r16;
        r1 = r21;
        r18 = java.lang.Math.min(r0, r1);
        r0 = r18;
        r0 = (long) r0;
        r22 = r0;
        r0 = r26;
        r0 = r0.chunkSize;
        r24 = r0;
        r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1));
        if (r21 <= 0) goto L_0x0194;
    L_0x0189:
        r0 = r26;
        r0 = r0.chunkSize;
        r22 = r0;
        r0 = r22;
        r0 = (int) r0;
        r18 = r0;
    L_0x0194:
        r0 = r28;
        r1 = r18;
        r6 = r0.readRetainedSlice(r1);
        r0 = r26;
        r0 = r0.chunkSize;
        r22 = r0;
        r0 = r18;
        r0 = (long) r0;
        r24 = r0;
        r22 = r22 - r24;
        r0 = r22;
        r2 = r26;
        r2.chunkSize = r0;
        r0 = r26;
        r0 = r0.chunkSize;
        r22 = r0;
        r24 = 0;
        r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1));
        if (r21 != 0) goto L_0x01d6;
    L_0x01bb:
        r21 = new io.netty.handler.codec.http.DefaultLastHttpContent;
        r0 = r26;
        r0 = r0.validateHeaders;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0.<init>(r6, r1);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        r26.resetNow();
        goto L_0x001c;
    L_0x01d6:
        r21 = new io.netty.handler.codec.http.DefaultHttpContent;
        r0 = r21;
        r0.<init>(r6);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        goto L_0x001c;
    L_0x01e6:
        r0 = r26;
        r0 = r0.lineParser;	 Catch:{ Exception -> 0x0213 }
        r21 = r0;
        r0 = r21;
        r1 = r28;
        r11 = r0.parse(r1);	 Catch:{ Exception -> 0x0213 }
        if (r11 == 0) goto L_0x001c;
    L_0x01f6:
        r21 = r11.toString();	 Catch:{ Exception -> 0x0213 }
        r5 = getChunkSize(r21);	 Catch:{ Exception -> 0x0213 }
        r0 = (long) r5;	 Catch:{ Exception -> 0x0213 }
        r22 = r0;
        r0 = r22;
        r2 = r26;
        r2.chunkSize = r0;	 Catch:{ Exception -> 0x0213 }
        if (r5 != 0) goto L_0x0225;
    L_0x0209:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_CHUNK_FOOTER;	 Catch:{ Exception -> 0x0213 }
        r0 = r21;
        r1 = r26;
        r1.currentState = r0;	 Catch:{ Exception -> 0x0213 }
        goto L_0x001c;
    L_0x0213:
        r7 = move-exception;
        r0 = r26;
        r1 = r28;
        r21 = r0.invalidChunk(r1, r7);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        goto L_0x001c;
    L_0x0225:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_CHUNKED_CONTENT;	 Catch:{ Exception -> 0x0213 }
        r0 = r21;
        r1 = r26;
        r1.currentState = r0;	 Catch:{ Exception -> 0x0213 }
    L_0x022d:
        r21 = $assertionsDisabled;
        if (r21 != 0) goto L_0x0244;
    L_0x0231:
        r0 = r26;
        r0 = r0.chunkSize;
        r22 = r0;
        r24 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1));
        if (r21 <= 0) goto L_0x0244;
    L_0x023e:
        r21 = new java.lang.AssertionError;
        r21.<init>();
        throw r21;
    L_0x0244:
        r0 = r26;
        r0 = r0.chunkSize;
        r22 = r0;
        r0 = r22;
        r0 = (int) r0;
        r21 = r0;
        r0 = r26;
        r0 = r0.maxChunkSize;
        r22 = r0;
        r18 = java.lang.Math.min(r21, r22);
        r21 = r28.readableBytes();
        r0 = r18;
        r1 = r21;
        r18 = java.lang.Math.min(r0, r1);
        if (r18 == 0) goto L_0x001c;
    L_0x0267:
        r4 = new io.netty.handler.codec.http.DefaultHttpContent;
        r0 = r28;
        r1 = r18;
        r21 = r0.readRetainedSlice(r1);
        r0 = r21;
        r4.<init>(r0);
        r0 = r26;
        r0 = r0.chunkSize;
        r22 = r0;
        r0 = r18;
        r0 = (long) r0;
        r24 = r0;
        r22 = r22 - r24;
        r0 = r22;
        r2 = r26;
        r2.chunkSize = r0;
        r0 = r29;
        r0.add(r4);
        r0 = r26;
        r0 = r0.chunkSize;
        r22 = r0;
        r24 = 0;
        r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1));
        if (r21 != 0) goto L_0x001c;
    L_0x029a:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_CHUNK_DELIMITER;
        r0 = r21;
        r1 = r26;
        r1.currentState = r0;
    L_0x02a2:
        r20 = r28.writerIndex();
        r14 = r28.readerIndex();
        r15 = r14;
    L_0x02ab:
        r0 = r20;
        if (r0 <= r15) goto L_0x031a;
    L_0x02af:
        r14 = r15 + 1;
        r0 = r28;
        r12 = r0.getByte(r15);
        r21 = 10;
        r0 = r21;
        if (r12 != r0) goto L_0x02cc;
    L_0x02bd:
        r21 = io.netty.handler.codec.http.HttpObjectDecoder.State.READ_CHUNK_SIZE;
        r0 = r21;
        r1 = r26;
        r1.currentState = r0;
    L_0x02c5:
        r0 = r28;
        r0.readerIndex(r14);
        goto L_0x001c;
    L_0x02cc:
        r15 = r14;
        goto L_0x02ab;
    L_0x02ce:
        r0 = r26;
        r1 = r28;
        r19 = r0.readTrailingHeaders(r1);	 Catch:{ Exception -> 0x02e4 }
        if (r19 == 0) goto L_0x001c;
    L_0x02d8:
        r0 = r29;
        r1 = r19;
        r0.add(r1);	 Catch:{ Exception -> 0x02e4 }
        r26.resetNow();	 Catch:{ Exception -> 0x02e4 }
        goto L_0x001c;
    L_0x02e4:
        r7 = move-exception;
        r0 = r26;
        r1 = r28;
        r21 = r0.invalidChunk(r1, r7);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        goto L_0x001c;
    L_0x02f6:
        r21 = r28.readableBytes();
        r0 = r28;
        r1 = r21;
        r0.skipBytes(r1);
        goto L_0x001c;
    L_0x0303:
        r17 = r28.readableBytes();
        if (r17 <= 0) goto L_0x001c;
    L_0x0309:
        r0 = r28;
        r1 = r17;
        r21 = r0.readBytes(r1);
        r0 = r29;
        r1 = r21;
        r0.add(r1);
        goto L_0x001c;
    L_0x031a:
        r14 = r15;
        goto L_0x02c5;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.HttpObjectDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        super.decodeLast(ctx, in, out);
        if (this.resetRequested) {
            resetNow();
        }
        if (this.message != null) {
            boolean chunked = HttpUtil.isTransferEncodingChunked(this.message);
            if (this.currentState == State.READ_VARIABLE_LENGTH_CONTENT && !in.isReadable() && !chunked) {
                out.add(LastHttpContent.EMPTY_LAST_CONTENT);
                resetNow();
            } else if (this.currentState == State.READ_HEADER) {
                out.add(invalidMessage(Unpooled.EMPTY_BUFFER, new PrematureChannelClosureException("Connection closed before received headers")));
                resetNow();
            } else {
                boolean prematureClosure;
                if (isDecodingRequest() || chunked) {
                    prematureClosure = true;
                } else {
                    prematureClosure = contentLength() > 0;
                }
                if (!prematureClosure) {
                    out.add(LastHttpContent.EMPTY_LAST_CONTENT);
                }
                resetNow();
            }
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof HttpExpectationFailedEvent) {
            switch (this.currentState) {
                case READ_CHUNK_SIZE:
                case READ_VARIABLE_LENGTH_CONTENT:
                case READ_FIXED_LENGTH_CONTENT:
                    reset();
                    break;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    protected boolean isContentAlwaysEmpty(HttpMessage msg) {
        if (!(msg instanceof HttpResponse)) {
            return false;
        }
        HttpResponse res = (HttpResponse) msg;
        int code = res.status().code();
        if (code < 100 || code >= 200) {
            switch (code) {
                case 204:
                case 205:
                case 304:
                    return true;
                default:
                    return false;
            }
        } else if (code == 101 && !res.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT) && res.headers().contains(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET, true)) {
            return false;
        } else {
            return true;
        }
    }

    public void reset() {
        this.resetRequested = true;
    }

    private void resetNow() {
        HttpMessage message = this.message;
        this.message = null;
        this.name = null;
        this.value = null;
        this.contentLength = Long.MIN_VALUE;
        this.lineParser.reset();
        this.headerParser.reset();
        this.trailer = null;
        if (!isDecodingRequest()) {
            HttpResponse res = (HttpResponse) message;
            if (res != null && res.status().code() == 101) {
                this.currentState = State.UPGRADED;
                return;
            }
        }
        this.currentState = State.SKIP_CONTROL_CHARS;
    }

    private HttpMessage invalidMessage(ByteBuf in, Exception cause) {
        this.currentState = State.BAD_MESSAGE;
        in.skipBytes(in.readableBytes());
        if (this.message != null) {
            this.message.setDecoderResult(DecoderResult.failure(cause));
        } else {
            this.message = createInvalidMessage();
            this.message.setDecoderResult(DecoderResult.failure(cause));
        }
        HttpMessage ret = this.message;
        this.message = null;
        return ret;
    }

    private HttpContent invalidChunk(ByteBuf in, Exception cause) {
        this.currentState = State.BAD_MESSAGE;
        in.skipBytes(in.readableBytes());
        HttpContent chunk = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        chunk.setDecoderResult(DecoderResult.failure(cause));
        this.message = null;
        this.trailer = null;
        return chunk;
    }

    private static boolean skipControlCharacters(ByteBuf buffer) {
        int rIdx;
        boolean skiped = false;
        int wIdx = buffer.writerIndex();
        int rIdx2 = buffer.readerIndex();
        while (wIdx > rIdx2) {
            rIdx = rIdx2 + 1;
            int c = buffer.getUnsignedByte(rIdx2);
            if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
                rIdx--;
                skiped = true;
                break;
            }
            rIdx2 = rIdx;
        }
        rIdx = rIdx2;
        buffer.readerIndex(rIdx);
        return skiped;
    }

    private State readHeaders(ByteBuf buffer) {
        HttpMessage message = this.message;
        HttpHeaders headers = message.headers();
        AppendableCharSequence line = this.headerParser.parse(buffer);
        if (line == null) {
            return null;
        }
        if (line.length() > 0) {
            do {
                char firstChar = line.charAt(0);
                if (this.name == null || !(firstChar == HttpConstants.SP_CHAR || firstChar == '\t')) {
                    if (this.name != null) {
                        headers.add(this.name, this.value);
                    }
                    splitHeader(line);
                } else {
                    StringBuilder buf = new StringBuilder((this.value.length() + line.length()) + 1);
                    buf.append(this.value).append(HttpConstants.SP_CHAR).append(line.toString().trim());
                    this.value = buf.toString();
                }
                line = this.headerParser.parse(buffer);
                if (line == null) {
                    return null;
                }
            } while (line.length() > 0);
        }
        if (this.name != null) {
            headers.add(this.name, this.value);
        }
        this.name = null;
        this.value = null;
        if (isContentAlwaysEmpty(message)) {
            HttpUtil.setTransferEncodingChunked(message, false);
            return State.SKIP_CONTROL_CHARS;
        } else if (HttpUtil.isTransferEncodingChunked(message)) {
            return State.READ_CHUNK_SIZE;
        } else {
            if (contentLength() >= 0) {
                return State.READ_FIXED_LENGTH_CONTENT;
            }
            return State.READ_VARIABLE_LENGTH_CONTENT;
        }
    }

    private long contentLength() {
        if (this.contentLength == Long.MIN_VALUE) {
            this.contentLength = HttpUtil.getContentLength(this.message, -1);
        }
        return this.contentLength;
    }

    private LastHttpContent readTrailingHeaders(ByteBuf buffer) {
        AppendableCharSequence line = this.headerParser.parse(buffer);
        if (line == null) {
            return null;
        }
        CharSequence lastHeader = null;
        if (line.length() <= 0) {
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        LastHttpContent trailer = this.trailer;
        if (trailer == null) {
            trailer = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
            this.trailer = trailer;
        }
        do {
            char firstChar = line.charAt(0);
            if (lastHeader == null || !(firstChar == HttpConstants.SP_CHAR || firstChar == '\t')) {
                splitHeader(line);
                CharSequence headerName = this.name;
                if (!(HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(headerName) || HttpHeaderNames.TRANSFER_ENCODING.contentEqualsIgnoreCase(headerName) || HttpHeaderNames.TRAILER.contentEqualsIgnoreCase(headerName))) {
                    trailer.trailingHeaders().add(headerName, this.value);
                }
                lastHeader = this.name;
                this.name = null;
                this.value = null;
            } else {
                List<String> current = trailer.trailingHeaders().getAll(lastHeader);
                if (!current.isEmpty()) {
                    int lastPos = current.size() - 1;
                    String lineTrimmed = line.toString().trim();
                    CharSequence currentLastPos = (CharSequence) current.get(lastPos);
                    StringBuilder b = new StringBuilder(currentLastPos.length() + lineTrimmed.length());
                    b.append(currentLastPos).append(lineTrimmed);
                    current.set(lastPos, b.toString());
                }
            }
            line = this.headerParser.parse(buffer);
            if (line == null) {
                return null;
            }
        } while (line.length() > 0);
        this.trailer = null;
        return trailer;
    }

    private static int getChunkSize(String hex) {
        hex = hex.trim();
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            if (c == ';' || Character.isWhitespace(c) || Character.isISOControl(c)) {
                hex = hex.substring(0, i);
                break;
            }
        }
        return Integer.parseInt(hex, 16);
    }

    private static String[] splitInitialLine(AppendableCharSequence sb) {
        int aStart = findNonWhitespace(sb, 0);
        int aEnd = findWhitespace(sb, aStart);
        int bStart = findNonWhitespace(sb, aEnd);
        int bEnd = findWhitespace(sb, bStart);
        int cStart = findNonWhitespace(sb, bEnd);
        int cEnd = findEndOfString(sb);
        String[] strArr = new String[3];
        strArr[0] = sb.subStringUnsafe(aStart, aEnd);
        strArr[1] = sb.subStringUnsafe(bStart, bEnd);
        strArr[2] = cStart < cEnd ? sb.subStringUnsafe(cStart, cEnd) : "";
        return strArr;
    }

    private void splitHeader(AppendableCharSequence sb) {
        int length = sb.length();
        int nameStart = findNonWhitespace(sb, 0);
        int nameEnd = nameStart;
        while (nameEnd < length) {
            char ch = sb.charAt(nameEnd);
            if (ch == ':' || Character.isWhitespace(ch)) {
                break;
            }
            nameEnd++;
        }
        int colonEnd = nameEnd;
        while (colonEnd < length) {
            if (sb.charAt(colonEnd) == ':') {
                colonEnd++;
                break;
            }
            colonEnd++;
        }
        this.name = sb.subStringUnsafe(nameStart, nameEnd);
        int valueStart = findNonWhitespace(sb, colonEnd);
        if (valueStart == length) {
            this.value = "";
        } else {
            this.value = sb.subStringUnsafe(valueStart, findEndOfString(sb));
        }
    }

    private static int findNonWhitespace(AppendableCharSequence sb, int offset) {
        for (int result = offset; result < sb.length(); result++) {
            if (!Character.isWhitespace(sb.charAtUnsafe(result))) {
                return result;
            }
        }
        return sb.length();
    }

    private static int findWhitespace(AppendableCharSequence sb, int offset) {
        for (int result = offset; result < sb.length(); result++) {
            if (Character.isWhitespace(sb.charAtUnsafe(result))) {
                return result;
            }
        }
        return sb.length();
    }

    private static int findEndOfString(AppendableCharSequence sb) {
        for (int result = sb.length() - 1; result > 0; result--) {
            if (!Character.isWhitespace(sb.charAtUnsafe(result))) {
                return result + 1;
            }
        }
        return 0;
    }
}
