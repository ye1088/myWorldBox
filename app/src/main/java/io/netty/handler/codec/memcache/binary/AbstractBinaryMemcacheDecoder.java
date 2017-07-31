package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectDecoder;
import io.netty.handler.codec.memcache.DefaultLastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;

public abstract class AbstractBinaryMemcacheDecoder<M extends BinaryMemcacheMessage> extends AbstractMemcacheObjectDecoder {
    public static final int DEFAULT_MAX_CHUNK_SIZE = 8192;
    private int alreadyReadChunkSize;
    private final int chunkSize;
    private M currentMessage;
    private State state;

    enum State {
        READ_HEADER,
        READ_EXTRAS,
        READ_KEY,
        READ_CONTENT,
        BAD_MESSAGE
    }

    protected abstract M buildInvalidMessage();

    protected abstract M decodeHeader(ByteBuf byteBuf);

    protected AbstractBinaryMemcacheDecoder() {
        this(8192);
    }

    protected AbstractBinaryMemcacheDecoder(int chunkSize) {
        this.state = State.READ_HEADER;
        if (chunkSize < 0) {
            throw new IllegalArgumentException("chunkSize must be a positive integer: " + chunkSize);
        }
        this.chunkSize = chunkSize;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void decode(io.netty.channel.ChannelHandlerContext r12, io.netty.buffer.ByteBuf r13, java.util.List<java.lang.Object> r14) throws java.lang.Exception {
        /*
        r11 = this;
        r8 = io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$memcache$binary$AbstractBinaryMemcacheDecoder$State;
        r9 = r11.state;
        r9 = r9.ordinal();
        r8 = r8[r9];
        switch(r8) {
            case 1: goto L_0x0029;
            case 2: goto L_0x003f;
            case 3: goto L_0x005a;
            case 4: goto L_0x007e;
            case 5: goto L_0x0108;
            default: goto L_0x000d;
        };
    L_0x000d:
        r8 = new java.lang.Error;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "Unknown state reached: ";
        r9 = r9.append(r10);
        r10 = r11.state;
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.<init>(r9);
        throw r8;
    L_0x0029:
        r8 = r13.readableBytes();	 Catch:{ Exception -> 0x00d5 }
        r9 = 24;
        if (r8 >= r9) goto L_0x0032;
    L_0x0031:
        return;
    L_0x0032:
        r11.resetDecoder();	 Catch:{ Exception -> 0x00d5 }
        r8 = r11.decodeHeader(r13);	 Catch:{ Exception -> 0x00d5 }
        r11.currentMessage = r8;	 Catch:{ Exception -> 0x00d5 }
        r8 = io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder.State.READ_EXTRAS;	 Catch:{ Exception -> 0x00d5 }
        r11.state = r8;	 Catch:{ Exception -> 0x00d5 }
    L_0x003f:
        r8 = r11.currentMessage;	 Catch:{ Exception -> 0x00e2 }
        r3 = r8.extrasLength();	 Catch:{ Exception -> 0x00e2 }
        if (r3 <= 0) goto L_0x0056;
    L_0x0047:
        r8 = r13.readableBytes();	 Catch:{ Exception -> 0x00e2 }
        if (r8 < r3) goto L_0x0031;
    L_0x004d:
        r8 = r11.currentMessage;	 Catch:{ Exception -> 0x00e2 }
        r9 = r13.readRetainedSlice(r3);	 Catch:{ Exception -> 0x00e2 }
        r8.setExtras(r9);	 Catch:{ Exception -> 0x00e2 }
    L_0x0056:
        r8 = io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder.State.READ_KEY;	 Catch:{ Exception -> 0x00e2 }
        r11.state = r8;	 Catch:{ Exception -> 0x00e2 }
    L_0x005a:
        r8 = r11.currentMessage;	 Catch:{ Exception -> 0x00ef }
        r4 = r8.keyLength();	 Catch:{ Exception -> 0x00ef }
        if (r4 <= 0) goto L_0x0071;
    L_0x0062:
        r8 = r13.readableBytes();	 Catch:{ Exception -> 0x00ef }
        if (r8 < r4) goto L_0x0031;
    L_0x0068:
        r8 = r11.currentMessage;	 Catch:{ Exception -> 0x00ef }
        r9 = r13.readRetainedSlice(r4);	 Catch:{ Exception -> 0x00ef }
        r8.setKey(r9);	 Catch:{ Exception -> 0x00ef }
    L_0x0071:
        r8 = r11.currentMessage;	 Catch:{ Exception -> 0x00ef }
        r8 = r8.retain();	 Catch:{ Exception -> 0x00ef }
        r14.add(r8);	 Catch:{ Exception -> 0x00ef }
        r8 = io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder.State.READ_CONTENT;	 Catch:{ Exception -> 0x00ef }
        r11.state = r8;	 Catch:{ Exception -> 0x00ef }
    L_0x007e:
        r8 = r11.currentMessage;	 Catch:{ Exception -> 0x00c8 }
        r8 = r8.totalBodyLength();	 Catch:{ Exception -> 0x00c8 }
        r9 = r11.currentMessage;	 Catch:{ Exception -> 0x00c8 }
        r9 = r9.keyLength();	 Catch:{ Exception -> 0x00c8 }
        r8 = r8 - r9;
        r9 = r11.currentMessage;	 Catch:{ Exception -> 0x00c8 }
        r9 = r9.extrasLength();	 Catch:{ Exception -> 0x00c8 }
        r7 = r8 - r9;
        r6 = r13.readableBytes();	 Catch:{ Exception -> 0x00c8 }
        if (r7 <= 0) goto L_0x0102;
    L_0x0099:
        if (r6 == 0) goto L_0x0031;
    L_0x009b:
        r8 = r11.chunkSize;	 Catch:{ Exception -> 0x00c8 }
        if (r6 <= r8) goto L_0x00a1;
    L_0x009f:
        r6 = r11.chunkSize;	 Catch:{ Exception -> 0x00c8 }
    L_0x00a1:
        r8 = r11.alreadyReadChunkSize;	 Catch:{ Exception -> 0x00c8 }
        r5 = r7 - r8;
        if (r6 <= r5) goto L_0x00a8;
    L_0x00a7:
        r6 = r5;
    L_0x00a8:
        r1 = r13.readRetainedSlice(r6);	 Catch:{ Exception -> 0x00c8 }
        r8 = r11.alreadyReadChunkSize;	 Catch:{ Exception -> 0x00c8 }
        r8 = r8 + r6;
        r11.alreadyReadChunkSize = r8;	 Catch:{ Exception -> 0x00c8 }
        if (r8 < r7) goto L_0x00fc;
    L_0x00b3:
        r0 = new io.netty.handler.codec.memcache.DefaultLastMemcacheContent;	 Catch:{ Exception -> 0x00c8 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x00c8 }
    L_0x00b8:
        r14.add(r0);	 Catch:{ Exception -> 0x00c8 }
        r8 = r11.alreadyReadChunkSize;	 Catch:{ Exception -> 0x00c8 }
        if (r8 < r7) goto L_0x0031;
    L_0x00bf:
        r11.resetDecoder();	 Catch:{ Exception -> 0x00c8 }
        r8 = io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder.State.READ_HEADER;	 Catch:{ Exception -> 0x00c8 }
        r11.state = r8;	 Catch:{ Exception -> 0x00c8 }
        goto L_0x0031;
    L_0x00c8:
        r2 = move-exception;
        r11.resetDecoder();
        r8 = r11.invalidChunk(r2);
        r14.add(r8);
        goto L_0x0031;
    L_0x00d5:
        r2 = move-exception;
        r11.resetDecoder();
        r8 = r11.invalidMessage(r2);
        r14.add(r8);
        goto L_0x0031;
    L_0x00e2:
        r2 = move-exception;
        r11.resetDecoder();
        r8 = r11.invalidMessage(r2);
        r14.add(r8);
        goto L_0x0031;
    L_0x00ef:
        r2 = move-exception;
        r11.resetDecoder();
        r8 = r11.invalidMessage(r2);
        r14.add(r8);
        goto L_0x0031;
    L_0x00fc:
        r0 = new io.netty.handler.codec.memcache.DefaultMemcacheContent;	 Catch:{ Exception -> 0x00c8 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x00c8 }
        goto L_0x00b8;
    L_0x0102:
        r8 = io.netty.handler.codec.memcache.LastMemcacheContent.EMPTY_LAST_CONTENT;	 Catch:{ Exception -> 0x00c8 }
        r14.add(r8);	 Catch:{ Exception -> 0x00c8 }
        goto L_0x00bf;
    L_0x0108:
        r8 = r11.actualReadableBytes();
        r13.skipBytes(r8);
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    private M invalidMessage(Exception cause) {
        this.state = State.BAD_MESSAGE;
        M message = buildInvalidMessage();
        message.setDecoderResult(DecoderResult.failure(cause));
        return message;
    }

    private MemcacheContent invalidChunk(Exception cause) {
        this.state = State.BAD_MESSAGE;
        MemcacheContent chunk = new DefaultLastMemcacheContent(Unpooled.EMPTY_BUFFER);
        chunk.setDecoderResult(DecoderResult.failure(cause));
        return chunk;
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        resetDecoder();
    }

    protected void resetDecoder() {
        if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
        }
        this.alreadyReadChunkSize = 0;
    }
}
