package io.netty.util;

import io.netty.util.ByteProcessor.IndexOfProcessor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public final class AsciiString implements CharSequence, Comparable<CharSequence> {
    public static final HashingStrategy<CharSequence> CASE_INSENSITIVE_HASHER = new HashingStrategy<CharSequence>() {
        public int hashCode(CharSequence o) {
            return AsciiString.hashCode(o);
        }

        public boolean equals(CharSequence a, CharSequence b) {
            return AsciiString.contentEqualsIgnoreCase(a, b);
        }
    };
    public static final HashingStrategy<CharSequence> CASE_SENSITIVE_HASHER = new HashingStrategy<CharSequence>() {
        public int hashCode(CharSequence o) {
            return AsciiString.hashCode(o);
        }

        public boolean equals(CharSequence a, CharSequence b) {
            return AsciiString.contentEquals(a, b);
        }
    };
    public static final AsciiString EMPTY_STRING = new AsciiString((CharSequence) "");
    public static final int INDEX_NOT_FOUND = -1;
    private static final char MAX_CHAR_VALUE = 'ÿ';
    private int hash;
    private final int length;
    private final int offset;
    private String string;
    private final byte[] value;

    private interface CharEqualityComparator {
        boolean equals(char c, char c2);
    }

    private static final class AsciiCaseInsensitiveCharEqualityComparator implements CharEqualityComparator {
        static final AsciiCaseInsensitiveCharEqualityComparator INSTANCE = new AsciiCaseInsensitiveCharEqualityComparator();

        private AsciiCaseInsensitiveCharEqualityComparator() {
        }

        public boolean equals(char a, char b) {
            return AsciiString.equalsIgnoreCase(a, b);
        }
    }

    private static final class DefaultCharEqualityComparator implements CharEqualityComparator {
        static final DefaultCharEqualityComparator INSTANCE = new DefaultCharEqualityComparator();

        private DefaultCharEqualityComparator() {
        }

        public boolean equals(char a, char b) {
            return a == b;
        }
    }

    private static final class GeneralCaseInsensitiveCharEqualityComparator implements CharEqualityComparator {
        static final GeneralCaseInsensitiveCharEqualityComparator INSTANCE = new GeneralCaseInsensitiveCharEqualityComparator();

        private GeneralCaseInsensitiveCharEqualityComparator() {
        }

        public boolean equals(char a, char b) {
            return Character.toUpperCase(a) == Character.toUpperCase(b) || Character.toLowerCase(a) == Character.toLowerCase(b);
        }
    }

    public AsciiString(byte[] value) {
        this(value, true);
    }

    public AsciiString(byte[] value, boolean copy) {
        this(value, 0, value.length, copy);
    }

    public AsciiString(byte[] value, int start, int length, boolean copy) {
        if (copy) {
            this.value = Arrays.copyOfRange(value, start, start + length);
            this.offset = 0;
        } else if (MathUtil.isOutOfBounds(start, length, value.length)) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= " + "value.length(" + value.length + ')');
        } else {
            this.value = value;
            this.offset = start;
        }
        this.length = length;
    }

    public AsciiString(ByteBuffer value) {
        this(value, true);
    }

    public AsciiString(ByteBuffer value, boolean copy) {
        this(value, value.position(), value.remaining(), copy);
    }

    public AsciiString(ByteBuffer value, int start, int length, boolean copy) {
        if (MathUtil.isOutOfBounds(start, length, value.capacity())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= " + "value.capacity(" + value.capacity() + ')');
        }
        if (!value.hasArray()) {
            this.value = new byte[length];
            int oldPos = value.position();
            value.get(this.value, 0, length);
            value.position(oldPos);
            this.offset = 0;
        } else if (copy) {
            int bufferOffset = value.arrayOffset() + start;
            this.value = Arrays.copyOfRange(value.array(), bufferOffset, bufferOffset + length);
            this.offset = 0;
        } else {
            this.value = value.array();
            this.offset = start;
        }
        this.length = length;
    }

    public AsciiString(char[] value) {
        this(value, 0, value.length);
    }

    public AsciiString(char[] value, int start, int length) {
        if (MathUtil.isOutOfBounds(start, length, value.length)) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= " + "value.length(" + value.length + ')');
        }
        this.value = new byte[length];
        int i = 0;
        int j = start;
        while (i < length) {
            this.value[i] = c2b(value[j]);
            i++;
            j++;
        }
        this.offset = 0;
        this.length = length;
    }

    public AsciiString(char[] value, Charset charset) {
        this(value, charset, 0, value.length);
    }

    public AsciiString(char[] value, Charset charset, int start, int length) {
        CharBuffer cbuf = CharBuffer.wrap(value, start, length);
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        ByteBuffer nativeBuffer = ByteBuffer.allocate((int) (encoder.maxBytesPerChar() * ((float) length)));
        encoder.encode(cbuf, nativeBuffer, true);
        int bufferOffset = nativeBuffer.arrayOffset();
        this.value = Arrays.copyOfRange(nativeBuffer.array(), bufferOffset, nativeBuffer.position() + bufferOffset);
        this.offset = 0;
        this.length = this.value.length;
    }

    public AsciiString(CharSequence value) {
        this(value, 0, value.length());
    }

    public AsciiString(CharSequence value, int start, int length) {
        if (MathUtil.isOutOfBounds(start, length, value.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= " + "value.length(" + value.length() + ')');
        }
        this.value = new byte[length];
        int i = 0;
        int j = start;
        while (i < length) {
            this.value[i] = c2b(value.charAt(j));
            i++;
            j++;
        }
        this.offset = 0;
        this.length = length;
    }

    public AsciiString(CharSequence value, Charset charset) {
        this(value, charset, 0, value.length());
    }

    public AsciiString(CharSequence value, Charset charset, int start, int length) {
        CharBuffer cbuf = CharBuffer.wrap(value, start, start + length);
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        ByteBuffer nativeBuffer = ByteBuffer.allocate((int) (encoder.maxBytesPerChar() * ((float) length)));
        encoder.encode(cbuf, nativeBuffer, true);
        int offset = nativeBuffer.arrayOffset();
        this.value = Arrays.copyOfRange(nativeBuffer.array(), offset, nativeBuffer.position() + offset);
        this.offset = 0;
        this.length = this.value.length;
    }

    public int forEachByte(ByteProcessor visitor) throws Exception {
        return forEachByte0(0, length(), visitor);
    }

    public int forEachByte(int index, int length, ByteProcessor visitor) throws Exception {
        if (!MathUtil.isOutOfBounds(index, length, length())) {
            return forEachByte0(index, length, visitor);
        }
        throw new IndexOutOfBoundsException("expected: 0 <= index(" + index + ") <= start + length(" + length + ") <= " + "length(" + length() + ')');
    }

    private int forEachByte0(int index, int length, ByteProcessor visitor) throws Exception {
        int len = (this.offset + index) + length;
        for (int i = this.offset + index; i < len; i++) {
            if (!visitor.process(this.value[i])) {
                return i - this.offset;
            }
        }
        return -1;
    }

    public int forEachByteDesc(ByteProcessor visitor) throws Exception {
        return forEachByteDesc0(0, length(), visitor);
    }

    public int forEachByteDesc(int index, int length, ByteProcessor visitor) throws Exception {
        if (!MathUtil.isOutOfBounds(index, length, length())) {
            return forEachByteDesc0(index, length, visitor);
        }
        throw new IndexOutOfBoundsException("expected: 0 <= index(" + index + ") <= start + length(" + length + ") <= " + "length(" + length() + ')');
    }

    private int forEachByteDesc0(int index, int length, ByteProcessor visitor) throws Exception {
        int end = this.offset + index;
        for (int i = ((this.offset + index) + length) - 1; i >= end; i--) {
            if (!visitor.process(this.value[i])) {
                return i - this.offset;
            }
        }
        return -1;
    }

    public byte byteAt(int index) {
        if (index < 0 || index >= this.length) {
            throw new IndexOutOfBoundsException("index: " + index + " must be in the range [0," + this.length + ")");
        } else if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.getByte(this.value, this.offset + index);
        } else {
            return this.value[this.offset + index];
        }
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public int length() {
        return this.length;
    }

    public void arrayChanged() {
        this.string = null;
        this.hash = 0;
    }

    public byte[] array() {
        return this.value;
    }

    public int arrayOffset() {
        return this.offset;
    }

    public boolean isEntireArrayUsed() {
        return this.offset == 0 && this.length == this.value.length;
    }

    public byte[] toByteArray() {
        return toByteArray(0, length());
    }

    public byte[] toByteArray(int start, int end) {
        return Arrays.copyOfRange(this.value, this.offset + start, this.offset + end);
    }

    public void copy(int srcIdx, byte[] dst, int dstIdx, int length) {
        if (MathUtil.isOutOfBounds(srcIdx, length, length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + length() + ')');
        }
        System.arraycopy(this.value, this.offset + srcIdx, ObjectUtil.checkNotNull(dst, "dst"), dstIdx, length);
    }

    public char charAt(int index) {
        return b2c(byteAt(index));
    }

    public boolean contains(CharSequence cs) {
        return indexOf(cs) >= 0;
    }

    public int compareTo(CharSequence string) {
        if (this == string) {
            return 0;
        }
        int length1 = length();
        int length2 = string.length();
        int minLength = Math.min(length1, length2);
        int i = 0;
        int j = arrayOffset();
        while (i < minLength) {
            int result = b2c(this.value[j]) - string.charAt(i);
            if (result != 0) {
                return result;
            }
            i++;
            j++;
        }
        return length1 - length2;
    }

    public AsciiString concat(CharSequence string) {
        int thisLen = length();
        int thatLen = string.length();
        if (thatLen == 0) {
            return this;
        }
        byte[] newValue;
        if (string.getClass() == AsciiString.class) {
            AsciiString that = (AsciiString) string;
            if (isEmpty()) {
                return that;
            }
            newValue = new byte[(thisLen + thatLen)];
            System.arraycopy(this.value, arrayOffset(), newValue, 0, thisLen);
            System.arraycopy(that.value, that.arrayOffset(), newValue, thisLen, thatLen);
            this(newValue, false);
            return this;
        } else if (isEmpty()) {
            this(string);
            return this;
        } else {
            newValue = new byte[(thisLen + thatLen)];
            System.arraycopy(this.value, arrayOffset(), newValue, 0, thisLen);
            int i = thisLen;
            int j = 0;
            while (i < newValue.length) {
                newValue[i] = c2b(string.charAt(j));
                i++;
                j++;
            }
            this(newValue, false);
            return this;
        }
    }

    public boolean endsWith(CharSequence suffix) {
        int suffixLen = suffix.length();
        return regionMatches(length() - suffixLen, suffix, 0, suffixLen);
    }

    public boolean contentEqualsIgnoreCase(CharSequence string) {
        if (string == null || string.length() != length()) {
            return false;
        }
        int i;
        int j;
        if (string.getClass() == AsciiString.class) {
            AsciiString rhs = (AsciiString) string;
            i = arrayOffset();
            j = rhs.arrayOffset();
            while (i < length()) {
                if (!equalsIgnoreCase(this.value[i], rhs.value[j])) {
                    return false;
                }
                i++;
                j++;
            }
            return true;
        }
        i = arrayOffset();
        j = 0;
        while (i < length()) {
            if (!equalsIgnoreCase(b2c(this.value[i]), string.charAt(j))) {
                return false;
            }
            i++;
            j++;
        }
        return true;
    }

    public char[] toCharArray() {
        return toCharArray(0, length());
    }

    public char[] toCharArray(int start, int end) {
        int length = end - start;
        if (length == 0) {
            return EmptyArrays.EMPTY_CHARS;
        }
        if (MathUtil.isOutOfBounds(start, length, length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= srcIdx + length(" + length + ") <= srcLen(" + length() + ')');
        }
        char[] buffer = new char[length];
        int i = 0;
        int j = start + arrayOffset();
        while (i < length) {
            buffer[i] = b2c(this.value[j]);
            i++;
            j++;
        }
        return buffer;
    }

    public void copy(int srcIdx, char[] dst, int dstIdx, int length) {
        if (dst == null) {
            throw new NullPointerException("dst");
        } else if (MathUtil.isOutOfBounds(srcIdx, length, length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + length() + ')');
        } else {
            int dstEnd = dstIdx + length;
            int i = dstIdx;
            int j = srcIdx + arrayOffset();
            while (i < dstEnd) {
                dst[i] = b2c(this.value[j]);
                i++;
                j++;
            }
        }
    }

    public AsciiString subSequence(int start) {
        return subSequence(start, length());
    }

    public AsciiString subSequence(int start, int end) {
        return subSequence(start, end, true);
    }

    public AsciiString subSequence(int start, int end, boolean copy) {
        if (MathUtil.isOutOfBounds(start, end - start, length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= end (" + end + ") <= length(" + length() + ')');
        } else if (start == 0 && end == length()) {
            return this;
        } else {
            if (end == start) {
                return EMPTY_STRING;
            }
            return new AsciiString(this.value, this.offset + start, end - start, copy);
        }
    }

    public int indexOf(CharSequence string) {
        return indexOf(string, 0);
    }

    public int indexOf(CharSequence subString, int start) {
        if (start < 0) {
            start = 0;
        }
        int thisLen = length();
        int subCount = subString.length();
        if (subCount <= 0) {
            return start < thisLen ? start : thisLen;
        } else {
            if (subCount > thisLen - start) {
                return -1;
            }
            char firstChar = subString.charAt(0);
            if (firstChar > MAX_CHAR_VALUE) {
                return -1;
            }
            ByteProcessor IndexOfVisitor = new IndexOfProcessor((byte) firstChar);
            while (true) {
                try {
                    int i = forEachByte(start, thisLen - start, IndexOfVisitor);
                    if (i != -1 && subCount + i <= thisLen) {
                        int o1 = i;
                        int o2 = 0;
                        do {
                            o2++;
                            if (o2 >= subCount) {
                                break;
                            }
                            o1++;
                        } while (b2c(this.value[arrayOffset() + o1]) == subString.charAt(o2));
                        if (o2 == subCount) {
                            return i;
                        }
                        start = i + 1;
                    }
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                    return -1;
                }
            }
            return -1;
        }
    }

    public int indexOf(char ch, int start) {
        int i = -1;
        if (start < 0) {
            start = 0;
        }
        int thisLen = length();
        if (ch <= MAX_CHAR_VALUE) {
            try {
                i = forEachByte(start, thisLen - start, new IndexOfProcessor((byte) ch));
            } catch (Exception e) {
                PlatformDependent.throwException(e);
            }
        }
        return i;
    }

    public int lastIndexOf(CharSequence string) {
        return lastIndexOf(string, length());
    }

    public int lastIndexOf(CharSequence subString, int start) {
        int thisLen = length();
        int subCount = subString.length();
        if (subCount > thisLen || start < 0) {
            return -1;
        }
        if (subCount > 0) {
            start = Math.min(start, thisLen - subCount);
            char firstChar = subString.charAt(0);
            if (firstChar > MAX_CHAR_VALUE) {
                return -1;
            }
            ByteProcessor IndexOfVisitor = new IndexOfProcessor((byte) firstChar);
            while (true) {
                try {
                    int i = forEachByteDesc(start, thisLen - start, IndexOfVisitor);
                    if (i == -1) {
                        return -1;
                    }
                    int o1 = i;
                    int o2 = 0;
                    do {
                        o2++;
                        if (o2 >= subCount) {
                            break;
                        }
                        o1++;
                    } while (b2c(this.value[arrayOffset() + o1]) == subString.charAt(o2));
                    if (o2 == subCount) {
                        return i;
                    }
                    start = i - 1;
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                    return -1;
                }
            }
        } else if (start < thisLen) {
            return start;
        } else {
            return thisLen;
        }
    }

    public boolean regionMatches(int thisStart, CharSequence string, int start, int length) {
        if (string == null) {
            throw new NullPointerException("string");
        } else if (start < 0 || string.length() - start < length) {
            return false;
        } else {
            int thisLen = length();
            if (thisStart < 0 || thisLen - thisStart < length) {
                return false;
            }
            if (length <= 0) {
                return true;
            }
            int thatEnd = start + length;
            int i = start;
            int j = thisStart + arrayOffset();
            while (i < thatEnd) {
                if (b2c(this.value[j]) != string.charAt(i)) {
                    return false;
                }
                i++;
                j++;
            }
            return true;
        }
    }

    public boolean regionMatches(boolean ignoreCase, int thisStart, CharSequence string, int start, int length) {
        if (!ignoreCase) {
            return regionMatches(thisStart, string, start, length);
        }
        if (string == null) {
            throw new NullPointerException("string");
        }
        int thisLen = length();
        if (thisStart < 0 || length > thisLen - thisStart || start < 0 || length > string.length() - start) {
            return false;
        }
        thisStart += arrayOffset();
        int thisEnd = thisStart + length;
        int start2 = start;
        int thisStart2 = thisStart;
        while (thisStart2 < thisEnd) {
            thisStart = thisStart2 + 1;
            start = start2 + 1;
            if (!equalsIgnoreCase(b2c(this.value[thisStart2]), string.charAt(start2))) {
                return false;
            }
            start2 = start;
            thisStart2 = thisStart;
        }
        start = start2;
        thisStart = thisStart2;
        return true;
    }

    public AsciiString replace(char oldChar, char newChar) {
        if (oldChar > MAX_CHAR_VALUE) {
            return this;
        }
        byte oldCharByte = c2b(oldChar);
        try {
            if (forEachByte(new IndexOfProcessor(oldCharByte)) == -1) {
                return this;
            }
            byte newCharByte = c2b(newChar);
            byte[] buffer = new byte[length()];
            int i = 0;
            int j = arrayOffset();
            while (i < buffer.length) {
                byte b = this.value[j];
                if (b == oldCharByte) {
                    b = newCharByte;
                }
                buffer[i] = b;
                i++;
                j++;
            }
            this(buffer, false);
            return this;
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            return this;
        }
    }

    public boolean startsWith(CharSequence prefix) {
        return startsWith(prefix, 0);
    }

    public boolean startsWith(CharSequence prefix, int start) {
        return regionMatches(start, prefix, 0, prefix.length());
    }

    public AsciiString toLowerCase() {
        int i;
        boolean lowercased = true;
        int len = length() + arrayOffset();
        for (i = arrayOffset(); i < len; i++) {
            byte b = this.value[i];
            if (b >= (byte) 65 && b <= (byte) 90) {
                lowercased = false;
                break;
            }
        }
        if (lowercased) {
            return this;
        }
        byte[] newValue = new byte[length()];
        i = 0;
        int j = arrayOffset();
        while (i < newValue.length) {
            newValue[i] = toLowerCase(this.value[j]);
            i++;
            j++;
        }
        this(newValue, false);
        return this;
    }

    public AsciiString toUpperCase() {
        int i;
        boolean uppercased = true;
        int len = length() + arrayOffset();
        for (i = arrayOffset(); i < len; i++) {
            byte b = this.value[i];
            if (b >= (byte) 97 && b <= (byte) 122) {
                uppercased = false;
                break;
            }
        }
        if (uppercased) {
            return this;
        }
        byte[] newValue = new byte[length()];
        i = 0;
        int j = arrayOffset();
        while (i < newValue.length) {
            newValue[i] = toUpperCase(this.value[j]);
            i++;
            j++;
        }
        this(newValue, false);
        return this;
    }

    public AsciiString trim() {
        int start = arrayOffset();
        int last = (arrayOffset() + length()) - 1;
        int end = last;
        while (start <= end && this.value[start] <= (byte) 32) {
            start++;
        }
        while (end >= start && this.value[end] <= (byte) 32) {
            end--;
        }
        return (start == 0 && end == last) ? this : new AsciiString(this.value, start, (end - start) + 1, false);
    }

    public boolean contentEquals(CharSequence a) {
        if (a == null || a.length() != length()) {
            return false;
        }
        if (a.getClass() == AsciiString.class) {
            return equals(a);
        }
        int i = arrayOffset();
        for (int j = 0; j < a.length(); j++) {
            if (b2c(this.value[i]) != a.charAt(j)) {
                return false;
            }
            i++;
        }
        return true;
    }

    public boolean matches(String expr) {
        return Pattern.matches(expr, this);
    }

    public AsciiString[] split(String expr, int max) {
        return toAsciiStringArray(Pattern.compile(expr).split(this, max));
    }

    public AsciiString[] split(char delim) {
        int i;
        List<AsciiString> res = InternalThreadLocalMap.get().arrayList();
        int start = 0;
        int length = length();
        for (i = 0; i < length; i++) {
            if (charAt(i) == delim) {
                if (start == i) {
                    res.add(EMPTY_STRING);
                } else {
                    res.add(new AsciiString(this.value, arrayOffset() + start, i - start, false));
                }
                start = i + 1;
            }
        }
        if (start == 0) {
            res.add(this);
        } else if (start != length) {
            res.add(new AsciiString(this.value, arrayOffset() + start, length - start, false));
        } else {
            i = res.size() - 1;
            while (i >= 0 && ((AsciiString) res.get(i)).isEmpty()) {
                res.remove(i);
                i--;
            }
        }
        return (AsciiString[]) res.toArray(new AsciiString[res.size()]);
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash = PlatformDependent.hashCodeAscii(this.value, this.offset, this.length);
        }
        return this.hash;
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != AsciiString.class) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AsciiString other = (AsciiString) obj;
        if (length() == other.length() && hashCode() == other.hashCode() && PlatformDependent.equals(array(), arrayOffset(), other.array(), other.arrayOffset(), length())) {
            return true;
        }
        return false;
    }

    public String toString() {
        if (this.string != null) {
            return this.string;
        }
        this.string = toString(0);
        return this.string;
    }

    public String toString(int start) {
        return toString(start, length());
    }

    public String toString(int start, int end) {
        int length = end - start;
        if (length == 0) {
            return "";
        }
        if (!MathUtil.isOutOfBounds(start, length, length())) {
            return new String(this.value, 0, this.offset + start, length);
        }
        throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= srcIdx + length(" + length + ") <= srcLen(" + length() + ')');
    }

    public boolean parseBoolean() {
        return this.length >= 1 && this.value[this.offset] != (byte) 0;
    }

    public char parseChar() {
        return parseChar(0);
    }

    public char parseChar(int start) {
        if (start + 1 >= length()) {
            throw new IndexOutOfBoundsException("2 bytes required to convert to character. index " + start + " would go out of bounds.");
        }
        int startWithOffset = start + this.offset;
        return (char) ((b2c(this.value[startWithOffset]) << 8) | b2c(this.value[startWithOffset + 1]));
    }

    public short parseShort() {
        return parseShort(0, length(), 10);
    }

    public short parseShort(int radix) {
        return parseShort(0, length(), radix);
    }

    public short parseShort(int start, int end) {
        return parseShort(start, end, 10);
    }

    public short parseShort(int start, int end, int radix) {
        short intValue = parseInt(start, end, radix);
        short result = (short) intValue;
        if (result == intValue) {
            return result;
        }
        throw new NumberFormatException(subSequence(start, end, false).toString());
    }

    public int parseInt() {
        return parseInt(0, length(), 10);
    }

    public int parseInt(int radix) {
        return parseInt(0, length(), radix);
    }

    public int parseInt(int start, int end) {
        return parseInt(start, end, 10);
    }

    public int parseInt(int start, int end, int radix) {
        if (radix < 2 || radix > 36) {
            throw new NumberFormatException();
        } else if (start == end) {
            throw new NumberFormatException();
        } else {
            boolean negative;
            int i = start;
            if (byteAt(i) == (byte) 45) {
                negative = true;
            } else {
                negative = false;
            }
            if (negative) {
                i++;
                if (i == end) {
                    throw new NumberFormatException(subSequence(start, end, false).toString());
                }
            }
            return parseInt(i, end, radix, negative);
        }
    }

    private int parseInt(int start, int end, int radix, boolean negative) {
        int max = Integer.MIN_VALUE / radix;
        int result = 0;
        int i = start;
        while (i < end) {
            int currOffset = i + 1;
            int digit = Character.digit((char) (this.value[this.offset + i] & 255), radix);
            if (digit == -1) {
                throw new NumberFormatException(subSequence(start, end, false).toString());
            } else if (max > result) {
                throw new NumberFormatException(subSequence(start, end, false).toString());
            } else {
                int next = (result * radix) - digit;
                if (next > result) {
                    throw new NumberFormatException(subSequence(start, end, false).toString());
                }
                result = next;
                i = currOffset;
            }
        }
        if (!negative) {
            result = -result;
            if (result < 0) {
                throw new NumberFormatException(subSequence(start, end, false).toString());
            }
        }
        return result;
    }

    public long parseLong() {
        return parseLong(0, length(), 10);
    }

    public long parseLong(int radix) {
        return parseLong(0, length(), radix);
    }

    public long parseLong(int start, int end) {
        return parseLong(start, end, 10);
    }

    public long parseLong(int start, int end, int radix) {
        if (radix < 2 || radix > 36) {
            throw new NumberFormatException();
        } else if (start == end) {
            throw new NumberFormatException();
        } else {
            boolean negative;
            int i = start;
            if (byteAt(i) == (byte) 45) {
                negative = true;
            } else {
                negative = false;
            }
            if (negative) {
                i++;
                if (i == end) {
                    throw new NumberFormatException(subSequence(start, end, false).toString());
                }
            }
            return parseLong(i, end, radix, negative);
        }
    }

    private long parseLong(int start, int end, int radix, boolean negative) {
        long max = Long.MIN_VALUE / ((long) radix);
        long result = 0;
        int currOffset = start;
        while (currOffset < end) {
            int currOffset2 = currOffset + 1;
            int digit = Character.digit((char) (this.value[this.offset + currOffset] & 255), radix);
            if (digit == -1) {
                throw new NumberFormatException(subSequence(start, end, false).toString());
            } else if (max > result) {
                throw new NumberFormatException(subSequence(start, end, false).toString());
            } else {
                long next = (((long) radix) * result) - ((long) digit);
                if (next > result) {
                    throw new NumberFormatException(subSequence(start, end, false).toString());
                }
                result = next;
                currOffset = currOffset2;
            }
        }
        if (!negative) {
            result = -result;
            if (result < 0) {
                throw new NumberFormatException(subSequence(start, end, false).toString());
            }
        }
        return result;
    }

    public float parseFloat() {
        return parseFloat(0, length());
    }

    public float parseFloat(int start, int end) {
        return Float.parseFloat(toString(start, end));
    }

    public double parseDouble() {
        return parseDouble(0, length());
    }

    public double parseDouble(int start, int end) {
        return Double.parseDouble(toString(start, end));
    }

    public static AsciiString of(CharSequence string) {
        return string.getClass() == AsciiString.class ? (AsciiString) string : new AsciiString(string);
    }

    public static int hashCode(CharSequence value) {
        if (value == null) {
            return 0;
        }
        if (value.getClass() == AsciiString.class) {
            return value.hashCode();
        }
        return PlatformDependent.hashCodeAscii(value);
    }

    public static boolean contains(CharSequence a, CharSequence b) {
        return contains(a, b, DefaultCharEqualityComparator.INSTANCE);
    }

    public static boolean containsIgnoreCase(CharSequence a, CharSequence b) {
        return contains(a, b, AsciiCaseInsensitiveCharEqualityComparator.INSTANCE);
    }

    public static boolean contentEqualsIgnoreCase(CharSequence a, CharSequence b) {
        boolean z = true;
        if (a == null || b == null) {
            if (a != b) {
                z = false;
            }
            return z;
        } else if (a.getClass() == AsciiString.class) {
            return ((AsciiString) a).contentEqualsIgnoreCase(b);
        } else {
            if (b.getClass() == AsciiString.class) {
                return ((AsciiString) b).contentEqualsIgnoreCase(a);
            }
            if (a.length() != b.length()) {
                return false;
            }
            int i = 0;
            int j = 0;
            while (i < a.length()) {
                if (!equalsIgnoreCase(a.charAt(i), b.charAt(j))) {
                    return false;
                }
                i++;
                j++;
            }
            return true;
        }
    }

    public static boolean containsContentEqualsIgnoreCase(Collection<CharSequence> collection, CharSequence value) {
        for (CharSequence v : collection) {
            if (contentEqualsIgnoreCase(value, v)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAllContentEqualsIgnoreCase(Collection<CharSequence> a, Collection<CharSequence> b) {
        for (CharSequence v : b) {
            if (!containsContentEqualsIgnoreCase(a, v)) {
                return false;
            }
        }
        return true;
    }

    public static boolean contentEquals(CharSequence a, CharSequence b) {
        boolean z = true;
        if (a == null || b == null) {
            if (a != b) {
                z = false;
            }
            return z;
        } else if (a.getClass() == AsciiString.class) {
            return ((AsciiString) a).contentEquals(b);
        } else {
            if (b.getClass() == AsciiString.class) {
                return ((AsciiString) b).contentEquals(a);
            }
            if (a.length() != b.length()) {
                return false;
            }
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) != b.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static AsciiString[] toAsciiStringArray(String[] jdkResult) {
        AsciiString[] res = new AsciiString[jdkResult.length];
        for (int i = 0; i < jdkResult.length; i++) {
            res[i] = new AsciiString(jdkResult[i]);
        }
        return res;
    }

    private static boolean contains(CharSequence a, CharSequence b, CharEqualityComparator cmp) {
        if (a == null || b == null || a.length() < b.length()) {
            return false;
        }
        if (b.length() == 0) {
            return true;
        }
        int bStart = 0;
        for (int i = 0; i < a.length(); i++) {
            if (cmp.equals(b.charAt(bStart), a.charAt(i))) {
                bStart++;
                if (bStart == b.length()) {
                    return true;
                }
            } else if (a.length() - i < b.length()) {
                return false;
            } else {
                bStart = 0;
            }
        }
        return false;
    }

    private static boolean regionMatchesCharSequences(CharSequence cs, int csStart, CharSequence string, int start, int length, CharEqualityComparator charEqualityComparator) {
        if (csStart < 0 || length > cs.length() - csStart || start < 0 || length > string.length() - start) {
            return false;
        }
        int csIndex = csStart;
        int csEnd = csIndex + length;
        int stringIndex = start;
        int csIndex2 = csIndex;
        while (csIndex2 < csEnd) {
            csIndex = csIndex2 + 1;
            int stringIndex2 = stringIndex + 1;
            if (!charEqualityComparator.equals(cs.charAt(csIndex2), string.charAt(stringIndex))) {
                return false;
            }
            stringIndex = stringIndex2;
            csIndex2 = csIndex;
        }
        return true;
    }

    public static boolean regionMatches(CharSequence cs, boolean ignoreCase, int csStart, CharSequence string, int start, int length) {
        if (cs == null || string == null) {
            return false;
        }
        if ((cs instanceof String) && (string instanceof String)) {
            return ((String) cs).regionMatches(ignoreCase, csStart, (String) string, start, length);
        } else if (cs instanceof AsciiString) {
            return ((AsciiString) cs).regionMatches(ignoreCase, csStart, string, start, length);
        } else {
            return regionMatchesCharSequences(cs, csStart, string, start, length, ignoreCase ? GeneralCaseInsensitiveCharEqualityComparator.INSTANCE : DefaultCharEqualityComparator.INSTANCE);
        }
    }

    public static boolean regionMatchesAscii(CharSequence cs, boolean ignoreCase, int csStart, CharSequence string, int start, int length) {
        if (cs == null || string == null) {
            return false;
        }
        if (!ignoreCase && (cs instanceof String) && (string instanceof String)) {
            return ((String) cs).regionMatches(false, csStart, (String) string, start, length);
        } else if (cs instanceof AsciiString) {
            return ((AsciiString) cs).regionMatches(ignoreCase, csStart, string, start, length);
        } else {
            return regionMatchesCharSequences(cs, csStart, string, start, length, ignoreCase ? AsciiCaseInsensitiveCharEqualityComparator.INSTANCE : DefaultCharEqualityComparator.INSTANCE);
        }
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int searchStrLen = searchStr.length();
        int endLimit = (str.length() - searchStrLen) + 1;
        if (startPos > endLimit) {
            return -1;
        }
        if (searchStrLen == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (regionMatches(str, true, i, searchStr, 0, searchStrLen)) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOfIgnoreCaseAscii(CharSequence str, CharSequence searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int searchStrLen = searchStr.length();
        int endLimit = (str.length() - searchStrLen) + 1;
        if (startPos > endLimit) {
            return -1;
        }
        if (searchStrLen == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (regionMatchesAscii(str, true, i, searchStr, 0, searchStrLen)) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(CharSequence cs, char searchChar, int start) {
        if (cs instanceof String) {
            return ((String) cs).indexOf(searchChar, start);
        }
        if (cs instanceof AsciiString) {
            return ((AsciiString) cs).indexOf(searchChar, start);
        }
        if (cs == null) {
            return -1;
        }
        int sz = cs.length();
        if (start < 0) {
            start = 0;
        }
        for (int i = start; i < sz; i++) {
            if (cs.charAt(i) == searchChar) {
                return i;
            }
        }
        return -1;
    }

    private static boolean equalsIgnoreCase(byte a, byte b) {
        return a == b || toLowerCase(a) == toLowerCase(b);
    }

    private static boolean equalsIgnoreCase(char a, char b) {
        return a == b || toLowerCase(a) == toLowerCase(b);
    }

    private static byte toLowerCase(byte b) {
        return isUpperCase(b) ? (byte) (b + 32) : b;
    }

    private static char toLowerCase(char c) {
        return isUpperCase(c) ? (char) (c + 32) : c;
    }

    private static byte toUpperCase(byte b) {
        return isLowerCase(b) ? (byte) (b - 32) : b;
    }

    private static boolean isLowerCase(byte value) {
        return value >= (byte) 97 && value <= (byte) 122;
    }

    public static boolean isUpperCase(byte value) {
        return value >= (byte) 65 && value <= (byte) 90;
    }

    public static boolean isUpperCase(char value) {
        return value >= 'A' && value <= 'Z';
    }

    public static byte c2b(char c) {
        if (c > MAX_CHAR_VALUE) {
            c = '?';
        }
        return (byte) c;
    }

    public static char b2c(byte b) {
        return (char) (b & 255);
    }
}
