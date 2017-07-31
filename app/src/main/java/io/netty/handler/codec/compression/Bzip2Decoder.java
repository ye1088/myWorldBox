package io.netty.handler.codec.compression;

import io.netty.handler.codec.ByteToMessageDecoder;

public class Bzip2Decoder extends ByteToMessageDecoder {
    private int blockCRC;
    private Bzip2BlockDecompressor blockDecompressor;
    private int blockSize;
    private State currentState = State.INIT;
    private Bzip2HuffmanStageDecoder huffmanStageDecoder;
    private final Bzip2BitReader reader = new Bzip2BitReader();
    private int streamCRC;

    private enum State {
        INIT,
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        RECEIVE_HUFFMAN_USED_MAP,
        RECEIVE_HUFFMAN_USED_BITMAPS,
        RECEIVE_SELECTORS_NUMBER,
        RECEIVE_SELECTORS,
        RECEIVE_HUFFMAN_LENGTH,
        DECODE_HUFFMAN_DATA,
        EOF
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void decode(io.netty.channel.ChannelHandlerContext r44, io.netty.buffer.ByteBuf r45, java.util.List<java.lang.Object> r46) throws java.lang.Exception {
        /*
        r43 = this;
        r2 = r45.isReadable();
        if (r2 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = r43;
        r7 = r0.reader;
        r0 = r45;
        r7.setByteBuf(r0);
    L_0x0010:
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$Bzip2Decoder$State;
        r0 = r43;
        r3 = r0.currentState;
        r3 = r3.ordinal();
        r2 = r2[r3];
        switch(r2) {
            case 1: goto L_0x0025;
            case 2: goto L_0x0069;
            case 3: goto L_0x00cd;
            case 4: goto L_0x00f6;
            case 5: goto L_0x0110;
            case 6: goto L_0x01a0;
            case 7: goto L_0x01d2;
            case 8: goto L_0x0222;
            case 9: goto L_0x02cc;
            case 10: goto L_0x033f;
            default: goto L_0x001f;
        };
    L_0x001f:
        r2 = new java.lang.IllegalStateException;
        r2.<init>();
        throw r2;
    L_0x0025:
        r2 = r45.readableBytes();
        r3 = 4;
        if (r2 < r3) goto L_0x0006;
    L_0x002c:
        r31 = r45.readUnsignedMedium();
        r2 = 4348520; // 0x425a68 float:6.093574E-39 double:2.1484543E-317;
        r0 = r31;
        if (r0 == r2) goto L_0x0040;
    L_0x0037:
        r2 = new io.netty.handler.codec.compression.DecompressionException;
        r3 = "Unexpected stream identifier contents. Mismatched bzip2 protocol version?";
        r2.<init>(r3);
        throw r2;
    L_0x0040:
        r2 = r45.readByte();
        r12 = r2 + -48;
        r2 = 1;
        if (r12 < r2) goto L_0x004d;
    L_0x0049:
        r2 = 9;
        if (r12 <= r2) goto L_0x0056;
    L_0x004d:
        r2 = new io.netty.handler.codec.compression.DecompressionException;
        r3 = "block size is invalid";
        r2.<init>(r3);
        throw r2;
    L_0x0056:
        r2 = 100000; // 0x186a0 float:1.4013E-40 double:4.94066E-319;
        r2 = r2 * r12;
        r0 = r43;
        r0.blockSize = r2;
        r2 = 0;
        r0 = r43;
        r0.streamCRC = r2;
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK;
        r0 = r43;
        r0.currentState = r2;
    L_0x0069:
        r2 = 10;
        r2 = r7.hasReadableBytes(r2);
        if (r2 == 0) goto L_0x0006;
    L_0x0071:
        r2 = 24;
        r29 = r7.readBits(r2);
        r2 = 24;
        r30 = r7.readBits(r2);
        r2 = 1536581; // 0x177245 float:2.153209E-39 double:7.59172E-318;
        r0 = r29;
        if (r0 != r2) goto L_0x00a8;
    L_0x0084:
        r2 = 3690640; // 0x385090 float:5.171688E-39 double:1.8234184E-317;
        r0 = r30;
        if (r0 != r2) goto L_0x00a8;
    L_0x008b:
        r36 = r7.readInt();
        r0 = r43;
        r2 = r0.streamCRC;
        r0 = r36;
        if (r0 == r2) goto L_0x00a0;
    L_0x0097:
        r2 = new io.netty.handler.codec.compression.DecompressionException;
        r3 = "stream CRC error";
        r2.<init>(r3);
        throw r2;
    L_0x00a0:
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.EOF;
        r0 = r43;
        r0.currentState = r2;
        goto L_0x0010;
    L_0x00a8:
        r2 = 3227993; // 0x314159 float:4.523382E-39 double:1.5948404E-317;
        r0 = r29;
        if (r0 != r2) goto L_0x00b6;
    L_0x00af:
        r2 = 2511705; // 0x265359 float:3.519648E-39 double:1.240947E-317;
        r0 = r30;
        if (r0 == r2) goto L_0x00bf;
    L_0x00b6:
        r2 = new io.netty.handler.codec.compression.DecompressionException;
        r3 = "bad block header";
        r2.<init>(r3);
        throw r2;
    L_0x00bf:
        r2 = r7.readInt();
        r0 = r43;
        r0.blockCRC = r2;
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK_PARAMS;
        r0 = r43;
        r0.currentState = r2;
    L_0x00cd:
        r2 = 25;
        r2 = r7.hasReadableBits(r2);
        if (r2 == 0) goto L_0x0006;
    L_0x00d5:
        r5 = r7.readBoolean();
        r2 = 24;
        r6 = r7.readBits(r2);
        r2 = new io.netty.handler.codec.compression.Bzip2BlockDecompressor;
        r0 = r43;
        r3 = r0.blockSize;
        r0 = r43;
        r4 = r0.blockCRC;
        r2.<init>(r3, r4, r5, r6, r7);
        r0 = r43;
        r0.blockDecompressor = r2;
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_MAP;
        r0 = r43;
        r0.currentState = r2;
    L_0x00f6:
        r2 = 16;
        r2 = r7.hasReadableBits(r2);
        if (r2 == 0) goto L_0x0006;
    L_0x00fe:
        r0 = r43;
        r2 = r0.blockDecompressor;
        r3 = 16;
        r3 = r7.readBits(r3);
        r2.huffmanInUse16 = r3;
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_USED_BITMAPS;
        r0 = r43;
        r0.currentState = r2;
    L_0x0110:
        r0 = r43;
        r10 = r0.blockDecompressor;
        r0 = r10.huffmanInUse16;
        r25 = r0;
        r9 = java.lang.Integer.bitCount(r25);
        r0 = r10.huffmanSymbolMap;
        r23 = r0;
        r2 = r9 * 16;
        r2 = r2 + 3;
        r2 = r7.hasReadableBits(r2);
        if (r2 == 0) goto L_0x0006;
    L_0x012a:
        r21 = 0;
        if (r9 <= 0) goto L_0x0164;
    L_0x012e:
        r24 = 0;
    L_0x0130:
        r2 = 16;
        r0 = r24;
        if (r0 >= r2) goto L_0x0164;
    L_0x0136:
        r2 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r2 = r2 >>> r24;
        r2 = r2 & r25;
        if (r2 == 0) goto L_0x0161;
    L_0x013f:
        r27 = 0;
        r28 = r24 << 4;
        r22 = r21;
    L_0x0145:
        r2 = 16;
        r0 = r27;
        if (r0 >= r2) goto L_0x015f;
    L_0x014b:
        r2 = r7.readBoolean();
        if (r2 == 0) goto L_0x034a;
    L_0x0151:
        r21 = r22 + 1;
        r0 = r28;
        r2 = (byte) r0;
        r23[r22] = r2;
    L_0x0158:
        r27 = r27 + 1;
        r28 = r28 + 1;
        r22 = r21;
        goto L_0x0145;
    L_0x015f:
        r21 = r22;
    L_0x0161:
        r24 = r24 + 1;
        goto L_0x0130;
    L_0x0164:
        r2 = r21 + 1;
        r10.huffmanEndOfBlockSymbol = r2;
        r2 = 3;
        r40 = r7.readBits(r2);
        r2 = 2;
        r0 = r40;
        if (r0 < r2) goto L_0x0177;
    L_0x0172:
        r2 = 6;
        r0 = r40;
        if (r0 <= r2) goto L_0x0180;
    L_0x0177:
        r2 = new io.netty.handler.codec.compression.DecompressionException;
        r3 = "incorrect huffman groups number";
        r2.<init>(r3);
        throw r2;
    L_0x0180:
        r8 = r21 + 2;
        r2 = 258; // 0x102 float:3.62E-43 double:1.275E-321;
        if (r8 <= r2) goto L_0x018f;
    L_0x0186:
        r2 = new io.netty.handler.codec.compression.DecompressionException;
        r3 = "incorrect alphabet size";
        r2.<init>(r3);
        throw r2;
    L_0x018f:
        r2 = new io.netty.handler.codec.compression.Bzip2HuffmanStageDecoder;
        r0 = r40;
        r2.<init>(r7, r0, r8);
        r0 = r43;
        r0.huffmanStageDecoder = r2;
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_SELECTORS_NUMBER;
        r0 = r43;
        r0.currentState = r2;
    L_0x01a0:
        r2 = 15;
        r2 = r7.hasReadableBits(r2);
        if (r2 == 0) goto L_0x0006;
    L_0x01a8:
        r2 = 15;
        r39 = r7.readBits(r2);
        r2 = 1;
        r0 = r39;
        if (r0 < r2) goto L_0x01b9;
    L_0x01b3:
        r2 = 18002; // 0x4652 float:2.5226E-41 double:8.894E-320;
        r0 = r39;
        if (r0 <= r2) goto L_0x01c2;
    L_0x01b9:
        r2 = new io.netty.handler.codec.compression.DecompressionException;
        r3 = "incorrect selectors number";
        r2.<init>(r3);
        throw r2;
    L_0x01c2:
        r0 = r43;
        r2 = r0.huffmanStageDecoder;
        r0 = r39;
        r3 = new byte[r0];
        r2.selectors = r3;
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_SELECTORS;
        r0 = r43;
        r0.currentState = r2;
    L_0x01d2:
        r0 = r43;
        r0 = r0.huffmanStageDecoder;
        r20 = r0;
        r0 = r20;
        r0 = r0.selectors;
        r35 = r0;
        r0 = r35;
        r0 = r0.length;
        r39 = r0;
        r0 = r20;
        r0 = r0.tableMTF;
        r38 = r0;
        r0 = r20;
        r0 = r0.currentSelector;
        r17 = r0;
    L_0x01ef:
        r0 = r17;
        r1 = r39;
        if (r0 >= r1) goto L_0x021c;
    L_0x01f5:
        r2 = 6;
        r2 = r7.hasReadableBits(r2);
        if (r2 != 0) goto L_0x0204;
    L_0x01fc:
        r0 = r17;
        r1 = r20;
        r1.currentSelector = r0;
        goto L_0x0006;
    L_0x0204:
        r26 = 0;
    L_0x0206:
        r2 = r7.readBoolean();
        if (r2 == 0) goto L_0x020f;
    L_0x020c:
        r26 = r26 + 1;
        goto L_0x0206;
    L_0x020f:
        r0 = r38;
        r1 = r26;
        r2 = r0.indexToFront(r1);
        r35[r17] = r2;
        r17 = r17 + 1;
        goto L_0x01ef;
    L_0x021c:
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.RECEIVE_HUFFMAN_LENGTH;
        r0 = r43;
        r0.currentState = r2;
    L_0x0222:
        r0 = r43;
        r0 = r0.huffmanStageDecoder;
        r20 = r0;
        r0 = r20;
        r0 = r0.totalTables;
        r40 = r0;
        r0 = r20;
        r13 = r0.tableCodeLengths;
        r0 = r20;
        r8 = r0.alphabetSize;
        r0 = r20;
        r0 = r0.currentLength;
        r16 = r0;
        r14 = 0;
        r0 = r20;
        r0 = r0.modifyLength;
        r32 = r0;
        r34 = 0;
        r0 = r20;
        r15 = r0.currentGroup;
    L_0x0249:
        r0 = r40;
        if (r15 >= r0) goto L_0x0256;
    L_0x024d:
        r2 = 5;
        r2 = r7.hasReadableBits(r2);
        if (r2 != 0) goto L_0x026e;
    L_0x0254:
        r34 = 1;
    L_0x0256:
        if (r34 == 0) goto L_0x02c3;
    L_0x0258:
        r0 = r20;
        r0.currentGroup = r15;
        r0 = r16;
        r1 = r20;
        r1.currentLength = r0;
        r0 = r20;
        r0.currentAlpha = r14;
        r0 = r32;
        r1 = r20;
        r1.modifyLength = r0;
        goto L_0x0006;
    L_0x026e:
        if (r16 >= 0) goto L_0x0275;
    L_0x0270:
        r2 = 5;
        r16 = r7.readBits(r2);
    L_0x0275:
        r0 = r20;
        r14 = r0.currentAlpha;
    L_0x0279:
        if (r14 >= r8) goto L_0x02b7;
    L_0x027b:
        r2 = r7.isReadable();
        if (r2 != 0) goto L_0x0284;
    L_0x0281:
        r34 = 1;
        goto L_0x0256;
    L_0x0284:
        if (r32 != 0) goto L_0x028c;
    L_0x0286:
        r2 = r7.readBoolean();
        if (r2 == 0) goto L_0x02ad;
    L_0x028c:
        r2 = r7.isReadable();
        if (r2 != 0) goto L_0x0297;
    L_0x0292:
        r32 = 1;
        r34 = 1;
        goto L_0x0256;
    L_0x0297:
        r2 = r7.readBoolean();
        if (r2 == 0) goto L_0x02ab;
    L_0x029d:
        r2 = -1;
    L_0x029e:
        r16 = r16 + r2;
        r32 = 0;
        r2 = r7.isReadable();
        if (r2 != 0) goto L_0x0284;
    L_0x02a8:
        r34 = 1;
        goto L_0x0256;
    L_0x02ab:
        r2 = 1;
        goto L_0x029e;
    L_0x02ad:
        r2 = r13[r15];
        r0 = r16;
        r3 = (byte) r0;
        r2[r14] = r3;
        r14 = r14 + 1;
        goto L_0x0279;
    L_0x02b7:
        r16 = -1;
        r14 = 0;
        r0 = r20;
        r0.currentAlpha = r14;
        r32 = 0;
        r15 = r15 + 1;
        goto L_0x0249;
    L_0x02c3:
        r20.createHuffmanDecodingTables();
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.DECODE_HUFFMAN_DATA;
        r0 = r43;
        r0.currentState = r2;
    L_0x02cc:
        r0 = r43;
        r10 = r0.blockDecompressor;
        r33 = r45.readerIndex();
        r0 = r43;
        r2 = r0.huffmanStageDecoder;
        r19 = r10.decodeHuffmanData(r2);
        if (r19 == 0) goto L_0x0006;
    L_0x02de:
        r2 = r45.readerIndex();
        r0 = r33;
        if (r2 != r0) goto L_0x02ef;
    L_0x02e6:
        r2 = r45.isReadable();
        if (r2 == 0) goto L_0x02ef;
    L_0x02ec:
        r7.refill();
    L_0x02ef:
        r11 = r10.blockLength();
        r2 = r44.alloc();
        r42 = r2.buffer(r11);
        r37 = 0;
    L_0x02fd:
        r41 = r10.read();	 Catch:{ all -> 0x030b }
        if (r41 < 0) goto L_0x0312;
    L_0x0303:
        r0 = r42;
        r1 = r41;
        r0.writeByte(r1);	 Catch:{ all -> 0x030b }
        goto L_0x02fd;
    L_0x030b:
        r2 = move-exception;
        if (r37 != 0) goto L_0x0311;
    L_0x030e:
        r42.release();
    L_0x0311:
        throw r2;
    L_0x0312:
        r18 = r10.checkCRC();	 Catch:{ all -> 0x030b }
        r0 = r43;
        r2 = r0.streamCRC;	 Catch:{ all -> 0x030b }
        r2 = r2 << 1;
        r0 = r43;
        r3 = r0.streamCRC;	 Catch:{ all -> 0x030b }
        r3 = r3 >>> 31;
        r2 = r2 | r3;
        r2 = r2 ^ r18;
        r0 = r43;
        r0.streamCRC = r2;	 Catch:{ all -> 0x030b }
        r0 = r46;
        r1 = r42;
        r0.add(r1);	 Catch:{ all -> 0x030b }
        r37 = 1;
        if (r37 != 0) goto L_0x0337;
    L_0x0334:
        r42.release();
    L_0x0337:
        r2 = io.netty.handler.codec.compression.Bzip2Decoder.State.INIT_BLOCK;
        r0 = r43;
        r0.currentState = r2;
        goto L_0x0010;
    L_0x033f:
        r2 = r45.readableBytes();
        r0 = r45;
        r0.skipBytes(r2);
        goto L_0x0006;
    L_0x034a:
        r21 = r22;
        goto L_0x0158;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.Bzip2Decoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    public boolean isClosed() {
        return this.currentState == State.EOF;
    }
}
