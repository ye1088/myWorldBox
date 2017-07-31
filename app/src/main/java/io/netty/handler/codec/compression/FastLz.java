package io.netty.handler.codec.compression;

final class FastLz {
    static final byte BLOCK_TYPE_COMPRESSED = (byte) 1;
    static final byte BLOCK_TYPE_NON_COMPRESSED = (byte) 0;
    static final byte BLOCK_WITHOUT_CHECKSUM = (byte) 0;
    static final byte BLOCK_WITH_CHECKSUM = (byte) 16;
    static final int CHECKSUM_OFFSET = 4;
    private static final int HASH_LOG = 13;
    private static final int HASH_MASK = 8191;
    private static final int HASH_SIZE = 8192;
    static final int LEVEL_1 = 1;
    static final int LEVEL_2 = 2;
    static final int LEVEL_AUTO = 0;
    static final int MAGIC_NUMBER = 4607066;
    static final int MAX_CHUNK_LENGTH = 65535;
    private static final int MAX_COPY = 32;
    private static final int MAX_DISTANCE = 8191;
    private static final int MAX_FARDISTANCE = 73725;
    private static final int MAX_LEN = 264;
    static final int MIN_LENGTH_TO_COMPRESSION = 32;
    private static final int MIN_RECOMENDED_LENGTH_FOR_LEVEL_2 = 65536;
    static final int OPTIONS_OFFSET = 3;

