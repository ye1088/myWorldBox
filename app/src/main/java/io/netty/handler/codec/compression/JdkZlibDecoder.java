package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class JdkZlibDecoder extends ZlibDecoder {
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FRESERVED = 224;
    private final ByteBufChecksum crc;
    private boolean decideZlibOrNone;
    private final byte[] dictionary;
    private volatile boolean finished;
    private int flags;
    private GzipState gzipState;
    private Inflater inflater;
    private int xlen;

    private enum GzipState {
        HEADER_START,
        HEADER_END,
        FLG_READ,
        XLEN_READ,
        SKIP_FNAME,
        SKIP_COMMENT,
        PROCESS_FHCRC,
        FOOTER_START
    }

    public JdkZlibDecoder() {
        this(ZlibWrapper.ZLIB, null);
    }

    public JdkZlibDecoder(byte[] dictionary) {
        this(ZlibWrapper.ZLIB, dictionary);
    }

    public JdkZlibDecoder(ZlibWrapper wrapper) {
        this(wrapper, null);
    }

    private JdkZlibDecoder(ZlibWrapper wrapper, byte[] dictionary) {
        this.gzipState = GzipState.HEADER_START;
        this.flags = -1;
        this.xlen = -1;
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        switch (wrapper) {
            case GZIP:
                this.inflater = new Inflater(true);
                this.crc = ByteBufChecksum.wrapChecksum(new CRC32());
                break;
            case NONE:
                this.inflater = new Inflater(true);
                this.crc = null;
                break;
            case ZLIB:
                this.inflater = new Inflater();
                this.crc = null;
                break;
            case ZLIB_OR_NONE:
                this.decideZlibOrNone = true;
                this.crc = null;
                break;
            default:
                throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + wrapper);
        }
        this.dictionary = dictionary;
    }

    public boolean isClosed() {
        return this.finished;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (this.finished) {
            in.skipBytes(in.readableBytes());
            return;
        }
        int readableBytes = in.readableBytes();
        if (readableBytes != 0) {
            if (this.decideZlibOrNone) {
                if (readableBytes >= 2) {
                    this.inflater = new Inflater(!looksLikeZlib(in.getShort(in.readerIndex())));
                    this.decideZlibOrNone = false;
                } else {
                    return;
                }
            }
            if (this.crc != null) {
                switch (this.gzipState) {
                    case FOOTER_START:
                        if (readGZIPFooter(in)) {
                            this.finished = true;
                            return;
                        }
                        return;
                    default:
                        if (this.gzipState == GzipState.HEADER_END || readGZIPHeader(in)) {
                            readableBytes = in.readableBytes();
                            break;
                        }
                        return;
                }
            }
            if (in.hasArray()) {
                this.inflater.setInput(in.array(), in.arrayOffset() + in.readerIndex(), readableBytes);
            } else {
                byte[] array = new byte[readableBytes];
                in.getBytes(in.readerIndex(), array);
                this.inflater.setInput(array);
            }
            int maxOutputLength = this.inflater.getRemaining() << 1;
            ByteBuf decompressed = ctx.alloc().heapBuffer(maxOutputLength);
            boolean readFooter = false;
            byte[] outArray = decompressed.array();
            while (!this.inflater.needsInput()) {
                int writerIndex = decompressed.writerIndex();
                int outIndex = decompressed.arrayOffset() + writerIndex;
                int length = decompressed.writableBytes();
                if (length == 0) {
                    out.add(decompressed);
                    decompressed = ctx.alloc().heapBuffer(maxOutputLength);
                    outArray = decompressed.array();
                } else {
                    try {
                        int outputLength = this.inflater.inflate(outArray, outIndex, length);
                        if (outputLength > 0) {
                            decompressed.writerIndex(writerIndex + outputLength);
                            if (this.crc != null) {
                                this.crc.update(outArray, outIndex, outputLength);
                            }
                        } else if (this.inflater.needsDictionary()) {
                            if (this.dictionary == null) {
                                throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
                            }
                            this.inflater.setDictionary(this.dictionary);
                        }
                        if (this.inflater.finished()) {
                            if (this.crc == null) {
                                this.finished = true;
                            } else {
                                readFooter = true;
                            }
                            in.skipBytes(readableBytes - this.inflater.getRemaining());
                            if (readFooter) {
                                this.gzipState = GzipState.FOOTER_START;
                                if (readGZIPFooter(in)) {
                                    this.finished = true;
                                }
                            }
                            if (decompressed.isReadable()) {
                                decompressed.release();
                            } else {
                                out.add(decompressed);
                            }
                        }
                    } catch (DataFormatException e) {
                        throw new DecompressionException("decompression failure", e);
                    } catch (Throwable th) {
                        if (decompressed.isReadable()) {
                            out.add(decompressed);
                        } else {
                            decompressed.release();
                        }
                    }
                }
            }
            in.skipBytes(readableBytes - this.inflater.getRemaining());
            if (readFooter) {
                this.gzipState = GzipState.FOOTER_START;
                if (readGZIPFooter(in)) {
                    this.finished = true;
                }
            }
            if (decompressed.isReadable()) {
                decompressed.release();
            } else {
                out.add(decompressed);
            }
        }
    }

    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved0(ctx);
        if (this.inflater != null) {
            this.inflater.end();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean readGZIPHeader(io.netty.buffer.ByteBuf r12) {
        /*
        r11 = this;
        r10 = 4;
        r6 = 0;
        r7 = io.netty.handler.codec.compression.JdkZlibDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$JdkZlibDecoder$GzipState;
        r8 = r11.gzipState;
        r8 = r8.ordinal();
        r7 = r7[r8];
        switch(r7) {
            case 2: goto L_0x0015;
            case 3: goto L_0x00a9;
            case 4: goto L_0x00d4;
            case 5: goto L_0x00f5;
            case 6: goto L_0x0110;
            case 7: goto L_0x012b;
            case 8: goto L_0x0143;
            default: goto L_0x000f;
        };
    L_0x000f:
        r6 = new java.lang.IllegalStateException;
        r6.<init>();
        throw r6;
    L_0x0015:
        r7 = r12.readableBytes();
        r8 = 10;
        if (r7 >= r8) goto L_0x001e;
    L_0x001d:
        return r6;
    L_0x001e:
        r1 = r12.readByte();
        r2 = r12.readByte();
        r7 = 31;
        if (r1 == r7) goto L_0x0033;
    L_0x002a:
        r6 = new io.netty.handler.codec.compression.DecompressionException;
        r7 = "Input is not in the GZIP format";
        r6.<init>(r7);
        throw r6;
    L_0x0033:
        r7 = r11.crc;
        r7.update(r1);
        r7 = r11.crc;
        r7.update(r2);
        r3 = r12.readUnsignedByte();
        r7 = 8;
        if (r3 == r7) goto L_0x0066;
    L_0x0045:
        r6 = new io.netty.handler.codec.compression.DecompressionException;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "Unsupported compression method ";
        r7 = r7.append(r8);
        r7 = r7.append(r3);
        r8 = " in the GZIP header";
        r7 = r7.append(r8);
        r7 = r7.toString();
        r6.<init>(r7);
        throw r6;
    L_0x0066:
        r7 = r11.crc;
        r7.update(r3);
        r7 = r12.readUnsignedByte();
        r11.flags = r7;
        r7 = r11.crc;
        r8 = r11.flags;
        r7.update(r8);
        r7 = r11.flags;
        r7 = r7 & 224;
        if (r7 == 0) goto L_0x0087;
    L_0x007e:
        r6 = new io.netty.handler.codec.compression.DecompressionException;
        r7 = "Reserved flags are set in the GZIP header";
        r6.<init>(r7);
        throw r6;
    L_0x0087:
        r7 = r11.crc;
        r8 = r12.readerIndex();
        r7.update(r12, r8, r10);
        r12.skipBytes(r10);
        r7 = r11.crc;
        r8 = r12.readUnsignedByte();
        r7.update(r8);
        r7 = r11.crc;
        r8 = r12.readUnsignedByte();
        r7.update(r8);
        r7 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.FLG_READ;
        r11.gzipState = r7;
    L_0x00a9:
        r7 = r11.flags;
        r7 = r7 & 4;
        if (r7 == 0) goto L_0x00d0;
    L_0x00af:
        r7 = r12.readableBytes();
        r8 = 2;
        if (r7 < r8) goto L_0x001d;
    L_0x00b6:
        r4 = r12.readUnsignedByte();
        r5 = r12.readUnsignedByte();
        r7 = r11.crc;
        r7.update(r4);
        r7 = r11.crc;
        r7.update(r5);
        r7 = r11.xlen;
        r8 = r4 << 8;
        r8 = r8 | r5;
        r7 = r7 | r8;
        r11.xlen = r7;
    L_0x00d0:
        r7 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.XLEN_READ;
        r11.gzipState = r7;
    L_0x00d4:
        r7 = r11.xlen;
        r8 = -1;
        if (r7 == r8) goto L_0x00f1;
    L_0x00d9:
        r7 = r12.readableBytes();
        r8 = r11.xlen;
        if (r7 < r8) goto L_0x001d;
    L_0x00e1:
        r7 = r11.crc;
        r8 = r12.readerIndex();
        r9 = r11.xlen;
        r7.update(r12, r8, r9);
        r7 = r11.xlen;
        r12.skipBytes(r7);
    L_0x00f1:
        r7 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_FNAME;
        r11.gzipState = r7;
    L_0x00f5:
        r7 = r11.flags;
        r7 = r7 & 8;
        if (r7 == 0) goto L_0x010c;
    L_0x00fb:
        r7 = r12.isReadable();
        if (r7 == 0) goto L_0x001d;
    L_0x0101:
        r0 = r12.readUnsignedByte();
        r7 = r11.crc;
        r7.update(r0);
        if (r0 != 0) goto L_0x0146;
    L_0x010c:
        r7 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.SKIP_COMMENT;
        r11.gzipState = r7;
    L_0x0110:
        r7 = r11.flags;
        r7 = r7 & 16;
        if (r7 == 0) goto L_0x0127;
    L_0x0116:
        r7 = r12.isReadable();
        if (r7 == 0) goto L_0x001d;
    L_0x011c:
        r0 = r12.readUnsignedByte();
        r7 = r11.crc;
        r7.update(r0);
        if (r0 != 0) goto L_0x014d;
    L_0x0127:
        r7 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.PROCESS_FHCRC;
        r11.gzipState = r7;
    L_0x012b:
        r7 = r11.flags;
        r7 = r7 & 2;
        if (r7 == 0) goto L_0x013a;
    L_0x0131:
        r7 = r12.readableBytes();
        if (r7 < r10) goto L_0x001d;
    L_0x0137:
        r11.verifyCrc(r12);
    L_0x013a:
        r6 = r11.crc;
        r6.reset();
        r6 = io.netty.handler.codec.compression.JdkZlibDecoder.GzipState.HEADER_END;
        r11.gzipState = r6;
    L_0x0143:
        r6 = 1;
        goto L_0x001d;
    L_0x0146:
        r7 = r12.isReadable();
        if (r7 != 0) goto L_0x0101;
    L_0x014c:
        goto L_0x010c;
    L_0x014d:
        r7 = r12.isReadable();
        if (r7 != 0) goto L_0x011c;
    L_0x0153:
        goto L_0x0127;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.JdkZlibDecoder.readGZIPHeader(io.netty.buffer.ByteBuf):boolean");
    }

    private boolean readGZIPFooter(ByteBuf buf) {
        if (buf.readableBytes() < 8) {
            return false;
        }
        verifyCrc(buf);
        int dataLength = 0;
        for (int i = 0; i < 4; i++) {
            dataLength |= buf.readUnsignedByte() << (i * 8);
        }
        int readLength = this.inflater.getTotalOut();
        if (dataLength == readLength) {
            return true;
        }
        throw new DecompressionException("Number of bytes mismatch. Expected: " + dataLength + ", Got: " + readLength);
    }

    private void verifyCrc(ByteBuf in) {
        long crcValue = 0;
        for (int i = 0; i < 4; i++) {
            crcValue |= ((long) in.readUnsignedByte()) << (i * 8);
        }
        long readCrc = this.crc.getValue();
        if (crcValue != readCrc) {
            throw new DecompressionException("CRC value missmatch. Expected: " + crcValue + ", Got: " + readCrc);
        }
    }

    private static boolean looksLikeZlib(short cmf_flg) {
        return (cmf_flg & 30720) == 30720 && cmf_flg % 31 == 0;
    }
}
