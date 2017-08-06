package io.netty.buffer;

import android.support.v4.view.MotionEventCompat;
import com.huluxia.video.recorder.b;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.ByteProcessor.IndexOfProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Locale;
import org.bytedeco.javacpp.avcodec.AVCodecContext;
import org.mozilla.classfile.ByteCode;

public final class ByteBufUtil {
    private static final FastThreadLocal<CharBuffer> CHAR_BUFFERS = new FastThreadLocal<CharBuffer>() {
        protected CharBuffer initialValue() throws Exception {
            return CharBuffer.allocate(1024);
        }
    };
    static final ByteBufAllocator DEFAULT_ALLOCATOR;
    private static final ByteProcessor FIND_NON_ASCII = new ByteProcessor() {
        public boolean process(byte value) {
            return value >= (byte) 0;
        }
    };
    private static final int MAX_BYTES_PER_CHAR_UTF8 = ((int) CharsetUtil.encoder(CharsetUtil.UTF_8).maxBytesPerChar());
    private static final int MAX_CHAR_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.maxThreadLocalCharBufferSize", 16384);
    private static final int THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 65536);
    private static final byte WRITE_UTF_UNKNOWN = (byte) 63;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ByteBufUtil.class);

    private static final class HexUtil {
        private static final char[] BYTE2CHAR = new char[256];
        private static final String[] BYTE2HEX = new String[256];
        private static final String[] BYTEPADDING = new String[16];
        private static final String[] HEXDUMP_ROWPREFIXES = new String[4096];
        private static final char[] HEXDUMP_TABLE = new char[1024];
        private static final String[] HEXPADDING = new String[16];

        private HexUtil() {
        }

        static {
            int i;
            char[] DIGITS = "0123456789abcdef".toCharArray();
            for (i = 0; i < 256; i++) {
                HEXDUMP_TABLE[i << 1] = DIGITS[(i >>> 4) & 15];
                HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i & 15];
            }
            for (i = 0; i < HEXPADDING.length; i++) {
                int j;
                int padding = HEXPADDING.length - i;
                StringBuilder buf = new StringBuilder(padding * 3);
                for (j = 0; j < padding; j++) {
                    buf.append("   ");
                }
                HEXPADDING[i] = buf.toString();
            }
            for (i = 0; i < HEXDUMP_ROWPREFIXES.length; i++) {
                buf = new StringBuilder(12);
                buf.append(StringUtil.NEWLINE);
                buf.append(Long.toHexString((((long) (i << 4)) & 4294967295L) | 4294967296L));
                buf.setCharAt(buf.length() - 9, '|');
                buf.append('|');
                HEXDUMP_ROWPREFIXES[i] = buf.toString();
            }
            for (i = 0; i < BYTE2HEX.length; i++) {
                BYTE2HEX[i] = HttpConstants.SP_CHAR + StringUtil.byteToHexStringPadded(i);
            }
            for (i = 0; i < BYTEPADDING.length; i++) {
                padding = BYTEPADDING.length - i;
                buf = new StringBuilder(padding);
                for (j = 0; j < padding; j++) {
                    buf.append(HttpConstants.SP_CHAR);
                }
                BYTEPADDING[i] = buf.toString();
            }
            i = 0;
            while (i < BYTE2CHAR.length) {
                if (i <= 31 || i >= 127) {
                    BYTE2CHAR[i] = '.';
                } else {
                    BYTE2CHAR[i] = (char) i;
                }
                i++;
            }
        }

        private static String hexDump(ByteBuf buffer, int fromIndex, int length) {
            if (length < 0) {
                throw new IllegalArgumentException("length: " + length);
            } else if (length == 0) {
                return "";
            } else {
                int endIndex = fromIndex + length;
                char[] buf = new char[(length << 1)];
                int srcIdx = fromIndex;
                int dstIdx = 0;
                while (srcIdx < endIndex) {
                    System.arraycopy(HEXDUMP_TABLE, buffer.getUnsignedByte(srcIdx) << 1, buf, dstIdx, 2);
                    srcIdx++;
                    dstIdx += 2;
                }
                return new String(buf);
            }
        }

        private static String hexDump(byte[] array, int fromIndex, int length) {
            if (length < 0) {
                throw new IllegalArgumentException("length: " + length);
            } else if (length == 0) {
                return "";
            } else {
                int endIndex = fromIndex + length;
                char[] buf = new char[(length << 1)];
                int srcIdx = fromIndex;
                int dstIdx = 0;
                while (srcIdx < endIndex) {
                    System.arraycopy(HEXDUMP_TABLE, (array[srcIdx] & 255) << 1, buf, dstIdx, 2);
                    srcIdx++;
                    dstIdx += 2;
                }
                return new String(buf);
            }
        }

        private static String prettyHexDump(ByteBuf buffer, int offset, int length) {
            if (length == 0) {
                return "";
            }
            StringBuilder buf = new StringBuilder((((length % 15 == 0 ? 0 : 1) + (length / 16)) + 4) * 80);
            appendPrettyHexDump(buf, buffer, offset, length);
            return buf.toString();
        }

        private static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset, int length) {
            if (MathUtil.isOutOfBounds(offset, length, buf.capacity())) {
                throw new IndexOutOfBoundsException("expected: 0 <= offset(" + offset + ") <= offset + length(" + length + ") <= " + "buf.capacity(" + buf.capacity() + ')');
            } else if (length != 0) {
                int rowStartIndex;
                int rowEndIndex;
                int j;
                dump.append("         +-------------------------------------------------+" + StringUtil.NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a_isRightVersion  b  c  d  e  f |" + StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
                int startIndex = offset;
                int fullRows = length >>> 4;
                int remainder = length & 15;
                for (int row = 0; row < fullRows; row++) {
                    rowStartIndex = (row << 4) + startIndex;
                    appendHexDumpRowPrefix(dump, row, rowStartIndex);
                    rowEndIndex = rowStartIndex + 16;
                    for (j = rowStartIndex; j < rowEndIndex; j++) {
                        dump.append(BYTE2HEX[buf.getUnsignedByte(j)]);
                    }
                    dump.append(" |");
                    for (j = rowStartIndex; j < rowEndIndex; j++) {
                        dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
                    }
                    dump.append('|');
                }
                if (remainder != 0) {
                    rowStartIndex = (fullRows << 4) + startIndex;
                    appendHexDumpRowPrefix(dump, fullRows, rowStartIndex);
                    rowEndIndex = rowStartIndex + remainder;
                    for (j = rowStartIndex; j < rowEndIndex; j++) {
                        dump.append(BYTE2HEX[buf.getUnsignedByte(j)]);
                    }
                    dump.append(HEXPADDING[remainder]);
                    dump.append(" |");
                    for (j = rowStartIndex; j < rowEndIndex; j++) {
                        dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
                    }
                    dump.append(BYTEPADDING[remainder]);
                    dump.append('|');
                }
                dump.append(StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
            }
        }

        private static void appendHexDumpRowPrefix(StringBuilder dump, int row, int rowStartIndex) {
            if (row < HEXDUMP_ROWPREFIXES.length) {
                dump.append(HEXDUMP_ROWPREFIXES[row]);
                return;
            }
            dump.append(StringUtil.NEWLINE);
            dump.append(Long.toHexString((((long) rowStartIndex) & 4294967295L) | 4294967296L));
            dump.setCharAt(dump.length() - 9, '|');
            dump.append('|');
        }
    }

    static final class ThreadLocalDirectByteBuf extends UnpooledDirectByteBuf {
        private static final Recycler<ThreadLocalDirectByteBuf> RECYCLER = new Recycler<ThreadLocalDirectByteBuf>() {
            protected ThreadLocalDirectByteBuf newObject(Handle<ThreadLocalDirectByteBuf> handle) {
                return new ThreadLocalDirectByteBuf(handle);
            }
        };
        private final Handle<ThreadLocalDirectByteBuf> handle;

        static ThreadLocalDirectByteBuf newInstance() {
            ThreadLocalDirectByteBuf buf = (ThreadLocalDirectByteBuf) RECYCLER.get();
            buf.setRefCnt(1);
            return buf;
        }

        private ThreadLocalDirectByteBuf(Handle<ThreadLocalDirectByteBuf> handle) {
            super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle;
        }

        protected void deallocate() {
            if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
                super.deallocate();
                return;
            }
            clear();
            this.handle.recycle(this);
        }
    }

    static final class ThreadLocalUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
        private static final Recycler<ThreadLocalUnsafeDirectByteBuf> RECYCLER = new Recycler<ThreadLocalUnsafeDirectByteBuf>() {
            protected ThreadLocalUnsafeDirectByteBuf newObject(Handle<ThreadLocalUnsafeDirectByteBuf> handle) {
                return new ThreadLocalUnsafeDirectByteBuf(handle);
            }
        };
        private final Handle<ThreadLocalUnsafeDirectByteBuf> handle;

        static ThreadLocalUnsafeDirectByteBuf newInstance() {
            ThreadLocalUnsafeDirectByteBuf buf = (ThreadLocalUnsafeDirectByteBuf) RECYCLER.get();
            buf.setRefCnt(1);
            return buf;
        }

        private ThreadLocalUnsafeDirectByteBuf(Handle<ThreadLocalUnsafeDirectByteBuf> handle) {
            super(UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle;
        }

        protected void deallocate() {
            if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
                super.deallocate();
                return;
            }
            clear();
            this.handle.recycle(this);
        }
    }

    static {
        ByteBufAllocator alloc;
        String allocType = SystemPropertyUtil.get("io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled").toLowerCase(Locale.US).trim();
        if ("unpooled".equals(allocType)) {
            alloc = UnpooledByteBufAllocator.DEFAULT;
            logger.debug("-Dio.netty.allocator.type: {}", allocType);
        } else if ("pooled".equals(allocType)) {
            alloc = PooledByteBufAllocator.DEFAULT;
            logger.debug("-Dio.netty.allocator.type: {}", allocType);
        } else {
            alloc = PooledByteBufAllocator.DEFAULT;
            logger.debug("-Dio.netty.allocator.type: pooled (unknown: {})", allocType);
        }
        DEFAULT_ALLOCATOR = alloc;
        logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", Integer.valueOf(THREAD_LOCAL_BUFFER_SIZE));
        logger.debug("-Dio.netty.maxThreadLocalCharBufferSize: {}", Integer.valueOf(MAX_CHAR_BUFFER_SIZE));
    }

    public static String hexDump(ByteBuf buffer) {
        return hexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
    }

    public static String hexDump(ByteBuf buffer, int fromIndex, int length) {
        return HexUtil.hexDump(buffer, fromIndex, length);
    }

    public static String hexDump(byte[] array) {
        return hexDump(array, 0, array.length);
    }

    public static String hexDump(byte[] array, int fromIndex, int length) {
        return HexUtil.hexDump(array, fromIndex, length);
    }

    public static int hashCode(ByteBuf buffer) {
        int i;
        int aLen = buffer.readableBytes();
        int intCount = aLen >>> 2;
        int byteCount = aLen & 3;
        int hashCode = 1;
        int arrayIndex = buffer.readerIndex();
        if (buffer.order() == ByteOrder.BIG_ENDIAN) {
            for (i = intCount; i > 0; i--) {
                hashCode = (hashCode * 31) + buffer.getInt(arrayIndex);
                arrayIndex += 4;
            }
        } else {
            for (i = intCount; i > 0; i--) {
                hashCode = (hashCode * 31) + swapInt(buffer.getInt(arrayIndex));
                arrayIndex += 4;
            }
        }
        i = byteCount;
        int arrayIndex2 = arrayIndex;
        while (i > 0) {
            hashCode = (hashCode * 31) + buffer.getByte(arrayIndex2);
            i--;
            arrayIndex2++;
        }
        if (hashCode == 0) {
            return 1;
        }
        return hashCode;
    }

    public static boolean equals(ByteBuf a, int aStartIndex, ByteBuf b, int bStartIndex, int length) {
        if (aStartIndex < 0 || bStartIndex < 0 || length < 0) {
            throw new IllegalArgumentException("All indexes and lengths must be non-negative");
        } else if (a.writerIndex() - length < aStartIndex || b.writerIndex() - length < bStartIndex) {
            return false;
        } else {
            int i;
            int longCount = length >>> 3;
            int byteCount = length & 7;
            if (a.order() == b.order()) {
                for (i = longCount; i > 0; i--) {
                    if (a.getLong(aStartIndex) != b.getLong(bStartIndex)) {
                        return false;
                    }
                    aStartIndex += 8;
                    bStartIndex += 8;
                }
            } else {
                for (i = longCount; i > 0; i--) {
                    if (a.getLong(aStartIndex) != swapLong(b.getLong(bStartIndex))) {
                        return false;
                    }
                    aStartIndex += 8;
                    bStartIndex += 8;
                }
            }
            for (i = byteCount; i > 0; i--) {
                if (a.getByte(aStartIndex) != b.getByte(bStartIndex)) {
                    return false;
                }
                aStartIndex++;
                bStartIndex++;
            }
            return true;
        }
    }

    public static boolean equals(ByteBuf bufferA, ByteBuf bufferB) {
        int aLen = bufferA.readableBytes();
        if (aLen != bufferB.readableBytes()) {
            return false;
        }
        return equals(bufferA, bufferA.readerIndex(), bufferB, bufferB.readerIndex(), aLen);
    }

    public static int compare(ByteBuf bufferA, ByteBuf bufferB) {
        int aLen = bufferA.readableBytes();
        int bLen = bufferB.readableBytes();
        int minLength = Math.min(aLen, bLen);
        int uintCount = minLength >>> 2;
        int byteCount = minLength & 3;
        int aIndex = bufferA.readerIndex();
        int bIndex = bufferB.readerIndex();
        if (uintCount > 0) {
            boolean bufferAIsBigEndian = bufferA.order() == ByteOrder.BIG_ENDIAN;
            int uintCountIncrement = uintCount << 2;
            long res = bufferA.order() == bufferB.order() ? bufferAIsBigEndian ? compareUintBigEndian(bufferA, bufferB, aIndex, bIndex, uintCountIncrement) : compareUintLittleEndian(bufferA, bufferB, aIndex, bIndex, uintCountIncrement) : bufferAIsBigEndian ? compareUintBigEndianA(bufferA, bufferB, aIndex, bIndex, uintCountIncrement) : compareUintBigEndianB(bufferA, bufferB, aIndex, bIndex, uintCountIncrement);
            if (res != 0) {
                return (int) Math.min(2147483647L, res);
            }
            aIndex += uintCountIncrement;
            bIndex += uintCountIncrement;
        }
        int aEnd = aIndex + byteCount;
        while (aIndex < aEnd) {
            int comp = bufferA.getUnsignedByte(aIndex) - bufferB.getUnsignedByte(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex++;
            bIndex++;
        }
        return aLen - bLen;
    }

    private static long compareUintBigEndian(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = bufferA.getUnsignedInt(aIndex) - bufferB.getUnsignedInt(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    private static long compareUintLittleEndian(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = bufferA.getUnsignedIntLE(aIndex) - bufferB.getUnsignedIntLE(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    private static long compareUintBigEndianA(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = bufferA.getUnsignedInt(aIndex) - bufferB.getUnsignedIntLE(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    private static long compareUintBigEndianB(ByteBuf bufferA, ByteBuf bufferB, int aIndex, int bIndex, int uintCountIncrement) {
        int aEnd = aIndex + uintCountIncrement;
        while (aIndex < aEnd) {
            long comp = bufferA.getUnsignedIntLE(aIndex) - bufferB.getUnsignedInt(bIndex);
            if (comp != 0) {
                return comp;
            }
            aIndex += 4;
            bIndex += 4;
        }
        return 0;
    }

    public static int indexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
        if (fromIndex <= toIndex) {
            return firstIndexOf(buffer, fromIndex, toIndex, value);
        }
        return lastIndexOf(buffer, fromIndex, toIndex, value);
    }

    public static short swapShort(short value) {
        return Short.reverseBytes(value);
    }

    public static int swapMedium(int value) {
        int swapped = (((value << 16) & 16711680) | (MotionEventCompat.ACTION_POINTER_INDEX_MASK & value)) | ((value >>> 16) & 255);
        if ((8388608 & swapped) != 0) {
            return swapped | -16777216;
        }
        return swapped;
    }

    public static int swapInt(int value) {
        return Integer.reverseBytes(value);
    }

    public static long swapLong(long value) {
        return Long.reverseBytes(value);
    }

    public static ByteBuf readBytes(ByteBufAllocator alloc, ByteBuf buffer, int length) {
        boolean release = true;
        ByteBuf dst = alloc.buffer(length);
        try {
            buffer.readBytes(dst);
            release = false;
            return dst;
        } finally {
            if (release) {
                dst.release();
            }
        }
    }

    private static int firstIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
        fromIndex = Math.max(fromIndex, 0);
        if (fromIndex >= toIndex || buffer.capacity() == 0) {
            return -1;
        }
        return buffer.forEachByte(fromIndex, toIndex - fromIndex, new IndexOfProcessor(value));
    }

    private static int lastIndexOf(ByteBuf buffer, int fromIndex, int toIndex, byte value) {
        fromIndex = Math.min(fromIndex, buffer.capacity());
        if (fromIndex < 0 || buffer.capacity() == 0) {
            return -1;
        }
        return buffer.forEachByteDesc(toIndex, fromIndex - toIndex, new IndexOfProcessor(value));
    }

    public static ByteBuf writeUtf8(ByteBufAllocator alloc, CharSequence seq) {
        ByteBuf buf = alloc.buffer(utf8MaxBytes(seq));
        writeUtf8(buf, seq);
        return buf;
    }

    public static int writeUtf8(ByteBuf buf, CharSequence seq) {
        int len = seq.length();
        buf.ensureWritable(utf8MaxBytes(seq));
        while (!(buf instanceof AbstractByteBuf)) {
            if (buf instanceof WrappedByteBuf) {
                buf = buf.unwrap();
            } else {
                byte[] bytes = seq.toString().getBytes(CharsetUtil.UTF_8);
                buf.writeBytes(bytes);
                return bytes.length;
            }
        }
        AbstractByteBuf byteBuf = (AbstractByteBuf) buf;
        int written = writeUtf8(byteBuf, byteBuf.writerIndex, seq, len);
        byteBuf.writerIndex += written;
        return written;
    }

    static int writeUtf8(AbstractByteBuf buffer, int writerIndex, CharSequence seq, int len) {
        int oldWriterIndex = writerIndex;
        int i = 0;
        int writerIndex2 = writerIndex;
        while (i < len) {
            char c = seq.charAt(i);
            if (c < '') {
                writerIndex = writerIndex2 + 1;
                buffer._setByte(writerIndex2, (byte) c);
            } else if (c < 'ࠀ') {
                writerIndex = writerIndex2 + 1;
                buffer._setByte(writerIndex2, (byte) ((c >> 6) | 192));
                writerIndex2 = writerIndex + 1;
                buffer._setByte(writerIndex, (byte) ((c & 63) | 128));
                writerIndex = writerIndex2;
            } else if (!StringUtil.isSurrogate(c)) {
                writerIndex = writerIndex2 + 1;
                buffer._setByte(writerIndex2, (byte) ((c >> 12) | 224));
                writerIndex2 = writerIndex + 1;
                buffer._setByte(writerIndex, (byte) (((c >> 6) & 63) | 128));
                writerIndex = writerIndex2 + 1;
                buffer._setByte(writerIndex2, (byte) ((c & 63) | 128));
            } else if (Character.isHighSurrogate(c)) {
                i++;
                try {
                    char c2 = seq.charAt(i);
                    if (Character.isLowSurrogate(c2)) {
                        int codePoint = Character.toCodePoint(c, c2);
                        writerIndex = writerIndex2 + 1;
                        buffer._setByte(writerIndex2, (byte) ((codePoint >> 18) | b.bpd));
                        writerIndex2 = writerIndex + 1;
                        buffer._setByte(writerIndex, (byte) (((codePoint >> 12) & 63) | 128));
                        writerIndex = writerIndex2 + 1;
                        buffer._setByte(writerIndex2, (byte) (((codePoint >> 6) & 63) | 128));
                        writerIndex2 = writerIndex + 1;
                        buffer._setByte(writerIndex, (byte) ((codePoint & 63) | 128));
                        writerIndex = writerIndex2;
                    } else {
                        writerIndex = writerIndex2 + 1;
                        buffer._setByte(writerIndex2, 63);
                        writerIndex2 = writerIndex + 1;
                        if (Character.isHighSurrogate(c2)) {
                            c2 = '?';
                        }
                        buffer._setByte(writerIndex, c2);
                        writerIndex = writerIndex2;
                    }
                } catch (IndexOutOfBoundsException e) {
                    writerIndex = writerIndex2 + 1;
                    buffer._setByte(writerIndex2, 63);
                }
            } else {
                writerIndex = writerIndex2 + 1;
                buffer._setByte(writerIndex2, 63);
            }
            i++;
            writerIndex2 = writerIndex;
        }
        writerIndex = writerIndex2;
        return writerIndex - oldWriterIndex;
    }

    public static int utf8MaxBytes(CharSequence seq) {
        return seq.length() * MAX_BYTES_PER_CHAR_UTF8;
    }

    public static ByteBuf writeAscii(ByteBufAllocator alloc, CharSequence seq) {
        ByteBuf buf = alloc.buffer(seq.length());
        writeAscii(buf, seq);
        return buf;
    }

    public static int writeAscii(ByteBuf buf, CharSequence seq) {
        int len = seq.length();
        buf.ensureWritable(len);
        if (seq instanceof AsciiString) {
            AsciiString asciiString = (AsciiString) seq;
            buf.writeBytes(asciiString.array(), asciiString.arrayOffset(), asciiString.length());
            return len;
        }
        while (!(buf instanceof AbstractByteBuf)) {
            if (buf instanceof WrappedByteBuf) {
                buf = buf.unwrap();
            } else {
                buf.writeBytes(seq.toString().getBytes(CharsetUtil.US_ASCII));
            }
        }
        AbstractByteBuf byteBuf = (AbstractByteBuf) buf;
        int written = writeAscii(byteBuf, byteBuf.writerIndex, seq, len);
        byteBuf.writerIndex += written;
        return written;
    }

    static int writeAscii(AbstractByteBuf buffer, int writerIndex, CharSequence seq, int len) {
        int i = 0;
        int writerIndex2 = writerIndex;
        while (i < len) {
            writerIndex = writerIndex2 + 1;
            buffer._setByte(writerIndex2, (byte) seq.charAt(i));
            i++;
            writerIndex2 = writerIndex;
        }
        return len;
    }

    public static ByteBuf encodeString(ByteBufAllocator alloc, CharBuffer src, Charset charset) {
        return encodeString0(alloc, false, src, charset, 0);
    }

    public static ByteBuf encodeString(ByteBufAllocator alloc, CharBuffer src, Charset charset, int extraCapacity) {
        return encodeString0(alloc, false, src, charset, extraCapacity);
    }

    static ByteBuf encodeString0(ByteBufAllocator alloc, boolean enforceHeap, CharBuffer src, Charset charset, int extraCapacity) {
        ByteBuf dst;
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        int length = ((int) (((double) src.remaining()) * ((double) encoder.maxBytesPerChar()))) + extraCapacity;
        if (enforceHeap) {
            dst = alloc.heapBuffer(length);
        } else {
            dst = alloc.buffer(length);
        }
        try {
            ByteBuffer dstBuf = dst.internalNioBuffer(0, length);
            int pos = dstBuf.position();
            CoderResult cr = encoder.encode(src, dstBuf, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            cr = encoder.flush(dstBuf);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            dst.writerIndex((dst.writerIndex() + dstBuf.position()) - pos);
            if (false) {
                dst.release();
            }
            return dst;
        } catch (CharacterCodingException x) {
            throw new IllegalStateException(x);
        } catch (Throwable th) {
            if (true) {
                dst.release();
            }
        }
    }

    static String decodeString(ByteBuf src, int readerIndex, int len, Charset charset) {
        if (len == 0) {
            return "";
        }
        CharsetDecoder decoder = CharsetUtil.decoder(charset);
        int maxLength = (int) (((double) len) * ((double) decoder.maxCharsPerByte()));
        CharBuffer dst = (CharBuffer) CHAR_BUFFERS.get();
        if (dst.length() < maxLength) {
            dst = CharBuffer.allocate(maxLength);
            if (maxLength <= MAX_CHAR_BUFFER_SIZE) {
                CHAR_BUFFERS.set(dst);
            }
        } else {
            dst.clear();
        }
        if (src.nioBufferCount() == 1) {
            decodeString(decoder, src.internalNioBuffer(readerIndex, len), dst);
        } else {
            ByteBuf buffer = src.alloc().heapBuffer(len);
            try {
                buffer.writeBytes(src, readerIndex, len);
                decodeString(decoder, buffer.internalNioBuffer(0, len), dst);
            } finally {
                buffer.release();
            }
        }
        return dst.flip().toString();
    }

    private static void decodeString(CharsetDecoder decoder, ByteBuffer src, CharBuffer dst) {
        try {
            CoderResult cr = decoder.decode(src, dst, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            cr = decoder.flush(dst);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
        } catch (CharacterCodingException x) {
            throw new IllegalStateException(x);
        }
    }

    public static ByteBuf threadLocalDirectBuffer() {
        if (THREAD_LOCAL_BUFFER_SIZE <= 0) {
            return null;
        }
        if (PlatformDependent.hasUnsafe()) {
            return ThreadLocalUnsafeDirectByteBuf.newInstance();
        }
        return ThreadLocalDirectByteBuf.newInstance();
    }

    public static byte[] getBytes(ByteBuf buf) {
        return getBytes(buf, buf.readerIndex(), buf.readableBytes());
    }

    public static byte[] getBytes(ByteBuf buf, int start, int length) {
        return getBytes(buf, start, length, true);
    }

    public static byte[] getBytes(ByteBuf buf, int start, int length, boolean copy) {
        if (MathUtil.isOutOfBounds(start, length, buf.capacity())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= " + "buf.capacity(" + buf.capacity() + ')');
        } else if (!buf.hasArray()) {
            byte[] v = new byte[length];
            buf.getBytes(start, v);
            return v;
        } else if (!copy && start == 0 && length == buf.capacity()) {
            return buf.array();
        } else {
            int baseOffset = buf.arrayOffset() + start;
            return Arrays.copyOfRange(buf.array(), baseOffset, baseOffset + length);
        }
    }

    public static void copy(AsciiString src, int srcIdx, ByteBuf dst, int dstIdx, int length) {
        if (MathUtil.isOutOfBounds(srcIdx, length, src.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + src.length() + ')');
        }
        ((ByteBuf) ObjectUtil.checkNotNull(dst, "dst")).setBytes(dstIdx, src.array(), src.arrayOffset() + srcIdx, length);
    }

    public static void copy(AsciiString src, int srcIdx, ByteBuf dst, int length) {
        if (MathUtil.isOutOfBounds(srcIdx, length, src.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + src.length() + ')');
        }
        ((ByteBuf) ObjectUtil.checkNotNull(dst, "dst")).writeBytes(src.array(), src.arrayOffset() + srcIdx, length);
    }

    public static String prettyHexDump(ByteBuf buffer) {
        return prettyHexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
    }

    public static String prettyHexDump(ByteBuf buffer, int offset, int length) {
        return HexUtil.prettyHexDump(buffer, offset, length);
    }

    public static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf) {
        appendPrettyHexDump(dump, buf, buf.readerIndex(), buf.readableBytes());
    }

    public static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset, int length) {
        HexUtil.appendPrettyHexDump(dump, buf, offset, length);
    }

    public static boolean isText(ByteBuf buf, Charset charset) {
        return isText(buf, buf.readerIndex(), buf.readableBytes(), charset);
    }

    public static boolean isText(ByteBuf buf, int index, int length, Charset charset) {
        ObjectUtil.checkNotNull(buf, "buf");
        ObjectUtil.checkNotNull(charset, "charset");
        int maxIndex = buf.readerIndex() + buf.readableBytes();
        if (index < 0 || length < 0 || index > maxIndex - length) {
            throw new IndexOutOfBoundsException("index: " + index + " length: " + length);
        } else if (charset.equals(CharsetUtil.UTF_8)) {
            return isUtf8(buf, index, length);
        } else {
            if (charset.equals(CharsetUtil.US_ASCII)) {
                return isAscii(buf, index, length);
            }
            CharsetDecoder decoder = CharsetUtil.decoder(charset, CodingErrorAction.REPORT, CodingErrorAction.REPORT);
            ByteBuf heapBuffer;
            try {
                if (buf.nioBufferCount() == 1) {
                    decoder.decode(buf.internalNioBuffer(index, length));
                    return true;
                }
                heapBuffer = buf.alloc().heapBuffer(length);
                heapBuffer.writeBytes(buf, index, length);
                decoder.decode(heapBuffer.internalNioBuffer(0, length));
                heapBuffer.release();
                return true;
            } catch (CharacterCodingException e) {
                return false;
            } catch (Throwable th) {
                heapBuffer.release();
            }
        }
    }

    private static boolean isAscii(ByteBuf buf, int index, int length) {
        return buf.forEachByte(index, length, FIND_NON_ASCII) == -1;
    }

    private static boolean isUtf8(ByteBuf buf, int index, int length) {
        int endIndex = index + length;
        int index2 = index;
        while (index2 < endIndex) {
            index = index2 + 1;
            byte b1 = buf.getByte(index2);
            if ((b1 & 128) == 0) {
                index2 = index;
            } else {
                if ((b1 & 224) == 192) {
                    if (index >= endIndex) {
                        return false;
                    }
                    index2 = index + 1;
                    if ((buf.getByte(index) & 192) != 128) {
                        index = index2;
                        return false;
                    }
                    if ((b1 & 255) < ByteCode.MONITORENTER) {
                        index = index2;
                        return false;
                    }
                    index = index2;
                } else if ((b1 & b.bpd) == 224) {
                    if (index > endIndex - 2) {
                        return false;
                    }
                    index2 = index + 1;
                    b2 = buf.getByte(index);
                    index = index2 + 1;
                    b3 = buf.getByte(index2);
                    if ((b2 & 192) != 128 || (b3 & 192) != 128) {
                        return false;
                    }
                    if ((b1 & 15) == 0 && (b2 & 255) < 160) {
                        return false;
                    }
                    if ((b1 & 15) == 13 && (b2 & 255) > 159) {
                        return false;
                    }
                } else if ((b1 & 248) != b.bpd || index > endIndex - 3) {
                    return false;
                } else {
                    index2 = index + 1;
                    b2 = buf.getByte(index);
                    index = index2 + 1;
                    b3 = buf.getByte(index2);
                    index2 = index + 1;
                    byte b4 = buf.getByte(index);
                    if ((b2 & 192) == 128 && (b3 & 192) == 128 && (b4 & 192) == 128) {
                        if ((b1 & 255) > AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE || (((b1 & 255) == b.bpd && (b2 & 255) < 144) || ((b1 & 255) == AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE && (b2 & 255) > 143))) {
                            index = index2;
                            return false;
                        }
                        index = index2;
                    } else {
                        index = index2;
                        return false;
                    }
                }
                index2 = index;
            }
        }
        index = index2;
        return true;
    }

    private ByteBufUtil() {
    }
}