    static int calculateOutputBufferLength(int inputLength) {
        return Math.max((int) (((double) inputLength) * 1.06d), 66);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int compress(byte[] r28, int r29, int r30, byte[] r31, int r32, int r33) {
        /*
        if (r33 != 0) goto L_0x004a;
    L_0x0002:
        r22 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r30;
        r1 = r22;
        if (r0 >= r1) goto L_0x0048;
    L_0x000a:
        r15 = 1;
    L_0x000b:
        r10 = 0;
        r22 = r10 + r30;
        r12 = r22 + -2;
        r22 = r10 + r30;
        r13 = r22 + -12;
        r17 = 0;
        r22 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = r22;
        r8 = new int[r0];
        r22 = 4;
        r0 = r30;
        r1 = r22;
        if (r0 >= r1) goto L_0x0058;
    L_0x0024:
        if (r30 == 0) goto L_0x0055;
    L_0x0026:
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = r30 + -1;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r12 = r12 + 1;
        r11 = r10;
    L_0x0036:
        if (r11 > r12) goto L_0x004d;
    L_0x0038:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r31[r22] = r23;
        r18 = r17;
        r11 = r10;
        goto L_0x0036;
    L_0x0048:
        r15 = 2;
        goto L_0x000b;
    L_0x004a:
        r15 = r33;
        goto L_0x000b;
    L_0x004d:
        r17 = r30 + 1;
        r10 = r11;
        r22 = r17;
        r17 = r18;
    L_0x0054:
        return r22;
    L_0x0055:
        r22 = 0;
        goto L_0x0054;
    L_0x0058:
        r5 = 0;
    L_0x0059:
        r22 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = r22;
        if (r5 >= r0) goto L_0x0064;
    L_0x005f:
        r8[r5] = r10;
        r5 = r5 + 1;
        goto L_0x0059;
    L_0x0064:
        r4 = 2;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = 31;
        r31[r22] = r23;
        r17 = r18 + 1;
        r22 = r32 + 1;
        r11 = r10 + 1;
        r23 = r29 + r10;
        r23 = r28[r23];
        r31[r22] = r23;
        r17 = r17 + 1;
        r22 = r32 + 2;
        r10 = r11 + 1;
        r23 = r29 + 1;
        r23 = r28[r23];
        r31[r22] = r23;
    L_0x0085:
        r18 = r17;
    L_0x0087:
        if (r10 >= r13) goto L_0x04d7;
    L_0x0089:
        r19 = 0;
        r6 = 0;
        r14 = 3;
        r2 = r10;
        r16 = 0;
        r22 = 2;
        r0 = r22;
        if (r15 != r0) goto L_0x0542;
    L_0x0097:
        r22 = r29 + r10;
        r22 = r28[r22];
        r23 = r29 + r10;
        r23 = r23 + -1;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0542;
    L_0x00a7:
        r22 = r29 + r10;
        r22 = r22 + -1;
        r0 = r28;
        r1 = r22;
        r22 = readU16(r0, r1);
        r23 = r29 + r10;
        r23 = r23 + 1;
        r0 = r28;
        r1 = r23;
        r23 = readU16(r0, r1);
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0542;
    L_0x00c5:
        r6 = 1;
        r10 = r10 + 3;
        r22 = r2 + -1;
        r19 = r22 + 3;
        r16 = 1;
        r11 = r10;
    L_0x00d0:
        if (r16 != 0) goto L_0x053d;
    L_0x00d2:
        r22 = r29 + r11;
        r0 = r28;
        r1 = r22;
        r9 = hashFunction(r0, r1);
        r5 = r9;
        r19 = r8[r9];
        r22 = r2 - r19;
        r0 = r22;
        r6 = (long) r0;
        r8[r5] = r2;
        r22 = 0;
        r22 = (r6 > r22 ? 1 : (r6 == r22 ? 0 : -1));
        if (r22 == 0) goto L_0x053a;
    L_0x00ec:
        r22 = 1;
        r0 = r22;
        if (r15 != r0) goto L_0x0119;
    L_0x00f2:
        r22 = 8191; // 0x1fff float:1.1478E-41 double:4.047E-320;
        r22 = (r6 > r22 ? 1 : (r6 == r22 ? 0 : -1));
        if (r22 < 0) goto L_0x0120;
    L_0x00f8:
        r10 = r11;
    L_0x00f9:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r3 = r2 + 1;
        r23 = r29 + r2;
        r23 = r28[r23];
        r31[r22] = r23;
        r10 = r3;
        r4 = r4 + 1;
        r22 = 32;
        r0 = r22;
        if (r4 != r0) goto L_0x0085;
    L_0x010e:
        r4 = 0;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = 31;
        r31[r22] = r23;
        goto L_0x0087;
    L_0x0119:
        r22 = 73725; // 0x11ffd float:1.03311E-40 double:3.6425E-319;
        r22 = (r6 > r22 ? 1 : (r6 == r22 ? 0 : -1));
        if (r22 >= 0) goto L_0x053a;
    L_0x0120:
        r20 = r19 + 1;
        r22 = r29 + r19;
        r22 = r28[r22];
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0156;
    L_0x0132:
        r19 = r20 + 1;
        r22 = r29 + r20;
        r22 = r28[r22];
        r11 = r10 + 1;
        r23 = r29 + r10;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x053a;
    L_0x0144:
        r20 = r19 + 1;
        r22 = r29 + r19;
        r22 = r28[r22];
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x0159;
    L_0x0156:
        r19 = r20;
        goto L_0x00f9;
    L_0x0159:
        r22 = 2;
        r0 = r22;
        if (r15 != r0) goto L_0x01ad;
    L_0x015f:
        r22 = 8191; // 0x1fff float:1.1478E-41 double:4.047E-320;
        r22 = (r6 > r22 ? 1 : (r6 == r22 ? 0 : -1));
        if (r22 < 0) goto L_0x01ad;
    L_0x0165:
        r11 = r10 + 1;
        r22 = r29 + r10;
        r22 = r28[r22];
        r19 = r20 + 1;
        r23 = r29 + r20;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x0537;
    L_0x0177:
        r10 = r11 + 1;
        r22 = r29 + r11;
        r22 = r28[r22];
        r20 = r19 + 1;
        r23 = r29 + r19;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x01ab;
    L_0x0189:
        r19 = r20;
    L_0x018b:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r3 = r2 + 1;
        r23 = r29 + r2;
        r23 = r28[r23];
        r31[r22] = r23;
        r10 = r3;
        r4 = r4 + 1;
        r22 = 32;
        r0 = r22;
        if (r4 != r0) goto L_0x0085;
    L_0x01a0:
        r4 = 0;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = 31;
        r31[r22] = r23;
        goto L_0x0087;
    L_0x01ab:
        r14 = r14 + 2;
    L_0x01ad:
        r10 = r2 + r14;
        r22 = 1;
        r6 = r6 - r22;
        r22 = 0;
        r22 = (r6 > r22 ? 1 : (r6 == r22 ? 0 : -1));
        if (r22 != 0) goto L_0x0254;
    L_0x01b9:
        r22 = r29 + r10;
        r22 = r22 + -1;
        r21 = r28[r22];
    L_0x01bf:
        if (r10 >= r12) goto L_0x0533;
    L_0x01c1:
        r19 = r20 + 1;
        r22 = r29 + r20;
        r22 = r28[r22];
        r0 = r22;
        r1 = r21;
        if (r0 == r1) goto L_0x024e;
    L_0x01cd:
        if (r4 == 0) goto L_0x0319;
    L_0x01cf:
        r22 = r32 + r18;
        r22 = r22 - r4;
        r22 = r22 + -1;
        r23 = r4 + -1;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
    L_0x01de:
        r4 = 0;
        r10 = r10 + -3;
        r14 = r10 - r2;
        r22 = 2;
        r0 = r22;
        if (r15 != r0) goto L_0x041c;
    L_0x01e9:
        r22 = 8191; // 0x1fff float:1.1478E-41 double:4.047E-320;
        r22 = (r6 > r22 ? 1 : (r6 == r22 ? 0 : -1));
        if (r22 >= 0) goto L_0x036f;
    L_0x01ef:
        r22 = 7;
        r0 = r22;
        if (r14 >= r0) goto L_0x031f;
    L_0x01f5:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = r14 << 5;
        r0 = r23;
        r0 = (long) r0;
        r24 = r0;
        r23 = 8;
        r26 = r6 >>> r23;
        r24 = r24 + r26;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r24 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r24 = r24 & r6;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r17 = r18;
    L_0x0228:
        r22 = r29 + r10;
        r0 = r28;
        r1 = r22;
        r9 = hashFunction(r0, r1);
        r11 = r10 + 1;
        r8[r9] = r10;
        r22 = r29 + r11;
        r0 = r28;
        r1 = r22;
        r9 = hashFunction(r0, r1);
        r10 = r11 + 1;
        r8[r9] = r11;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = 31;
        r31[r22] = r23;
        goto L_0x0087;
    L_0x024e:
        r10 = r10 + 1;
        r20 = r19;
        goto L_0x01bf;
    L_0x0254:
        r19 = r20 + 1;
        r22 = r29 + r20;
        r22 = r28[r22];
        r11 = r10 + 1;
        r23 = r29 + r10;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x0269;
    L_0x0266:
        r10 = r11;
        goto L_0x01cd;
    L_0x0269:
        r20 = r19 + 1;
        r22 = r29 + r19;
        r22 = r28[r22];
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x027f;
    L_0x027b:
        r19 = r20;
        goto L_0x01cd;
    L_0x027f:
        r19 = r20 + 1;
        r22 = r29 + r20;
        r22 = r28[r22];
        r11 = r10 + 1;
        r23 = r29 + r10;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x0294;
    L_0x0291:
        r10 = r11;
        goto L_0x01cd;
    L_0x0294:
        r20 = r19 + 1;
        r22 = r29 + r19;
        r22 = r28[r22];
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x02aa;
    L_0x02a6:
        r19 = r20;
        goto L_0x01cd;
    L_0x02aa:
        r19 = r20 + 1;
        r22 = r29 + r20;
        r22 = r28[r22];
        r11 = r10 + 1;
        r23 = r29 + r10;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x02bf;
    L_0x02bc:
        r10 = r11;
        goto L_0x01cd;
    L_0x02bf:
        r20 = r19 + 1;
        r22 = r29 + r19;
        r22 = r28[r22];
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x02d5;
    L_0x02d1:
        r19 = r20;
        goto L_0x01cd;
    L_0x02d5:
        r19 = r20 + 1;
        r22 = r29 + r20;
        r22 = r28[r22];
        r11 = r10 + 1;
        r23 = r29 + r10;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x02ea;
    L_0x02e7:
        r10 = r11;
        goto L_0x01cd;
    L_0x02ea:
        r20 = r19 + 1;
        r22 = r29 + r19;
        r22 = r28[r22];
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x0530;
    L_0x02fc:
        r19 = r20;
        goto L_0x01cd;
    L_0x0300:
        r20 = r19;
        r11 = r10;
    L_0x0303:
        if (r11 >= r12) goto L_0x052b;
    L_0x0305:
        r19 = r20 + 1;
        r22 = r29 + r20;
        r22 = r28[r22];
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r0 = r22;
        r1 = r23;
        if (r0 == r1) goto L_0x0300;
    L_0x0317:
        goto L_0x01cd;
    L_0x0319:
        r17 = r18 + -1;
        r18 = r17;
        goto L_0x01de;
    L_0x031f:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r24 = 224; // 0xe0 float:3.14E-43 double:1.107E-321;
        r23 = 8;
        r26 = r6 >>> r23;
        r24 = r24 + r26;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r14 = r14 + -7;
        r18 = r17;
    L_0x033b:
        r22 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r0 = r22;
        if (r14 < r0) goto L_0x034e;
    L_0x0341:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = -1;
        r31[r22] = r23;
        r14 = r14 + -255;
        r18 = r17;
        goto L_0x033b;
    L_0x034e:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r0 = (byte) r14;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r24 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r24 = r24 & r6;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r17 = r18;
        goto L_0x0228;
    L_0x036f:
        r22 = 7;
        r0 = r22;
        if (r14 >= r0) goto L_0x03bc;
    L_0x0375:
        r22 = 8191; // 0x1fff float:1.1478E-41 double:4.047E-320;
        r6 = r6 - r22;
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = r14 << 5;
        r23 = r23 + 31;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = -1;
        r31[r22] = r23;
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = 8;
        r24 = r6 >>> r23;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r24 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r24 = r24 & r6;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r17 = r18;
        goto L_0x0228;
    L_0x03bc:
        r22 = 8191; // 0x1fff float:1.1478E-41 double:4.047E-320;
        r6 = r6 - r22;
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = -1;
        r31[r22] = r23;
        r14 = r14 + -7;
        r18 = r17;
    L_0x03cc:
        r22 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r0 = r22;
        if (r14 < r0) goto L_0x03df;
    L_0x03d2:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = -1;
        r31[r22] = r23;
        r14 = r14 + -255;
        r18 = r17;
        goto L_0x03cc;
    L_0x03df:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r0 = (byte) r14;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = -1;
        r31[r22] = r23;
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = 8;
        r24 = r6 >>> r23;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r24 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r24 = r24 & r6;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r17 = r18;
        goto L_0x0228;
    L_0x041c:
        r22 = 262; // 0x106 float:3.67E-43 double:1.294E-321;
        r0 = r22;
        if (r14 <= r0) goto L_0x0461;
    L_0x0422:
        r22 = 262; // 0x106 float:3.67E-43 double:1.294E-321;
        r0 = r22;
        if (r14 <= r0) goto L_0x0461;
    L_0x0428:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r24 = 224; // 0xe0 float:3.14E-43 double:1.107E-321;
        r23 = 8;
        r26 = r6 >>> r23;
        r24 = r24 + r26;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = -3;
        r31[r22] = r23;
        r17 = r18 + 1;
        r22 = r32 + r18;
        r24 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r24 = r24 & r6;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r14 = r14 + -262;
        r18 = r17;
        goto L_0x0422;
    L_0x0461:
        r22 = 7;
        r0 = r22;
        if (r14 >= r0) goto L_0x049c;
    L_0x0467:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r23 = r14 << 5;
        r0 = r23;
        r0 = (long) r0;
        r24 = r0;
        r23 = 8;
        r26 = r6 >>> r23;
        r24 = r24 + r26;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r24 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r24 = r24 & r6;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r17 = r18;
        goto L_0x0228;
    L_0x049c:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r24 = 224; // 0xe0 float:3.14E-43 double:1.107E-321;
        r23 = 8;
        r26 = r6 >>> r23;
        r24 = r24 + r26;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = r14 + -7;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r17 = r18 + 1;
        r22 = r32 + r18;
        r24 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r24 = r24 & r6;
        r0 = r24;
        r0 = (int) r0;
        r23 = r0;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        goto L_0x0228;
    L_0x04d7:
        r12 = r12 + 1;
        r11 = r10;
    L_0x04da:
        if (r11 > r12) goto L_0x04fb;
    L_0x04dc:
        r17 = r18 + 1;
        r22 = r32 + r18;
        r10 = r11 + 1;
        r23 = r29 + r11;
        r23 = r28[r23];
        r31[r22] = r23;
        r4 = r4 + 1;
        r22 = 32;
        r0 = r22;
        if (r4 != r0) goto L_0x0527;
    L_0x04f0:
        r4 = 0;
        r18 = r17 + 1;
        r22 = r32 + r17;
        r23 = 31;
        r31[r22] = r23;
        r11 = r10;
        goto L_0x04da;
    L_0x04fb:
        if (r4 == 0) goto L_0x0524;
    L_0x04fd:
        r22 = r32 + r18;
        r22 = r22 - r4;
        r22 = r22 + -1;
        r23 = r4 + -1;
        r0 = r23;
        r0 = (byte) r0;
        r23 = r0;
        r31[r22] = r23;
        r17 = r18;
    L_0x050e:
        r22 = 2;
        r0 = r22;
        if (r15 != r0) goto L_0x051f;
    L_0x0514:
        r22 = r31[r32];
        r22 = r22 | 32;
        r0 = r22;
        r0 = (byte) r0;
        r22 = r0;
        r31[r32] = r22;
    L_0x051f:
        r10 = r11;
        r22 = r17;
        goto L_0x0054;
    L_0x0524:
        r17 = r18 + -1;
        goto L_0x050e;
    L_0x0527:
        r18 = r17;
        r11 = r10;
        goto L_0x04da;
    L_0x052b:
        r19 = r20;
        r10 = r11;
        goto L_0x01cd;
    L_0x0530:
        r11 = r10;
        goto L_0x0303;
    L_0x0533:
        r19 = r20;
        goto L_0x01cd;
    L_0x0537:
        r10 = r11;
        goto L_0x018b;
    L_0x053a:
        r10 = r11;
        goto L_0x00f9;
    L_0x053d:
        r20 = r19;
        r10 = r11;
        goto L_0x01ad;
    L_0x0542:
        r11 = r10;
        goto L_0x00d0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.FastLz.compress(byte[], int, int, byte[], int, int):int");
    }

    static int decompress(byte[] input, int inOffset, int inLength, byte[] output, int outOffset, int outLength) {
        int level = (input[inOffset] >> 5) + 1;
        if (level == 1 || level == 2) {
            int ip = 0 + 1;
            long ctrl = (long) (input[inOffset + 0] & 31);
            int loop = 1;
            int op = 0;
            while (true) {
                int ip2;
                int i;
                int ref = op;
                long len = ctrl >> 5;
                long ofs = (31 & ctrl) << 8;
                if (ctrl >= 32) {
                    int code;
                    len--;
                    ref = (int) (((long) ref) - ofs);
                    if (len == 6) {
                        if (level == 1) {
                            len += (long) (input[inOffset + ip] & 255);
                            ip++;
                        } else {
                            do {
                                ip2 = ip;
                                ip = ip2 + 1;
                                code = input[inOffset + ip2] & 255;
                                len += (long) code;
                            } while (code == 255);
                        }
                    }
                    if (level == 1) {
                        ref -= input[inOffset + ip] & 255;
                        ip++;
                    } else {
                        ip2 = ip + 1;
                        code = input[inOffset + ip] & 255;
                        ref -= code;
                        if (code == 255 && ofs == 7936) {
                            ip = ip2 + 1;
                            ref = (int) ((((long) op) - (((long) ((input[inOffset + ip2] & 255) << 8)) + ((long) (input[inOffset + ip] & 255)))) - 8191);
                            ip++;
                        } else {
                            ip = ip2;
                        }
                    }
                    if ((((long) op) + len) + 3 > ((long) outLength)) {
                        ip2 = ip;
                        i = op;
                        return 0;
                    } else if (ref - 1 < 0) {
                        ip2 = ip;
                        i = op;
                        return 0;
                    } else {
                        if (ip < inLength) {
                            ip2 = ip + 1;
                            ctrl = (long) (input[inOffset + ip] & 255);
                        } else {
                            loop = 0;
                            ip2 = ip;
                        }
                        if (ref == op) {
                            byte b = output[(outOffset + ref) - 1];
                            i = op + 1;
                            output[outOffset + op] = b;
                            op = i + 1;
                            output[outOffset + i] = b;
                            i = op + 1;
                            output[outOffset + op] = b;
                            op = i;
                            while (len != 0) {
                                i = op + 1;
                                output[outOffset + op] = b;
                                len--;
                                op = i;
                            }
                            i = op;
                        } else {
                            ref--;
                            i = op + 1;
                            int ref2 = ref + 1;
                            output[outOffset + op] = output[outOffset + ref];
                            op = i + 1;
                            ref = ref2 + 1;
                            output[outOffset + i] = output[outOffset + ref2];
                            i = op + 1;
                            ref2 = ref + 1;
                            output[outOffset + op] = output[outOffset + ref];
                            op = i;
                            while (len != 0) {
                                i = op + 1;
                                ref = ref2 + 1;
                                output[outOffset + op] = output[outOffset + ref2];
                                len--;
                                ref2 = ref;
                                op = i;
                            }
                            i = op;
                        }
                    }
                } else {
                    ctrl++;
                    if (((long) op) + ctrl > ((long) outLength)) {
                        ip2 = ip;
                        i = op;
                        return 0;
                    } else if (((long) ip) + ctrl > ((long) inLength)) {
                        ip2 = ip;
                        i = op;
                        return 0;
                    } else {
                        i = op + 1;
                        ip2 = ip + 1;
                        output[outOffset + op] = input[inOffset + ip];
                        ctrl--;
                        op = i;
                        ip = ip2;
                        while (ctrl != 0) {
                            i = op + 1;
                            ip2 = ip + 1;
                            output[outOffset + op] = input[inOffset + ip];
                            ctrl--;
                            op = i;
                            ip = ip2;
                        }
                        loop = ip < inLength ? 1 : 0;
                        if (loop != 0) {
                            ip2 = ip + 1;
                            ctrl = (long) (input[inOffset + ip] & 255);
                            i = op;
                        } else {
                            i = op;
                            ip2 = ip;
                        }
                    }
                }
                if (loop == 0) {
                    return i;
                }
                op = i;
                ip = ip2;
            }
        } else {
            throw new DecompressionException(String.format("invalid level: %d (expected: %d or %d)", new Object[]{Integer.valueOf(level), Integer.valueOf(1), Integer.valueOf(2)}));
        }
    }

    private static int hashFunction(byte[] p, int offset) {
        int v = readU16(p, offset);
        return (v ^ (readU16(p, offset + 1) ^ (v >> 3))) & 8191;
    }

    private static int readU16(byte[] data, int offset) {
        if (offset + 1 >= data.length) {
            return data[offset] & 255;
        }
        return ((data[offset + 1] & 255) << 8) | (data[offset] & 255);
    }

    private FastLz() {
    }
}
